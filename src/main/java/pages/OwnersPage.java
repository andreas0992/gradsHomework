package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class OwnersPage {

    WebDriver driver;

    public OwnersPage(WebDriver driver) {
        this.driver = driver;
    }

    //---------------Locators---------------
    By pageTitle = By.cssSelector("h2");
    By addOwnerButton = By.cssSelector("button");
    By userRows = By.cssSelector("tr");

    //---------------Methods----------------
    public WebElement getPageTitle () {
        return driver.findElement(pageTitle);
    }

    public void clickAddOwner(){
        driver.findElement(addOwnerButton).click();
    }

    public List<WebElement> getUserRows(){
        return driver.findElements(userRows);
    }
}