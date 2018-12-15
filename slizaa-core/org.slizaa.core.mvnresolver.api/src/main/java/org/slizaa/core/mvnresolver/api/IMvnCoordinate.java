package org.slizaa.core.mvnresolver.api;

public interface IMvnCoordinate {

  String getGroupId();

  String getArtifactId();

  String getPackagingType();

  String getClassifier();

  String getVersion();
}
