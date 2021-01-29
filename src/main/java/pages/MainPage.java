package pages;

import helpers.Constants;
import helpers.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class MainPage extends PageObject {

    @FindBy(id = "inputImage")
    private WebElement chooseImage;
    @FindBy(xpath = "//li[@ng-repeat='item in items']")
    private List<WebElement> items;
    @FindBy(xpath = "//img")
    private List<WebElement> itemImages;
    @FindBy(xpath = "//textarea[@name='text']")
    private WebElement textArea;
    @FindBy(xpath = "//button[text()='Create Item']")
    private  WebElement createItemButton;
    @FindBy(xpath = "//button[@ng-click='submit()']")
    private  WebElement deleteButton;
    @FindBy(xpath = "//p[contains(@class,'story')]")
    private List<WebElement> itemDescriptions;

    private By updateItem = By.xpath("//button[text()='Update Item']");
    private By deleteItems = By.xpath("//*[@class='btn btn-default' and contains(text(),'Delete')]");
    private By editItems =By.xpath("//*[@class='btn btn-default' and contains(text(),'Edit')]");
    private By targets = By.xpath("//p[contains(@class,'story')]");





    public MainPage(WebDriver driver) {
        super(driver);
    }

    public void createItem(String description) {
        chooseImage.sendKeys(System.getProperty("user.dir")+ Constants.IMAGE);
        textArea.sendKeys(description);
        createItemButton.click();

    }
    public boolean isPresentOnList(String description){
        Wait<WebDriver> wait = new FluentWait<>(driver).withTimeout(Duration.of(2, ChronoUnit.SECONDS));
        wait.until(driver -> driver.findElement(By.xpath("//p[@class='story ng-binding' and text()='"+description+"']")));
        ArrayList<String> descriptions = new ArrayList<>();
        itemDescriptions.forEach(itemD-> descriptions.add(itemD.getText()));
        return descriptions.stream().anyMatch(item-> item.equals(description));
    }



    public void editItem(String itemToUpdate,String editItem) {
        Wait<WebDriver> wait = new FluentWait<>(driver).withTimeout(Duration.of(2, ChronoUnit.SECONDS));
        ArrayList<String> descriptions = new ArrayList<>();
        wait.until(driver -> driver.findElement(By.xpath("//p[@class='story ng-binding' and text()='"+itemToUpdate+"']")));
        itemDescriptions.forEach(itemD-> descriptions.add(itemD.getText()));
        List<WebElement> editButtons=driver.findElements(editItems);
        editButtons.get(descriptions.indexOf(itemToUpdate)).click();
        textArea.clear();
        textArea.sendKeys(editItem);
        driver.findElement(updateItem).click();
        wait.until(driver -> driver.findElement(By.xpath("//p[@class='story ng-binding' and text()='"+editItem+"']")));

    }

    public void deleteItem(String description) {
        Wait<WebDriver> wait = new FluentWait<>(driver).withTimeout(Duration.of(1, ChronoUnit.SECONDS));
        ArrayList<String> descriptions = new ArrayList<>();
        wait.until(driver -> driver.findElement(By.xpath("//p[@class='story ng-binding' and text()='"+description+"']")));
        itemDescriptions.forEach(itemD-> descriptions.add(itemD.getText()));
        List<WebElement> deleteButtons=driver.findElements(deleteItems);
        deleteButtons.get(descriptions.indexOf(description)).click();
        deleteButton.click();
        wait.until(ExpectedConditions.numberOfElementsToBeLessThan(targets,descriptions.size()));
    }

    public boolean isDeleted(String description) {
        ArrayList<String> descriptions = new ArrayList<>();
        itemDescriptions.forEach(itemD-> descriptions.add(itemD.getText()));
        return !descriptions.stream().anyMatch(item-> item.equals(description));
    }

    public boolean checkMaxLongDescription() {
        textArea.sendKeys(FileUtils.getRandomAlphaNumString(301));
        return !createItemButton.isEnabled();
    }
}
