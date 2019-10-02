
@Slf4j
public class FooClient extends WebServiceGatewaySupport {

    public void getTemplateNames() {
        GetTemplateNames request = new GetTemplateNames();

        GetTemplateNamesResponse response = (GetTemplateNamesResponse) getWebServiceTemplate().marshalSendAndReceive(request);
        log.info("Response: " + response);
    }

}
