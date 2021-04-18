package lcaparros.demo;

import lcaparros.demo.framework.CustomMethods;
import lcaparros.demo.utils.CommonUtils;
import lcaparros.demo.utils.DataProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.assertEquals;

public class RegisterUserDemoQaTest {

    // Test data
    private static final int timeoutInSeconds = 5;
    private static final String url = "https://demoqa.com/automation-practice-form";
    private static final String successMessage = "Thanks for submitting the form";
    private static final List<String> listOfUsers = Arrays.asList(
            "Jan van Dam", "Chack Norris", "Klark n Kent", "John Daw", "Bat Man",
            "Tim Los", "Dave o Core", "Pay Pal", "Lazy Cat", "Jack & Jones"
    );

    private static final Logger logger = LoggerFactory.getLogger(RegisterUserDemoQaTest.class);

    private WebDriver driver;
    private WebDriverWait wait;

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
        wait = new WebDriverWait(driver, timeoutInSeconds);
    }

    @After
    public void afterTest() {
        if (driver != null) {
            logger.info("Closing browser and ending session");
            driver.quit();
        }
    }

    @Test
    public void registerUserDemoQa() {
        // Open webpage
        logger.info("Getting web from url");
        driver.get(url);

        // Verify URL
        logger.info("Verifying url");
        assertEquals("URL Verification", url, driver.getCurrentUrl());
//        assertEquals("Header link verification", headerLink, driver.findElement(By.className("headerLink")).getAttribute("href"));

        // Get five random users to register
        logger.info("Getting five random users from constant");
        List<String> usersToRegister = getFiveRandomUsers(listOfUsers);
        logger.info("Users to register:\n{}", formatList(usersToRegister));

        // Register each user
        usersToRegister.forEach(user -> registerUser(driver, user));

        logger.info("Registered users:\n{}", formatList(usersToRegister));
        logger.info("Pending users:\n{}", formatList(CommonUtils.diffLists(listOfUsers, usersToRegister)));
    }

    private List<String> getFiveRandomUsers(List<String> listOfUsers) {
        List<String> shuffleList = new ArrayList<>(listOfUsers);
        Collections.shuffle(shuffleList);
        return shuffleList.subList(0, 4);
    }

    private void registerUser(WebDriver driver, String user) {

        // Split name
        String[] splittedName = splitName(user);
        logger.info("*******************  Filling form for user {}  *******************", user);

        // Wait for form to be ready
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("firstName")));
        logger.info("Form is ready");

        // Introduce First Name
        WebElement firstNameField = driver.findElement(By.id("firstName"));
        firstNameField.sendKeys(splittedName[0]);
        logger.info("Introduced First Name: {}", splittedName[0]);

        // Introduce Last Name
        WebElement lastNameField = driver.findElement(By.id("lastName"));
        lastNameField.sendKeys(splittedName[1]);
        logger.info("Introduced Last Name: {}", splittedName[1]);

        // Introduce Email
        WebElement emailField = driver.findElement(By.id("userEmail"));
        String email = buildEmail(splittedName);
        emailField.sendKeys(email);
        logger.info("Introduced Email: {}", email);

        // Select Gender
        String randomGender = String.format("gender-radio-%d", ThreadLocalRandom.current().nextInt(1, 3));
        WebElement genderField = driver.findElement(By.id(randomGender));
        CustomMethods.clickButton(driver, genderField);
        logger.info("Selected Gender: {}", randomGender);

        // Introduce Phone Number
        WebElement phoneField = driver.findElement(By.id("userNumber"));
        String phoneNumber = DataProvider.generateRandomPhoneNumber();
        phoneField.sendKeys(phoneNumber);
        logger.info("Introduced Phone Number: {}", phoneNumber);

        // Introduce Birth Date
        WebElement birthDateField = driver.findElement(By.id("dateOfBirthInput"));
        String birthDate = DataProvider.getRandomDateString();
        birthDateField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        birthDateField.sendKeys(birthDate);
        birthDateField.sendKeys(Keys.chord(Keys.ENTER));
        logger.info("Introduced Birth Date: {}", birthDate);

        // Introduce Subjects
        WebElement subjectsField = driver.findElement(By.id("subjectsInput"));
        DataProvider.getRandomSubjects().forEach(subject -> {
            subjectsField.sendKeys(subject);
            subjectsField.sendKeys(Keys.chord(Keys.TAB));
        });

        // Introduce Hobbies
        List<Integer> shuffle = Arrays.asList(1, 2, 3);
        Collections.shuffle(shuffle);
        for (int i = 0; i < ThreadLocalRandom.current().nextInt(0, 3); i++) {
            WebElement hobbyField = driver.findElement(By.id("hobbies-checkbox-" + shuffle.get(i).toString()));
            CustomMethods.clickButton(driver, hobbyField);
            logger.info("Hobby {} selected", shuffle.get(i).toString());
        }

        // Upload picture
        if (ThreadLocalRandom.current().nextBoolean()) {
            WebElement pictureField = driver.findElement(By.id("uploadPicture"));
            String picturePath = DataProvider.getRandomPicture();
            pictureField.sendKeys(picturePath);
            logger.info("Uploaded {} picture for user {}", picturePath, user);
        } else {
            logger.info("No picture uploaded for user {}", user);
        }

        // Introduce Address
        WebElement addressField = driver.findElement(By.id("currentAddress"));
        String address = DataProvider.getRandomAddress();
        addressField.sendKeys(address);
        logger.info("Introduced address: {}", address);

        // Introduce State
        WebElement stateField = driver.findElement(By.id("react-select-3-input"));
        String state = DataProvider.getRandomState();
        stateField.sendKeys(state);
        stateField.sendKeys(Keys.chord(Keys.TAB));
        logger.info("Introduced State: {}", state);

        // Introduce City
        WebElement cityField = driver.findElement(By.id("react-select-4-input"));
        String city = DataProvider.getRandomCityByState(state);
        cityField.sendKeys(city);
        cityField.sendKeys(Keys.chord(Keys.TAB));
        logger.info("Introduced City: {}", city);

        logger.info("The form has been filled with user data successfully");

        // Submitting form
        logger.info("Submitting form");
        WebElement submitButton = driver.findElement(By.id("submit"));
        submitButton.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("example-modal-sizes-title-lg")));

        String message = driver.findElement(By.id("example-modal-sizes-title-lg")).getText();
        assertEquals("Verifying success message", successMessage, message);

        logger.info("Submitted");

        WebElement closeButton = driver.findElement(By.id("closeLargeModal"));
        closeButton.click();
    }

    private String[] splitName(String name) {
        String[] split = name.split("\\s+");
        return new String[]{split[0], split[split.length - 1]};
    }

    private String formatList(List<String> list) {
        StringBuilder stringBuilder = new StringBuilder();
        list.forEach(element -> {
            String[] splitted = splitName(element);
            stringBuilder.append(String.format("%s %s\n", splitted[0], splitted[1]));
        });
        return stringBuilder.toString();
    }

    private String buildEmail(String[] splitedName) {
        return String.format("%s@%s.com", splitedName[0], splitedName[1]);
    }
}
