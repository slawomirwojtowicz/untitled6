package pl.ninebits.qa.eurocash.site.vendorportal.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pl.ninebits.qa.automated.tests.core.utils.RandomUtils;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;

import java.util.List;

public class AddOfferPage extends VendorPortalBasePage {
  public AddOfferPage(BasePage pageObject) {
    super(pageObject);
  }

  public void clickSortIcon() {
    WebElement sortIcon = findElement(By.cssSelector(".relative.select"));
    sortIcon.click();
    sleep(500);
  }

  public void typeSearchForVendorValues(String vendor) {
    WebElement inputSearchVendor = findClickableElement(By.cssSelector(".autocomp-select"));
    inputSearchVendor.click();
    inputSearchVendor.clear();
    inputSearchVendor.sendKeys(vendor);
    WebElement clickVendor = findClickableElement(By.xpath("//p[contains(.,'" + vendor + "')]"));
    clickVendor.click();
  }

  public void clickConfirmBtn() {
    WebElement confirmBtn = findElement(By.cssSelector(".box-button.mat-flat-button.mat-primary > .mat-button-wrapper"));
    confirmBtn.click();
  }

  public DealDataPage clickAddOffer() {
    WebElement addOfferBtn = findElement(By.cssSelector(".right .mat-button-wrapper"));
    addOfferBtn.click();
    return new DealDataPage(this);
  }

  public void clickRandomProduct() throws Exception {
    sleep();
    List<WebElement> productTitles = webDriver.findElements(By.cssSelector(".ng-star-inserted.select__content-row > .ng-star-inserted"));
    if (productTitles.isEmpty()) {
      throw new Exception("Brak wyszukanych produktów");
    }
    productTitles.get(RandomUtils.randomInt(0, productTitles.size())).click();
  }

}
