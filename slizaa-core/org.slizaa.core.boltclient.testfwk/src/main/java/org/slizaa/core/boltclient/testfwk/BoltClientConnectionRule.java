package org.slizaa.core.boltclient.testfwk;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.concurrent.Executors;

import org.junit.rules.ExternalResource;
import org.slizaa.core.boltclient.IBoltClient;
import org.slizaa.core.boltclient.IBoltClientFactory;

/**
 * <p>
 * </p>
 *
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class BoltClientConnectionRule extends ExternalResource {

  /** - */
  private IBoltClient _boltClient;

  /** - */
  private String      _host;

  /** - */
  private int         _port;

  /**
   * <p>
   * Creates a new instance of type {@link BoltClientConnectionRule}.
   * </p>
   *
   */
  public BoltClientConnectionRule() {
    this("localhost", 5001);
  }

  /**
   * <p>
   * Creates a new instance of type {@link BoltClientConnectionRule}.
   * </p>
   *
   * @param host
   * @param port
   */
  public BoltClientConnectionRule(String host, int port) {
    this._host = checkNotNull(host);
    this._port = port;
  }

  /**
   * <p>
   * </p>
   *
   * @return the neo4JRemoteRepository
   */
  public IBoltClient getBoltClient() {
    return this._boltClient;
  }

  @Override
  protected void before() throws Throwable {
    this._boltClient = IBoltClientFactory.newInstance(Executors.newFixedThreadPool(5))
        .createBoltClient(String.format("bolt://%s:%s", this._host, this._port));
    this._boltClient.connect();
  }

  @Override
  protected void after() {

    try {
      this._boltClient.disconnect();
    } catch (Exception e) {
      //
    }
  }
}
