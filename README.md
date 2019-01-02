# Slack web hook connector

Very basic connector that send message to provided slack web hook. 
To use connector fill webHookUrl in config params. 





Add this dependency to your application pom.xml

```

<groupId>pl.decerto.mule</groupId>
<artifactId>slack-webhook-connector</artifactId>
<version>0.0.1</version>
<classifier>mule-plugin</classifier>
```



Sample mule flow that exposes http endpoint /slack/webhook:


```
<?xml version="1.0" encoding="UTF-8"?>

<mule xmlns:basic="http://www.mulesoft.org/schema/mule/basic" xmlns:http="http://www.mulesoft.org/schema/mule/http"
	xmlns="http://www.mulesoft.org/schema/mule/core"
	xmlns:doc="http://www.mulesoft.org/schema/mule/documentation" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.mulesoft.org/schema/mule/core http://www.mulesoft.org/schema/mule/core/current/mule.xsd
http://www.mulesoft.org/schema/mule/http http://www.mulesoft.org/schema/mule/http/current/mule-http.xsd
http://www.mulesoft.org/schema/mule/basic http://www.mulesoft.org/schema/mule/basic/current/mule-basic.xsd">
	<http:listener-config name="HTTP_Listener_config" doc:name="HTTP Listener config" doc:id="a4ce53cd-a057-42a4-855b-9883ba7b30bb" basePath="/slack" >
		<http:listener-connection host="0.0.0.0" port="8085" />
	</http:listener-config>
	
	<basic:config name="SlackWebHook_Config" 
	responseTimeout="5000" 
	doc:name="SlackWebHook Config" doc:id="91ae4b28-4f31-4b0f-bfff-3bb9a3928460" 
	configId="10"
	webHookUrl=" ... " />
	
	<flow name="sample-connector-usageFlow" doc:id="c8bab093-92b6-4b13-a76b-c44f2c37c9f8" >
		<http:listener doc:name="Listener" doc:id="b8a4cbab-5b45-44dd-8dff-901a0ae4e5d4" config-ref="HTTP_Listener_config" path="/webhook"/>
		<basic:send-message doc:name="Send message" doc:id="7e9cc766-9d2b-4e3e-993c-145700962405" config-ref="SlackWebHook_Config" message="#[attributes.queryParams.msg]"/>
	</flow>
</mule>

```


Sample usage :

```
curl http://localhost:8085/slack/webhook?msg=foo
```