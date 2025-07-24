package com.example.apitests; // Assuming this is your package

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.Assert;
import static io.restassured.matcher.RestAssuredMatchers.matchesXsd; // Import matchesXsd

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class ValidateInlineXmlSchema { // New class for validating inline XML schema

    @Test
    public void validateBookingXmlSchemaInline() {
        // This line is included as per your request to relax HTTPS validation.
        RestAssured.useRelaxedHTTPSValidation();

        // Define the XSD schema inline as a String
        String xsdString = "<xs:schema attributeFormDefault=\"unqualified\" elementFormDefault=\"qualified\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">\n" +
                           "  <xs:element name=\"booking\">\n" +
                           "    <xs:complexType>\n" +
                           "      <xs:sequence>\n" +
                           "        <xs:element type=\"xs:string\" name=\"firstname\"/>\n" +
                           "        <xs:element type=\"xs:string\" name=\"lastname\"/>\n" +
                           "        <xs:element type=\"xs:int\" name=\"totalprice\"/>\n" +
                           "        <xs:element type=\"xs:boolean\" name=\"depositpaid\"/>\n" +
                           "        <xs:element name=\"bookingdates\">\n" +
                           "          <xs:complexType>\n" +
                           "            <xs:sequence>\n" +
                           "              <xs:element type=\"xs:date\" name=\"checkin\"/>\n" +
                           "              <xs:element type=\"xs:date\" name=\"checkout\"/>\n" +
                           "            </xs:sequence>\n" +
                           "          </xs:complexType>\n" +
                           "        </xs:element>\n" +
                           "        <xs:element type=\"xs:string\" name=\"additionalneeds\" minOccurs=\"0\"/>\n" + // additionalneeds might be optional
                           "      </xs:sequence>\n" +
                           "    </xs:complexType>\n" +
                           "  </xs:element>\n" +
                           "</xs:schema>";

        // Define the base URI for the API
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        // Perform the GET request to the specific booking ID
        // The endpoint is /booking/:id, and the id is 119
        Response response = RestAssured.given()
                .pathParam("id", 119) // Set the path parameter for the booking ID
                .header("Content-Type", "application/xml") // Set Content-Type header for request
                .header("Accept", "application/xml")       // Set Accept header for response
                .log().all() // Log all request details for debugging
                .get("/booking/{id}"); // Specify the endpoint with path parameter

        // Print the response details for verification
        System.out.println("\nResponse Status Code: " + response.getStatusCode());
        System.out.println("Response Body:\n" + response.getBody().asString());

        // Validate the response against the inline XML Schema Definition (XSD)
        System.out.println("\n--- Validating XML Response against Inline XSD ---");
        response.then().assertThat().statusCode(200); // Ensure success before validation
        response.then().assertThat().body(matchesXsd(xsdString));

        System.out.println("\nXML Schema validation successful using inline XSD!");
    }
}
