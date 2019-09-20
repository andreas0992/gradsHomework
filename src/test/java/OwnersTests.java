import dataGeneration.OwnerGenerator;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.NewOwnerPage;
import pages.OwnersPage;
import utils.DatabaseInteraction;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OwnersTests extends BaseTest {

    @BeforeMethod
    public void setUp() {
        logger = LogManager.getLogger(OwnersTests.class);
        driver.get(prop.getProperty("ownersPage"));
        ownersPage = new OwnersPage(driver);
        newOwnerPage = new NewOwnerPage(driver);
    }

    @Test
    public void navigateToAddOwner() {
        logger.info("Testing the navigation to New Owner");
        boolean addOwnerClickable = false;
        while (!addOwnerClickable) {
            try {
                ownersPage.clickAddOwner();
                addOwnerClickable = true;
            } catch (Exception e) {
                System.out.println("still not clickable");
            }
        }
        Assert.assertEquals("New Owner", newOwnerPage.getNewOwnerPageTitle().getText());
        logger.info("New Owner".equals(newOwnerPage.getNewOwnerPageTitle().getText()) ? "PASS" : "FAIL");
    }

    @Test
    public void addOwner() throws InterruptedException, SQLException {
        Thread.sleep(2000);
        ownersPage.clickAddOwner();
        generatedOwner = new OwnerGenerator();
        System.out.println(generatedOwner.getFirstName() + " " + generatedOwner.getLastName() + " "
                + generatedOwner.getAddress() + " " + generatedOwner.getCity() + " " + generatedOwner.getPhoneNumber());
        newOwnerPage.inputFirstName(generatedOwner.getFirstName());
        newOwnerPage.inputLastName(generatedOwner.getLastName());
        newOwnerPage.inputAddress(generatedOwner.getAddress());
        newOwnerPage.inputCity(generatedOwner.getCity());
        newOwnerPage.inputPhoneNumber(generatedOwner.getPhoneNumber().substring(0, 10));
        newOwnerPage.clickAddOwnerButton();
        Thread.sleep(2000);
        boolean isUserPresent = false;
        List<WebElement> users = ownersPage.getUserRows();
        for (WebElement user : users) {
            if (user.getText().contains(generatedOwner.getFirstName() + " " + generatedOwner.getLastName()))
                isUserPresent = true;
        }
        Assert.assertTrue(isUserPresent);
        DatabaseInteraction databaseInteraction = new DatabaseInteraction();
        ArrayList<String> rows = databaseInteraction.getQueryResults(prop.getProperty("databaseUrl"), prop.getProperty("databaseUser")
                , prop.getProperty("databasePass"), "select * from owners where first_name=\""
                        + generatedOwner.getFirstName() + "\" and last_name=\"" + generatedOwner.getLastName() + "\"");
        boolean isUserInDatabase = false;
        for (String row : rows) {
            System.out.println(row);
            if (row.contains(generatedOwner.getFirstName()) && row.contains(generatedOwner.getLastName())
                    && row.contains(generatedOwner.getAddress()))
                isUserInDatabase = true;
        }
        Assert.assertTrue(isUserInDatabase);
    }
}
