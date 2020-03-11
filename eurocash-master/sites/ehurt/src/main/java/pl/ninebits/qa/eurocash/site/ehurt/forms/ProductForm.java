package pl.ninebits.qa.eurocash.site.ehurt.forms;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;

import java.text.MessageFormat;
import java.util.List;

public class ProductForm extends EhurtBaseForm {
  public ProductForm(BasePage page) {
    super(page);
    waitForFormIsLoaded();
  }

  public void closeProductForm() {
    WebElement closeBtn = webDriver.findElement(By.xpath("//span[contains(text(),'Wróć do listy produktów')]"));
    Actions actions = new Actions(webDriver);
    actions.moveToElement(closeBtn).click().perform();
    waitForElementToBeInvisible(By.xpath("//div[@class='product-preview']"));
  }

  private void waitForFormIsLoaded() {
    waitUntil(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='table-detail']//div[@class='cel-2']//span")));
  }

  public void checkAttributePresent(String attributeValue, String attributeName, List<String> errors) {
    String extractedAttribute = getProductAttribute(attributeName);

    if (!attributeValue.equals(extractedAttribute)) {
      errors.add(MessageFormat.format("Wyszukany {0} {1} jest inny od wyszukiwanego {2}", attributeName, extractedAttribute, attributeValue));
    }
  }

  public void checkParametersPresent(List<String> errors) {
    List<WebElement> parameters = webDriver.findElements(By.xpath("//div[@class='table-detail']//div[@class='cel-2']//span"));

    for (WebElement parameter : parameters) {
      if (parameter.getText().trim().isEmpty()) {
        errors.add("Brak jednego z parametrów w szczegółach produktu");
      }
    }
  }

  public void checkImagePresent(List<String> errors) {
    if (isElementPresent(By.xpath("//img[contains(@src,'zaslepka')]"))) {
      errors.add("Na szczegółach produktu pokazuje się zaślepka obrazka");
    }
  }

  public void checkTitlePresent(String productName, List<String> errors) {
    String extractedProductName = getTitle();
    if (!extractedProductName.equals(productName)) {
      errors.add(MessageFormat.format("W szczegółach produktu wyświetla się nazwa {0}, oczekiwano {1}", extractedProductName, productName));
    }
  }

  public String getTitle(){
    return getTextFromElement(By.xpath("//div[@class='title']/span"));
  }

  public String getProductAttribute(String attribute) {
    waitForFormIsLoaded();
    sleep(1000);
    waitForElementToBeVisible(By.xpath("//div[@class='title']/span"));
    return getTextFromElement(By.xpath("//div[@class='table-detail']//div[@class='cel-1']" +
      "/span[contains(text(),'" + attribute + "')]/../../div[@class='cel-2']/span"));
  }

  //TODO Spróbować zamienić sleepa na waita
  public String getEanCode() {
    waitForFormIsLoaded();
    sleep();
    return getTextFromElement(By.cssSelector(".ean__main-text"));
  }

  public void checkEanPresent(String eanCode, List<String> errors) {
    String extractedEanCode = getEanCode();

    if (!eanCode.equals(extractedEanCode)) {
      errors.add(MessageFormat.format("Wyszukany kod EAN {0} jest inny od wyszukiwanego {1}", eanCode, extractedEanCode));
    }
  }
}
