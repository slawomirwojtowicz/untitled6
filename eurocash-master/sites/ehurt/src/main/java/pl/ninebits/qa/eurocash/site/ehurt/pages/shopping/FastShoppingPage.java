package pl.ninebits.qa.eurocash.site.ehurt.pages.shopping;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtBasePage;

import java.text.MessageFormat;
import java.util.List;

public class FastShoppingPage extends EhurtBasePage {
  public FastShoppingPage(BasePage pageObject) {
    super(pageObject);
    waitForDataIsLoaded();
  }


  public void checkFastShoppingPagePresent(List<String> errors) {
    if (!isElementPresent(By.cssSelector(".ech-quick-shopping"))) {
      errors.add("Strona z szybkimi zakupami nie została wczytana poprawnie");
    }
  }

  public void checkProductsPresent(List<String> productNames, List<String> errors) {
    waitForElementToBeInvisible(By.cssSelector(".dx-datagrid-nodata"));
    for (String productName : productNames) {
      int length = productName.length();
      if (length > 10) {
        length = 10;
      }
      if (!isElementPresent(By.xpath("//td[contains(@aria-label,'" + productName.substring(0, length) + "')]"))) {
        errors.add(MessageFormat.format("Produkt {0} nie znajduje się na liście szybkich zakupów", productName));
      }
    }
  }

  public void typeSearchPhrase(String searchPhrase) {
    WebElement search = findVisibleElement(By.xpath("//ech-quick-shopping//input[contains(@class,'search-input')]"));
    search.clear();
    search.sendKeys(searchPhrase);
    search.sendKeys(Keys.RETURN);
    waitForDataIsLoaded();
  }

  public String getProductName() {
    return findVisibleElement(By.xpath("//div[@openproductdetails]")).getAttribute("title");
  }

  public void enterUnits(String packageAmount) {
    WebElement unitsInput = findVisibleElement(By.xpath("//input[@name='JednostkaMiary']"));
    unitsInput.sendKeys(packageAmount);
    unitsInput.sendKeys(Keys.RETURN);
    waitForDataIsLoaded();
  }

  public void clickAddToCart() {
    findClickableElement(By.xpath("//div[contains(text(),'DODAJ DO KOSZYKA')]")).click();
  }

  public void checkProductPresent(String productName, List<String> errors) {
    waitForElementToBeInvisible(By.cssSelector(".dx-datagrid-nodata"));
    int length = productName.length();
    if (length > 10) {
      length = 10;
    }
    if (!isElementPresent(By.xpath("//td[contains(@aria-label,'" + productName.substring(0, length) + "')]"))) {
      errors.add(MessageFormat.format("Produkt {0} nie znajduje się na liście szybkich zakupów", productName));
    }
  }

  public void checkProductNotPresent(String productName, List<String> errors) {
    waitForDataIsLoaded();
    if (isElementPresent(By.xpath("//table//a[@title='" + productName + "']"))) {
      errors.add(MessageFormat.format("Produkt {0} nie został usunięty z listy szybkich zakupów", productName));
    }
  }

  public void checkFastShoppingListCleared(List<String> errors) {
    try {
      waitForElementToBeVisible(By.cssSelector(".dx-datagrid-nodata"), 7);
    } catch (Exception e) {
      errors.add("Lista szybkich zakupów nie została wyczyszczona");
    }
  }

  public String getFirstProductNameFromGrid() {
    waitForDataIsLoaded();
    return getTextFromElement(By.xpath("//td[contains(@aria-label,'Nazwa produktu, Value')]//span[@openproductdetails]"));
  }

  public void removeFirstProduct() {
    webDriver.findElements(By.cssSelector(".eh-i-usun")).get(0).click();
    sleep(2000);
    waitForDataIsLoaded();
  }

  public void checkIndexPresent(String numbersInIndex, List<String> errors) {
    waitForDataIsLoaded();
    if (!isElementPresent(By.xpath("//div[@class='list-item']//span[contains(text(),'" + numbersInIndex + "')]"))) {
      errors.add(MessageFormat.format("Brak produktu zawierającego {0} w indeksie", numbersInIndex));
    }
  }

  public void checkIndexInGridPresent(String index, List<String> errors) {
    if (!isElementPresent(By.xpath("//td[contains(@aria-label,'Indeks') and contains(text(),'" + index + "')]"))) {
      errors.add(MessageFormat.format("W gridzie szybkich zakupów brak produktu zawierającego index {0}", index));
    }
  }
}
