import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Tests {


    WebDriver driver;
    static Properties prop;
    PetClinicHomepage petClinicHomepage;
    HeaderPage headerPage;
    OwnersPage ownersPage;

    @BeforeClass
    public static void initialSetUp(){
        prop = new Properties();
        try {
            FileInputStream fInput = new FileInputStream(System.getProperty("user.dir") + "/config/config.properties");
            prop.load(fInput);
        } catch (FileNotFoundException e) {
            System.out.println("Couldn't find the properties file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @BeforeMethod
    public void setUp(){
        System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"/config/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(prop.getProperty("homepage"));
        petClinicHomepage = new PetClinicHomepage(driver);
        headerPage = new HeaderPage(driver);
        ownersPage = new OwnersPage(driver);
    }

    @Test
    public void homepagePresenceOfElements(){
        Assert.assertTrue(petClinicHomepage.getWelcomeTitle().isDisplayed());
        for(WebElement element : headerPage.getHeaderButtons()){
            System.out.println(element.getText());
        }
    }

    @Test
    public void navigationToAllOwners() throws InterruptedException {
        for(WebElement element : headerPage.getHeaderButtons()){
            if(element.getText().equals("OWNERS")){
                element.click();
                for(WebElement element1 : headerPage.getHeaderButtons()){
                    if(element1.getText().equals("ALL")){
                        element1.click();
                    }
                }
            }
        }
        Assert.assertEquals(ownersPage.getPageTitle().getText(),"Owners");
    }

    @AfterMethod
    public void tearDown(){
        driver.quit();
    }
}
