package pl.ninebits.qa.automated.tests.site.commons;

import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public abstract class BasePage extends BaseUiObject {
  private static final Logger LOGGER = LoggerFactory.getLogger(BasePage.class);

  public BasePage(WebDriver webDriver, int actionTimeout) {
    super(webDriver, actionTimeout);
    waitForPageToLoad();
  }

  public BasePage(BasePage pageObject) {
    this(pageObject.webDriver, pageObject.actionTimeout);
  }

  protected void waitForPageToLoad() {
    ExecutorService executor = Executors.newSingleThreadExecutor();
    Callable waitForPageLoadTask = new Callable() {
      @Override
      public Object call() throws Exception {
        new WebDriverWait(webDriver, actionTimeout).until(new DocumentReadyStateCondition());
        return Boolean.TRUE;
      }
    };

    sleep();
    LOGGER.trace("Waiting for page to load. Checking document.ready ...");

    int i = 1;
    while (true) {
      Object result = null;
      try {
        result = executor.submit(waitForPageLoadTask).get(actionTimeout, TimeUnit.SECONDS);
      } catch (Exception e) {
        LOGGER.trace(MessageFormat.format("Reloading page after unsuccessful waiting for document.ready. Attempt no {0}....", i));
        reloadPage();
        LOGGER.trace("Page reloaded.");
      }

      if (Boolean.TRUE.equals(result)) {
        LOGGER.trace("Page loaded successfully");
        return;
      }
      i++;
      if (i == 3) {
        throw new RuntimeException(MessageFormat.format("Failed to load page after {0} attempts", i));
      }
    }
  }

  public <T> T navigateBack(Class<T> nextPageType) {
    webDriver.navigate().back();
    return createPageObject(nextPageType);
  }

  protected <T> T createPageObject(Class<T> pageObjectType) {
    try {
      return ConstructorUtils.invokeConstructor(pageObjectType, this);
    } catch (Exception e) {
      throw new RuntimeException(MessageFormat.format("Failed to create page object of type {0}", pageObjectType), e);
    }
  }

  protected void validateCaptcha() {
    String checkboxXpath = "//div[@class='recaptcha-checkbox-border']";
    String checkedXpath = "//span[@id='recaptcha-anchor' and @aria-checked='true']";

    try {
      enterIFrame(By.xpath("(//iframe[contains(@src,'recaptcha')])[1]"));
      webDriver.findElement(By.xpath(checkboxXpath)).click();
      waitForElementToBeVisibleIgnoredException(By.xpath(checkedXpath), 3);
      leaveIFrame();
    } catch (Exception e) {
      LOGGER.info(MessageFormat.format("Exception while validating captcha\n{0}", e));
    }
  }
}
