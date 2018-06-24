/**
 *
 */
package org.slizaa.core.boltclient.internal.gson;

import java.lang.reflect.Type;

import org.neo4j.driver.internal.InternalNode;

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
public class InternalNodeAdapter implements JsonSerializer<InternalNode> {

  @Override
  public JsonElement serialize(InternalNode node, Type typeOfSrc, JsonSerializationContext context) {

    // create result
    JsonObject result = new JsonObject();

    // add the id
    result.addProperty("id", node.id());

    // add the labels
    result.add("labels", context.serialize(node.labels()));

    // add the properties
    result.add("properties", context.serialize(node.asMap()));

    //
    result.addProperty("__type", "NODE");

    //
    return result;
  }
}
