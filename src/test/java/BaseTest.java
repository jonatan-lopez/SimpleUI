import helpers.Constants;
import helpers.DriverFactory;
import helpers.FileUtils;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

public abstract class BaseTest {
    protected static WebDriver driver;
    FileUtils fileUtils = new FileUtils();


    public void setUp() {
        driver = DriverFactory.getDriver(fileUtils.getEnvProperties(Constants.BROWSER));
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get(fileUtils.getEnvProperties(Constants.URL));
    }




}
