package org.slizaa.scanner.mvnresolver.itest;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;

import org.junit.Test;
import org.slizaa.core.mvnresolver.MvnResolverServiceFactoryFactory;
import org.slizaa.core.mvnresolver.api.IMvnResolverService;
import org.slizaa.core.mvnresolver.api.IMvnResolverServiceFactory;

public class MvnResolverServiceImplementationTest {

  @Test
  public void testIt() {

    IMvnResolverServiceFactory mvnResolverServiceFactory = MvnResolverServiceFactoryFactory
        .createNewResolverServiceFactory();

    //
    IMvnResolverService impl = mvnResolverServiceFactory.newMvnResolverService().create();

    //
    File[] files = impl.resolve("org.neo4j.test:neo4j-harness:2.3.3");

    //
    assertThat(files).hasSize(75);
  }
}
