package pl.ninebits.qa.eurocash.site.marketplace.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;
import pl.ninebits.qa.eurocash.site.marketplace.forms.ListForm;

import java.util.List;

public class InvoiceDetailsPage extends MarketplaceBasePage {
  public InvoiceDetailsPage(BasePage pageObject){super(pageObject);}

  public void checkInvoiceNumber(String invoice, List<String> errors) {
    if (!isElementPresent(By.xpath("//h2[contains(.,'Faktura nr "+invoice+"')]"))) {
      errors.add("Brak numery faktury");
    }
  }

  public ListForm clickSaveAsShoppingList() {
    WebElement saveShoppingList = findElement(By.xpath("//div[contains(@class,'uiKit-center-vh')]//div[contains(.,'Zapisz jako listę zakupową')]"));
    saveShoppingList.click();
    return new ListForm(this);
  }

  public void checkInvoiceDetails(List<String> errors) {
    if (!isElementPresent(By.cssSelector(".invoices-details"))) {
      errors.add("Brak szczegółów wysyłki");
    }
  }

  public void checkOrderedProducts(List<String> errors) {
    if (!isElementPresent(By.cssSelector("app-invoices-dt-products-grid .uni-grid-body"))) {
      errors.add("Brak listy zamówieonych produktów");
    }
  }
}
