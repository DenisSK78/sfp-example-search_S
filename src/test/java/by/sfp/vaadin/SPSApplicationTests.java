package by.sfp.vaadin;

import by.sfp.vaadin.service.MessagesService;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import java.io.InputStream;
import java.util.Scanner;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.Assert.assertEquals;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;


@EnableAutoConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SfpVaadinApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SPSApplicationTests {
    private static WebDriver driver;
    @Autowired
    protected WebApplicationContext server;
    @LocalServerPort
    protected int randomServerPort;
    private MockRestServiceServer mockServer;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    MessagesService messagesService;

    private InputStream jsonInputStream = this.getClass().getClassLoader().getResourceAsStream("test/three_service_providers.json");
    private String jsonString = convertInputStreamToString(jsonInputStream);

    @BeforeClass
    public static void init() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/test/chromedriver.exe");
        driver = new ChromeDriver();
        WebDriverRunner.setWebDriver(driver);
    }

    @AfterClass
    public static void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Before
    public void mockRest() {
        mockServer = MockRestServiceServer.bindTo(restTemplate).build();
    }

    @Test
    public void testOne(){
        forTestOne();
        open( "http://localhost:" + randomServerPort +"/#!service-providers?action=search");
        $(".searchNameLayout").has(Condition.attribute(".buttonSearchNameOff"));
        sleep(2000);
        $(".searchTextField").setValue("п");
        $(".searchNameLayout").has(Condition.attribute(".buttonSearchNameOn"));
        $(".buttonSearchNameOn").click();
        assertEquals("п","П", $(".underline").getText());
        $(".allProvidersLayout").has(Condition.attribute(".serviceProviderInfoLayout"));
        sleep(2000);
        $(".buttonSearchNameOn").click();
        $(".searchTextField").setValue("  ");
        sleep(2000);
        $(".buttonSearchNameOn").click();
        $(".searchTextField").setValue("rrr");
        $(".buttonSearchNameOn").click();
        assertEquals(messagesService.getMessage("ResultSearchLabel.NoMatchesFound"), $(".resultSearchLabel").getText());
        sleep(2000);
        $(".searchTextField").setValue("BAD_REQUEST");
        $(".buttonSearchNameOn").click();
        assertEquals($(".v-label").getText(), messagesService.getMessage("ErrorView.no.page"));
        sleep(2000);
    }

    public void forTestOne() {
        mockServer.expect( requestTo("http://localhost:8080/SFP-TEST/service_providers?name&domainCategoryId&classCategoriesIds"))
                .andExpect(method(GET))
                .andRespond(withSuccess(jsonString, MediaType.APPLICATION_JSON));

        mockServer.expect( requestTo("http://localhost:8080/SFP-TEST/service_providers?name=%D0%BF&domainCategoryId&classCategoriesIds"))
                .andExpect(method(GET))
                .andRespond(withSuccess(jsonString, MediaType.APPLICATION_JSON));

        mockServer.expect( requestTo("http://localhost:8080/SFP-TEST/service_providers?name=rrr&domainCategoryId&classCategoriesIds"))
                .andExpect(method(GET))
                .andRespond(withNoContent());

        mockServer.expect( requestTo("http://localhost:8080/SFP-TEST/service_providers?name=BAD_REQUEST&domainCategoryId&classCategoriesIds"))
                .andExpect(method(GET))
                .andRespond(withBadRequest());
    }

    private static String convertInputStreamToString(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream, "UTF-8");
        String string = scanner.useDelimiter("\\Z").next();
        scanner.close();
        return string;
    }
}
