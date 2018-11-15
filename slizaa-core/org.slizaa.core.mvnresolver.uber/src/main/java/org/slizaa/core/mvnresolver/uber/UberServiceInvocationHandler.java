package org.slizaa.core.mvnresolver.uber;

import org.slizaa.core.mvnresolver.api.IMvnResolverServiceFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
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

      _service = _instanceCrator.apply(createTemporaryClassLoader(_proxyType));

    } catch (Exception e) {
      e.printStackTrace();
      new RuntimeException(e);
    }

  }

  /**
   *
   * @return
   * @throws IOException
   */
  private static ClassLoader createTemporaryClassLoader(Class<?> proxyType) throws IOException {

    //
    List<URL> urlList = new ArrayList<>();

    //
    URL codeSource = proxyType.getProtectionDomain().getCodeSource().getLocation();
    InputStream inputStream = codeSource.openStream();

    ZipInputStream zipInputStream = new ZipInputStream(inputStream);

    ZipEntry zipEntry = null;

    while ((zipEntry = zipInputStream.getNextEntry()) != null) {

      //
      if (!zipEntry.isDirectory() && zipEntry.getName().startsWith("libs/")) {

        // Create a temporary file
        Path path = Files.createTempFile(null, ".jar");

        // Delete the file on exit
        path.toFile().deleteOnExit();

        // Copy the content of my jar into the temporary file
        Files.copy(zipInputStream, path, StandardCopyOption.REPLACE_EXISTING);

        // add the url to the url list
        urlList.add(path.toFile().toURI().toURL());
      }
    }

    // create the url class loader
    return new URLClassLoader(urlList.toArray(new URL[0]), proxyType.getClassLoader());
  }

  /**
   *
   * @param <T>
   */
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
