import dataGeneration.OwnerGenerator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import pages.HeaderPage;
import pages.NewOwnerPage;
import pages.OwnersPage;
import pages.PetClinicHomepage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class BaseTest {
    static Properties prop;
    WebDriver driver;
    PetClinicHomepage petClinicHomepage;
    HeaderPage headerPage;
    OwnersPage ownersPage;
    NewOwnerPage newOwnerPage;
    OwnerGenerator generatedOwner;
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
    public void baseSetUp(){
        System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"/src/main/resources/config/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void tearDown(){
        System.out.println("tear down");
        driver.quit();
    }
}
