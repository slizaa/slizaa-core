package org.slizaa.core.mvnresolver.implementation;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.File;

import org.jboss.shrinkwrap.resolver.api.Resolvers;
import org.jboss.shrinkwrap.resolver.api.maven.ConfigurableMavenResolverSystem;
import org.jboss.shrinkwrap.resolver.api.maven.repository.MavenRemoteRepositories;
import org.jboss.shrinkwrap.resolver.api.maven.repository.MavenRemoteRepository;
import org.jboss.shrinkwrap.resolver.api.maven.repository.MavenUpdatePolicy;
import org.slizaa.core.mvnresolver.api.IMvnResolverService;
import org.slizaa.core.mvnresolver.api.IMvnResolverServiceFactory;

/**
 * <p>
 * </p>
 *
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class MvnResolverServiceFactoryImplementation implements IMvnResolverServiceFactory {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MvnResolverServiceFactoryBuilder newMvnResolverService() {
		Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
		return new MvnResolverServiceFactoryBuilderImplementation();
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
	 */
	public static class MvnResolverServiceFactoryBuilderImplementation implements MvnResolverServiceFactoryBuilder {

		/** - */
		private ConfigurableMavenResolverSystem _resolverSystem;

		/**
		 * <p>
		 * Creates a new instance of type
		 * {@link MvnResolverServiceFactoryBuilderImplementation}.
		 * </p>
		 */
		public MvnResolverServiceFactoryBuilderImplementation() {
			_resolverSystem = Resolvers.use(ConfigurableMavenResolverSystem.class);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public MvnResolverServiceFactoryBuilder withRemoteRepository(String id, String url) {
			MavenRemoteRepository mavenRemoteRepository = MavenRemoteRepositories.createRemoteRepository("central", "http://repo1.maven.org/maven2", "default");
			mavenRemoteRepository.setUpdatePolicy(MavenUpdatePolicy.UPDATE_POLICY_NEVER);
			_resolverSystem.withRemoteRepo(mavenRemoteRepository);
			return this;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public MvnResolverServiceFactoryBuilder withDefaultRemoteRepository() {
			return withRemoteRepository("central", "http://repo1.maven.org/maven2");
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public IMvnResolverService create() {
			MvnResolverServiceImplementation serviceImplementation = new MvnResolverServiceImplementation();
			serviceImplementation.initialize(_resolverSystem);
			return serviceImplementation;
		}

	}
}
