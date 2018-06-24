/**
 *
 */
package org.slizaa.core.boltclient;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.Executors;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.types.Node;
import org.neo4j.harness.junit.Neo4jRule;

/**
 * <p>
 * </p>
 *
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 *
 */
public class BoltClientTest {

  /** - */
  @ClassRule
  public static Neo4jRule NEO4J_RULE = new Neo4jRule();

  /** - */
  private IBoltClient     _boltClient;

  /**
   * <p>
   * </p>
   */
  @Before
  public void init() {

    //
    IBoltClientFactory boltClientFactory = IBoltClientFactory.newInstance(Executors.newFixedThreadPool(20));
    this._boltClient = boltClientFactory.createBoltClient(NEO4J_RULE.boltURI().toString(), "", "");
    this._boltClient.connect();

    //
    this._boltClient.syncExecCypherQuery("CREATE (n:Person { name: 'Andres', title: 'Developer' })");
  }

  /**
   * <p>
   * </p>
   */
  @After
  public void dispose() {

    //
    this._boltClient.disconnect();
  }

  /**
   * <p>
   * </p>
   *
   * @throws Exception
   */
  @Test
  public void testGetNode() throws Exception {

    Node node = this._boltClient.getNode(0);

    assertThat(node).isNotNull();
  }

  @Test
  public void testGson() throws Exception {

    StatementResult result = this._boltClient.syncExecCypherQuery("MATCH (n) return n");

    //
    assertThat(StatementResultToJsonConverter.convertToJson(result)).isEqualTo(
        "[{\"n\":{\"id\":0,\"labels\":[\"Person\"],\"properties\":{\"name\":\"Andres\",\"title\":\"Developer\"},\"__type\":\"NODE\"}}]");
  }
}
