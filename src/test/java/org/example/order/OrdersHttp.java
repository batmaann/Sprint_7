package org.example.order;

import io.restassured.response.ValidatableResponse;
import org.example.BaseHttp;

import java.util.Map;

import static org.example.baseUrl.BaseUrl.*;

public class OrdersHttp extends BaseHttp {
    private final String url;

    public OrdersHttp(String baseurl){
        super();
        url = baseurl;
    }


    public ValidatableResponse createOrder(OrderData orderData){
        return doPostRequest(url + ORDERS, orderData);

    }

    public ValidatableResponse getOrders(Map<String, String> queryParams) {
        return doGetRequest(url + ORDERS, queryParams);
    }

    public ValidatableResponse cancelOrder(OrderData orderData){
        return doPostRequest(url + ORDERS, orderData);

    }

}
