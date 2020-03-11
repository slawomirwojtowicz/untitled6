package pl.ninebits.qa.eurocash.site.ehurt.pages.shopping;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtBasePage;

import java.text.MessageFormat;
import java.util.List;

public class ShoppingListEditPage extends EhurtBasePage {
  public ShoppingListEditPage(BasePage pageObject) {
    super(pageObject);
  }

  public void enterListName(String listName) {
    WebElement listNameInput = findVisibleElement(By.xpath("//input[@name='shoppingListNameInput']"));
    listNameInput.clear();
    listNameInput.sendKeys(listName);
  }

  public void checkListNamePresentInHeader(String listName, List<String> errors) {
    if (!isElementPresent(By.xpath("//div[@class='breadcrumbs' and contains(.,'" + listName + "')]"))) {
      errors.add(MessageFormat.format("Brak headera z nazwą listy {0}", listName));
    }
  }

  public void clickSaveListBtn() {
    WebElement saveBtn = findClickableElement(By.xpath("//span[contains(@class,'btn-save-list') and contains(text(),'ZAPISZ')]"));
    saveBtn.click();
    waitForElementToBeVisible(By.xpath("//div[@class='snackbar-message' and contains(text(),'Lista zakupowa została utworzona')]"));
  }

  public ShoppingListsDashboardPage clickBackToAllListsBtn() {
    WebElement allListsLink = findClickableElement(By.xpath("//span[@class='pointer lcm']"));
    allListsLink.click();

    return new ShoppingListsDashboardPage(this);
  }

  public void checkShoppingListPagePresent(List<String> errors) {
    if (!isElementPresent(By.name("listaZakupowa"))) {
      errors.add("Strona z listami zakupów nie została wczytana poprawnie");
    }
  }

  public void typeSearchPhrase(String searchPhrase) {
    findVisibleElement(By.id("offerSearch")).sendKeys(searchPhrase);
  }

  public String getFirstSuggestedProduct() {
    return findVisibleElement(By.xpath("//li[contains(@class,'suggestion')]//div")).getText();
  }

  public void clickSuggestedProduct(String productName) {
    findClickableElement(By.xpath("//li[contains(@class,'suggestion')]//div[contains(text(),'" + productName + "')]")).click();
  }

  public void checkProductChosen(String productName, List<String> errors) {
    if (!isElementPresent(By.xpath("//span[.='Towar:']/..//span[.='" + productName + "']"))) {
      errors.add(MessageFormat.format("Produkt {0} nie został wybrany", productName));
    }
  }

  public String getChosenProductBarcode(List<String> errors) {
    String barcode = getTextFromElement(By.xpath("//span[text()='Kod kreskowy:']/../span[2]"));
    if (barcode.isEmpty()) {
      errors.add("Nie pobrano kodu kreskowego z listy zakupowej");
    }
    return barcode;
  }

  public void enterPackageAmount(String amount) {
    WebElement amountField = findVisibleElement(By.xpath("(//span[contains(text(),'Opak')]/..//input)[1]"));
    amountField.sendKeys(amount);
    amountField.sendKeys(Keys.RETURN);
  }

  public String getPackingMethod() {
    return findVisibleElement(By.xpath("//span[contains(text(),'Sposób pakowania')]/../span[2]")).getText().replaceAll("[A-Za-z.,\\s]", "");
  }

  public void clickAddToShoppingList() {
    findClickableElement(By.cssSelector(".btn-add-to-list")).click();
  }

  public void checkProductAdded(String productName, List<String> errors) {
    try {
      waitForElementToBeVisible(By.xpath("//table//span[@openproductdetails][contains(text(),'" + productName + "')]"), 10);
    } catch (Exception e) {
      errors.add(MessageFormat.format("Produkt {0} nie został dodany do listy", productName));
    }
  }

  public List<String> addProductsToList(String searchPhrase, String packageAmount, List<String> barCodes, List<String> errors) {
    typeSearchPhrase(searchPhrase);
    String productName = getFirstSuggestedProduct();
    clickSuggestedProduct(productName);
    checkProductChosen(productName, errors);
    barCodes.add(getChosenProductBarcode(errors));
    enterPackageAmount(packageAmount);
    clickAddToShoppingList();
    checkProductAdded(searchPhrase, errors);

    return barCodes;
  }

 /* public String getUnits() {
    findVisibleElement(By.xpath("(//span[contains(text(),'Opak')]/..//input)[2]"));

    return null;
  }

  public void checkUnits(String packageAmount, String packingMethod, List<String> errors) {
    int expectedUnitsAmount = Integer.valueOf(packageAmount) * Integer.valueOf(packingMethod);
    if (Integer.valueOf(getUnits()) != expectedUnitsAmount) {
      errors.add(MessageFormat.format("Nie zgadza się liczba jednostek. Oczekiwano {0} jest {1}", expectedUnitsAmount,Integer.valueOf(getUnits())));
    }
  }*/
}
