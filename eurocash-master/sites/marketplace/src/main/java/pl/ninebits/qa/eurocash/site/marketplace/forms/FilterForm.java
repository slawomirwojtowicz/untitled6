package pl.ninebits.qa.eurocash.site.marketplace.forms;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.MarketplaceBasePage;

public class FilterForm extends MarketplaceBasePage {
  public FilterForm(BasePage page) {
    super(page);
  }

  public void clickInvoiceNumberBtn() {
    WebElement filerBtn = findElement(By.xpath("(//div[contains(.,'Nr faktury')]//../div[@class='pointer sort-select'])[1]"));
    filerBtn.click();
  }

  public void clickInvoiceNumberOnOrderBtn() {
    WebElement filerBtn = findElement(By.xpath("//input[aria-label='Numer faktury']"));
    filerBtn.click();
  }
  public void typeInvoiceNumberValue(String invoiceNumber) {
    WebElement input = findElement(By.xpath("(//div[contains(.,'Nr faktury')]//..//app-mt-select//div[@class='mt-select--input-wrapper']/input[@type='text'])[1]"));
    input.click();
    input.clear();
    input.sendKeys(invoiceNumber);
  }

  public void typeInvoiceNumberValueOnOrderPage(String invoiceNumber) {
    WebElement input = findElement(By.xpath("//input[aria-label='Numer faktury']"));
    input.click();
    input.clear();
    input.sendKeys(invoiceNumber);
  }


  public void clickLoupeBtn() {
    WebElement loupBtn = findElement(By.cssSelector(".sort-select--down [height]"));
    loupBtn.click();
  }

  public void clickDeliveryBtn() {
    WebElement deliveryBtn = findClickableElement(By.xpath("//div[@class='label-text' and contains(.,'Nr dostawy')]//../app-mt-select//div[@class='current-name']"));
    deliveryBtn.click();
  }

  //TODO Poprawić xpatha
  public void typeDeliveryValues(String deliveryNumber) {
    WebElement inputDelivery = findClickableElement(By.xpath("//perfect-scrollbar[@class='custom-scrollbar-cart " +
      "scrollbar--content--cog']//app-delivery-filters//div[@class='filters']/div[2]/app-mt-select//input[@type='text']"));
    inputDelivery.click();
    inputDelivery.clear();
    inputDelivery.sendKeys(deliveryNumber);
  }

  public void clickComplaintBtn() {
    WebElement complaintBtn = findClickableElement(By.xpath("//app-complaint-filters/div/div[contains(.,'Nr reklamacji')]/app-mt-select//div[@class='current-name']"));
    complaintBtn.click();
  }

  public void typeComplaintValues(String complaintNumber) {
    WebElement inputComplaint = findClickableElement(By.xpath("//app-complaint-filters/div[@class='filters']/div[1]/app-mt-select//input[@type='text']"));
    inputComplaint.click();
    inputComplaint.clear();
    inputComplaint.sendKeys(complaintNumber);
  }

  public void clickHideResults() {
    WebElement hideResults = findElement(By.cssSelector(".hide-text"));
    hideResults.click();
  }

  public void filterByCartId(String cartId) {
    WebElement cartIdFilterMenu = findElement(By.xpath("//div[.='Nr koszyka']/..//div[@class='current-name']"));
    cartIdFilterMenu.click();
    WebElement filterInput = findElement(By.xpath("//div[.='Nr koszyka']/..//input"));
    filterInput.clear();
    filterInput.sendKeys(cartId, Keys.RETURN);
    waitForDataIsLoaded();
  }
}
