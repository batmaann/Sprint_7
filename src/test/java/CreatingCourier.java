import io.restassured.response.ValidatableResponse;
import org.example.baseUrl.BaseUrl;
import org.example.courier.CourierData;
import org.example.courier.CourierHttp;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CreatingCourier {


    private final CourierHttp courierHttp = new CourierHttp(BaseUrl.BASE_URL);

    @Test
    public void testCreateCourier(){
        CourierData request = new CourierData("dfvу2dd3ауаmdf", "gfg", "sdfsdf");
        ValidatableResponse response = courierHttp.createCourier(request);
        CourierData responseBody = response.extract().body().as(CourierData.class);
        assertThat(response.extract().statusCode()).isEqualTo(201);
        //assertThat(responseBody.getId()).isNotNull();
        //String idValue = responseBody.getId();
    }
    @Test
    public void testAuthCourier(){
        CourierData request = new CourierData("dfvу2dd3ауаmdf", "gfg");
        ValidatableResponse response = courierHttp.authCourier(request);
        CourierData responseBody = response.extract().body().as(CourierData.class);
        assertThat(response.extract().statusCode()).isEqualTo(200);
        //assertThat(responseBody.getId()).isNotNull();
        String id = responseBody.getId();
    }


    @Test
    public void testCreateCourierNoRequiredField(){
        CourierData request = new CourierData("ss", "");
        ValidatableResponse response = courierHttp.createCourier(request);
        CourierData responseBody = response.extract().body().as(CourierData.class);
        assertThat(response.extract().statusCode()).isEqualTo(400);
        //assertThat(responseBody.getId()).isNotNull();
        String idValue = responseBody.getId();
    }

//    @AfterClass
//    @Test
//    public void testDeleteCourier(){
//        CourierData request = new CourierData(id);
//        ValidatableResponse response = courierHttp.deleteCourier(request);
//        CourierData responseBody = response.extract().body().as(CourierData.class);
//        assertThat(response.extract().statusCode()).isEqualTo(200);
//    }

}
