package pl.ninebits.qa.eurocash.site.marketplace.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;
import pl.ninebits.qa.eurocash.site.marketplace.forms.FilterForm;

import java.util.List;

public class InvoicePage extends MarketplaceBasePage {
  public InvoicePage(BasePage pageObject) {
    super(pageObject);
  }

  public void clickSortUp() {
    WebElement iconUp = findElement(By.xpath("//div[@class='header-title' and contains(.,' Nr dostawy ') ]//..//i[contains(@class,'no-sort--up')]"));
    iconUp.click();
    waitForDataIsLoaded();
    sleep();
    iconUp.click();
    waitForDataIsLoaded();
    sleep();
  }

  public InvoiceDetailsPage clickInvoiceNumberBtn(String invoiceNumber) {
    WebElement invoiceNumberBtn = findElement(By.xpath("//div[@title='" + invoiceNumber + "']"));
    invoiceNumberBtn.click();
    return new InvoiceDetailsPage(this);
  }

  public FilterForm clickFilterBtn() {
    WebElement filterBtn = findElement(By.cssSelector(".filter-button"));
    filterBtn.click();
    return new FilterForm(this);
  }

  public void clickHideResults() {
    WebElement hideResults = findElement(By.cssSelector(".hide-text"));
    hideResults.click();
  }

  public void checkInvoiceNumber(String invoiceNumber, List<String> errors) {
    if (!isElementPresent(By.xpath("//table[contains(@class,'uni-grid-table')]//a[contains(@href,'faktury') and contains(.,'" + invoiceNumber + "')]"))) {
      errors.add("Brak numeru szukanej faktury na liście");
    }
  }
}
