package org.slizaa.core.boltclient;

/**
 * <p>
 * </p>
 *
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IBoltClientListener {

  /**
   * <p>
   * </p>
   *
   * @param adapter
   */
  void boltClientAdded(IBoltClient adapter);

  /**
   * <p>
   * </p>
   *
   * @param adapter
   */
  void boltClientRemoved(IBoltClient adapter);
}
