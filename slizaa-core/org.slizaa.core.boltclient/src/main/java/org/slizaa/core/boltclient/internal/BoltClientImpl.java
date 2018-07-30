/*******************************************************************************
 * Copyright (c) Gerd Wuetherich 2012-2016. All rights reserved. This program and the accompanying materials are made
 * available under the terms of the GNU Public License v3.0 which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 *
 * Contributors: Gerd W�therich (gerd@gerd-wuetherich.de) - initial API and implementation
 ******************************************************************************/
package org.slizaa.core.boltclient.internal;

import static com.google.common.base.Preconditions.checkNotNull;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.function.Consumer;
import java.util.function.Function;

import org.neo4j.driver.v1.Config;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.exceptions.Neo4jException;
import org.neo4j.driver.v1.types.Node;
import org.neo4j.driver.v1.types.Relationship;
import org.slizaa.core.boltclient.IBoltClient;
import org.slizaa.core.boltclient.IQueryResultConsumer;
import org.slizaa.core.boltclient.internal.asynch.StatementCallable;
import org.slizaa.core.boltclient.internal.asynch.StatementResultConsumerCallable;
import org.slizaa.core.boltclient.internal.osgi.ServiceRegistrator;

/**
 * <p>
 * </p>
 *
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class BoltClientImpl implements IBoltClient {

  /** - */
  private final PropertyChangeSupport _propertyChangeSupport = new PropertyChangeSupport(this);

  /** - */
  private final ExecutorService       _executorService;

  /** - */
  private String                      _name;

  /** - */
  private String                      _description;

  /** - */
  private String                      _uri;

  /** - */
  private Driver                      _driver;

  /** - */
  private boolean                     _connected;

  /**
   * <p>
   * Creates a new instance of type {@link BoltClientImpl}.
   * </p>
   *
   * @param uri
   * @param name
   * @param description
   */
  public BoltClientImpl(ExecutorService executorService, String uri, String name, String description) {
    this._executorService = checkNotNull(executorService);
    this._uri = checkNotNull(uri);
    this._name = name;
    this._description = description;
  }

  public void addPropertyChangeListener(PropertyChangeListener listener) {
    this._propertyChangeSupport.addPropertyChangeListener(listener);
  }

  public void removePropertyChangeListener(PropertyChangeListener listener) {
    this._propertyChangeSupport.removePropertyChangeListener(listener);
  }

  @Override
  public String getName() {
    return this._name;
  }

  @Override
  public String getDescription() {
    return this._description;
  }

  @Override
  public boolean isConnected() {
    return this._connected;
  }

  @Override
  public String getUri() {
    return this._uri;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void connect() {

    //
    Config config = Config.build().withoutEncryption().toConfig();
    this._driver = GraphDatabase.driver(getUri(), config);

    // register adapter
    try {
      ServiceRegistrator.registerAsOsgiService(this);
    } catch (Exception e) {
      // ignore
    }

    //
    setConnected(true);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void disconnect() {

    // unregister adapter
    try {
      ServiceRegistrator.unregisterAsOsgiService(this);
    } catch (Exception e) {
      // ignore
    }

    //
    this._driver.close();

    //
    setConnected(false);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Relationship getRelationship(long nodeId) {

    assertConnected();

    try (Session session = this._driver.session()) {
      StatementResult result = session.run(String.format("MATCH ()-[r]->() WHERE id(r) = %s RETURN r ", nodeId));
      return result.single().get("r").asRelationship();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Node getNode(long nodeId) {

    //
    return syncExecCypherQuery(String.format("MATCH (n) WHERE id(n) = %s RETURN n ", nodeId)).single().get("n")
        .asNode();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<String> getRelationshipTypes() {

    //
    return syncExecCypherQuery("CALL db.relationshipTypes").list(r -> r.get("relationshipType").asString());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<String> getNodeLabels() {

    //
    return syncExecCypherQuery("CALL db.labels").list(r -> r.get("label").asString());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<String> getPropertyKeys() {

    //
    return syncExecCypherQuery("CALL db.propertyKeys").list(r -> r.get("propertyKey").asString());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public StatementResult syncExecCypherQuery(String cypherQuery) {

    checkNotNull(cypherQuery);
    assertConnected();

    try (Session session = this._driver.session()) {
      return session.run(cypherQuery);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public StatementResult syncExecCypherQuery(String cypherQuery, Map<String, Object> params) {
    checkNotNull(cypherQuery);
    checkNotNull(params);
    assertConnected();

    try (Session session = this._driver.session()) {
      return session.run(cypherQuery, params);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Future<StatementResult> asyncExecCypherQuery(String cypherQuery) {
    return asyncExecCypherQuery(cypherQuery, (Map<String, Object>) null);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Future<StatementResult> asyncExecCypherQuery(String cypherQuery, Map<String, Object> params) {

    //
    assertConnected();
    checkNotNull(cypherQuery);

    try (Session session = this._driver.session()) {

      // create future task
      FutureTask<StatementResult> futureTask = new FutureTask<StatementResult>(
          new StatementCallable<StatementResult>(this._driver, checkNotNull(cypherQuery), params, result -> result));

      // execute
      this._executorService.execute(futureTask);

      // return the running task
      return futureTask;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <T> Future<T> asyncExecCypherQueryAndTransformResult(String cypherQuery,
      Function<StatementResult, T> function) {
    return this.asyncExecCypherQueryAndTransformResult(cypherQuery, (Map<String, Object>) null, function);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <T> Future<T> asyncExecCypherQueryAndTransformResult(String cypherQuery, Map<String, Object> params,
      Function<StatementResult, T> function) {

    //
    assertConnected();
    checkNotNull(cypherQuery);

    try (Session session = this._driver.session()) {

      // create future task
      FutureTask<T> futureTask = new FutureTask<T>(
          new StatementCallable<T>(this._driver, checkNotNull(cypherQuery), params, function));

      // execute
      this._executorService.execute(futureTask);

      // return the running task
      return futureTask;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Future<Void> asyncExecCypherQuery(String cypherQuery, IQueryResultConsumer consumer) {
    return this.asyncExecCypherQuery(cypherQuery, (Map<String, Object>) null, consumer);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Future<Void> asyncExecCypherQuery(String cypherQuery, Map<String, Object> params,
      IQueryResultConsumer consumer) {

    //
    consumer.handleQueryStarted(cypherQuery);

    //
    Future<Void> future = asyncExecCypherQuery(cypherQuery, result -> {

      //
      try {

        consumer.handleQueryResultReceived(cypherQuery, result);
      } catch (Neo4jException e) {
        consumer.handleError(cypherQuery, result, e);
      }

    });

    //
    return future;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Future<Void> asyncExecCypherQuery(String cypherQuery, Consumer<StatementResult> consumer) {
    return asyncExecCypherQuery(cypherQuery, null, consumer);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Future<Void> asyncExecCypherQuery(String cypherQuery, Map<String, Object> params,
      Consumer<StatementResult> consumer) {

    //
    assertConnected();
    checkNotNull(cypherQuery);

    try (Session session = this._driver.session()) {

      // create future task
      FutureTask<Void> futureTask = new FutureTask<Void>(
          new StatementResultConsumerCallable(this._driver, checkNotNull(cypherQuery), null, consumer, this));

      // execute
      this._executorService.execute(futureTask);

      // return the running task
      return futureTask;
    }
  }

  /**
   * <p>
   * </p>
   *
   * @param newConnected
   */
  protected void setConnected(boolean connected) {
    boolean oldValue = this._connected;
    this._connected = connected;
    this._propertyChangeSupport.firePropertyChange("connected", oldValue, connected);
  }

  /**
   * <p>
   * </p>
   */
  private void assertConnected() {
    if (!isConnected()) {
      throw new RuntimeException();
    }
  }
}
