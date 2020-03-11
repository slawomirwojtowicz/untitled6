package pl.ninebits.qa.eurocash.site.ehurt.pages.shopping;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import pl.ninebits.qa.automated.tests.core.utils.TextUtils;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;
import pl.ninebits.qa.eurocash.site.ehurt.forms.SettingsForm;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtBasePage;

import java.text.MessageFormat;
import java.util.List;

public class CartStep1Page extends EhurtBasePage {
  public CartStep1Page(BasePage pageObject) {
    super(pageObject);
    waitForDataIsLoaded();
  }

  @Override
  protected void validateCaptcha() {
    super.validateCaptcha();
  }

  public void checkCartStep1PageLoaded(List<String> errors) {
    if (!isElementPresent(By.cssSelector("#cartGrid"))) {
      errors.add("Strona z koszykiem nie została wczytana poprawnie");
    }
  }

  public void checkProductsInCart(List<String> productNames, List<String> errors) {
    sleep(2000);
    waitForDataIsLoaded();
    for (String productName : productNames) {
      if (productName.contains("'")) {
        productName = productName.substring(0, productName.indexOf("'"));
        if (productName.length() > 10) {
          productName = productName.substring(0, 10);
        }
      }
      if (!isElementPresent(By.xpath("//table//span[@openproductdetails][contains(text(),'" + productName + "')]"))) {
        errors.add(MessageFormat.format("Produkt {0} nie znajduje się w koszyku", productName));
      }
    }
  }

  private void waitForCartToBeEmpty() {
    waitForElementToBeVisible(By.cssSelector("null-holder"), 10);
  }

  public void clickProductCheckboxes(int numberOfCheckboxesToCheck) {
    for (int i = 1; i <= numberOfCheckboxesToCheck; i++) {
      findClickableElement(By.xpath("(//td[@class='coll-min'][.='" + i + "']/..//label)[1]")).click();
    }
  }

  public void clickListActionMenu() {
    findClickableElement(By.xpath("//span[contains(text(),'Dodaj produkt')]")).click();
    try {
      waitForElementToBeVisible(By.xpath("//div[contains(text(),'produkty do listy')]/..//span[@class='shopping-list-link pointer lcm']"));
    } catch (Exception e) {
      Assert.fail("Nie rozwineło sie menu operacji na listach w koszyku");
    }
  }

  public void addToListInListActionMenu(String listName, List<String> errors) {
    WebElement menu = findVisibleElement(By.xpath("//div[contains(text(),'Dodaj zaznaczone produkty')]//..//div[@class='ech-dropdown']"));
    menu.click();
    WebElement clickList = findVisibleElement(By.xpath("(//li[@class='ech-dropdown__list-item'][contains(.,'" + listName + "')])[3]"));
    clickList.click();
    if (isElementPresent(By.xpath("//span[contains(.,'" + listName + "')]"))) {
      findClickableElement(By.xpath("//span[contains(text(),'Dodaj do listy')]")).click();
    } else {
      errors.add(MessageFormat.format("Brak listy {0} do wyboru", listName));
    }
  }

  public void makeListFromCart(String listName) {
    clickActionsInCartBtn();
    findClickableElement(By.xpath("//div[contains(text(),'Stwórz listę')]")).click();
    findVisibleElement(By.xpath("//input[@name='shoppingListName']")).sendKeys(listName);
    findClickableElement(By.xpath("//span[contains(text(),'Utwórz listę')]")).click();
  }

  private void clickActionsInCartBtn() {
    findClickableElement(By.xpath("//span[contains(.,'Akcje w koszyku')]")).click();
  }

  public void replaceCartWithList(String listName, List<String> errors) {
    WebElement replaceWithListMenu = findVisibleElement(By.xpath("//div[contains(text(),'Zastąp koszyk')]/..//span[@role='listbox']"));
    replaceWithListMenu.click();
    for (int i = 0; i < 10; i++) {
      replaceWithListMenu.sendKeys(Keys.ARROW_DOWN);
      if (isElementPresent(By.xpath("//span[@class='k-input' and contains(.,'" + listName + "')]"))) {
        WebElement replaceBtn = findClickableElement(By.xpath("//span[contains(text(),'Zastąp koszyk')]"));
        replaceBtn.click();
        replaceBtn.click();
        sleep(3000);
        waitForPageToLoad();
        waitForDataIsLoaded();
        break;
      } else if (i == 9 && !isElementPresent(By.xpath("//span[@class='k-input' and contains(.,'" + listName + "')]"))) {
        errors.add(MessageFormat.format("Nie udało się wybrać listy {0}", listName));
      }
    }
  }

  public void addProductsFromListToCart(String listName, List<String> errors) {
    WebElement addListMenu = findVisibleElement(By.xpath("//div[contains(text(),'Dopisz produkty')]/..//span[@role='listbox']"));
    addListMenu.click();
    for (int i = 0; i < 10; i++) {
      addListMenu.sendKeys(Keys.ARROW_DOWN);
      if (isElementPresent(By.xpath("//span[@class='k-input' and contains(.,'" + listName + "')]"))) {
        WebElement addBtn = findClickableElement(By.xpath("//span[contains(text(),'Dopisz do koszyka')]"));
        addBtn.click();
        addBtn.click();
        sleep(3000);
        waitForPageToLoad();
        waitForDataIsLoaded();
        break;
      } else if (i == 9 && !isElementPresent(By.xpath("//span[@class='k-input' and contains(.,'" + listName + "')]"))) {
        errors.add(MessageFormat.format("Nie udało się wybrać listy {0}", listName));
      }
    }
  }

  public List<String> getProductNames() throws Exception {
    List<String> productNames = TextUtils.getTextFromWebElementsList(webDriver.findElements(By.xpath("//table//span[@openproductdetails]")));
    if (productNames.isEmpty()) {
      Assert.fail("Nie pobrano nazw produktów w koszyku");
    }
    return productNames;
  }

  public void removeFirstProduct() {
    findClickableElement(By.cssSelector(".eh-i-usun")).click();
    waitForDataIsLoaded();
  }

  public void checkProductInCartPresent(String productName, Boolean shouldBePresent, List<String> errors) {
    if (!shouldBePresent) {
      if (isElementPresent(By.xpath("//table//span[@openproductdetails][contains(text(),'" + productName + "')]"))) {
        errors.add(MessageFormat.format("Produkt {0} nie został usunięty z koszyka", productName));
      }
    } else {
      if (!isElementPresent(By.xpath("//table//span[@openproductdetails][contains(text(),'" + productName + "')]"))) {
        errors.add(MessageFormat.format("Produkt {0} nie znajduje się w koszyku", productName));
      }
    }
  }

  public ProductDetailsPage clickProductDetails(String productName) {
    WebElement productLink = findClickableElement(By.xpath("//span[contains(@class,'product-details-opener') and contains(.,'" + productName + "')]"));
    productLink.click();
    waitForDataIsLoaded();
    return new ProductDetailsPage(this);
  }

  public void enterSearchPhrase(String productIndex) {
    WebElement offerHeaderSearch = findVisibleElement(By.id("offerHeaderSearch"));
    offerHeaderSearch.sendKeys(productIndex);
    offerHeaderSearch.sendKeys(Keys.RETURN);
    waitForDataIsLoaded();
    sleep(1000);
  }

  public void checkCartSize(int expectedSize, List<String> errors) {
    int size = webDriver.findElements(By.xpath("//ech-cart-grid//table[@id='tableBodyGrid']//tr")).size();

    if (size != expectedSize) {
      errors.add(MessageFormat.format("Oczewana ilośc produktów w koszyku {0} a jest {1}", expectedSize, size));
    }
  }

  public void checkCartEmpty(List<String> errors) {
    if (!isElementPresent(By.xpath("//td[contains(text(),'Brak danych')]"))) {
      errors.add("Brak informacji o pustym koszyku");
    }

    if (webDriver.findElements(By.xpath("//ech-cart-grid//table[@id='tableBodyGrid']//tr[not(contains(@class,'null'))]")).size() > 0) {
      errors.add("W koszyku znajdują się nieusunięte produkty");
    }
  }

  public SettingsForm clickGridSettingsBtn() {
    findClickableElement(By.xpath("//ech-grid-custom-settings//i[contains(@class,'cog')]")).click();
    waitForElementToBeVisible(By.cssSelector(".grid-settings-content"), 10);
    return new SettingsForm(this);
  }

  public void checkNumberOfRows(String numberOfRows, List<String> errors) {
    if (webDriver.findElements(By.xpath("//table[@id='tableBodyGrid']//tr")).size() != Integer.valueOf(numberOfRows)) {
      errors.add(MessageFormat.format("W koszyku znajduje sie inna ilość wierszy niż {0}", numberOfRows));
    }
  }

  public void checkGrossValueLarger(List<String> errors) {
    if (getGrossCartValue() <= getNetCartValue()) {
      errors.add("Wartość brutto w koszyku nie jest większa od wartości netto");
    }
  }

  private double getNetCartValue() {
    String text = findVisibleElement(By.xpath("//span[@class='current-cart-worth__item' and contains(text(),'netto')]/strong")).getText();
    text = text.replaceAll("[\\s\\u00A0zł]", "");
    text = text.replace(",", ".");
    return Double.valueOf(text);
  }

  private double getGrossCartValue() {
    String text = findVisibleElement(By.xpath("//span[@class='current-cart-worth__item' and contains(text(),'brutto')]/strong")).getText();
    text = text.replaceAll("[\\s\\u00A0zł]", "");
    text = text.replace(",", ".");
    return Double.valueOf(text);
  }

  public void checkProductsInCartByBarcode(List<String> productBarcodes, List<String> errors) throws Exception {
    sleep(2000);
    waitForDataIsLoaded();
    int columnHeaderIndex = getColumnHeaderIndex("Kod kresk.");
    for (String productBarCode : productBarcodes) {
      if (!isElementPresent(By.xpath("//td[@title='" + productBarCode + "']"))) {
        errors.add(MessageFormat.format("Produkt o Kodzie kreskowym {0} nie znajduje się w koszyku", productBarCode));
      }
    }
  }

  private int getColumnHeaderIndex(String columnHeader) throws Exception {
    scrollToElement(webDriver.findElement(By.xpath("//div[contains(@class,'headView')]//td[contains(@class,'coll')][contains(text(),'Kod kresk')]")));
    List<String> headers = TextUtils.getTextFromWebElementsList(
      webDriver.findElements(By.xpath("//div[contains(@class,'headView')]//td[contains(@class,'coll')]")));

    return headers.indexOf(columnHeader) + 5;//TODO:puste kolumny - zbadać jeszcze
  }

  public CartStep2Page clickAcceptOrderBtn() {
    WebElement acceptOrderBtn = findElement(By.cssSelector(".accept-order-btn"));
    acceptOrderBtn.click();
    return new CartStep2Page(this);
  }

  public void clickBudgetDown(String productName, List<String> errors) {
      enterSearchPhrase(productName);
      WebElement element = findElement(By.xpath("//div[contains(@class,'budget-input-wrapper')]//img[contains(@class, 'fa-arrow-down')]"));
      if(element != null) {
          element.click();
          waitForDataIsLoaded();
      }
      else {
          errors.add(MessageFormat.format("Produkt o Kodzie kreskowym {0} nie znajduje się w koszyku", productName));
      }
  }

  public void cartImport(String path, String format) {
    WebElement popup = findVisibleElement(By.cssSelector(".headerBarContent .popup-filter-btn"));
    popup.click();
    waitForElementToBeVisible(By.cssSelector(".assortiment-popup"));
    findClickableElement(By.xpath("//div[contains(@class,'assortiment-popup')]//div[contains(@class,'action-popup__item')][contains(text(), 'Import')]")).click();

    waitForElementToBeVisible(By.xpath("//div[contains(@class, 'dx-popup-normal')]//div[contains(text(), 'Import zamówień do koszyka')]"));
    WebElement formatInput = findVisibleElement(By.cssSelector(".dx-popup-normal .dx-dropdowneditor-input-wrapper .dx-texteditor-input"));
    formatInput.clear();
    formatInput.sendKeys(format);
    waitForElementToBeVisible(By.cssSelector(".dx-dropdownlist-popup-wrapper .dx-scrollable-container"));
    formatInput.sendKeys(Keys.RETURN);

    WebElement fileForm = findElement(By.name("fileInputForm"));
    WebElement fileInput = fileForm.findElement(By.xpath("input"));
    ((JavascriptExecutor) webDriver).executeScript("arguments[0].setAttribute('dispaly', 'block')", fileInput);
    fileInput.sendKeys(path);

    sleep(500);
    By by = By.xpath("//div[contains(@class,'fancy-btn')][text()='Importuj']");
    findClickableElement(by).click();
    waitForElementToBeInvisible(by);

    By popupBy = By.xpath("//div[contains(@class, 'dx-popup-normal')]//div[contains(text(), 'Import zamówień do koszyka')]");
    waitForElementToBeVisible(popupBy);
    new WebDriverWait(webDriver, 300).until(ExpectedConditions.invisibilityOfAllElements(webDriver.findElements(By.xpath("//div[contains(@class,'loading loading-panel')]"))));
    by = By.xpath("//div[contains(@class,'fancy-btn')][contains(text(),'Przepisz do koszyka')]");
    waitForElementToBeClickable(by);
    findClickableElement(by).click();
    waitForElementToBeInvisible(popupBy, 300);

    waitForDataIsLoaded();
  }

  public void cartImportStep1(String path, String format) {
    WebElement popup = findVisibleElement(By.cssSelector(".headerBarContent .popup-filter-btn"));
    popup.click();
    waitForElementToBeVisible(By.cssSelector(".assortiment-popup"));
    findClickableElement(By.xpath("//div[contains(@class,'assortiment-popup')]//div[contains(@class,'action-popup__item')][contains(text(), 'Import')]")).click();

    waitForElementToBeVisible(By.xpath("//div[contains(@class, 'dx-popup-normal')]//div[contains(text(), 'Import zamówień do koszyka')]"));
    WebElement formatInput = findVisibleElement(By.cssSelector(".dx-popup-normal .dx-dropdowneditor-input-wrapper .dx-texteditor-input"));
    formatInput.clear();
    formatInput.sendKeys(format);
    waitForElementToBeVisible(By.cssSelector(".dx-dropdownlist-popup-wrapper .dx-scrollable-container"));
    formatInput.sendKeys(Keys.RETURN);

    WebElement fileForm = findElement(By.name("fileInputForm"));
    WebElement fileInput = fileForm.findElement(By.xpath("input"));
    ((JavascriptExecutor) webDriver).executeScript("arguments[0].setAttribute('dispaly', 'block')", fileInput);
    fileInput.sendKeys(path);
    sleep(500);
  }

  public void cartImportStep2() {
    By by = By.xpath("//div[contains(@class,'fancy-btn')][text()='Importuj']");
    //bywa, że mimo, że element jest widoczny i do kliknięcia nie działa klik!?
    //jeżeli to nie pomoże to słać ENTER zamiast klik myszy - jeżeli zadziała na tych przyciskach
    //findClickableElement(by).click();
    findVisibleElement(by).click();
    waitForElementToBeInvisible(by);

    By popupBy = By.xpath("//div[contains(@class, 'dx-popup-normal')]//div[contains(text(), 'Import zamówień do koszyka')]");
    waitForElementToBeVisible(popupBy);
    new WebDriverWait(webDriver, 300).until(ExpectedConditions.invisibilityOfAllElements(webDriver.findElements(By.xpath("//div[contains(@class,'loading loading-panel')]"))));
    by = By.xpath("//div[contains(@class,'fancy-btn')][contains(text(),'Przepisz do koszyka')]");
    findVisibleElement(by).click();
    //findClickableElement(by).click();
    waitForElementToBeInvisible(popupBy, 300);

    waitForDataIsLoaded();
  }


}
