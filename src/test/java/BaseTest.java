import dataGeneration.OwnerGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import pages.HeaderPage;
import pages.NewOwnerPage;
import pages.OwnersPage;
import pages.PetClinicHomepage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class BaseTest {
    Logger logger;
    Properties prop;
    WebDriver driver;
    PetClinicHomepage petClinicHomepage;
    HeaderPage headerPage;
    OwnersPage ownersPage;
    NewOwnerPage newOwnerPage;
    OwnerGenerator generatedOwner;

    @BeforeClass
    public void initialSetUp() {
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
    @Parameters("browser")
    public void baseSetUp(String browser) {
        if (browser.equals("chrome")) {
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + prop.getProperty("pathToChromeDriver"));
            driver = new ChromeDriver();
        }
        else if(browser.equals("firefox")){
            System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir")+ prop.getProperty("pathToFirefoxDriver"));
            driver = new FirefoxDriver();
        }
        driver.manage().window().maximize();
        logger = LogManager.getLogger(((RemoteWebDriver)driver).getCapabilities().getBrowserName());
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
