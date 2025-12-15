package spinner.dictionary;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import java.io.PrintWriter;
import java.io.StringWriter;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import java.io.InputStream;

public class DictionaryRequestHandler
        implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private TouroDictionary dictionary;
    private Gson gson;

    public DictionaryRequestHandler() {
        try {
            S3Client s3Client = S3Client.create();

            GetObjectRequest getObjectRequest = GetObjectRequest
                    .builder()
                    .bucket("spinner-dictionary")
                    .key("dictionary.txt")
                    .build();

            InputStream dictionaryStream = s3Client.getObject(getObjectRequest);

            this.dictionary = new TouroDictionary(dictionaryStream);
            this.gson = new Gson();
        } catch (Exception e) {
            System.err.println("Failed to load dictionary from S3 bucket: " + e.getMessage());
            e.printStackTrace();
        }

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
        } catch (Exception e) {
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
