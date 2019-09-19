import dataGeneration.OwnerGenerator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.NewOwnerPage;
import pages.OwnersPage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class OwnersTests {
    WebDriver driver;
    static Properties prop;
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
    public void setUp(){
        System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"/src/main/resources/config/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(prop.getProperty("ownersPage"));
        ownersPage = new OwnersPage(driver);
        newOwnerPage = new NewOwnerPage(driver);
    }

    @Test
    public void navigateToAddOwner() {
        ownersPage.clickAddOwner();
        Assert.assertEquals("New Owner",newOwnerPage.getNewOwnerPageTitle().getText());
    }

    @Test
    public void addOwner() throws InterruptedException {
        Thread.sleep(2000);
        ownersPage.clickAddOwner();
        generatedOwner = new OwnerGenerator();
        System.out.println(generatedOwner.getFirstName() +" "+ generatedOwner.getLastName() + " " + generatedOwner.getAddress()+ " " + generatedOwner.getCity() +" " + generatedOwner.getPhoneNumber());
        newOwnerPage.inputFirstName(generatedOwner.getFirstName());
        newOwnerPage.inputLastName(generatedOwner.getLastName());
        newOwnerPage.inputAddress(generatedOwner.getAddress());
        newOwnerPage.inputCity(generatedOwner.getCity());
        newOwnerPage.inputPhoneNumber(generatedOwner.getPhoneNumber());
        newOwnerPage.clickAddOwnerButton();
        Thread.sleep(2000);
        boolean isUserPresent = false;
        List<WebElement> users = ownersPage.getUserRows();
        for(WebElement user : users){
//            if(user.findElement(By.cssSelector("td")).getText() == generatedOwner.getFirstName()+" "+generatedOwner.getLastName())
//                System.out.println("found him");
            if(user.getText().contains(generatedOwner.getFirstName() +" "+ generatedOwner.getLastName()))
                //System.out.println("FOUND HIM!\n"+user.getText());
                isUserPresent = true;
        }
        Assert.assertTrue(isUserPresent);
    }

    @AfterMethod
    public void tearDown(){
        driver.quit();
    }
}
