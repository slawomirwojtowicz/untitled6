package pl.ninebits.qa.eurocash.site.marketplace.pages.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;
import pl.ninebits.qa.eurocash.site.marketplace.forms.ShoppingMenu;
import pl.ninebits.qa.eurocash.site.marketplace.pages.CartsToPayPage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.MarketplaceBasePage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.MyAccountPage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.ShoppingPage;

import java.time.Duration;

public class Header extends MarketplaceBasePage {
  public Header(BasePage pageObject) {
    super(pageObject);
  }

  public ShoppingPage clickShoppingBtn() {
    WebElement shoppingMenuLink = findElement(By.linkText("Zakupy"), "shoppingMenuLink");
    shoppingMenuLink.click();
    waitForDataIsLoaded();
    ensureMenuClosed();
    waitForDataIsLoaded();

    return new ShoppingPage(this);
  }

  private void ensureMenuClosed() {
    if (isElementPresent(By.xpath("//a[text()='Zakupy']"))) {
      WebElement shoppingBtn = findElement(By.xpath("//a[text()='Zakupy']"));
      shoppingBtn.click();
      waitForElementToBeInvisible(By.xpath("//a[text()='Zakupy']"));
    }
  }

  public CartsToPayPage openCartsToPayPage() {
    webDriver.get("http://marketplaceuat.eurocash.pl/moje-konto/koszyki"); //todo: klikanie po linkach
    return new CartsToPayPage(this);
  }

  public MyAccountPage clickMyAccountBtn() {
    WebElement myAccountLink = findElement(By.linkText("Moje konto"), "myAccountLink");
    myAccountLink.click();
    waitForDataIsLoaded();
    return new MyAccountPage(this);
  }

  public ShoppingPage searchForProduct(String searchQuery) {
    typeSearchQuery(searchQuery);
    clickSearchBtn();
    waitForDataIsLoaded();
    return new ShoppingPage(this);
  }

  private void typeSearchQuery(String searchQuery) {
    WebElement searchInput = findElement(By.cssSelector(".autocomplete-search"), "searchInput");
    searchInput.clear();
    searchInput.sendKeys(searchQuery);
  }

  private void clickSearchBtn() {
    WebElement searchBtn = findElement(By.cssSelector(".icon-search"), "searchBtn");
    searchBtn.click();
    waitForDataIsLoaded();
  }

  public ShoppingMenu spanShoppingMenu() {
    actionStream()
      .moveToElement(findElement(By.xpath("//a[contains(@class,'menu-item__link') and contains(text(),'Zakupy')]"), "shoppingMenu"))
      .pause(Duration.ofSeconds(1))
      .perform();
    return new ShoppingMenu(this);
  }
}
