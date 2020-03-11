package pl.ninebits.qa.eurocash.site.ehurt.forms;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pl.ninebits.qa.automated.tests.site.commons.BaseForm;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;

public abstract class EhurtBaseForm extends BaseForm {

  public EhurtBaseForm(BasePage page) {
    super(page);
  }

  protected void waitForDataIsLoaded() {
    sleep(1000);
    waitUntil(ExpectedConditions.invisibilityOfAllElements(webDriver.findElements(By.xpath("//div[contains(@class,'loading loading-panel')]"))));
    try {
      waitUntil(ExpectedConditions.invisibilityOfAllElements(webDriver.findElements(By.cssSelector(".k-overlay"))));
    } catch (Exception e) {
      //
    }
  }
}