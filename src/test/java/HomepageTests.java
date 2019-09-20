import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.HeaderPage;
import pages.OwnersPage;
import pages.PetClinicHomepage;

public class HomepageTests extends BaseTest{

    @BeforeMethod
    public void setUp(){
        //logger = LogManager.getLogger(HomepageTests.class);
        //logger = LogManager.getLogger(((RemoteWebDriver)driver).getCapabilities().getBrowserName());
        driver.get(prop.getProperty("homepage"));
        petClinicHomepage = new PetClinicHomepage(driver);
        headerPage = new HeaderPage(driver);
        ownersPage = new OwnersPage(driver);
    }

    @Test
    public void homepagePresenceOfElements(){
        logger.info("Verifying that I am on the homepage");
        Assert.assertTrue(petClinicHomepage.getWelcomeTitle().isDisplayed());
    }

    @Test
    public void navigationToAllOwners() {
        logger.info("Testing navigation from Homepage to Owners");
        for(WebElement element : headerPage.getHeaderButtons()){
            if(element.getText().equals("OWNERS")){
                element.click();
                for(WebElement element1 : headerPage.getHeaderButtons()){
                    if(element1.getText().equals("ALL")){
                        logger.info("Clicking the Owners button");
                        element1.click();
                    }
                }
            }
        }
        logger.info("Verifying that I am on the Owners page");
        Assert.assertEquals(ownersPage.getPageTitle().getText(),"Owners");
        logger.info("Landed on the Owners page");
    }
}
