package org.slizaa.core.mvnresolver.implementation;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.slizaa.core.mvnresolver.api.IMvnResolverService;
import org.slizaa.core.mvnresolver.implementation.MvnResolverServiceFactoryImplementation;

/**
 * <p>
 * </p>
 *
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class MvnResolverServiceImplementationTest {

  /** - */
  private IMvnResolverService _mvnResolverService;

  /**
   * <p>
   * </p>
   */
  @Before
  public void init() {

    // create the resolver service
    this._mvnResolverService = new MvnResolverServiceFactoryImplementation().newMvnResolverService().create();
  }

  /**
   * <p>
   * </p>
   */
  @Test
  public void test_1() {

    //
    File[] files = this._mvnResolverService.resolve("org.neo4j.test:neo4j-harness:2.3.3");
    List<String> names = Arrays.asList(files).stream().map(file -> file.getName()).collect(Collectors.toList());

    //
    assertThat(files).hasSize(75);
    assertThat(names).contains("neo4j-harness-2.3.3.jar");
    assertThat(names).contains("neo4j-2.3.3.jar");
    assertThat(names).contains("neo4j-kernel-2.3.3.jar");
    assertThat(names).contains("neo4j-lucene-index-2.3.3.jar");
    assertThat(names).contains("lucene-core-3.6.2.jar");
    assertThat(names).contains("neo4j-graph-algo-2.3.3.jar");
    assertThat(names).contains("neo4j-udc-2.3.3.jar");
    assertThat(names).contains("neo4j-graph-matching-2.3.3.jar");
    assertThat(names).contains("neo4j-cypher-2.3.3.jar");
    assertThat(names).contains("scala-library-2.11.7.jar");
    assertThat(names).contains("scala-reflect-2.11.7.jar");
    assertThat(names).contains("scala-parser-combinators_2.11-1.0.4.jar");
    assertThat(names).contains("neo4j-codegen-2.3.3.jar");
    assertThat(names).contains("asm-5.0.2.jar");
    assertThat(names).contains("neo4j-cypher-compiler-1.9_2.11-2.0.5.jar");
    assertThat(names).contains("neo4j-cypher-compiler-2.2_2.11-2.2.6.jar");
    assertThat(names).contains("neo4j-cypher-compiler-2.3-2.3.3.jar");
    assertThat(names).contains("neo4j-cypher-frontend-2.3-2.3.3.jar");
    assertThat(names).contains("parboiled-scala_2.11-1.1.7.jar");
    assertThat(names).contains("parboiled-core-1.1.7.jar");
    assertThat(names).contains("opencsv-2.3.jar");
    assertThat(names).contains("concurrentlinkedhashmap-lru-1.4.2.jar");
    assertThat(names).contains("neo4j-jmx-2.3.3.jar");
    assertThat(names).contains("neo4j-consistency-check-2.3.3.jar");
    assertThat(names).contains("neo4j-consistency-check-legacy-2.3.3.jar");
    assertThat(names).contains("neo4j-server-2.3.3.jar");
    assertThat(names).contains("neo4j-2.3.3.pom");
    assertThat(names).contains("server-api-2.3.3.jar");
    assertThat(names).contains("jsr311-api-1.1.2.r612.jar");
    assertThat(names).contains("neo4j-browser-2.3.3.jar");
    assertThat(names).contains("neo4j-shell-2.3.3.jar");
    assertThat(names).contains("jline-2.12.jar");
    assertThat(names).contains("logback-classic-1.1.2.jar");
    assertThat(names).contains("logback-core-1.1.2.jar");
    assertThat(names).contains("slf4j-api-1.7.6.jar");
    assertThat(names).contains("logback-access-1.1.2.jar");
    assertThat(names).contains("jetty-server-9.2.9.v20150224.jar");
    assertThat(names).contains("javax.servlet-api-3.1.0.jar");
    assertThat(names).contains("jetty-http-9.2.9.v20150224.jar");
    assertThat(names).contains("jetty-util-9.2.9.v20150224.jar");
    assertThat(names).contains("jetty-io-9.2.9.v20150224.jar");
    assertThat(names).contains("jetty-webapp-9.2.9.v20150224.jar");
    assertThat(names).contains("jetty-xml-9.2.9.v20150224.jar");
    assertThat(names).contains("jetty-servlet-9.2.9.v20150224.jar");
    assertThat(names).contains("jetty-security-9.2.9.v20150224.jar");
    assertThat(names).contains("jersey-server-1.19.jar");
    assertThat(names).contains("jersey-servlet-1.19.jar");
    assertThat(names).contains("commons-configuration-1.10.jar");
    assertThat(names).contains("commons-lang-2.6.jar");
    assertThat(names).contains("commons-logging-1.1.1.jar");
    assertThat(names).contains("netty-all-4.0.28.Final.jar");
    assertThat(names).contains("commons-digester-2.1.jar");
    assertThat(names).contains("commons-beanutils-1.8.3.jar");
    assertThat(names).contains("commons-io-2.4.jar");
    assertThat(names).contains("jackson-jaxrs-1.9.13.jar");
    assertThat(names).contains("jackson-core-asl-1.9.13.jar");
    assertThat(names).contains("jackson-mapper-asl-1.9.13.jar");
    assertThat(names).contains("rrd4j-2.2.jar");
    assertThat(names).contains("rhino-1.7R4.jar");
    assertThat(names).contains("bcprov-jdk15on-1.52.jar");
    assertThat(names).contains("bcpkix-jdk15on-1.52.jar");
    assertThat(names).contains("jersey-multipart-1.19.jar");
    assertThat(names).contains("mimepull-1.9.3.jar");
    assertThat(names).contains("neo4j-kernel-2.3.3-tests.jar");
    assertThat(names).contains("neo4j-primitive-collections-2.3.3.jar");
    assertThat(names).contains("neo4j-function-2.3.3.jar");
    assertThat(names).contains("neo4j-io-2.3.3.jar");
    assertThat(names).contains("neo4j-csv-2.3.3.jar");
    assertThat(names).contains("neo4j-logging-2.3.3.jar");
    assertThat(names).contains("neo4j-io-2.3.3-tests.jar");
    assertThat(names).contains("neo4j-unsafe-2.3.3.jar");
    assertThat(names).contains("commons-lang3-3.3.2.jar");
    assertThat(names).contains("jersey-client-1.19.jar");
    assertThat(names).contains("jersey-core-1.19.jar");
    assertThat(names).contains("neo4j-server-2.3.3-tests.jar");
  }

  @Test
  public void test_2() {

    //
    File[] files = this._mvnResolverService.resolve("net.bytebuddy:byte-buddy:jar:1.8.5",
        "org.mockito:mockito-core:jar:2.18.3");
    List<String> names = Arrays.asList(files).stream().map(file -> file.getName()).collect(Collectors.toList());

    //
    assertThat(names).hasSize(4);
    assertThat(names).contains("byte-buddy-1.8.5.jar");
    assertThat(names).contains("mockito-core-2.18.3.jar");
    assertThat(names).contains("byte-buddy-agent-1.8.5.jar");
    assertThat(names).contains("objenesis-2.6.jar");

  }

  @Test
  public void test_3() {

    //
    File[] files = this._mvnResolverService.newMvnResolverJob()
        .withDependencies("net.bytebuddy:byte-buddy:jar:1.8.5", "org.mockito:mockito-core:jar:2.18.3").resolve();
    List<String> names = Arrays.asList(files).stream().map(file -> file.getName()).collect(Collectors.toList());

    //
    assertThat(names).hasSize(4);
    assertThat(names).contains("byte-buddy-1.8.5.jar");
    assertThat(names).contains("mockito-core-2.18.3.jar");
    assertThat(names).contains("byte-buddy-agent-1.8.5.jar");
    assertThat(names).contains("objenesis-2.6.jar");
  }

  @Test
  public void test_4() {

    //
    File[] files = this._mvnResolverService.newMvnResolverJob()
        .withDependencies("net.bytebuddy:byte-buddy:jar:1.8.5", "org.mockito:mockito-core:jar:2.18.3")
        .withExclusionPattern("*:byte-buddy-*").resolve();
    List<String> names = Arrays.asList(files).stream().map(file -> file.getName()).collect(Collectors.toList());

    //
    assertThat(names).hasSize(3);
    assertThat(names).contains("byte-buddy-1.8.5.jar");
    assertThat(names).contains("mockito-core-2.18.3.jar");
    assertThat(names).contains("objenesis-2.6.jar");

  }
}
