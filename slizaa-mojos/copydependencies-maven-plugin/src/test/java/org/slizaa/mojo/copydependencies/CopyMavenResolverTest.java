package org.slizaa.mojo.copydependencies;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.util.List;

import org.junit.Test;

/**
 */
public class CopyMavenResolverTest extends AbstractCopyDependencyTest {

  /**
   * <p>
   * Creates a new instance of type {@link CopyMavenResolverTest}.
   * </p>
   */
  public CopyMavenResolverTest() {
    super("copy-maven-resolver");
  }

  @Test
  public void testCopyDependencyMojo() throws Exception {

    //
    CopyDependenciesMojo mojo = (CopyDependenciesMojo) super.findCopyDependencyMojo("copyDependencies");
    mojo.execute();

    List<File> copiedFiles = getCopiedFiles();
    assertThat(copiedFiles).hasSize(55);
  }
}