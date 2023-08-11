package org.example.courier;

import io.restassured.response.ValidatableResponse;
import org.example.BaseHttp;

import static org.example.baseUrl.BaseUrl.*;


public class CourierHttp extends BaseHttp {

    private final String url;


    public CourierHttp(String baseurl){
        super();
        url = baseurl;
    }

    public ValidatableResponse createCourier(CourierData courierData){
        return doPostRequest(url + CREATE_COURIER, courierData);

    }
    public ValidatableResponse authCourier(CourierData courierData){
        return doPostRequest(url + AUTH_COURIER, courierData);
    }

    public ValidatableResponse deleteCourier(CourierData courierData){
        return doDeleteRequest(url + DELETE_COURIER + courierData.getId() , courierData);
    }







}
