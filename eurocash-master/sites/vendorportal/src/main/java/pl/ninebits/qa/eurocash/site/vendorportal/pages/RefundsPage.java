package pl.ninebits.qa.eurocash.site.vendorportal.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;

public class RefundsPage extends VendorPortalBasePage {
  public RefundsPage(BasePage pageObject) {
    super(pageObject);
  }

  public void searchProduct(String complaints) {
    WebElement searchInput = findElement(By.cssSelector(".input-property.input-property__value"), "searchInput");
    searchInput.click();
    searchInput.clear();
    searchInput.sendKeys(complaints);
    clickSearchBtn();
  }

  private void clickSearchBtn() {
    WebElement searchBtn = findElement(By.cssSelector("[ng-reflect-icon-name='lupa']"), "searchBtn");
    searchBtn.click();
    waitForPageToLoad();
  }

  public void clickEditBtn() {
    WebElement edithBtn = findElement(By.cssSelector("[ng-reflect-icon-name='dots']"), "edithBtn");
    edithBtn.click();
    sleep();
    WebElement acceptBtn = findElement(By.xpath("//span[contains(.,'Akceptuj')]"));
    acceptBtn.click();
  }

}
