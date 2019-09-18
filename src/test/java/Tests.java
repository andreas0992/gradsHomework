import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;
import java.util.Properties;
@RunWith(Parallelized.class)
public class Tests {
    String platform;
    String browserName;
    String browserVersion;


    WebDriver driver;
    static Properties prop;
    PetClinicHomepage petClinicHomepage;
    HeaderPage headerPage;
    OwnersPage ownersPage;

    @Parameterized.Parameters
    public static LinkedList<String[]> getEnvironments() throws Exception{
        LinkedList<String[]> env = new LinkedList<String[]>();
        env.add(new String[]{Platform.WIN10.toString(),"chrome","latest"});
        env.add(new String[]{Platform.WIN10.toString(),"firefox","latest"});
        return env;
    }

    public Tests(String platform, String browserName, String browserVersion){
        this.platform = platform;
        this.browserName = browserName;
        this.browserVersion = browserVersion;
    }

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

    @Before
    public void setUp(){
        System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"/config/chromedriver.exe");
        DesiredCapabilities capabilities = new DesiredCapabilities();
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

    @After
    public void tearDown(){
        driver.quit();
    }
}
