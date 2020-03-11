package pl.ninebits.qa.eurocash.site.marketplace.pages.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.CartStep1Page;
import pl.ninebits.qa.eurocash.site.marketplace.pages.MarketplaceBasePage;

public class Footer extends MarketplaceBasePage {
  public Footer(BasePage pageObject) {
    super(pageObject);
  }

  public CartStep1Page clickGoToCartBtn() {
    WebElement miniCartBox = findElement(By.cssSelector(".cart__green"), "miniCartBox");
    miniCartBox.click();
    waitForDataIsLoaded();

    return new CartStep1Page(this);
  }

  public boolean checkCartEmpty() {
    return isElementPresent(By.cssSelector(".cart__empty"));
  }
}
