package pl.ninebits.qa.eurocash.site.marketplace.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;

public abstract class MarketplaceBasePage extends BasePage {
  public MarketplaceBasePage(WebDriver webDriver, int actionTimeout) {
    super(webDriver, actionTimeout);
  }

  public MarketplaceBasePage(BasePage pageObject) {
    super(pageObject);
    waitForDataIsLoaded();
  }

  public MarketplaceLoginPage logOut() {
    WebElement clickLogOutBtn = findElement(By.xpath("//div[contains(@class,'user-logged-box')]"));
    clickLogOutBtn.click();
    waitForElementToBeVisible(By.cssSelector(".logout"));
    WebElement logOutBtn = findElement(By.cssSelector(".logout"));
    logOutBtn.click();
    waitForDataIsLoaded();
    return new MarketplaceLoginPage(this);
  }

  protected void waitForDataIsLoaded() {
    try {
      waitForPageToLoad();
      waitForElementToBeInvisible(By.cssSelector(".full-page-loading"), 10);
    } catch (Exception e) {
      throw new RuntimeException(String.format("Strona %s nie załadowała się!", getClass().getSimpleName()));
    }
  }

}
