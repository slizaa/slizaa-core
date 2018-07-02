package org.slizaa.core.boltclient.internal.gson;

import org.neo4j.driver.v1.Value;
import org.neo4j.driver.v1.types.Node;
import org.neo4j.driver.v1.types.Path;
import org.neo4j.driver.v1.types.Relationship;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class BoltAwareGsonFactory {

  public static Gson createGson() {
    return new GsonBuilder().disableHtmlEscaping().registerTypeHierarchyAdapter(Value.class, new InternalValueAdapter())
        .registerTypeHierarchyAdapter(Node.class, new InternalNodeAdapter())
        .registerTypeHierarchyAdapter(Relationship.class, new InternalRelationshipAdapter())
        .registerTypeHierarchyAdapter(Path.class, new InternalPathAdapter()).create();
  }
}
