package lcaparros.demo.framework;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CustomMethods {
    public static void clickButton(WebDriver driver, WebElement button) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].checked = true;", button);
    }
}
