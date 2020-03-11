package pl.ninebits.qa.eurocash.site.marketplace.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;
import pl.ninebits.qa.eurocash.site.marketplace.forms.ListForm;

import java.text.MessageFormat;
import java.util.List;

public class ShoppingListPage extends MarketplaceBasePage {
  public ShoppingListPage(BasePage pageObject) {
    super(pageObject);
  }

  public void removeLists() {
    if (isElementPresent(By.xpath("//div[@class='short-name' and contains(.,'TestyAutomatyczne')]"))) {
      WebElement shoppingList = findElement(By.xpath("//div[@class='short-name' and contains(.,'TestyAutomatyczne')]"));
      shoppingList.click();
      List<WebElement> lists = webDriver.findElements(By.xpath("//div[@class='short-name' and contains(.,'TestyAutomatyczne')]//..//..//..//div[@title='Usuń listę']"));
      while (lists.size() > 0) {
        lists.get(0).click();
        sleep();
        waitForElementToBeInvisible(By.cssSelector(".full-page-loading"));
        sleep(400);
        lists = webDriver.findElements(By.xpath("//div[@class='short-name' and contains(.,'TestyAutomatyczne')]//..//..//..//div[@title='Usuń listę']"));
      }
    }
  }

  public ListForm clickNewShoppingLisBtn() {
    WebElement newShoppingListBtn = findElement(By.xpath("//div[contains(@class,'mt-btn') and contains(.,'Nowa lista zakupowa')]"));
    newShoppingListBtn.click();
    waitForElementToBeVisible(By.cssSelector(".tooltip-input"));
    return new ListForm(this);
  }

  public void typeListQuery(String listQuery) {
    waitForDataIsLoaded();
    WebElement listInput = findElement(By.cssSelector(".tooltip-input"));
    listInput.clear();
    listInput.sendKeys(listQuery);
  }

  public void clickAddList() {
    WebElement listBtn = findElement(By.xpath("//div[contains(@class,'mt-btn--custom')]"));
    listBtn.click();
    waitForElementToBeVisible(By.xpath("//div[contains(@class,'mt-btn text-bold') and contains(.,'Zamknij okno')]"));
  }

  public void clickCloseWindowBtn() {
    WebElement closeWindowBtn = findElement(By.xpath("//div[contains(@class,'mt-btn text-bold') and contains(.,'Zamknij okno')]"));
    closeWindowBtn.click();
  }

  public void clickPlus() {
    WebElement listBtn = findElement(By.xpath("//input[@placeholder='szt']//..//span[contains(@class,'plus')]"));
    listBtn.click();
  }

  public void addToCart() {
    WebElement listBtn = findElement(By.cssSelector(".btn__text"));
    listBtn.click();
    sleep(1000);
  }

  public void checkProductNamePresent(String productTitle, List<String> errors) {
    WebElement name = findElement(By.cssSelector(".tile__name"));
    String productName = name.getText();
    waitForDataIsLoaded();
    if (!productTitle.contains(productName)) {
      errors.add(MessageFormat.format("Nazwa produktu  {0}, nie zgadza się z nazwą na liście produktowej {1}", productTitle, productName));
    }
  }
}
