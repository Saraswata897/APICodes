package com.newproject.apitests; // Using your existing API tests package

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.Assert;

import java.io.File; // Import File class

public class FileUploadTest { // New class for File Upload API test

    @Test
    public void verifyFileUploadSuccess() {
        // This line is included as per your request to relax HTTPS validation.
        RestAssured.useRelaxedHTTPSValidation();

        System.out.println("\n--- Verifying File Upload API Success using Multipart ---");

        // Step 2: Store the input file in resource folder
        // Get the file from the classpath (src/test/resources/)
        File fileToUpload = new File(getClass().getClassLoader().getResource("upload_test_file.txt").getFile());

        // Define the base URI for the API
        RestAssured.baseURI = "https://the-internet.herokuapp.com";

        // Step 3: Use multipart and Get the response for the API with POST method
        Response response = RestAssured.given()
                .multiPart("file", fileToUpload) // "file" is the parameter name expected by the API
                .log().all() // Log all request details for debugging
                .post("/upload"); // Specify the upload endpoint

        // Print the response details for verification
        System.out.println("\nResponse Status Code: " + response.getStatusCode());
        System.out.println("Response Body:\n" + response.getBody().asString());

        // Step 4: Verify the success response code
        response.then().assertThat().statusCode(200);
        System.out.println("\nStatus code verification successful! Expected 200, Got " + response.getStatusCode());

        // Optional: Further assertions based on the expected success message in the response body
        // The-internet.herokuapp.com/upload usually redirects to /upload after success
        // and the body contains "File Uploaded!"
        response.then().assertThat().body(org.hamcrest.Matchers.containsString("File Uploaded!"));
        System.out.println("Validated that the response body contains 'File Uploaded!'.");

        System.out.println("\nFile upload API success verification complete!");
    }
}
