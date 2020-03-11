package pl.ninebits.qa.eurocash.site.marketplace.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;

public class MarketplaceLoginPage extends MarketplaceBasePage {
  public MarketplaceLoginPage(WebDriver webDriver, int actionTimeout) {
    super(webDriver, actionTimeout);
  }

  public MarketplaceLoginPage(BasePage pageObject) {
    super(pageObject);
  }


  public void typeLogin(String login) {
    WebElement userInput = findElement(By.id("user"));
    userInput.clear();
    userInput.sendKeys(login);
  }

  public void typePassword(String password) {
    WebElement passwordInput = findElement(By.id("pass"));
    passwordInput.clear();
    passwordInput.sendKeys(password);
  }

  public MarketplaceDashboardPage clickLoginBtn() {
    WebElement loginBtn = findElement(By.cssSelector("[value='Zaloguj się']"));
    loginBtn.click();

    return new MarketplaceDashboardPage(this);
  }
}
