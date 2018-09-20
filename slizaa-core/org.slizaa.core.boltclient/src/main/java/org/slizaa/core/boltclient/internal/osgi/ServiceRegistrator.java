package org.slizaa.core.boltclient.internal.osgi;

import java.util.HashMap;
import java.util.Map;

import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;
import org.slizaa.core.boltclient.IBoltClient;
import org.slizaa.core.boltclient.internal.BoltClientImpl;

/**
 * <p>
 * </p>
 *
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 *
 */
public class ServiceRegistrator {

  /** - */
  private static Map<IBoltClient, ServiceRegistration<IBoltClient>> serviceRegistrationMap = new HashMap<>();

  /**
   * <p>
   * </p>
   *
   * @param boltClient
   */
  public static void registerAsOsgiService(IBoltClient boltClient) {

    try {

      // register adapter
      ServiceRegistration<IBoltClient> serviceRegistration = FrameworkUtil.getBundle(BoltClientImpl.class)
          .getBundleContext().registerService(IBoltClient.class, boltClient, null);

      //
      serviceRegistrationMap.put(boltClient, serviceRegistration);

    } catch (Exception e) {
      // ignore
    }
  }

  /**
   * <p>
   * </p>
   *
   * @param boltClient
   */
  public static void unregisterAsOsgiService(IBoltClient boltClient) {

    try {

      //
      if (serviceRegistrationMap.containsKey(boltClient)) {
        ServiceRegistration<IBoltClient> registration = serviceRegistrationMap.get(boltClient);
        registration.unregister();
      }

    } catch (Exception e) {
      // ignore
    }
  }
}
