package pl.ninebits.qa.eurocash.site.marketplace.forms;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;

public class ShoppingMenu extends MarketplaceBaseForm {
  public ShoppingMenu(BasePage page) {
    super(page);
  }


  public void typeSearchQuery(String searchQuery) {
    WebElement searchInputInShoppingMenu = findElement(By.cssSelector("nav-shopping-tags input"), "searchInputInShoppingMenu");
    searchInputInShoppingMenu.click();
    searchInputInShoppingMenu.clear();
    searchInputInShoppingMenu.sendKeys(searchQuery, Keys.RETURN);
  }
}
