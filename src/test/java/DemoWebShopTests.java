import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class DemoWebShopTests {

    private final static String successMessage = "The product has been added to your <a href=\"/wishlist\">wishlist</a>";

    @Test
    void addItemToWishlist() {
        Response response = given()
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .body("addtocart_43.EnteredQuantity=1")
                .when()
                .post("http://demowebshop.tricentis.com/addproducttocart/details/43/2")
                .then()
                .statusCode(200)
                .extract()
                .response();

        assertThat(response.path("message").toString()).isEqualTo(successMessage);


    }

}
