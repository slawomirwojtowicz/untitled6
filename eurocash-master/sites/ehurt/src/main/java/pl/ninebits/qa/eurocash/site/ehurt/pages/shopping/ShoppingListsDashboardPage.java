package pl.ninebits.qa.eurocash.site.ehurt.pages.shopping;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import pl.ninebits.qa.automated.tests.core.utils.TextUtils;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtBasePage;

import java.text.MessageFormat;
import java.util.List;

public class ShoppingListsDashboardPage extends EhurtBasePage {
  public ShoppingListsDashboardPage(BasePage pageObject) {
    super(pageObject);
    ensurePageLoaded();
  }

  private void ensurePageLoaded() {
    try {
      waitForElementToBeVisible(By.xpath("//form[@name='listaZakupowa']"));
      waitUntil(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".list-main")));
    } catch (Exception e) {
      Assert.fail(MessageFormat.format("Strona {0} nie została wczytana poprawnie", ShoppingListsDashboardPage.class.getSimpleName()));
    }
  }

  public ShoppingListEditPage clickNewShoppingListBtn() {
    WebElement addNewListBtn = findClickableElement(By.xpath("//span[contains(text(),'STWÓRZ NOWĄ LISTĘ')]"));
    addNewListBtn.click();

    return new ShoppingListEditPage(this);
  }

  public void checkListPresent(String listName, List<String> errors) throws Exception {
    waitForDataIsLoaded();
    sleep(1500); //TODO: namierzyć się na loader w headerze list
    List<String> listNames = TextUtils.getTextFromWebElementsList(webDriver.findElements(By.xpath("//strong[@class='shoppingListName lcm-tb']")));
    if (!listNames.contains(listName)) {
      errors.add(MessageFormat.format("Brak listy {0} na stronie Listy Zakupowe", listName));
    }
  }

  public void removeList(String listName) {
    WebElement removeBtn =
      findVisibleElement(By.xpath("//strong[@class='shoppingListName lcm-tb' and contains(text(),'" + listName + "')]/../..//i[contains(@class,'delete-list')]"));
    scrollToElement(removeBtn);
    // sleep(5000);
    removeBtn.click();
    WebElement delDialogBtn = findVisibleElement(By.cssSelector(".fancy-btn--normal.bmc"));
    delDialogBtn.click();
    waitForElementToBeInvisible(delDialogBtn);
    waitForElementToBeInvisible(removeBtn);
  }

  public void checkListRemoved(String listName, List<String> errors) throws Exception {
    List<WebElement> listNameElems = webDriver.findElements(By.xpath("//div[@class='shoppingListName']"));
    List<String> listNames = TextUtils.getTextFromWebElementsList(listNameElems);
    if (listNames.contains(listName)) {
      errors.add(MessageFormat.format("Lista zakupowa {0} nie została usunięta", listName));
    }
  }

  public ShoppingListPage clickOpenList(String listName) {
    WebElement listLink = findVisibleElement(By.xpath("//strong[contains(@class,'shoppingListName')][contains(text(),'" + listName + "')]"));
    listLink.click();

    return new ShoppingListPage(this);
  }

  public void removeLists() {
    List<WebElement> lists = webDriver.findElements(By.cssSelector(".delete-list"));
    while (lists.size() > 0) {
      lists.get(0).click();
      sleep();
      findVisibleElement(By.cssSelector(".fancy-btn--normal.bmc")).click();
      waitForElementToBeInvisible(By.cssSelector(".fancy-btn--normal.bmc"));
      sleep(400);
      lists = webDriver.findElements(By.cssSelector(".delete-list"));
    }
  }
}
