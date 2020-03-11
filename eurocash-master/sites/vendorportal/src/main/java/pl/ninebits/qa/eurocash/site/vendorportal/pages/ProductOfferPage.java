package pl.ninebits.qa.eurocash.site.vendorportal.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;

public class ProductOfferPage extends ProductBasePage {
  ProductOfferPage(BasePage pageObject) {
    super(pageObject);
  }


  public Double editPrice(Double productPrice) {
    WebElement priceInput = findElement(By.cssSelector("[formcontrolname='price_net'] input"), "priceInput");
    priceInput.clear();
    priceInput.sendKeys(String.valueOf(productPrice));

    return productPrice;
  }

}
