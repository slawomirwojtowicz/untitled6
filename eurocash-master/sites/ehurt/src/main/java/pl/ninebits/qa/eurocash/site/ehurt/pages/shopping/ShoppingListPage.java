package pl.ninebits.qa.eurocash.site.ehurt.pages.shopping;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pl.ninebits.qa.automated.tests.core.utils.TextUtils;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtBasePage;

import java.text.MessageFormat;
import java.util.List;

public class ShoppingListPage extends EhurtBasePage {
  public ShoppingListPage(BasePage pageObject) {
    super(pageObject);
    waitForDataIsLoaded();
  }


  public ShoppingListEditPage clickEditList() {
    waitForElementToBeInvisible(By.cssSelector(".snackbar-body"));
    findClickableElement(By.cssSelector(".edit-list")).click();

    return new ShoppingListEditPage(this);
  }

  public void selectAllProducts() {
    findClickableElement(By.xpath("//label[@for='selectAll']/span")).click();
    sleep();
  }

  public void clickAddToCart() {
    findClickableElement(By.cssSelector(".accept-order-btn")).click();
    waitForElementToBeVisible(By.xpath("//span[contains(text(),'dodane do koszyka')]"), 5);
  }

  public CartStep1Page clickGoToCart() {
    findClickableElement(By.cssSelector(".fancy-btn--normal.bcm")).click();

    return new CartStep1Page(this);
  }

  public void checkProductOnList(List<String> products, List<String> errors) {
    for (String product : products) {
      if (product.length() > 10) {
        product = product.substring(0, product.indexOf(" "));
      }
      if (!isElementPresent(By.xpath("//table//span[@openproductdetails][contains(text(),'" + product + "')]"))) {
        errors.add(MessageFormat.format("Produkt {0} nie widnieje na liście", product));
      }
    }
  }

  public ShoppingListsDashboardPage clickBackToLists() {
    findClickableElement(By.cssSelector(".pointer.lcm")).click();
    return new ShoppingListsDashboardPage(this);
  }

  public void checkListNotEmpty(String listName, List<String> errors) {
    int gridRows = webDriver.findElements(By.xpath("//tr[@class='sltable__headerbar']//..//tr")).size();
    if (gridRows < 1) {
      errors.add(MessageFormat.format("Lista {0} jest pusta", listName));
    }
  }

  public String getFirstProductName() {
    return findVisibleElement(By.xpath("(//span[contains(@class,'product-details-opener')]/a)[1]")).getText();
  }

  public void clickRemoveFirstProduct() {
    findClickableElement(By.xpath("(//span[contains(@class,'delete-btn')])[2]")).click();
    sleep(1000);
    waitForDataIsLoaded();
    sleep(1000);
  }

  public void checkProductRemoved(String product, List<String> errors) {
    if (isElementPresent(By.xpath("//span[contains(@class,'product-details-opener')]/a[contains(text(),'" + product + "')]"))) {
      errors.add(MessageFormat.format("Produkt {0} nie został usunięty z listy", product));
    }
  }

  public void clickSelectProducts(int numberOfProducts) {
    List<WebElement> elements = webDriver.findElements(By.xpath("//td//label[@for]/span"));

    for (int i = 0; i < numberOfProducts; i++) {
      elements.get(i).click();
    }
  }

  public List<String> getProductNames() throws Exception {
    List<WebElement> elements = webDriver.findElements(By.xpath("//span[contains(@class,'product-details-opener')]/a"));
    return TextUtils.getTextFromWebElementsList(elements);
  }

  public void clickRemoveSelected() {
    findClickableElement(By.xpath("//span[contains(@class,'delete-btn')][contains(.,'Usuń zaznaczone')]")).click();
    sleep(2000);
    waitForDataIsLoaded();
  }

  public int getListSize() {
    waitForDataIsLoaded();
    waitForPageToLoad();
    sleep(1000);
    return webDriver.findElements(By.xpath("//table//span[@openproductdetails]")).size();
  }

  public void checkProductsRemoved(int listSize, int listSizeAfterDeletion, int productsRemoved, List<String> errors) {
    if (listSizeAfterDeletion != listSize - productsRemoved) {
      errors.add("Produkty z listy nie zostały usunięte");
    }
  }
}
