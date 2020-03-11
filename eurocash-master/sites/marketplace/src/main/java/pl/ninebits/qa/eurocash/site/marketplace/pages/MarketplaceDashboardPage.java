package pl.ninebits.qa.eurocash.site.marketplace.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.components.Footer;
import pl.ninebits.qa.eurocash.site.marketplace.pages.components.Header;

import java.text.MessageFormat;
import java.util.List;

public class MarketplaceDashboardPage extends MarketplaceBasePage {
  public MarketplaceDashboardPage(BasePage pageObject) {
    super(pageObject);
    closePopups();
    closeNotificationsMenu();
    closeReminderBox();
  }

  public final Header header = new Header(createPageObject(Header.class));
  public final Footer footer = new Footer(createPageObject(Footer.class));

  private void closePopups() {
    waitForPageToLoad();
    List<WebElement> closeBtns = webDriver.findElements(By.cssSelector("app-notice-popup .popup-close"));
    while (!closeBtns.isEmpty()) {
      closeBtns.get(0).click();
      waitForPageToLoad();
      closeBtns = webDriver.findElements(By.cssSelector("app-notice-popup .popup-close"));
    }

    closeBtns = webDriver.findElements(By.cssSelector("app-popup .popup-close"));
    while (!closeBtns.isEmpty()) {
      closeBtns.get(0).click();
      waitForPageToLoad();
      closeBtns = webDriver.findElements(By.cssSelector("app-popup .popup-close"));
    }
  }

  private void closeNotificationsMenu() {
    waitForPageToLoad();
    List<WebElement> hideBtns = webDriver.findElements(By.cssSelector(".close-button"));
    while (!hideBtns.isEmpty()) {
      hideBtns.get(0).click();
      waitForPageToLoad();
      hideBtns = webDriver.findElements(By.cssSelector(".close-button"));
    }
  }

  private void closeReminderBox() {
    if (isElementPresent(By.cssSelector(".alert__link"))) {
      WebElement element = webDriver.findElement(By.linkText("Strona główna"));
      element.click();
      waitForPageToLoad();
    }
  }

  public void checkUserLoggedIn(String login, List<String> errors) throws Exception {
    String loginXpath = "//div[contains(text(),'Zalogowany jako')]/../div[2]";

    if (isElementPresent(By.xpath(loginXpath))) {
      if (!getTextFromElement(By.xpath(loginXpath)).contains(login)) {
        errors.add(MessageFormat.format("Brak info o zalogowanym użytkowniku {0}", login));
      }
    } else {
      throw new Exception(MessageFormat.format("Uzytkownik {0} nie został zalogowany", login));
    }
  }

  public ShoppingListPage clickShoppinListgBtn() {
    WebElement shoppingMenuLink = findElement(By.cssSelector(".icons-heart"));
    shoppingMenuLink.click();
    waitForDataIsLoaded();
    return new ShoppingListPage(this);
  }

}
