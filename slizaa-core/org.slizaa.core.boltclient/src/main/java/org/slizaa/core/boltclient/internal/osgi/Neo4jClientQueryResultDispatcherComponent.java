package org.slizaa.core.boltclient.internal.osgi;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;
import org.slizaa.core.boltclient.IBoltClient;
import org.slizaa.core.boltclient.IBoltClientListener;
import org.slizaa.core.boltclient.IQueryResultConsumer;
import org.slizaa.core.boltclient.IQueryResultConsumerListener;

@Component
public class Neo4jClientQueryResultDispatcherComponent {

  /** - */
  private IBoltClient                        _currentBoltClient;

  /** - */
  private List<IQueryResultConsumer>         _queryResultConsumers      = new CopyOnWriteArrayList<>();

  /** - */
  private List<IBoltClientListener>          _neo4jClientListeners      = new CopyOnWriteArrayList<>();

  /** - */
  private List<IQueryResultConsumerListener> _queryResultConsumerAwares = new CopyOnWriteArrayList<>();

  /**
   * <p>
   * </p>
   */
  @Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
  public void addGraphDatabaseClientAdapterConsumer(IBoltClientListener consumer) {

    //
    if (this._currentBoltClient != null) {
      consumer.boltClientAdded(this._currentBoltClient);
    }

    //
    this._neo4jClientListeners.add(consumer);
  }

  /**
   * <p>
   * </p>
   */
  public void removeGraphDatabaseClientAdapterConsumer(IBoltClientListener consumer) {

    //
    this._neo4jClientListeners.remove(consumer);

    //
    if (this._currentBoltClient != null) {
      consumer.boltClientRemoved(this._currentBoltClient);
    }
  }

  /**
   * <p>
   * </p>
   *
   * @param clientAdapter
   */
  @Reference(cardinality = ReferenceCardinality.OPTIONAL, policy = ReferencePolicy.DYNAMIC, policyOption = ReferencePolicyOption.GREEDY)
  public void setNeo4jClient(IBoltClient client) {

    //
    this._currentBoltClient = client;

    //
    for (IBoltClientListener consumer : this._neo4jClientListeners) {
      consumer.boltClientAdded(this._currentBoltClient);
    }
  }

  /**
   * <p>
   * </p>
   *
   * @param client
   */
  public void unsetNeo4jClient(IBoltClient client) {

    //
    for (IBoltClientListener consumer : this._neo4jClientListeners) {
      consumer.boltClientRemoved(this._currentBoltClient);
    }

    //
    this._currentBoltClient = null;
  }

  /**
   * <p>
   * </p>
   *
   * @param consumerAware
   */
  @Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
  public void addQueryResultConsumerListener(IQueryResultConsumerListener consumerAware) {

    //
    for (IQueryResultConsumer resultConsumer : this._queryResultConsumers) {
      consumerAware.queryResultConsumerAdded(resultConsumer);
    }

    //
    this._queryResultConsumerAwares.add(consumerAware);
  }

  /**
   * <p>
   * </p>
   *
   * @param consumerAware
   */
  public void removeQueryResultConsumerListener(IQueryResultConsumerListener consumerAware) {

    //
    for (IQueryResultConsumer resultConsumer : this._queryResultConsumers) {
      consumerAware.queryResultConsumerRemoved(resultConsumer);
    }

    //
    this._queryResultConsumerAwares.remove(consumerAware);
  }

  /**
   * <p>
   * </p>
   *
   * @param queryResultConsumer
   */
  @Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
  public void addQueryResultConsumer(IQueryResultConsumer queryResultConsumer) {

    //
    for (IQueryResultConsumerListener aware : this._queryResultConsumerAwares) {
      aware.queryResultConsumerAdded(queryResultConsumer);
    }

    //
    this._queryResultConsumers.add(queryResultConsumer);
  }

  /**
   * <p>
   * </p>
   */
  public void removeQueryResultConsumer(IQueryResultConsumer queryResultConsumer) {

    //
    this._queryResultConsumers.remove(queryResultConsumer);

    //
    for (IQueryResultConsumerListener aware : this._queryResultConsumerAwares) {
      aware.queryResultConsumerRemoved(queryResultConsumer);
    }
  }
}
