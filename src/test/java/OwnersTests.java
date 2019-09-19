import dataGeneration.OwnerGenerator;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.NewOwnerPage;
import pages.OwnersPage;
import utils.DatabaseInteraction;

import java.sql.SQLException;
import java.util.List;

public class OwnersTests extends BaseTest{

    @BeforeMethod
    public void setUp(){
        driver.get(prop.getProperty("ownersPage"));
        ownersPage = new OwnersPage(driver);
        newOwnerPage = new NewOwnerPage(driver);
    }

    @Test
    public void navigateToAddOwner() throws InterruptedException, SQLException {
        ownersPage.clickAddOwner();
        Thread.sleep(2000);
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
}
