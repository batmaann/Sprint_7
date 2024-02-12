import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import jdk.jfr.Description;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.baseUrl.BaseUrl;
import org.example.order.OrdersHttp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.example.baseUrl.BaseUrl.ORDERS;

@RunWith(Parameterized.class)
public class GetOrder {
    private final OrdersHttp ordersHttp = new OrdersHttp(BaseUrl.BASE_URL);
    static Random random = new Random();

    private int courierId;
    private String nearestStation;
    private int limit;
    private int page;

    public GetOrder(int courierId, String nearestStation, int limit, int page) {
        this.courierId = courierId;
        this.nearestStation = nearestStation;
        this.limit = limit;
        this.page = page;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {random.nextInt(300 ), "{ : [\"1\", \"2\"] }", random.nextInt(300 ), random.nextInt(300 )},
                { random.nextInt(300 ),"{ : [\"2\", \"3\"] }", random.nextInt(300 ), random.nextInt(300 )},
                {random.nextInt(300 ), "{ : [\"3\", \"4\"] }", random.nextInt(300 ), random.nextInt(300 )},
                {random.nextInt(300 ), "{ : [\"4\", \"5\"] }", random.nextInt(300 ), random.nextInt(300 )},
                {random.nextInt(300 ), "{ : [\"5\", \"6\"] }", random.nextInt(300 ), random.nextInt(300 )}
        });
    }

    @Test
    @DisplayName("получение заказа ")
    @Description("получение заказа методом get  и обработка ошибок")
    public void testGetOrdersWithDifferentParameters() {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("courierId", String.valueOf(courierId));
        queryParams.put("nearestStation", nearestStation);
        queryParams.put("limit", String.valueOf(limit));
        queryParams.put("page", String.valueOf(page));
        ValidatableResponse response = ordersHttp.getOrders(queryParams);
        int statusCode = response.extract().statusCode();
        if (statusCode == 404) {
            assertThat(response.extract().body().jsonPath().getString("message"))
                    .isEqualTo("Курьер с идентификатором {courierId} не найден");
        } else if (statusCode == 200) {
            assertThat(response.extract().statusCode()).isEqualTo(200);
        }
    }
}
