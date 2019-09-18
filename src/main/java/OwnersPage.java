import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class OwnersPage {

    WebDriver driver;

    public OwnersPage(WebDriver driver) {
        this.driver = driver;
    }

    //---------------Locators---------------
    By pageTitle = By.cssSelector("h2");

    //---------------Methods----------------
    public WebElement getPageTitle () {
        return driver.findElement(pageTitle);
    }
}