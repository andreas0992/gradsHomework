import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.HeaderPage;
import pages.OwnersPage;
import pages.PetClinicHomepage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class HomepageTests {


    WebDriver driver;
    static Properties prop;
    PetClinicHomepage petClinicHomepage;
    HeaderPage headerPage;
    OwnersPage ownersPage;

    @BeforeClass
    public static void initialSetUp(){
        prop = new Properties();
        try {
            FileInputStream fInput = new FileInputStream(System.getProperty("user.dir") + "/src/main/resources/config/config.properties");
            prop.load(fInput);
        } catch (FileNotFoundException e) {
            System.out.println("Couldn't find the properties file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @BeforeMethod
    public void setUp(){
        System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"/src/main/resources/config/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(prop.getProperty("homepage"));
        petClinicHomepage = new PetClinicHomepage(driver);
        headerPage = new HeaderPage(driver);
        ownersPage = new OwnersPage(driver);
        System.out.println("Before method");
    }

    @Test
    public void homepagePresenceOfElements(){
        System.out.println("homepage presence test");
        Assert.assertTrue(petClinicHomepage.getWelcomeTitle().isDisplayed());
        for(WebElement element : headerPage.getHeaderButtons()){
            System.out.println(element.getText());
        }
    }

    @Test
    public void navigationToAllOwners() {
        System.out.println("navigation test");
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
        System.out.println("tear down");
        driver.quit();
    }
}
