package org.slizaa.core.mvnresolver.implementation;

import org.jboss.shrinkwrap.resolver.api.maven.coordinate.MavenCoordinate;
import org.slizaa.core.mvnresolver.api.IMvnCoordinate;

import static com.google.common.base.Preconditions.checkNotNull;

public class MvnCoordinateImplementation implements IMvnCoordinate {

  /** - */
  private MavenCoordinate _mavenCoordinate;

  /**
   *
   * @param mavenCoordinate
   */
  public MvnCoordinateImplementation(MavenCoordinate mavenCoordinate) {
    this._mavenCoordinate = checkNotNull(mavenCoordinate);
  }

  /**
   *
   * @return
   */
  @Override
  public String getGroupId() {
    return _mavenCoordinate.getGroupId();
  }

  /**
   *
   * @return
   */
  @Override
  public String getArtifactId() {
    return _mavenCoordinate.getArtifactId();
  }

  /**
   *
   * @return
   */
  @Override
  public String getPackagingType() {
    return _mavenCoordinate.getPackaging().toString();
  }

  /**
   *
   * @return
   */
  @Override
  public String getClassifier() {
    return _mavenCoordinate.getClassifier();
  }

  /**
   *
   * @return
   */
  @Override
  public String getVersion() {
    return _mavenCoordinate.getVersion();
  }
}
