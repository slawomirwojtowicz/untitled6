package pl.ninebits.qa.eurocash.site.vendorportal.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;

import java.util.List;

public class DealDataPage extends VendorPortalBasePage {
  public DealDataPage(BasePage pageObject) {
    super(pageObject);
  }

  public void checkDealDetailsPresent(List<String> errors) {
    if (!isElementPresent(By.cssSelector("ven-new-group-offer-wrapper"))) {
      errors.add("Nie wczytały się szczegóły deala");
    }
  }

  public void typeNetPriceValue(String price) {
    WebElement netPrice = findElement(By.xpath("//ven-input[contains(@ng-reflect-label,'netto')]//div[contains(@class,'input-property')]//input"));
    netPrice.click();
    netPrice.sendKeys(price);
  }

  public void typeRegularPriceValue(String price) {
    WebElement regularPrice = findElement(By.xpath("//ven-input[contains(@ng-reflect-label,'regularna')]//div[contains(@class,'input-property')]//input"));
    regularPrice.click();
    regularPrice.sendKeys(price);
  }

  public void typePackValue(String packValue) {
    WebElement pack = findElement(By.xpath("//ven-input[contains(@ng-reflect-label,'Paczka')]//div[contains(@class,'input-property')]//input"));
    pack.click();
    pack.sendKeys(packValue);
  }

  public void typeAggregateMinimumValue(String aggregate) {
    WebElement aggregateMinimum = findElement(By.xpath("//ven-input[contains(@ng-reflect-label,'Minimum')]//div[contains(@class,'input-property')]//input"));
    aggregateMinimum.click();
    aggregateMinimum.clear();
    aggregateMinimum.sendKeys(aggregate);
  }

  public void typeAggregateMaxValue(String aggregate) {
    WebElement aggregateMax = findElement(By.xpath("//ven-input[contains(@ng-reflect-label,'Maksimum')]//div[contains(@class,'input-property')]//input"));
    aggregateMax.click();
    aggregateMax.sendKeys(aggregate);
  }

  public void typeYourIndividualComment(String individualComment) {
    WebElement comment = findElement(By.cssSelector("input#comment"));
    comment.click();
    comment.sendKeys(individualComment);
  }

  public void typeOfferDescription(String offer) {
    WebElement offerDescription = findElement(By.cssSelector("textarea#description"));
    offerDescription.click();
    offerDescription.sendKeys(offer);
  }

  public void enterStartDate(String date) {
    Actions actions = new Actions(webDriver);
    WebElement startDateInput = webDriver.findElement(By.cssSelector("[formcontrolname='application_from']"));
    startDateInput.click();
    sleep(500);
    startDateInput.sendKeys(Keys.ENTER);
    actions.moveToElement(startDateInput).click().keyDown(Keys.LEFT_SHIFT).sendKeys(Keys.F10).keyUp(Keys.LEFT_SHIFT).build().perform();
    startDateInput.sendKeys(date);
    startDateInput.sendKeys(Keys.ENTER);

    waitVendorForDataIsLoaded();
  }

  public LogisticsPage enterEndDate(String date) {
    WebElement endDateInput = webDriver.findElement(By.cssSelector("[formcontrolname='application_to']"));
    endDateInput.clear();
    endDateInput.sendKeys(date);
    endDateInput.sendKeys(Keys.ENTER);
    waitVendorForDataIsLoaded();
    return new LogisticsPage(this);
  }

}
