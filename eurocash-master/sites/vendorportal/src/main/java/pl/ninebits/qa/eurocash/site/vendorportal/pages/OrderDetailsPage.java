package pl.ninebits.qa.eurocash.site.vendorportal.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;

import java.util.List;

public class OrderDetailsPage extends VendorPortalBasePage {
  public OrderDetailsPage(BasePage pageObject) {
    super(pageObject);
  }

  public void clickCancelOrder() {
    sleep(1000);
    WebElement cancelOrderBtn = findElement(By.xpath("(//span[contains(.,'Anuluj zamówieni')])[1]"));
    cancelOrderBtn.click();
  }

  public void selectCancelOrderList(String reason) {
    waitForElementToBeVisible(By.cssSelector(".popup-header"), 10);
    sleep();   //TODO Bez sleepów nie działa, zmienić na waity
    WebElement clickGiveReason = findElement(By.cssSelector("ven-select#reason input"));
    sleep(500);
    clickGiveReason.click();
    waitForElementToBeVisible(By.xpath("//p[@class='ng-star-inserted' and contains(.,'" + reason + "')]"));
    WebElement selectCancelReason = findElement(By.xpath("//p[@class='ng-star-inserted' and contains(.,'" + reason + "')]"));
    selectCancelReason.click();
  }

  public void clickConfirmationCancelOrder() {
    WebElement cancleOrderBtn = findElement(By.xpath("//div[contains(@class,'cancel-order')]//span[contains(.,'Anuluj zamówienie')]"));
    cancleOrderBtn.click();
  }

  public void clickSaveBtn() {
    WebElement saveBtn = findElement(By.xpath("(//span[@class='mat-button-wrapper' and contains(.,'Za')])[2]"));
    saveBtn.click();
    waitVendorForDataIsLoaded();
  }

  public void clickChangeStatusBtn() {
    WebElement saveBtn = findElement(By.xpath("(//span[@class='mat-button-wrapper' and contains(.,'Zmień status na')])[1]"));
    saveBtn.click();
    waitVendorForDataIsLoaded();
  }

  public void enterInvoiceNumber(String invoicNumber) {
    WebElement invoiceNumberBtn = findElement(By.xpath("//ven-input[@formcontrolname='invoiceNumber']//div[contains(@class,'input-border')]//input"));
    invoiceNumberBtn.click();
    invoiceNumberBtn.clear();
    invoiceNumberBtn.sendKeys(invoicNumber);
  }

  public void typeAmountInvoiced(String amountInvoice) {
    WebElement amountInvoicedBtn = findElement(By.cssSelector("[ng-reflect-data-row] [type]"));
    amountInvoicedBtn.click();
    amountInvoicedBtn.clear();
    amountInvoicedBtn.sendKeys(amountInvoice);
  }

  public void clickConfirmBtn() {
    WebElement confirmBtn = findElement(By.cssSelector(".change-date .mat-flat-button .mat-button-wrapper"));
    confirmBtn.click();
    waitVendorForDataIsLoaded();
  }

  public void checkEnterInvoiceNumber(List<String> errors) {
    waitVendorForDataIsLoaded();
    if (!isElementPresent(By.xpath("//p[contains(@class,'warning') and contains(.,'Wprowadź numer faktury')]"))) {
      errors.add("Brak alertu z prośbą o wprowadzenie numery faktury");
    }
  }

  public void clickCancelBtn() {
    WebElement cancelBtn = findElement(By.xpath("//div[contains(@class,'change-date')]//span[contains(.,'Anuluj')]"));
    cancelBtn.click();
  }

  public void checkOrderDetailsPresent(List<String> errors) {
    if (!isElementPresent(By.cssSelector(".orders-details"))) {
      errors.add("Nie wczytały się szczegóły zamówienia");
    }
  }
}
