package org.slizaa.core.mvnresolver.implementation;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.jboss.shrinkwrap.resolver.api.Resolvers;
import org.jboss.shrinkwrap.resolver.api.maven.ConfigurableMavenResolverSystem;
import org.jboss.shrinkwrap.resolver.api.maven.MavenResolverSystem;
import org.jboss.shrinkwrap.resolver.api.maven.ScopeType;
import org.jboss.shrinkwrap.resolver.api.maven.coordinate.MavenDependencies;
import org.jboss.shrinkwrap.resolver.api.maven.coordinate.MavenDependency;
import org.jboss.shrinkwrap.resolver.api.maven.coordinate.MavenDependencyExclusion;
import org.slizaa.core.mvnresolver.api.IMvnResolverService;

/**
 * <p>
 * https://github.com/shrinkwrap/resolver
 * </p>
 *
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class MvnResolverServiceImplementation implements IMvnResolverService {

	ConfigurableMavenResolverSystem _resolverSystem;

	/**
	 * <p>
	 * </p>
	 */
	public void initialize(ConfigurableMavenResolverSystem resolverSystem) {

		_resolverSystem = resolverSystem;
	}

	@Override
	public IMvnResolverJob newMvnResolverJob() {
		return new MvnResolverJobImplementation(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public File[] resolve(String... coords) {
		return _resolverSystem.resolve(checkNotNull(coords)).withTransitivity().asFile();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public File resolveArtifact(String canonicalForm) {
		return _resolverSystem.resolve(checkNotNull(canonicalForm)).withoutTransitivity().asSingleFile();
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param jobImplementation
	 * @return
	 */
	File[] resolve(MvnResolverJobImplementation job) {

		//
		List<MavenDependency> dependencies = new ArrayList<>();
		List<MavenDependencyExclusion> exclusions = new ArrayList<>();
		
		for (String exclusionPattern : job.getExclusionPatterns()) {
			exclusions.add(MavenDependencies.createExclusion(exclusionPattern));
		}
		
		for (String coord : job.getCoords()) {
			MavenDependency mavenDependency = MavenDependencies.createDependency(coord, ScopeType.COMPILE, false,exclusions.toArray(new MavenDependencyExclusion[0]));
			dependencies.add(mavenDependency);
		}
		
		return _resolverSystem
		  .addDependencies(dependencies).resolve().withTransitivity().asFile();

	}
}
