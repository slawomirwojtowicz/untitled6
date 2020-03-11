package pl.ninebits.qa.automated.tests.site.commons;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseUiObject {

  private static final Logger LOGGER = LoggerFactory.getLogger(BaseUiObject.class);
  private static final int DEFAULT_SLEEP = 350;
  private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(15);
  private static final Duration DEFAULT_POLLING_INTERVAL = Duration.ofSeconds(1);
  protected WebDriver webDriver;
  int actionTimeout;

  public BaseUiObject(WebDriver webDriver, int actionTimeout) {
    this.webDriver = webDriver;
    this.actionTimeout = actionTimeout;
  }

  protected void waitForElementToBeClickable(By locator) {
    new WebDriverWait(webDriver, actionTimeout).until(ExpectedConditions.elementToBeClickable(locator));
  }

  protected void waitForElementToBeClickable(By locator, int timeout) {
    new WebDriverWait(webDriver, timeout).until(ExpectedConditions.elementToBeClickable(locator));
  }

  protected void waitForElementToBeVisible(By locator) {
    new WebDriverWait(webDriver, actionTimeout).until(ExpectedConditions.visibilityOfElementLocated(locator));
  }

  protected void waitForElementToBeVisible(By locator, int timeout) {
    new WebDriverWait(webDriver, timeout).until(ExpectedConditions.visibilityOfElementLocated(locator));
  }

  protected void waitForElementToBeInvisible(By locator) {
    new WebDriverWait(webDriver, actionTimeout).until(ExpectedConditions.invisibilityOfElementLocated(locator));
  }

  protected void waitForElementToBeInvisible(By locator, int timeout) {
    new WebDriverWait(webDriver, timeout).until(ExpectedConditions.invisibilityOfElementLocated(locator));
  }

  protected void waitForElementToBeInvisible(WebElement element) {
    new WebDriverWait(webDriver, actionTimeout).until(ExpectedConditions.invisibilityOf(element));
  }

  protected void waitForElementToBeInvisible(WebElement element, int timeout) {
    new WebDriverWait(webDriver, timeout).until(ExpectedConditions.invisibilityOf(element));
  }

  protected void waitForElementToBeVisibleIgnoredException(By locator, int timeoutInSec) {
    try {
      waitForElementToBeVisible(locator, timeoutInSec);
    } catch (Exception e) {
      LOGGER.info(MessageFormat.format("Exception while waiting for element to be visible.\n{0}", e));
    }
  }

  protected void waitUntil(ExpectedCondition condition) {
    new WebDriverWait(webDriver, actionTimeout).until(condition);
  }

  protected WebElement findElement(By locator) {
    Wait<WebDriver> fluentWait = new FluentWait<>(webDriver)
      .withTimeout(DEFAULT_TIMEOUT)
      .pollingEvery(DEFAULT_POLLING_INTERVAL)
      .ignoring(NoSuchElementException.class)
      .ignoring(StaleElementReferenceException.class);
    try {
      return fluentWait.until(driver -> driver.findElement(locator));
    } catch (Exception e) {
      throw new RuntimeException(MessageFormat.format("Couldn''t find element by locator {0} on {1}", locator, getClass().getSimpleName()));
    }
  }

  protected WebElement findElement(By locator, String elemName) {
    Wait<WebDriver> fluentWait = new FluentWait<>(webDriver)
      .withTimeout(DEFAULT_TIMEOUT)
      .pollingEvery(DEFAULT_POLLING_INTERVAL)
      .ignoring(NoSuchElementException.class)
      .ignoring(StaleElementReferenceException.class);
    try {
      return fluentWait.until(driver -> driver.findElement(locator));
    } catch (Exception e) {
      throw new RuntimeException(MessageFormat.format("Couldn''t find element {0} on {1}", elemName, getClass().getSimpleName()));
    }
  }

  protected List<WebElement> findElements(By locator, String listName) throws Exception {
    List<WebElement> elements = webDriver.findElements(locator);

    int i = 0;
    while (elements.isEmpty() && i++ < 10) {
      sleep(1000);
      elements = webDriver.findElements(locator);
    }

    if (elements.isEmpty()) {
      throw new Exception(String.format("List %s is empty", listName));
    } else {
      return elements;
    }
  }

  protected Actions actionStream() {
    return new Actions(webDriver);
  }

  protected WebElement findVisibleElement(By locator) {
    waitForElementToBeVisible(locator);
    return webDriver.findElement(locator);
  }

  protected WebElement findClickableElement(By locator) {
    waitForElementToBeClickable(locator);
    return webDriver.findElement(locator);
  }

  protected WebElement findClickableElement(By locator, int timeout) {
    waitForElementToBeClickable(locator, timeout);
    return webDriver.findElement(locator);
  }

  protected String getTextFromElement(By locator) {
    WebElement element = findElement(locator);
    return element.getText().trim();
  }

  protected String getTextFromElement(WebElement element) {
    return element.getText().trim();
  }

  protected String getAttributeFromElement(By locator, String attribute) {
    WebElement element = findVisibleElement(locator);
    return element.getAttribute(attribute);
  }

  protected String getAttributeFromElement(WebElement element, String attribute) {
    return element.getAttribute(attribute);
  }

  protected void contextClickElement(WebElement element) {
    Actions actions = new Actions(webDriver);
    actions.contextClick(element).perform();
  }

  protected void moveToElement(WebElement element) {
    Point location = element.getLocation();
    int clientHeight = ((Long) ((JavascriptExecutor) webDriver).executeScript("return document.documentElement.clientHeight")).intValue();

    int offset = clientHeight - location.getY();
    if (offset < 0) {
      ((JavascriptExecutor) webDriver).executeScript("window.scrollBy(0, " + (100 - offset) + ");");
    } else {
      ((JavascriptExecutor) webDriver).executeScript("window.scrollBy(0, " + (20 - location.getY()) + ");");
    }
  }

  protected boolean isElementPresent(By locator) {
    try {
      WebElement element = webDriver.findElement(locator);
      return element.isDisplayed();
    } catch (Exception e) {
      return false;
    }
  }

  protected boolean isElementPresent(WebElement element) {
    try {
      return element.isDisplayed();
    } catch (Exception e) {
      return false;
    }
  }

  protected void sleep(long milis) {
    try {
      Thread.sleep(milis);
    } catch (InterruptedException e) {
      throw new RuntimeException("Sleep error", e);
    }
  }

  protected void sleep() {
    sleep(DEFAULT_SLEEP);
  }

  protected void click(WebElement element) {
    ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", element);
  }

  protected String getCurrentUrl() {
    String url = webDriver.getCurrentUrl();
    return url.contains("?") ? url.substring(0, url.indexOf('?')) : url;
  }

  protected void enterIFrame(By iFrameLocator) {
    WebElement iFrame = webDriver.findElement(iFrameLocator);
    webDriver.switchTo().frame(iFrame);
  }

  protected void leaveIFrame() {
    webDriver.switchTo().defaultContent();
  }

  protected void reloadPage() {
    webDriver.navigate().refresh();
  }

  protected void scrollToElement(WebElement element) {
    ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", element);
  }

  protected void scrollToTopOfPage() {
    ((JavascriptExecutor) webDriver).executeScript("window.scrollTo(0, -document.body.scrollHeight)");
    sleep();
  }

  protected void scrollToBottomOfPage() {
    ((JavascriptExecutor) webDriver).executeScript("window.scrollTo(0, document.body.scrollHeight)"); //-250
    sleep();
  }

  protected void scrollDown(int height) {
    ((JavascriptExecutor) webDriver).executeScript("window.scrollTo(0, " + height + ")");
    sleep();
  }

  protected void scrollUp(int height) {
    ((JavascriptExecutor) webDriver).executeScript("window.scrollTo(0, -" + height + ")");
    sleep();
  }

  protected void scrollUpByWebElementHeight(WebElement element) {
    ((JavascriptExecutor) webDriver).executeScript("window.scrollTo(0, -" + element.getSize().height + ")");
  }

  protected void scrollDownByWebElementHeight(WebElement element) {
    ((JavascriptExecutor) webDriver).executeScript("window.scrollTo(0, " + element.getSize().height + ")");
  }

  protected void switchTab(int tabIndex) {
    ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
    webDriver.switchTo().window(tabs.get(tabIndex - 1));
    sleep(1000);
  }

  protected void openNewTab() {
    Actions actions = new Actions(webDriver);
    actions.sendKeys(Keys.chord(Keys.CONTROL, "t")).perform();
    sleep(1000);
  }

  protected void hitEscapeBtn() {
    Actions actions = new Actions(webDriver);
    actions.sendKeys(Keys.ESCAPE).perform();
  }

  protected void checkConditionFulfilled(boolean condition, String exceptionMsg) throws Exception {
    if (!condition) {
      throw new Exception(exceptionMsg);
    }
  }

  protected void checkElementPresent(By locator, String exceptionMsg, int timeOutInSec) throws Exception {
    waitForElementToBeVisibleIgnoredException(locator, timeOutInSec);
    if (!isElementPresent(locator)) {
      throw new Exception(exceptionMsg);
    }
  }

  public void checkCondition(Boolean condition, String errorMessage, List<String> errors) {
    if (condition.equals(Boolean.FALSE)) {
      errors.add(errorMessage);
    }
  }
}
