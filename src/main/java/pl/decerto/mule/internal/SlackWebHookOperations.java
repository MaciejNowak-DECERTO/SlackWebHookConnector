package pl.decerto.mule.internal;

import static org.mule.runtime.extension.api.annotation.param.MediaType.ANY;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import org.mule.runtime.extension.api.annotation.param.Config;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.http.api.domain.message.response.HttpResponse;

/**
 * This class is a container for operations, every public method in this class will be taken as an extension operation.
 */
public class SlackWebHookOperations {

	@MediaType(value = ANY, strict = false)
	public String sendMessage(String message, @Connection SlackWebHookConnection connection, @Config BasicConfiguration basicConfiguration) throws IOException, TimeoutException {
		HttpResponse httpResponse = connection.sendMessage(message,basicConfiguration);
		return "Message : [" + message + "] sent with response status : " + httpResponse.getStatusCode();
	}

}
