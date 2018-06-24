package org.slizaa.core.boltclient;

/**
 * <p>
 * </p>
 *
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IQueryResultConsumerListener {

  /**
   * <p>
   * </p>
   *
   * @param consumer
   */
  void queryResultConsumerAdded(IQueryResultConsumer consumer);

  /**
   * <p>
   * </p>
   *
   * @param consumer
   */
  void queryResultConsumerRemoved(IQueryResultConsumer consumer);
}
