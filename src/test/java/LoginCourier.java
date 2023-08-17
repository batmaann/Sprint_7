import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import jdk.jfr.Description;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.baseUrl.BaseUrl;
import org.example.courier.CourierData;
import org.example.courier.CourierHttp;
import org.example.examplesData.ExamplesData;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginCourier {

    private final CourierHttp courierHttp = new CourierHttp(BaseUrl.BASE_URL);


    @Test
    @DisplayName("Авторизация")
    @Description("авторизация УЗ курьера с кодом 200")
    public void testAuthCourier() {
        CourierData request = ExamplesData.validCourier();
        ValidatableResponse response = courierHttp.authCourier(request);
        CourierData responseBody = response.extract().body().as(CourierData.class);
        assertThat(response.extract().statusCode()).isEqualTo(200);
        ValidatableResponse response1 = courierHttp.authCourier(request);
        String idValue = response.extract().body().jsonPath().getString("id");
        assertThat(idValue).isNotNull();

    }

    @Test
    @DisplayName("Авторизация без обязательных полей")
    @Description("авторизация если какого-то поля нет или пользователя, запрос возвращает ошибку;")
    public void testAuthCourierNullLogin() {
        CourierData request = new CourierData().withLogin("").withPassword(RandomStringUtils.randomAlphanumeric(8));
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
    @DisplayName("Авторизация без обязательных полей")
    @Description("авторизация если какого-то поля нет или пользователя, запрос возвращает ошибку;")
    public void testAuthCourierPassword() {
        CourierData request = new CourierData().withLogin(RandomStringUtils.randomAlphanumeric(8)).withPassword("");
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


}
