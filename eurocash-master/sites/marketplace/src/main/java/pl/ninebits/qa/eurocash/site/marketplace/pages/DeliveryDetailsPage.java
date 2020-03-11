package pl.ninebits.qa.eurocash.site.marketplace.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;
import pl.ninebits.qa.eurocash.site.marketplace.forms.ListForm;

import java.util.List;

public class DeliveryDetailsPage extends MarketplaceBasePage {
  public DeliveryDetailsPage(BasePage pageObject) {
    super(pageObject);
  }

  public ListForm clickSaveListBtn() {
    WebElement saveListBtn = findElement(By.cssSelector("app-shopping-list-tooltip"));
    saveListBtn.click();
    return new ListForm(this);
  }

  public void checkReasonForCancellation(String reason, List<String> errors) {
    if (!isElementPresent(By.xpath("//td[@class='uni-grid-cell' and contains(.,'"+reason +"')]"))) {
      errors.add("Brak powdou anulacji");
    }
  }

  public void checkTitleBox(List<String> errors) {
    if (!isElementPresent(By.xpath("//div[contains(@class,'title--1') and contains(.,'Dostawa nr')]"))) {
      errors.add("Brak boxa z tytułem");
    }
  }

  public void checkDetailsBox(List<String> errors) {
    if (!isElementPresent(By.xpath("//div[contains(@class,'mt-shadow uiKit-flex')]"))) {
      errors.add("Brak boxa z szczegółami dostawy");
    }
  }

  public void checkOrderedProductsBox(List<String> errors) {
    waitForDataIsLoaded();
    if (!isElementPresent(By.xpath("//div[contains(.,'produkty')]//div[contains(@class,'uni-grid-body')]"))) {
      errors.add("Brak boxa z zamówionymi produktami");
    }
  }

  public void checkLink(List<String> errors) {
    if (!isElementPresent(By.xpath("//div[@class='actions-list__item' and contains(.,'Zapisz')]"))) {
      errors.add("Brak linka Zapisz jako listę zakupową");
    }
    if (!isElementPresent(By.xpath("//div[@class='actions-list__item' and contains(.,'Eksportuj')]"))) {
      errors.add("Brak linka Eksportuj");
    }
  }

}
