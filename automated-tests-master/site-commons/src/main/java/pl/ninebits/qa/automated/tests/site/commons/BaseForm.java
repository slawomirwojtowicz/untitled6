package pl.ninebits.qa.automated.tests.site.commons;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public abstract class BaseForm extends BaseUiObject {

  public BaseForm(BasePage page) {
    super(page.webDriver, page.actionTimeout);
    waitForDataIsLoaded();
  }

  protected void waitForDataIsLoaded() {
    waitUntil(ExpectedConditions.invisibilityOfAllElements(webDriver.findElements(By.xpath("//div[contains(@class,'loading loading-panel')]"))));
    sleep(1000);
  }
}
