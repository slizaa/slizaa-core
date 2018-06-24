/*******************************************************************************
 * Copyright (C) 2011-2017 Gerd Wuetherich (gerd@gerd-wuetherich.de). All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this
 * distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Gerd Wuetherich (gerd@gerd-wuetherich.de) - initial API and implementation
 ******************************************************************************/
package org.slizaa.core.classpathscanner;

import java.util.function.Function;

import org.slizaa.core.classpathscanner.internal.ClasspathScannerFactory;

/**
 * <p>
 * </p>
 *
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ClasspathScannerFactoryBuilder {

  /** - */
  private ClasspathScannerFactory _classpathScannerFactory;

  /**
   * <p>
   * </p>
   *
   * @return
   */
  public static final ClasspathScannerFactoryBuilder newClasspathScannerFactory() {
    return new ClasspathScannerFactoryBuilder();
  }

  /**
   * <p>
   * </p>
   *
   * @param type
   * @param classLoaderProvider
   * @return
   */
  public <T> ClasspathScannerFactoryBuilder registerCodeSourceClassLoaderProvider(Class<T> type,
      Function<T, ClassLoader> classLoaderProvider) {

    //
    this._classpathScannerFactory.registerCodeSourceClassLoaderProvider(type, classLoaderProvider);

    //
    return this;
  }

  /**
   * <p>
   * </p>
   *
   * @return
   */
  public IClasspathScannerFactory create() {
    return this._classpathScannerFactory;
  }

  /**
   * <p>
   * Creates a new instance of type {@link ClasspathScannerFactoryBuilder}.
   * </p>
   */
  private ClasspathScannerFactoryBuilder() {
    this._classpathScannerFactory = new ClasspathScannerFactory();
  }
}
