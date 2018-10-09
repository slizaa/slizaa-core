package org.slizaa.core.mvnresolver.uber;

import org.xeustechnologies.jcl.JarClassLoader;

import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UberServiceInvocationHandler<T> implements InvocationHandler {

  /**
   * -
   */
  private Class<?> _proxyType;

  /**
   * -
   */
  private T _service;

  /**
   * -
   */
  private InstanceCreator<T> _instanceCrator;

  /**
   * <p>
   * Creates a new instance of type {@link UberServiceInvocationHandler}.
   * </p>
   *
   * @param instanceCrator
   */
  public UberServiceInvocationHandler(Class<?> proxyType, InstanceCreator<T> instanceCrator) {
    _proxyType = checkNotNull(proxyType, "Parameter proxyType must not be null.");
    _instanceCrator = checkNotNull(instanceCrator, "Parameter instanceCrator must not be null.");
  }

  @SuppressWarnings("unchecked")
  public static <T> T createNewResolverService(Class<T> proxyType, InstanceCreator<T> instanceCreator) {

    try {

      //
      UberServiceInvocationHandler<T> invocationHandler = new UberServiceInvocationHandler<>(proxyType,
          instanceCreator);
      invocationHandler.service();

      //
      return (T) Proxy.newProxyInstance(proxyType.getClassLoader(), new Class[] { proxyType }, invocationHandler);

    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  /**
   * <p>
   * </p>
   *
   * @param reference
   * @param errorMessage
   * @return
   */
  private static <T> T checkNotNull(T reference, Object errorMessage) {
    if (reference == null) {
      throw new NullPointerException(String.valueOf(errorMessage));
    }
    return reference;
  }

  /**
   * <p>
   * </p>
   *
   * @param expression
   * @param errorMessage
   */
  private static void checkState(boolean expression, Object errorMessage) {
    if (!expression) {
      throw new IllegalStateException(String.valueOf(errorMessage));
    }
  }

  private static boolean includeSlf4j() {

    //
    try {
      UberServiceInvocationHandler.class.getClassLoader().loadClass("org.slf4j.impl.StaticLoggerBinder");
      return false;
    } catch (ClassNotFoundException e) {
      return true;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    checkState(service() != null, "Service is null!");
    return method.invoke(service(), args);
  }

  /**
   * <p>
   * </p>
   *
   * @return
   */
  private final T service() {

    //
    intitialize();

    //
    return _service;
  }

  /**
   * <p>
   * </p>
   */
  private void intitialize() {

    //
    if (_service != null) {
      return;
    }

    try {
      //
      JarClassLoader jcl = new JarClassLoader();

      //
      URL codeSource = _proxyType.getProtectionDomain().getCodeSource().getLocation();
      try (InputStream inputStream = this.getClass().getProtectionDomain().getCodeSource().getLocation().openStream();
           ZipInputStream zipInputStream = new ZipInputStream(inputStream)) {

        ZipEntry zipEntry = null;
        while ((zipEntry = zipInputStream.getNextEntry()) != null) {
          if (!zipEntry.isDirectory() && zipEntry.getName().startsWith("libs/")) {

            //
            if (zipEntry.getName().contains("slf4j") && !includeSlf4j()) {
              continue;
            }

            String url = "jar:" + codeSource.toExternalForm() + "!/" + zipEntry.getName();
            jcl.add(new URL(url));
          }
        }
      }

      _service = _instanceCrator.apply(jcl);

    } catch (Exception e) {
      e.printStackTrace();
      new RuntimeException(e);
    }

  }

  @FunctionalInterface
  public interface InstanceCreator<T> {

    /**
     * Applies this function to the given argument.
     *
     * @return the function result
     */
    T apply(ClassLoader classLoader) throws Exception;
  }
}
