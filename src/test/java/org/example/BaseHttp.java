package org.example;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;


abstract public class BaseHttp {
    public ValidatableResponse doPostRequest ( String baseUrl, Object body) {
        RequestSpecification request = given(baseRequest());
        request.body(body);
        return request.post(baseUrl).then();

    }

    public ValidatableResponse doPostRequest (String baseUrl) {
        return given (baseRequest ()).post(baseUrl).then();
    }

    public ValidatableResponse doGetRequest (String url) {
        return given (baseRequest ()).get (url) .then();
    }

    public ValidatableResponse doDeleteRequest ( String baseUrl, Object body) {
        RequestSpecification request = given(baseRequest());
        request.body(body);
        return request.delete(baseUrl).then();
    }


    private RequestSpecification baseRequest() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .setRelaxedHTTPSValidation()
                .build();
    }
}
