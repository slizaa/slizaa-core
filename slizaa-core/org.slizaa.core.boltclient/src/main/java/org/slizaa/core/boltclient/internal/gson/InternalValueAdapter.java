/**
 *
 */
package org.slizaa.core.boltclient.internal.gson;

import java.lang.reflect.Type;
import java.util.Base64;

import org.neo4j.driver.v1.Value;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * <p>
 * </p>
 *
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 *
 */
public class InternalValueAdapter implements JsonSerializer<Value> {

  @Override
  public JsonElement serialize(Value value, Type typeOfSrc, JsonSerializationContext context) {

    // (ANY, ) BOOLEAN, BYTES, STRING, NUMBER, INTEGER, FLOAT, LIST, MAP, NODE, RELATIONSHIP, PATH, NULL
    switch (value.type().name()) {
    case "BOOLEAN": {
      return new JsonPrimitive(value.asBoolean());
    }
    case "BYTES": {
      String base64String = Base64.getEncoder().encodeToString(value.asByteArray());
      return new JsonPrimitive(base64String);
    }
    case "STRING": {
      return new JsonPrimitive(value.asString());
    }
    case "NUMBER": {
      return new JsonPrimitive(value.asNumber());
    }
    case "INTEGER": {
      return new JsonPrimitive(value.asNumber());
    }
    case "FLOAT": {
      return new JsonPrimitive(value.asNumber());
    }
    case "LIST": {
      JsonArray jsonArray = new JsonArray();
      value.asList().forEach(o -> jsonArray.add(context.serialize(o)));
      return jsonArray;
    }
    case "MAP": {
      return context.serialize(value.asNode());
    }
    case "NODE": {
      return context.serialize(value.asNode());
    }
    case "RELATIONSHIP": {
      return context.serialize(value.asRelationship());
    }
    case "PATH": {
      return context.serialize(value.asPath());
    }
    case "NULL": {
      return JsonNull.INSTANCE;
    }
    default: {
      // return result
      return new JsonObject();
    }
    }
  }
}
