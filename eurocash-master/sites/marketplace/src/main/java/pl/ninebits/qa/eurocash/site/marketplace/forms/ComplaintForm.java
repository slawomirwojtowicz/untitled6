package pl.ninebits.qa.eurocash.site.marketplace.forms;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;
import pl.ninebits.qa.eurocash.site.marketplace.constants.ComplaintType;
import pl.ninebits.qa.eurocash.site.marketplace.constants.Delivery;
import pl.ninebits.qa.eurocash.site.marketplace.constants.ProductName;
import pl.ninebits.qa.eurocash.site.marketplace.pages.MarketplaceBasePage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.NewComplaintPage;

public class ComplaintForm extends MarketplaceBasePage {
  public ComplaintForm(BasePage pageObject) {
    super(pageObject);
  }

  public void complaintTypeBtn(ComplaintType complaintType) {
    WebElement returnBtn = findElement(By.xpath("//div[@title='" + complaintType.getComplaintName() + "']"));
    returnBtn.click();
  }

  public void selectCommodity(ProductName productName) {
    waitForDataIsLoaded();
    sleep(1000);
    WebElement commodityInput = findElement(By.xpath("//app-mt-select[@formcontrolname='Product']//div[contains(@class,'sort-select')]"));
    sleep(500);
    commodityInput.click();
    WebElement selectProduct = findElement(By.xpath("//div[@class='drop-content--items drop-content--scroll']//div[contains(@title,'" + productName.getName() + "')]"));
    selectProduct.click();
  }

  public void selectInvoiceNumber() {
    waitForDataIsLoaded();
    sleep(1000);
    WebElement clickInvoice = findElement(By.xpath("//app-mt-select[@formcontrolname='Invoice']//div[contains(@class,'sort-select')]"));
    clickInvoice.click();
    WebElement selectInvoice = findElement(By.xpath("(//app-mt-select[@formcontrolname='Invoice']//div[contains(@class,'drop-item')])[1]"));
    selectInvoice.click();
  }

  public void selectDelivery(Delivery deliveryName) {
    waitForDataIsLoaded();
    sleep(1000);
    WebElement deliveryInput = findElement(By.xpath("//app-mt-select[@formcontrolname='Deliver']//div[contains(@class,'sort-select')]"));
    sleep(500);
    deliveryInput.click();
    WebElement selectDelivery = findElement(By.xpath("//div[@class='drop-content--items drop-content--scroll']//div[contains(@title,'" + deliveryName.getDeliveyName() + "')" +
      "]"));
    selectDelivery.click();
  }

  public void typeQuantity(String quantity) {
    WebElement quantityInput = findElement(By.cssSelector("[formcontrolname='Quantity']"));
    quantityInput.click();
    quantityInput.sendKeys(quantity);
    waitForElementToBeInvisible(By.xpath("//div[contains(@class,'mt-btn--complaint mt-btn__disabled')]"));
  }

  public void typeQuantityOrder(String quantity) {
    WebElement quantityOrderInput = findElement(By.cssSelector("[formcontrolname='QuantityOrder']"));
    quantityOrderInput.click();
    quantityOrderInput.sendKeys(quantity);
  }

  public void typeQuantityInvoice(String quantity) {
    WebElement quantityInvoiceInput = findElement(By.cssSelector("[formcontrolname='QuantityInvoice']"));
    quantityInvoiceInput.click();
    quantityInvoiceInput.sendKeys(quantity);
  }

  public void typeQuantityDelivery(String quantity) {
    WebElement quantityDeliveryInput = findElement(By.cssSelector("[formcontrolname='QuantityDelivery']"));
    quantityDeliveryInput.click();
    quantityDeliveryInput.sendKeys(quantity);
    waitForElementToBeInvisible(By.xpath("//div[contains(@class,'mt-btn--complaint mt-btn__disabled')]"));
  }

  public NewComplaintPage addProductToComplaint() {
    WebElement clickAddProduct = findElement(By.cssSelector("#addProduct"));
    sleep(2000);
    clickAddProduct.click();
    return new NewComplaintPage(this);
  }

  public NewComplaintPage sendComplaint() {
    WebElement clickSendProduct = findElement(By.cssSelector(".mt-btn--complaint"));
    moveToElement(clickSendProduct);
    scrollDownByWebElementHeight(clickSendProduct);
    scrollDown(70);
    clickSendProduct.click();
    return new NewComplaintPage(this);
  }

  public String getInvoiceNumber() {
    String invoiceNumber = getTextFromElement(By.xpath("(//app-mt-select[@formcontrolname='Invoice']//div[@class='now-selected-item']//span)[1]"));
    return invoiceNumber;
  }

  public void typeQuantityPriceOnOrder(String quantity) {
    WebElement priceOnOrderInput = findElement(By.xpath("(//div[@class='mt-input-wrap mt-input-wrap__short']/input[@type='number'])[1]"));
    priceOnOrderInput.click();
    priceOnOrderInput.sendKeys(quantity);
  }

  public void typeQuantityPriceInvoice(String quantity) {
    WebElement priceOnInvoiceInput = findElement(By.xpath("(//div[@class='mt-input-wrap mt-input-wrap__short']/input[@type='number'])[2]"));
    priceOnInvoiceInput.click();
    priceOnInvoiceInput.sendKeys(quantity);
  }

  public void typeQuantityPriceRequested(String quantity) {
    WebElement priceRequestedInput = findElement(By.xpath("(//div[@class='mt-input-wrap mt-input-wrap__short']/input[@type='number'])[3]"));
    priceRequestedInput.click();
    priceRequestedInput.sendKeys(quantity);
    waitForElementToBeInvisible(By.xpath("//div[contains(@class,'mt-btn--complaint mt-btn__disabled')]"));
  }

  public void typeEmailAddress(String email) {
    WebElement emailInput = findElement(By.xpath("//input[@aria-label='Adres e-mail']"));
    emailInput.click();
    emailInput.sendKeys(email);
  }

  public void typePhone(String phone) {
    WebElement phoneInput = findElement(By.xpath("//input[@aria-label='Numer telefonu']"));
    phoneInput.click();
    phoneInput.sendKeys(phone);
    waitForElementToBeInvisible(By.xpath("//div[contains(@class,'mt-btn--complaint mt-btn__disabled')]"));
  }

}
