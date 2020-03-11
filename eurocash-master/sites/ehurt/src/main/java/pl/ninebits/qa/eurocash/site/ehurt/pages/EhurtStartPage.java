package pl.ninebits.qa.eurocash.site.ehurt.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class EhurtStartPage extends EhurtBasePage {

  public EhurtStartPage(BasePage page) {
    super(page);
    checkLoginFormLoaded();
  }

  public EhurtStartPage(WebDriver webDriver, int actionTimeout) {
    super(webDriver, actionTimeout);
  }

  private void checkLoginFormLoaded() {
    try {
      waitForElementToBeVisibleIgnoredException(By.id("logInForm"), 60);
    } catch (Exception e) {
      throw new RuntimeException("Nie wczytał się formularz logowania");
    }
  }

  public void typeLogin(String login) {
    WebElement loginInput = findElement(By.xpath("//input[@name='login']"));
    loginInput.clear();
    loginInput.sendKeys(login);
  }

  public void typeLogin(String login, List<String> errors) {
    try {
      WebElement loginInput = findElement(By.xpath("//input[@name='login']"));
      loginInput.clear();
      loginInput.sendKeys(login);
    } catch (Exception e) {
      errors.add("Brak pola do wpisania loginu (3)");
    }
  }

  public void typePassword(String password) {
    WebElement passwordInput = findElement(By.xpath("//input[@name='pass']"));
    passwordInput.clear();
    passwordInput.sendKeys(password);
  }

  public void typePassword(String password, List<String> errors) {
    try {
      WebElement passwordInput = findElement(By.xpath("//input[@name='pass']"));
      passwordInput.clear();
      passwordInput.sendKeys(password);
    } catch (Exception e) {
      errors.add("Brak pola do wpisania hasła");
    }
  }

  public BasePage clickLoginBtn() {
    String pageUrl = getCurrentUrl();

    WebElement loginBtn = findElement(By.xpath("//input[@value='Zaloguj się']"));
    loginBtn.click();
    waitForPageToLoad();

    return getCurrentUrl().equals(pageUrl) ? this : new EhurtHomePage(this);
  }

  public BasePage clickLoginBtn(List<String> errors) {
    String pageUrl = getCurrentUrl();
    try {
      WebElement loginBtn = findElement(By.xpath("//input[@value='Zaloguj się']"));
      loginBtn.click();
      waitForPageToLoad();
    } catch (Exception e) {
      errors.add("Brak przycisku zaloguj");
    }

    return getCurrentUrl().equals(pageUrl) ? this : new EhurtHomePage(this);
  }

  public void checkLoginErrorMessage(boolean shouldBeVisible, List<String> errors) {
    if (shouldBeVisible && !isElementPresent(By.xpath("//label[@class='error']/span[contains(text(),'Nieprawidłowy login')]"))) {
      errors.add("Brak komunikatu o błędnych danych logowania.");
    }
  }

  private void checkErrorMessage(boolean shouldBeVisible, String message, List<String> errors) {
    List<WebElement> errorsElements = new ArrayList<WebElement>();
    errorsElements.addAll(webDriver.findElements(By.className("error")));

    boolean messagePresent = false;
    for (WebElement errorsElement : errorsElements) {
      if (message.equals(errorsElement.getText())) {
        messagePresent = true;
        break;
      }
    }

    if (shouldBeVisible && !messagePresent) {
      errors.add(MessageFormat.format("Error message ''{0}'' not present.", message));
    }
    if (!shouldBeVisible && messagePresent) {
      errors.add(MessageFormat.format("Error message ''{0}'' present.", message));
    }
  }
}