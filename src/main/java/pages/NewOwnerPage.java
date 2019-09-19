package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class NewOwnerPage {

    WebDriver driver;

    public NewOwnerPage(WebDriver driver) {
        this.driver = driver;
    }

    //---------------Locators---------------
    By newOwnerPageTitle = By.cssSelector("h2");
    By firstNameInputField = By.cssSelector("input[name=\"firstName\"]");
    By lastNameInputField = By.cssSelector("input[name=\"lastName\"]");
    By addressInputField = By.cssSelector("input[name=\"address\"]");
    By cityInputField = By.cssSelector("input[name=\"city\"]");
    By telephoneInputField = By.cssSelector("input[name=\"telephone\"]");
    By addOwnerButton = By.cssSelector("button[type=\"submit\"]");

    //---------------Methods----------------
    public WebElement getNewOwnerPageTitle() {
        return driver.findElement(newOwnerPageTitle);
    }

    public void inputFirstName(String firstName){
        driver.findElement(firstNameInputField).sendKeys(firstName);
    }
    public void inputLastName(String lastName){
        driver.findElement(lastNameInputField).sendKeys(lastName);
    }
    public void inputAddress(String address){
        driver.findElement(addressInputField).sendKeys(address);
    }
    public void inputCity(String city){
        driver.findElement(cityInputField).sendKeys(city);
    }
    public void inputPhoneNumber(String phoneNumber){
        driver.findElement(telephoneInputField).sendKeys(phoneNumber);
    }

    public void clickAddOwnerButton(){
        driver.findElement(addOwnerButton).click();
    }
}