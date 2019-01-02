package pl.decerto.mule.internal;

import javax.inject.Inject;
import org.mule.runtime.api.connection.CachedConnectionProvider;
import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.connection.ConnectionProvider;
import org.mule.runtime.api.connection.ConnectionValidationResult;
import org.mule.runtime.api.connection.PoolingConnectionProvider;
import org.mule.runtime.http.api.HttpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class (as it's name implies) provides connection instances and the funcionality to disconnect and validate those
 * connections.
 * <p>
 * All connection related parameters (values required in order to create a connection) must be
 * declared in the connection providers.
 * <p>
 * This particular example is a {@link PoolingConnectionProvider} which declares that connections resolved by this provider
 * will be pooled and reused. There are other implementations like {@link CachedConnectionProvider} which lazily creates and
 * caches connections or simply {@link ConnectionProvider} if you want a new connection each time something requires one.
 */
public class SlackWebHookConnectionProvider implements PoolingConnectionProvider<SlackWebHookConnection> {

	private final Logger LOGGER = LoggerFactory.getLogger(SlackWebHookConnectionProvider.class);

	@Inject // injecting only by fields :(
	private HttpService httpService;

	@Override
	public SlackWebHookConnection connect() throws ConnectionException {
		return new SlackWebHookConnection(httpService);
	}

	@Override
	public void disconnect(SlackWebHookConnection connection) {
		try {
			connection.invalidate();
		} catch (Exception e) {
			LOGGER.error("Error while disconnecting : " + e.getMessage(), e);
		}
	}

	@Override
	public ConnectionValidationResult validate(SlackWebHookConnection connection) {
		return ConnectionValidationResult.success();
	}
}
