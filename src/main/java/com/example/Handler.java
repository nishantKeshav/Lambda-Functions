package com.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Map;
import java.time.Instant;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

public class Handler implements RequestHandler<APIGatewayV2HTTPEvent, APIGatewayProxyResponseEvent> {

    private final Gson gson = new GsonBuilder().create();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayV2HTTPEvent input, Context context) {

        // --- 1. GET QUERY PARAMS ---
        // (from the URL, like ?myParam=test)
        Map<String, String> queryParams = input.getQueryStringParameters();
        String myParam = (queryParams != null) ? queryParams.get("myParam") : "not_found";

        // --- 2. GET HEADERS ---
        // (like My-Header: 1234)
        Map<String, String> headers = input.getHeaders();
        String myHeader = (headers != null) ? headers.get("my-header") : "not_found";

        // --- 3. GET THE REQUEST BODY ---
        // Get the body as a string
        String requestBodyString = input.getBody();
        // Use Gson to convert the string into our RequestDto object
        RequestDto requestData = gson.fromJson(requestBodyString, RequestDto.class);

        // --- 4. PREPARE YOUR LOGIC ---
        String timestamp = Instant.now().toString();

        String message = "Hello " + requestData.getName() + " Keshav " + " (Age: " + requestData.getAge() + "). "
                + "Your param was: " + myParam + ". "
                + "Your header was: " + myHeader;

        // --- 5. CREATE AND RETURN THE RESPONSE ---
        LambdaResponse responsePojo = new LambdaResponse(message, timestamp);
        String jsonBody = gson.toJson(responsePojo);

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        response.setStatusCode(200);
        response.setHeaders(Map.of("Content-Type", "application/json"));
        response.setBody(jsonBody);

        return response;
    }
}

// mvn dependency:copy-dependencies "-DoutputDirectory=./lambda-layer/java/lib"
// Compress-Archive -Path .\* -DestinationPath ..\my-java-libs-V2.zip
