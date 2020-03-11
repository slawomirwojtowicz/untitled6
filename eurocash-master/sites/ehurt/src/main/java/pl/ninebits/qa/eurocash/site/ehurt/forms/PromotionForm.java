package pl.ninebits.qa.eurocash.site.ehurt.forms;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import pl.ninebits.qa.automated.tests.core.utils.TextUtils;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;

import java.text.MessageFormat;
import java.util.List;

public class PromotionForm extends EhurtBaseForm {

  public PromotionForm(BasePage page) {
    super(page);
  }

  public void clickCloseBtn() {
    WebElement closeBtn = findClickableElement(By.id("headerClose"));
    closeBtn.click();
    waitForElementToBeInvisible(closeBtn);
  }

  public void checkProductPresent(String productName, List<String> errors) {
    if (!isElementPresent(By.xpath("//div[@id='mainPromoGrid']//td[contains(@aria-label,'" + productName + "')]"))) {
      errors.add(MessageFormat.format("W formularzu promocji nie odnaleziono produktu {0}", productName));
    }
  }

  public void checkProductContainsPhrase(String phrase, List<String> errors) {
    if (!isElementPresent(By.xpath("//div[@id='mainPromoGrid']//td[contains(@aria-label,'" + phrase.toUpperCase() + "')]"))) {
      errors.add(MessageFormat.format("W formularzu promocji nie odnaleziono produktu {0}", phrase));
    }
  }

  public void checkPromoType(String promoType, List<String> errors) {
    if (!isElementPresent(By.xpath("//strong[@class='header-promo__title' and contains(text(),'" + promoType.toLowerCase() + "')]"))) {
      errors.add(MessageFormat.format("Brak nazwy typy promocji {0} w nagłówku", promoType));
    }
  }

  public void checkIndex(String index, List<String> errors) {
    List<WebElement> elements = webDriver.findElements(By.xpath("//td[contains(@aria-label,'Indeks')][contains(text(),'" + index + "')]"));
    if (elements.isEmpty()) {
      errors.add(MessageFormat.format("Promocja nie zawiera indexu {0}", index));
    }
  }

  public void checkProductsContainsProducerName(String producerName, List<String> errors) {
    List<WebElement> elements = webDriver.findElements(By.xpath("//table//td[contains(@aria-label,'Column Produkt')]"));
    List<String> extracted = TextUtils.getAttributesFromWebElementsListWithoutTheQuotes(elements, "aria-label");
    for (String extractedElem : extracted) {
      if (!extractedElem.contains(producerName)) {
        errors.add(MessageFormat.format("Element {0} nie zawiera oczekiwanej frazy {1}", extracted, producerName));
      }
    }
  }

  public void enterSearchPhrase(String productName) {
    sleep(2000);
    WebElement searchInput = findVisibleElement(By.xpath("//ech-promotion-popup//input[contains(@placeholder,'Wpisz nazwę produktu')]"));
    searchInput.click();
    searchInput.sendKeys(productName);
    searchInput.sendKeys(Keys.RETURN);
    sleep();
  }
}
