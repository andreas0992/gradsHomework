package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PetClinicHomepage {

    WebDriver driver;

    public PetClinicHomepage(WebDriver driver) {
        this.driver = driver;
    }

    //---------------Locators---------------
    By welcomeTitle = By.cssSelector("app-welcome > h1");
    By welcomeImage = By.cssSelector("");

    //---------------Methods----------------
    public WebElement getWelcomeTitle() {
        return driver.findElement(welcomeTitle);
    }
}