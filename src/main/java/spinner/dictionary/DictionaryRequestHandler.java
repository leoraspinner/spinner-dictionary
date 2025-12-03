package spinner.dictionary;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;

import java.io.PrintWriter;
import java.io.StringWriter;

public class DictionaryRequestHandler
        implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private TouroDictionary dictionary;
    private Gson gson;

    public DictionaryRequestHandler() {
        this.dictionary = new TouroDictionary();
        this.gson = new Gson();
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context) {
        try {
            //retrieve the body and change json into an object
            String body = event.getBody();
            DictionaryRequest request = gson.fromJson(body, DictionaryRequest.class);

            //get the definition and create a DictionaryResponse
            String definition = dictionary.lookup(request.getWord());
            DictionaryResponse response = new DictionaryResponse(request.getWord(), definition);

            //create the HTTP response with the DictionaryResponse
            String responseJson = gson.toJson(response);
            APIGatewayProxyResponseEvent apiResponse = new APIGatewayProxyResponseEvent();
            apiResponse.setStatusCode(200);
            apiResponse.setBody(responseJson);
            return apiResponse;
        }
        catch (Exception e) {
            //this prints the error to the AWS log file
            e.printStackTrace();

            //this outputs the stack trace to the client
            return toResponseEvent(e);
        }
    }

    private APIGatewayProxyResponseEvent toResponseEvent(Exception e) {
        APIGatewayProxyResponseEvent apiResponse = new APIGatewayProxyResponseEvent();
        apiResponse.setStatusCode(500);
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        apiResponse.setBody(printWriter.toString());
        return apiResponse;
    }
}
