package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class HeaderPage {

    WebDriver driver;

    public HeaderPage(WebDriver driver) {
        this.driver = driver;
    }

    //---------------Locators---------------
    By headerButtons = By.cssSelector("ul li");

    //---------------Methods----------------
    public List<WebElement> getHeaderButtons() {
        return driver.findElements(headerButtons);
    }
}