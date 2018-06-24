/**
 *
 */
package org.slizaa.core.boltclient.internal;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.concurrent.ExecutorService;

import org.slizaa.core.boltclient.IBoltClient;
import org.slizaa.core.boltclient.IBoltClientFactory;

/**
 * <p>
 * </p>
 *
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 *
 */
public class BoltClientFactoryImpl implements IBoltClientFactory {

  /** - */
  private ExecutorService _executorService;

  /**
   * <p>
   * Creates a new instance of type {@link BoltClientFactoryImpl}.
   * </p>
   *
   * @param executorService
   */
  public BoltClientFactoryImpl(ExecutorService executorService) {
    this._executorService = checkNotNull(executorService);
  }

  @Override
  public IBoltClient createBoltClient(String uri) {
    return new BoltClientImpl(this._executorService, uri, uri, uri);
  }

  @Override
  public IBoltClient createBoltClient(String uri, String name) {
    return new BoltClientImpl(this._executorService, uri, name, uri);
  }

  /**
   * <p>
   * </p>
   *
   * @param uri
   * @param name
   * @param description
   * @return
   */
  @Override
  public IBoltClient createBoltClient(String uri, String name, String description) {
    return new BoltClientImpl(this._executorService, uri, name, description);
  }
}
