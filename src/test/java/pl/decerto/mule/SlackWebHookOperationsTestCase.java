package pl.decerto.mule;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

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
		String payloadValue = ((String) flowRunner("sample-connector-usageFlow").run()
			.getMessage()
			.getPayload()
			.getValue());
		assertThat(payloadValue, is("Message : [foo] sent with response status : 200"));
	}

}
