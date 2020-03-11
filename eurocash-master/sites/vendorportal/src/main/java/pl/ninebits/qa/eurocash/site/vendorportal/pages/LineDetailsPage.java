package pl.ninebits.qa.eurocash.site.vendorportal.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;

import java.util.List;

public class LineDetailsPage extends VendorPortalBasePage {

  public LineDetailsPage(BasePage pageObject) {
    super(pageObject);
  }

  public void clickDoNotAcceptBtn() {
    WebElement doNotAccepBtn = findElement(By.xpath("//div[@class='mat-radio-label-content' and contains(.,'Nie uznawaj reklamacji')]"));
    doNotAccepBtn.click();
    waitForElementToBeInvisible(By.xpath("//label[contains(@class,'ng-star-inserted') and contains(.,'Ilość uwzględniona')]"));
  }

  public EditComplaintPage clickApproveBtn() {
    WebElement approveBtn = findElement(By.xpath("//span[@class='mat-button-wrapper' and contains(.,'Zatwierdź')]"), "approveBtn");
    approveBtn.click();
    waitForPageToLoad();
    return new EditComplaintPage(this);
  }

  public void checkComplaintStatusRejected(List<String> errors) {
    waitVendorForDataIsLoaded();
    if (!isElementPresent(By.xpath("//p[contains(.,'Status reklamacji: Odrzucona')]"))) {
      errors.add("Nie pokazał się status reklamacja odrzucona");
    }
  }

  public void typeMessage(String message) {
    WebElement messageInput = findElement(By.cssSelector("[cols]"));
    moveToElement(messageInput);
    messageInput.click();
    messageInput.sendKeys(message);
  }

  public void typeAmountOfProducts(String quantity) {
    WebElement amountOfProductsInput = findElement(By.cssSelector(".input-property__value.ng-touched"));
    moveToElement(amountOfProductsInput);
    amountOfProductsInput.click();
    amountOfProductsInput.sendKeys(quantity);
  }

  public void clickSendMessageBtn() {
    WebElement sendMessageBtn = findElement(By.cssSelector(".message-button > .mat-button.mat-flat-button.mat-primary > .mat-button-wrapper"));
    sendMessageBtn.click();
    waitForElementToBeVisible(By.xpath("//div[@class='comment-header']"));
  }

  public void checkSendMessage(String message, List<String> errors) {
    if (!isElementPresent(By.xpath("//p[@class='comment-message' and contains(.,'" + message + "')]"))) {
      errors.add("Brak wysłanej wiadomości");
    }
  }

}
