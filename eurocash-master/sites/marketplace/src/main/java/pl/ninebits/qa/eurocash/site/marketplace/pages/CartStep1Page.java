package pl.ninebits.qa.eurocash.site.marketplace.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pl.ninebits.qa.automated.tests.core.utils.TextUtils;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;
import pl.ninebits.qa.eurocash.site.marketplace.forms.ListForm;
import pl.ninebits.qa.eurocash.site.marketplace.pages.payments.PaymentsPage;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

public class CartStep1Page extends MarketplaceBasePage {
  public CartStep1Page(BasePage pageObject) {
    super(pageObject);
  }


  public void deleteProducts() throws Exception {
    WebElement selectAllCheckbox = findElement(By.xpath("//input[@id='select-all']/..//i"), "selectAllCheckbox");
    selectAllCheckbox.click();
    waitForDataIsLoaded();
    WebElement deleteAllLink = findElement(By.xpath("//span[text()='Usuń z koszyka']"), "deleteAllLink");
    deleteAllLink.click();
    waitForDataIsLoaded();
    try {
      waitForElementToBeVisible(By.cssSelector(".uni-grid-empty-table"));
    } catch (Exception e) {
      throw new Exception("Produkty nie zostały usunięte z koszyka");
    }
  }

  public void checkMinLogisticCriteriaAreMet() throws Exception {
    if (isElementPresent(By.cssSelector(".cart-detail .short-name.color-red"))) {
      /*List<WebElement> errorData = webDriver.findElements(By.cssSelector("a.mt-red--f"));
      if (!errorData.isEmpty()) {
        errorData.forEach(err -> {
          err.getAttribute("title").to
        });
      }*/
      throw new Exception("W koszyku jest produkt z błędem");
    }
  }

  public PaymentsPage clickGoToPayment() {
    WebElement goToPaymentsButton = findElement(By.xpath("//a[contains(text(),'PRZEJDŹ DO PŁATNOŚCI')]"), "goToPaymentsButton");
    goToPaymentsButton.click();
    waitForDataIsLoaded();

    return new PaymentsPage(this);
  }

  public void checkProductInCartPresent(String productName) throws Exception {
    try {
      waitForElementToBeVisibleIgnoredException(By.xpath(".product-td a[title='" + productName + "']"), 5);
    } catch (Exception e) {
      throw new Exception(String.format("Product %s not found in cart\n", productName), e);
    }
  }

  public void increaseUnits() {
    List<WebElement> incrementBtns = webDriver.findElements(By.cssSelector(".amount .cart-plus"));
    incrementBtns.get(1).click();
    sleep();
    waitForDataIsLoaded();
  }

  public void decreaseUnits() {
    List<WebElement> decrementBtns = webDriver.findElements(By.cssSelector(".amount .cart-minus"));
    decrementBtns.get(1).click();
    sleep();
    waitForDataIsLoaded();
  }

  public void increaseUnitsAndPackages() {
    List<WebElement> incrementBtns = webDriver.findElements(By.cssSelector(".amount .cart-plus"));
    incrementBtns.get(0).click();
    sleep();
    waitForDataIsLoaded();
  }

  public void checkOrderValue(double expectedOrderVal, List<String> errors) throws Exception {
    sleep(2000); //konieczne bo brak żadnego widocznego przeładowania boxa etc
    Double orderVal = getOrderVal();

    if (!Objects.equals(orderVal, expectedOrderVal)) {
      errors.add(MessageFormat.format("Nieprawidłowa wartość koszyka. Oczekiwano {0} a jest {1}", expectedOrderVal, orderVal));
    }
  }

  private Double getOrderVal() throws Exception {
    List<WebElement> cartValues = webDriver.findElements(By.cssSelector(".cart-detail__info-value"));
    if (cartValues.size() < 1) {
      throw new Exception("Nie pobrano wartości koszyka");
    }

    return Double.valueOf(TextUtils.priceToStringReadyToBeDoubleFormat(cartValues.get(0).getText()));
  }

  public void checkProductNamePresent(String productTitle, List<String> errors) {
    WebElement name = findElement(By.xpath("//tr[contains(@class,'uni-grid-row ')]//td[contains(@class,'product-td')]"));
    String productName = name.getText();
    waitForDataIsLoaded();
    if (!productTitle.contains(productName)) {
      errors.add(MessageFormat.format("Nazwa produktu  {0}, nie zgadza się z nazwą w koszyku {1}", productTitle, productName));
    }
  }

  public ListForm clickSaveCartAsList(String listName) {
    WebElement saveListBtn = findElement(By.xpath("//span[contains(.,'Zapisz koszyk jako listę')]"));
    saveListBtn.click();
    waitForElementToBeVisible(By.xpath("//div[contains(@class,'list-name') and contains(.,'" + listName + "')]"));
    return new ListForm(this);
  }

  public void clickExistingList(String listName) {
    WebElement clickListBtn = findElement(By.xpath("//div[contains(@class,'list-name') and contains(.,'" + listName + "')]"));
    clickListBtn.click();
    WebElement closeWindowBtn = findElement(By.cssSelector(".mt-btn.text-bold"));
    closeWindowBtn.click();
  }

  public void selectProductInCart() {
    WebElement selectProduct = findElement(By.xpath("//label[contains(@class,'uiKit-flex--align-items-center')]/i"));
    selectProduct.click();
  }

  public ListForm clickMoveToList() {
    WebElement selectProduct = findElement(By.cssSelector(".table-configuration .d-inline-block div"));
    selectProduct.click();
    return new ListForm(this);
  }


}
