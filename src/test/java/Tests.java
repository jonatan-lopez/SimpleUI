import helpers.FileUtils;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import pages.MainPage;

public class Tests extends BaseTest {
    static String description = FileUtils.getRandomAlphaNumString(8);
    static String editDescription = FileUtils.getRandomAlphaNumString(8);

    @BeforeMethod
    public void beforeMethod() {
        setUp();
        MainPage mainPage = new MainPage(driver);
        mainPage.createItem(description);
    }

    @Test
    public void validCreationTest() {
        MainPage mainPage = new MainPage(driver);
        Assert.assertTrue(mainPage.isPresentOnList(description));

    }

    @Test
    public void editItemTest() {
        MainPage mainPage = new MainPage(driver);
        mainPage.editItem(description, editDescription);
        description = editDescription;
        Assert.assertTrue(mainPage.isPresentOnList(editDescription));


    }

    @Test
    public void deleteItemTest() {
        MainPage mainPage = new MainPage(driver);
        mainPage.deleteItem(description);
        Assert.assertTrue(mainPage.isDeleted(description));

    }

    @Test
    public void checkMaxLongDescription() {
        MainPage mainPage = new MainPage(driver);
        Assert.assertTrue(mainPage.checkMaxLongDescription());

    }

    @Test
    public void checkItemOnListTest() {
        MainPage mainPage = new MainPage(driver);
        Assert.assertTrue(mainPage.isPresentOnList("Creators: Matt Duffer, Ross Duffer"));
    }

    @AfterMethod
    public void baseTearDown() {
        try {
            MainPage mainPage = new MainPage(driver);
            mainPage.deleteItem(description);
        } catch (NoSuchElementException ignored) {
        }

        driver.quit();

    }


}
