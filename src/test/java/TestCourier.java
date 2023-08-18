import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import jdk.jfr.Description;
import org.example.baseUrl.BaseUrl;
import org.example.courier.CourierData;
import org.example.courier.CourierHttp;
import org.example.examplesData.ExamplesData;
import org.junit.Test;


import static org.assertj.core.api.Assertions.assertThat;

public class TestCourier {
    private final CourierHttp courierHttp = new CourierHttp(BaseUrl.BASE_URL);
    @Test
    @DisplayName("Создание новой УЗ курьера")
    @Description("Создается новая УЗ курьера с кодом 201")
    public void testCreateCourier() {
        CourierData request = ExamplesData.randomCourier();
        ValidatableResponse response = courierHttp.createCourier(request);
        String responseBody = response.extract().body().asString();
        assertThat(response.extract().statusCode()).isEqualTo(201);
        assertThat(responseBody).contains("{\"ok\":true}");
    }
    @Test
    @DisplayName("Создание УЗ курьера без  поля password")
    @Description("обработка ошибки 400")
    public void testCreateCourierNoRequiredFieldPassword() {

        CourierData request = ExamplesData.CourierNoNameAndFirstName();
        ValidatableResponse response = courierHttp.createCourier(request);
        //CourierData responseBody = response.extract().body().as(CourierData.class);
        assertThat(response.extract().statusCode()).isEqualTo(400);

    }

    @Test
    @DisplayName("Создание УЗ курьера без поля name")
    @Description("обработка ошибки 400")
    public void testCreateCourierNoRequiredFieldLogin() {
        CourierData request = ExamplesData.CourierNoName();
        ValidatableResponse response = courierHttp.createCourier(request);
        //CourierData responseBody = response.extract().body().as(CourierData.class);
        assertThat(response.extract().statusCode()).isEqualTo(400);
    }


    @Test
    @DisplayName("Авторизация без обязательных полей")
    @Description("авторизация если какого-то поля нет или пользователя, запрос возвращает ошибку;")
    public void testAuthCourierNullLogin() {
        CourierData request = ExamplesData.CourierAuthNoFeield();
        ValidatableResponse response = courierHttp.authCourier(request);
        int statusCode = response.extract().statusCode();
        if (statusCode == 400) {
            assertThat(response.extract().body().jsonPath().getString("message"))
                    .isEqualTo("Недостаточно данных для входа");
        } else if (statusCode == 404) {
            assertThat(response.extract().body().jsonPath().getString("message")).isEqualTo("Учетная запись не найдена");
        }
    }
    @Test
    @DisplayName("Создание дубля УЗ")
    @Description("Создание дублей обработка ошибок 201 и 409")
    public void testCreateCourierDouble() {
        CourierData request1 = ExamplesData.randomCourier();
        // Создаем первого курьера
        ValidatableResponse response1 = courierHttp.createCourier(request1);
        assertThat(response1.extract().statusCode()).isEqualTo(201);
        // Попытка создать второго курьера с теми же данными
        ValidatableResponse response2 = courierHttp.createCourier(request1);
        assertThat(response2.extract().statusCode()).isEqualTo(409);
    }


}
