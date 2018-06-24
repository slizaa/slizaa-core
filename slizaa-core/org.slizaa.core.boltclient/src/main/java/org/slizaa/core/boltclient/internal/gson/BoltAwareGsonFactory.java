package org.slizaa.core.boltclient.internal.gson;

import org.neo4j.driver.internal.InternalNode;
import org.neo4j.driver.internal.InternalPath;
import org.neo4j.driver.internal.InternalRelationship;
import org.neo4j.driver.internal.value.InternalValue;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class BoltAwareGsonFactory {

  public static Gson createGson() {
    return new GsonBuilder().disableHtmlEscaping().registerTypeAdapter(InternalValue.class, new InternalValueAdapter())
        .registerTypeAdapter(InternalNode.class, new InternalNodeAdapter())
        .registerTypeAdapter(InternalRelationship.class, new InternalRelationshipAdapter())
        .registerTypeAdapter(InternalPath.class, new InternalPathAdapter()).create();
  }
}
