/*******************************************************************************
 * Copyright (C) 2011-2017 Gerd Wuetherich (gerd@gerd-wuetherich.de).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Gerd Wuetherich (gerd@gerd-wuetherich.de) - initial API and implementation
 ******************************************************************************/
package org.slizaa.core.classpathscanner.internal;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.slizaa.core.classpathscanner.IClasspathScanner;
import org.slizaa.core.classpathscanner.IClasspathScannerFactory;

public class ClasspathScannerFactory implements IClasspathScannerFactory {

  /** - */
  private Map<Class<?>, Function<? extends Object, ClassLoader>> _classloaderProvider;

  /**
   * 
   */
  public ClasspathScannerFactory() {
    _classloaderProvider = new HashMap<>();
  }

  /**
   * <p>
   * </p>
   *
   * @param type
   * @param classLoaderProvider
   * @return
   */
  public <T> ClasspathScannerFactory registerCodeSourceClassLoaderProvider(Class<T> type,
      Function<T, ClassLoader> classLoaderProvider) {

    checkNotNull(type);
    checkNotNull(classLoaderProvider);

    _classloaderProvider.put(type, classLoaderProvider);

    //
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IClasspathScanner createScanner(List<?> elementsToScan) {
    return new ClasspathScanner(this, elementsToScan);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IClasspathScanner createScanner(Object... elementsToScan) {
    return new ClasspathScanner(this, Arrays.asList(elementsToScan));
  }

  /**
   * <p>
   * </p>
   *
   * @param type
   * @param codeSource
   * @return
   */
  ClassLoader classLoader(Object codeSource) {

    //
    for (Class<?> key : _classloaderProvider.keySet()) {
      if (key.isAssignableFrom(codeSource.getClass())) {
        Function<Object, ClassLoader> function = (Function<Object, ClassLoader>) _classloaderProvider.get(key);
        return function.apply(codeSource);
      }
    }

    //
    throw new RuntimeException(String.format("No class loader provider found for %s [registered: %s].",
        codeSource.getClass(), _classloaderProvider.keySet()));
  }
}