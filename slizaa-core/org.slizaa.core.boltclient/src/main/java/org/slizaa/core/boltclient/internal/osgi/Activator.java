package org.slizaa.core.boltclient.internal.osgi;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * <p>
 * </p>
 *
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class Activator implements BundleActivator {

  /** - */
  private static ExecutorService _executor;

  /**
   * <p>
   * </p>
   *
   * @return
   */
  public static ExecutorService getExecutor() {

    //
    initializeExecutor();

    return _executor;
  }

  @Override
  public void start(BundleContext context) throws Exception {

    //
    initializeExecutor();
  }

  @Override
  public void stop(BundleContext context) throws Exception {

    //
    _executor.shutdown();

    //
    try {
      _executor.awaitTermination(20, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /**
   * <p>
   * </p>
   */
  private static void initializeExecutor() {
    if (_executor == null) {
      _executor = Executors.newFixedThreadPool(20);
    }
  }
}
