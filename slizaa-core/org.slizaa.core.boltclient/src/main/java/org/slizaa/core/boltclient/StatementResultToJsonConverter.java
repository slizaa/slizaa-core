/**
 *
 */
package org.slizaa.core.boltclient;

import org.neo4j.driver.v1.StatementResult;
import org.slizaa.core.boltclient.internal.gson.BoltAwareGsonFactory;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

/**
 * <p>
 * </p>
 *
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class StatementResultToJsonConverter {

  /** - */
  private static Gson _gson = BoltAwareGsonFactory.createGson();

  /**
   * <p>
   * </p>
   *
   * @param statementResult
   * @return
   */
  public static JsonArray convertToJsonArray(StatementResult statementResult) {

    //
    JsonArray result = new JsonArray();

    //
    while (statementResult.hasNext()) {
      JsonElement element = _gson.toJsonTree(statementResult.next().asMap());
      result.add(element);
    }

    //
    return result;
  }

  /**
   * <p>
   * </p>
   *
   * @param statementResult
   * @return
   */
  public static String convertToJson(StatementResult statementResult) {

    //
    JsonArray result = new JsonArray();

    //
    while (statementResult.hasNext()) {
      JsonElement element = _gson.toJsonTree(statementResult.next().asMap());
      result.add(element);
    }

    //
    return _gson.toJson(result);
  }

  /**
   * <p>
   * Creates a new instance of type {@link StatementResultToJsonConverter}.
   * </p>
   *
   */
  private StatementResultToJsonConverter() {
    //
  }
}
