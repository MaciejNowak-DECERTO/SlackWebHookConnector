package pl.decerto.mule;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import org.junit.Test;
import org.mule.functional.junit4.MuleArtifactFunctionalTestCase;

public class SlackWebHookOperationsTestCase extends MuleArtifactFunctionalTestCase {

	/**
	 * Specifies the mule config xml with the flows that are going to be executed in the tests, this file lives in the test resources.
	 */
	@Override
	protected String getConfigFile() {
		return "test-mule-config.xml";
	}

	@Test
	public void executeSendMessage() throws Exception {
		//given message
		String message = "foo";

		//query params with message
		Map queryParams = ImmutableMap.<String, String>builder()
			.put("msg", message)
			.build();

		//and attributes
		Map<String, Object> attributes = ImmutableMap.<String, Object>builder()
			.put("queryParams", queryParams)
			.build();

		//when running flow with attributes
		String payloadValue = ((String) flowRunner("sample-connector-usageFlow")
			.withAttributes(attributes)
			.run()
			.getMessage()
			.getPayload()
			.getValue());

		//then message is sent
		assertThat(payloadValue, is("Message : [" + message + "] sent with response status : 200"));
	}

}
