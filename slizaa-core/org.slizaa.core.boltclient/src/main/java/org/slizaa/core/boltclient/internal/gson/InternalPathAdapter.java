/**
 *
 */
package org.slizaa.core.boltclient.internal.gson;

import java.lang.reflect.Type;

import org.neo4j.driver.v1.types.Path;

import com.google.gson.JsonArray;
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
public class InternalPathAdapter implements JsonSerializer<Path> {

  @Override
  public JsonElement serialize(Path path, Type typeOfSrc, JsonSerializationContext context) {

    // create result
    JsonObject result = new JsonObject();

    // add the nodes
    result.add("nodes", context.serialize(path.nodes()));

    // add the relationships
    result.add("relationships", context.serialize(path.relationships()));

    // add the segments
    JsonArray jsonArray = new JsonArray();
    path.forEach(seg -> jsonArray.add(context.serialize(seg)));
    result.add("segments", jsonArray);

    //
    result.addProperty("__type", "PATH");

    //
    return result;
  }
}
