package pl.ninebits.qa.eurocash.site.vendorportal.pages;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;

@Slf4j
public abstract class VendorPortalBasePage extends BasePage {
  public VendorPortalBasePage(WebDriver webDriver, int actionTimeout) {
    super(webDriver, actionTimeout);
  }

  public VendorPortalBasePage(BasePage pageObject) {
    super(pageObject);
    waitForPageToLoad();
    waitVendorForDataIsLoaded();
  }

  public VendorLoginPage logOut() {
    WebElement logOutBtn = findElement(By.xpath("//span[contains(text(),'Wyloguj')]"), "logOutBtn");
    logOutBtn.click();
    waitForPageToLoad();

    return new VendorLoginPage(this);
  }

  protected void waitVendorForDataIsLoaded() {
    try {
      waitForElementToBeVisibleIgnoredException(By.cssSelector(".loader-container .sk-cube-grid"), 1);
      waitForElementToBeInvisible(By.cssSelector(".loader-container .sk-cube-grid"));
      sleep(1000);
    } catch (Exception e) {
      log.info("Error in waitVendorForDataIsLoaded");
    }
  }

}
