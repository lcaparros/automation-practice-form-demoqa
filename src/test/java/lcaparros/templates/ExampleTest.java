package lcaparros.templates;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;

public class ExampleTest {
    private static final String url = "https://www.selenium.dev";
    private static final String headerLink = "https://selenium.dev/";

    private static final Logger logger = LoggerFactory.getLogger(ExampleTest.class);;

    private WebDriver driver;

    @BeforeClass
    public static void beforeClass() {
        System.setProperty("webdriver.chrome.driver", "/home/hunter/Descargas/chromedriver_linux64/chromedriver");
    }

    @Before
    public void beforeTest() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized"); // open Browser in maximized mode
        options.addArguments("disable-infobars"); // disabling infobars
        options.addArguments("--disable-extensions"); // disabling extensions
        options.addArguments("--disable-gpu"); // applicable to windows os only
        options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
        options.addArguments("--no-sandbox"); // Bypass OS security model
        options.addArguments("--remote-debugging-port=9222"); // Activating DevToolsActivePort
        driver = new ChromeDriver(options);
    }

    @After
    public void afterTest() {
        if (driver != null) {
            logger.info("Closing browser and ending session");
            driver.quit();
        }
    }

    @Test
    public void checkHeaderLink() {
        logger.info("Getting web from url");
        driver.get(url);
        logger.info("Verifying header link");
        assertEquals("Header link verification", headerLink, driver.findElement(By.className("headerLink")).getAttribute("href"));
    }
}
