import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.HeaderPage;
import pages.OwnersPage;
import pages.PetClinicHomepage;

public class HomepageTests extends BaseTest{

    @BeforeMethod
    public void setUp(){
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
    public void navigationToAllOwners() {
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
}
