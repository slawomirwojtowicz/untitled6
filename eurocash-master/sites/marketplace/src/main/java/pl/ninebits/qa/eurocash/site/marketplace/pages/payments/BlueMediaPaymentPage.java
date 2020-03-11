package pl.ninebits.qa.eurocash.site.marketplace.pages.payments;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.MarketplaceBasePage;

public class BlueMediaPaymentPage extends MarketplaceBasePage {

  public BlueMediaPaymentPage(BasePage pageObject) {
    super(pageObject);
  }


  public AfterPaymentPage clickPayBtn() {
    WebElement payBtn = findElement(By.cssSelector("input[value^='Zapłać']"));
    payBtn.click();
    waitForDataIsLoaded();

    return new AfterPaymentPage(this);
  }
}
