package pl.decerto.mule.internal;

import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import org.mule.runtime.api.metadata.MediaType;
import org.mule.runtime.api.tls.TlsContextFactory;
import org.mule.runtime.http.api.HttpConstants;
import org.mule.runtime.http.api.HttpHeaders;
import org.mule.runtime.http.api.HttpService;
import org.mule.runtime.http.api.client.HttpClient;
import org.mule.runtime.http.api.client.HttpClientConfiguration;
import org.mule.runtime.http.api.domain.entity.ByteArrayHttpEntity;
import org.mule.runtime.http.api.domain.message.request.HttpRequest;
import org.mule.runtime.http.api.domain.message.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class represents an extension connection just as example (there is no real connection with anything here c:).
 */
class SlackWebHookConnection {

	private final Logger LOGGER = LoggerFactory.getLogger(SlackWebHookConnection.class);

	private final HttpClient httpClient;

	SlackWebHookConnection(HttpService httpService) {
		httpClient = createClient(httpService);
	}

	HttpResponse sendMessage(String message, BasicConfiguration basicConfiguration) throws IOException, TimeoutException {

		//TODO how to handle exceptions?
		if (message == null) {
			throw new IllegalArgumentException("Message can not be null!");
		}

		String jsonText = formatMessage(message).toString();

		HttpResponse response = httpClient.send(HttpRequest.builder()
			.method(HttpConstants.Method.POST)
			.uri(basicConfiguration.getWebHookUrl())
			.addHeader(HttpHeaders.Names.CONTENT_TYPE, MediaType.APPLICATION_JSON.toRfcString())
			.entity(new ByteArrayHttpEntity(jsonText.getBytes()))
			.build(), basicConfiguration.getResponseTimeout(), true, null);

		LOGGER.debug("Message : {} , to url : {} , response : {} ", jsonText, basicConfiguration.getWebHookUrl(), response);

		return response;
	}

	private JsonObject formatMessage(String message) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("text", message);
		return jsonObject;
	}

	private HttpClient createClient(HttpService httpService) {
		HttpClient client = httpService.getClientFactory()
			.create(new HttpClientConfiguration.Builder()
				.setName("webHookClient")
				.setTlsContextFactory(TlsContextFactory.builder().buildDefault())
				.build()
			);

		//TODO why I need to start client??
		client.start();
		return client;
	}

	void invalidate() {
		httpClient.stop();
	}

}
