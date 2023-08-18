import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import jdk.jfr.Description;
import org.example.baseUrl.BaseUrl;
import org.example.order.OrdersHttp;
import org.example.order.OrderData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Parameterized.class)
public class CreateOrder {
    private final OrdersHttp ordersHttp = new OrdersHttp(BaseUrl.BASE_URL);


    private final String firstName;
    private final String lastName;
    private final String address;
    private final int metroStation;
    private final String phone;
    private final int rentTime;
    private final String deliveryDate;
    private final String comment;
    private final String[] color;

    public CreateOrder(String firstName, String lastName, String address, int metroStation, String phone, int rentTime, String deliveryDate, String comment, String[] color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[] data() {
        return new Object[][]{
                {"Naruto", "Uchiha", "Konoha, 142 apt.", 4, "+7 800 355 35 35", 2, "2020-06-06", "text", new String[]{"BLACK"}},
                {"Test2", "test2", "test2, 123 apt.", 5, "+8 800 355 35 35", 3, "2021-06-06", "text2", new String[]{"GREY"}},
                {"Test2", "test2", "test2, 123 apt.", 100, "+8 800 355 35 35", 3, "2021-06-06", "text3", new String[]{"GREY", "BLACK"}},
                {"Test2", "test2", "test2, 123 apt.", 0, "8 800 355 35 35", 3, "2024-06-06", "text4", new String[]{"BLACK", "GREY"}},
                {"Test2", "test2", "test2, 123 apt.", 5, "9 800 355 35 35", 3, "1981-06-06", "text5", new String[]{}}

        };
    }

    @Test
    @DisplayName("Создание заказа")
    @Description("Создание заказа со всеми проверками")
    public void createOrder() {
        OrderData request = new OrderData(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        ValidatableResponse response = ordersHttp.createOrder(request);
        assertThat(response.extract().statusCode()).isEqualTo(201);
        assertThat(response.extract().body().jsonPath().getString("track")).isNotNull();

    }
}
