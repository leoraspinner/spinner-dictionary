package spinner.dictionary;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;

public class DictionaryRequestHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private TouroDictionary dictionary;
    private Gson gson;

    public DictionaryRequestHandler() {
        this.dictionary = new TouroDictionary();
        this.gson = new Gson();
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context) {
        String body = event.getBody();
        DictionaryRequest request = gson.fromJson(body, DictionaryRequest.class);
        String definition = dictionary.lookup(request.getWord());
        DictionaryResponse response = new DictionaryResponse(request.getWord(), definition);
        String responseJson = gson.toJson(response);
        APIGatewayProxyResponseEvent apiResponse = new APIGatewayProxyResponseEvent();

        apiResponse.setStatusCode(200);
        apiResponse.setBody(responseJson);
        return apiResponse;
    }
}
