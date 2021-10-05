import com.codeborne.selenide.Condition;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.restassured.RestAssured.given;

public class DemoWebShopTests {

    private String userEmail = "ad@ad.com";
    private String userPassword = "123123123";
    private String cookies = null;
    private String itemsAmount = null;

    @Test
    void checkAmountOfProductsAddedToTheCart () {
        //Get user token
        cookies = given()
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .formParam("Email", userEmail)
                .formParam("Password", userPassword)
                .when()
                .post("http://demowebshop.tricentis.com/login")
                .then()
                .statusCode(302)
                .extract().cookie("NOPCOMMERCE.AUTH");

        //Add item several times to the cart
        Response response = given()
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .cookie("NOPCOMMERCE.AUTH", cookies)
                .body("addtocart_31.EnteredQuantity=3")
                .when()
                .post("http://demowebshop.tricentis.com/addproducttocart/details/31/1")
                .then()
                .statusCode(200)
                .extract()
                .response();
        itemsAmount = response.path("updatetopcartsectionhtml");

        //Set cookies to browser session
        open("http://demowebshop.tricentis.com/favicon.ico");
        getWebDriver().manage().addCookie(
                new Cookie("NOPCOMMERCE.AUTH", cookies));

        //Verify that amount of items in the cart is correct
        open("http://demowebshop.tricentis.com/");
        $(".cart-qty").shouldHave(Condition.text(itemsAmount));

    }

}
