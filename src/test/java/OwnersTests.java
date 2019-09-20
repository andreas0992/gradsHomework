import dataGeneration.OwnerGenerator;
import org.openqa.selenium.WebElement;
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
                logger.info("clicking the Add Owner button");
                ownersPage.clickAddOwner();
                addOwnerClickable = true;
            } catch (Exception e) {
                System.out.println("still not clickable");
            }
        }
        logger.info("Verifying that I am on the New Owner page");
        Assert.assertEquals("New Owner", newOwnerPage.getNewOwnerPageTitle().getText());
        logger.info("Landed on New Owner page");
    }

    @Test
    public void addOwner() throws SQLException {
        logger.info("Testing that I can add an owner");
        logger.info("Clicking on Add Owner");
        ownersPage.clickAddOwner();
        logger.info("Generating owner data");
        generatedOwner = new OwnerGenerator();
//        System.out.println(generatedOwner.getFirstName() + " " + generatedOwner.getLastName() + " "
//                + generatedOwner.getAddress() + " " + generatedOwner.getCity() + " " + generatedOwner.getPhoneNumber());
        logger.info("Inputting first name");
        newOwnerPage.inputFirstName(generatedOwner.getFirstName());
        logger.info("Inputting last name");
        newOwnerPage.inputLastName(generatedOwner.getLastName());
        logger.info("Inputting address");
        newOwnerPage.inputAddress(generatedOwner.getAddress());
        logger.info("Inputting city");
        newOwnerPage.inputCity(generatedOwner.getCity());
        logger.info("Inputting phone number");
        newOwnerPage.inputPhoneNumber(generatedOwner.getPhoneNumber().substring(0, 10));
        logger.info("Clicking add owner");
        newOwnerPage.clickAddOwnerButton();
        boolean isUserPresent = false;
        List<WebElement> users = ownersPage.getUserRows();
        for (WebElement user : users) {
            if (user.getText().contains(generatedOwner.getFirstName() + " " + generatedOwner.getLastName()))
                isUserPresent = true;
        }
        logger.info("Verifying that the user was added to the frontend");
        Assert.assertTrue(isUserPresent);
        logger.info("User present on frontend");
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
        logger.info("Verifying that the user was added to the database");
        Assert.assertTrue(isUserInDatabase);
        logger.info("User present in database");
    }
}
