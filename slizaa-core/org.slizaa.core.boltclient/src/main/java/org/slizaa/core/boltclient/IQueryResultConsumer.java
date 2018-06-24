package org.slizaa.core.boltclient;

import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.exceptions.Neo4jException;

/**
 * <p>
 * </p>
 *
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IQueryResultConsumer {

  /**
   * <p>
   * </p>
   *
   * @param cypherQuery
   * @return
   */
  boolean canConsume(String cypherQuery);

  /**
   * <p>
   * </p>
   *
   * @param cypherQuery
   */
  void handleQueryStarted(String cypherQuery);

  /**
   * <p>
   * </p>
   *
   * @param cypherQuery
   * @param result
   */
  void handleQueryResultReceived(String cypherQuery, StatementResult result);

  /**
   * <p>
   * </p>
   *
   * @param cypherQuery
   * @param result
   * @param exception
   */
  void handleError(String cypherQuery, StatementResult result, Neo4jException exception);
}
