package pl.ninebits.qa.eurocash.site.marketplace.pages.payments;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;
import pl.ninebits.qa.eurocash.site.marketplace.constants.PaymentType;
import pl.ninebits.qa.eurocash.site.marketplace.pages.MarketplaceBasePage;

public class PaymentsPage extends MarketplaceBasePage {
  public PaymentsPage(BasePage pageObject) {
    super(pageObject);
  }


  public BlueMediaPaymentPage clickPayment(PaymentType paymentType) {
    WebElement paymentBox = findElement(By.xpath("//img[contains(@src,'" + paymentType.getPaymentId() + ".gif')]"), "paymentBox");
    paymentBox.click();

    return new BlueMediaPaymentPage(this);
  }

  public void closeVerifyYourAddressDetails() {
    sleep(1000);
    if (isElementPresent(By.cssSelector(".popup-payment-address-verify .cursor-pointer"))) {
      WebElement closePopupBtn = webDriver.findElement(By.cssSelector(".popup-payment-address-verify .cursor-pointer"));
      closePopupBtn.click();
      waitForElementToBeInvisible(By.cssSelector(".popup-payment-address-verify .popup-title"));
    }
  }


  public void clickOnlinePaymentsTab() {
    WebElement onlinePaymentsTab = findElement(By.xpath("//p[text()='Płatność online']"));
    onlinePaymentsTab.click();
  }

  public void enterEmail(String email) {
    WebElement emailInput = findElement(By.cssSelector("[aria-label='Adres e-mail']"));
    emailInput.clear();
    emailInput.sendKeys(email);
  }

  public void enterPhoneNumber(String phoneNumber) {
    WebElement phoneInput = findElement(By.cssSelector("[aria-label='Numer telefonu']"));
    phoneInput.clear();
    phoneInput.sendKeys(phoneNumber);
    sleep();
  }

  public void clickPayViaBankTransferTab() {
    WebElement bankTransferTab = findElement(By.xpath("//p[text()='Przelew bankowy']"));
    bankTransferTab.click();
  }

  public TransferSummaryPage clickConfirmOrderAndPaymentTypeBtn() {
    WebElement confirmPaymentBtn = findElement(By.xpath("//div[contains(text(),'Potwierdź zamówienie i formę płatności')]"));
    confirmPaymentBtn.click();

    return new TransferSummaryPage(this);
  }
}
