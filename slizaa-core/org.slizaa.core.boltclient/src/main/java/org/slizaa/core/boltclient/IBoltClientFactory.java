/**
 *
 */
package org.slizaa.core.boltclient;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.concurrent.ExecutorService;

import org.slizaa.core.boltclient.internal.BoltClientFactoryImpl;

/**
 * <p>
 * </p>
 *
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 *
 */
public interface IBoltClientFactory {

  /**
   * <p>
   * </p>
   *
   * @param uri
   * @return
   */
  IBoltClient createBoltClient(String uri);

  /**
   * <p>
   * </p>
   *
   * @param uri
   * @param name
   * @return
   */
  IBoltClient createBoltClient(String uri, String name);

  /**
   * <p>
   * </p>
   *
   * @param uri
   * @param name
   * @param description
   * @return
   */
  IBoltClient createBoltClient(String uri, String name, String description);

  /**
   * <p>
   * </p>
   *
   * @param executorService
   * @return
   */
  public static IBoltClientFactory newInstance(ExecutorService executorService) {
    return new BoltClientFactoryImpl(checkNotNull(executorService));
  }
}
