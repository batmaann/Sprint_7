import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import jdk.jfr.Description;
import org.example.baseUrl.BaseUrl;
import org.example.courier.CourierData;
import org.example.courier.CourierHttp;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;

import static org.hamcrest.Matchers.*;

import static org.assertj.core.api.Assertions.assertThat;

public class CreatingCourier {
    String login = "gl";
    String password = "kjjjklknу";
    String firstName = "rcvbh";

    private final CourierHttp courierHttp = new CourierHttp(BaseUrl.BASE_URL);

    @Test
    @DisplayName("Создание новой УЗ курьера")
    @Description("Создается новая УЗ курьера с кодом 201")
    public void testCreateCourier() {
        CourierData request = new CourierData(login, password, firstName);
        ValidatableResponse response = courierHttp.createCourier(request);
        //CourierData responseBody = response.extract().body().as(CourierData.class);
        String responseBody = response.extract().body().asString();
        assertThat(response.extract().statusCode()).isEqualTo(201);
        assertThat(responseBody).contains("{\"ok\":true}");
    }



    @Test
    @DisplayName("Авторизация без обязательных полей")
    @Description("авторизация если какого-то поля нет или пользователя, запрос возвращает ошибку;")
    public void testAuthCourierNullLogin() {
        CourierData request = new CourierData("", "");
        ValidatableResponse response = courierHttp.authCourier(request);
        int statusCode = response.extract().statusCode();
        if (statusCode == 400) {
            CourierData responseBody = response.extract().body().as(CourierData.class);
            assertThat(response.extract().body().jsonPath().getString("message"))
                    .isEqualTo("Недостаточно данных для входа");
        } else if (statusCode == 404) {
            assertThat(response.extract().body().jsonPath().getString("message")).isEqualTo("Учетная запись не найдена");
        }
    }


    @Test
    @DisplayName("Создание УЗ курьера без одного поля")
    @Description("обработка ошибки 400")
    public void testCreateCourierNoRequiredField() {
        CourierData request = new CourierData("ss", "");
        ValidatableResponse response = courierHttp.createCourier(request);
        CourierData responseBody = response.extract().body().as(CourierData.class);
        assertThat(response.extract().statusCode()).isEqualTo(400);
        //assertThat(responseBody.getId()).isNotNull();
        String idValue = responseBody.getId();
    }

    @Test
    @DisplayName("Создание дубля УЗ")
    @Description("Создание дублей обработка ошибок 201 и 409")
    public void testCreateCourierDouble() {
        CourierData request1 = new CourierData(login, password);
        CourierData request2 = new CourierData(login, password);
        // Создаем первого курьера
        ValidatableResponse response1 = courierHttp.createCourier(request1);
        assertThat(response1.extract().statusCode()).isEqualTo(201);
        // Попытка создать второго курьера с теми же данными
        ValidatableResponse response2 = courierHttp.createCourier(request1);
        assertThat(response2.extract().statusCode()).isEqualTo(409);
    }


//    @AfterClass
//    @Test
//    public void testDeleteCourier(){
//        CourierData request = new CourierData();
//        ValidatableResponse responseAuth = courierHttp.authCourier(request);
//
//
//
//
////        CourierData responseBody = response.extract().body().as(CourierData.class);
////        assertThat(response.extract().statusCode()).isEqualTo(200);
//    }

}
