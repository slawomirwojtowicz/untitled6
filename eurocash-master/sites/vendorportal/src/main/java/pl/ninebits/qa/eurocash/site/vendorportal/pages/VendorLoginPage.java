package pl.ninebits.qa.eurocash.site.vendorportal.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;

import java.util.List;

public class VendorLoginPage extends VendorPortalBasePage {
  public VendorLoginPage(BasePage pageObject) {
    super(pageObject);
  }

  public VendorLoginPage(WebDriver webDriver, int actionTimeout) {
    super(webDriver, actionTimeout);
  }

  public void typeLogin(String login) {
    WebElement emailInput = findElement(By.id("email"), "emailInput");
    emailInput.clear();
    emailInput.sendKeys(login);
  }

  public void typePassword(String password) {
    WebElement passwordInput = findElement(By.id("password"), "passwordInput");
    passwordInput.clear();
    passwordInput.sendKeys(password);
  }

  public VendorDashboardPage clickLoginBtnAndCheckIfUserLoggedInSuccessfully() throws Exception {
    clickLoginBtn();
    List<WebElement> elements = webDriver.findElements(By.cssSelector(".input-error-message"));
    if (!elements.isEmpty()) {
      throw new Exception(String.format("Login error: %s", elements.get(0).getText()));
    }

    return new VendorDashboardPage(this);
  }

  public void clickLoginBtn() {
    WebElement loginBtn = findElement(By.xpath("//span[contains(.,'Zaloguj się')]"), "loginBtn");
    loginBtn.click();
    waitVendorForDataIsLoaded();
  }

  public void checkIncorrectCredentialsMsgPresent(List<String> errors) {
    if (!isElementPresent(By.cssSelector(".input-error-message"))) {
      errors.add("Brak komunikatu o błędzie logowania.");
    }
  }
}