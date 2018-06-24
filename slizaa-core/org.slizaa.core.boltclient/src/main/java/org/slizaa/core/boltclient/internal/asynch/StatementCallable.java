/*******************************************************************************
 * Copyright (c) Gerd W�therich 2012-2016. All rights reserved. This program and the accompanying materials are made
 * available under the terms of the GNU Public License v3.0 which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 *
 * Contributors: Gerd W�therich (gerd@gerd-wuetherich.de) - initial API and implementation
 ******************************************************************************/
package org.slizaa.core.boltclient.internal.asynch;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.function.Function;

import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;

/**
 * <p>
 * </p>
 *
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class StatementCallable<R> implements Callable<R> {

  private Driver                       _driver;

  /** - */
  private String                       _statement;

  /** - */
  private Function<StatementResult, R> _function;

  /** - */
  private Map<String, Object>          _params;

  /**
   * <p>
   * Creates a new instance of type {@link StatementCallable}.
   * </p>
   *
   * @param driver
   * @param statement
   * @param params
   */
  public StatementCallable(Driver driver, String statement, Map<String, Object> params,
      Function<StatementResult, R> function) {
    this._driver = checkNotNull(driver);
    this._statement = checkNotNull(statement);
    this._function = checkNotNull(function);
    this._params = params;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public R call() throws Exception {

    try (Session session = this._driver.session()) {

      if (this._params == null) {
        StatementResult statementResult = session.run(this._statement);
        return this._function.apply(statementResult);
      }
      //
      else {
        StatementResult statementResult = session.run(this._statement, this._params);
        return this._function.apply(statementResult);
      }
    }
  }
}
