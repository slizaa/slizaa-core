/**
 *
 */
package org.slizaa.core.boltclient.internal.gson;

import java.lang.reflect.Type;

import org.neo4j.driver.v1.types.Relationship;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * <p>
 * </p>
 *
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 *
 */
public class InternalRelationshipAdapter implements JsonSerializer<Relationship> {

  @Override
  public JsonElement serialize(Relationship relationship, Type typeOfSrc, JsonSerializationContext context) {

    // create result
    JsonObject result = new JsonObject();

    // add the id
    result.addProperty("id", relationship.id());

    // add the start id
    result.addProperty("start", relationship.startNodeId());

    // add the stop id
    result.addProperty("end", relationship.endNodeId());

    // add the type
    result.addProperty("type", relationship.type());

    // add the properties
    result.add("properties", context.serialize(relationship.asMap()));

    //
    result.addProperty("__type", "RELATIONSHIP");

    //
    return result;
  }
}
