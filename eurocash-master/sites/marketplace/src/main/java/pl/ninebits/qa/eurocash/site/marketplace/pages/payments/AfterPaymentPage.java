package pl.ninebits.qa.eurocash.site.marketplace.pages.payments;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.DeliveryPage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.MarketplaceBasePage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.MyAccountPage;

import java.util.List;

public class AfterPaymentPage extends MarketplaceBasePage {
  public AfterPaymentPage(BasePage pageObject) {
    super(pageObject);
  }


  public void checkPaymentSuccessfulMsgPresent(List<String> errors) {
    waitForElementToBeVisible(By.xpath("//div[contains(text(),'Dziękujemy za zakupy')]"), 10);
    if (!isElementPresent(By.xpath("//div[contains(text(),'Dziękujemy za zakupy')]"))) {
      errors.add("Brak komunikatu z podziękowaniem za zakupy.");
    }
  }

  public void checkCartsToPayForBtnPresent(List<String> errors) {
    waitForElementToBeVisible(By.xpath("//a[contains(text(),'Koszyki do opłacenia')]"), 10);
    if (!isElementPresent(By.xpath("//a[contains(text(),'Koszyki do opłacenia')]"))) {
      errors.add("Brak przycisku z przekierowaniem do koszyków do opłacenia.");
    }
  }

  public DeliveryPage clickGoToDeliveryPage() {
    WebElement goToMyAccountBtn = findElement(By.cssSelector("app-payment-status a[href='/moje-konto/dostawy']"));
    goToMyAccountBtn.click();
    return new DeliveryPage(this);
  }

  public MyAccountPage clickGoToMyAccount1() {
    WebElement goToMyAccountBtn = findElement(By.cssSelector(".mt-btn"));
    goToMyAccountBtn.click();
    return new MyAccountPage(this);
  }

  public String getCartId() {
    String cartId = getTextFromElement(By.cssSelector("app-payment-status div div:nth-of-type(2)"));
    return cartId.replaceAll("[^\\d]", "");
  }
}
