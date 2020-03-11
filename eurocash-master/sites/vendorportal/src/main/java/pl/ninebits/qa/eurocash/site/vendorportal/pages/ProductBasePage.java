package pl.ninebits.qa.eurocash.site.vendorportal.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;

public abstract class ProductBasePage extends VendorPortalBasePage {
  ProductBasePage(BasePage pageObject) {
    super(pageObject);
  }

  public ProductOfferPage clickOfferLink() {
    WebElement offerLink = findElement(By.partialLinkText("Oferta"), "offerLink");
    offerLink.click();

    return new ProductOfferPage(this);
  }

  public LogisticsPage clickLogisticsLink() {
    WebElement logisticsLink = findElement(By.xpath("//ven-box-navigation//a[contains(.,'Logistyka')]"), "logisticsLink");
    logisticsLink.click();

    return new LogisticsPage(this);
  }

  //todo: <T> T
  public void clickNextBtn() {
    WebElement nextBtn = findElement(By.xpath("//span[contains(.,'Dalej')]"), "nextBtn");
    scrollToElement(nextBtn);
    nextBtn.click();
    waitVendorForDataIsLoaded();
  }
}
