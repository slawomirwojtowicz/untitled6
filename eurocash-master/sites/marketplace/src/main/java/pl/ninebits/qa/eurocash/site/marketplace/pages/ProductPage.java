package pl.ninebits.qa.eurocash.site.marketplace.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.components.Footer;
import pl.ninebits.qa.eurocash.site.marketplace.pages.components.Header;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ProductPage extends MarketplaceBasePage {
  public ProductPage(BasePage pageObject) {
    super(pageObject);
  }

  public final Header header = new Header(this);
  public final Footer footer = new Footer(this);

  public void addToCartBtn() throws Exception {
    Map<String, WebElement> pluses = getPluses();
    pluses.get("increaseOpkJedn").click();
    waitForDataIsLoaded();
  }

  private Map<String, WebElement> getPluses() throws Exception {
    List<WebElement> increasePackages = findElements(By.cssSelector(".cart__input--box .plus"), "increasePackages");
    Map<String, WebElement> pluses = new HashMap<>();
    if (increasePackages.size() != 2) {
      throw new Exception("Oczekiwano dwóch przycisków +.");
    } else {
      pluses.put("increaseOpkJedn", increasePackages.get(0));
      pluses.put("increaseJedn", increasePackages.get(1));

      return pluses;
    }
  }

  public void clickToFavoritesBtn() {
    WebElement toFavoritesBtn = findElement(By.xpath("//div[@class='uiKit-center-vh cursor-pointer']"));
    toFavoritesBtn.click();
    waitForDataIsLoaded();
  }

  public void clickAddToCartBtn() {
    WebElement addToCartBtn = findElement(By.cssSelector(".btn__text.btn__text--incart"));
    addToCartBtn.click();
    waitForDataIsLoaded();
  }
  public void typeListQuery(String listQuery) {
    waitForDataIsLoaded();
    waitForElementToBeVisible(By.xpath("//div[contains(@class,'short-name') and contains(text(),'Ulubione')]"));
    WebElement listInput = findElement(By.xpath("//input[contains(@class,'mt-input tooltip-input')]"));
    listInput.clear();
    listInput.sendKeys(listQuery);
  }

  public void clickAddList() {
    waitForElementToBeVisible(By.cssSelector("[class='mt-btn mt-btn--custom w-100']"));
    WebElement listBtn = findElement(By.cssSelector("[class='mt-btn mt-btn--custom w-100']"));
    listBtn.click();
    waitForElementToBeVisible(By.xpath("//a[contains(@href,'/listy-zakupowe/')]"));
  }

  public ShoppingListPage clickShoppingList() {
    WebElement shoppingListBtn = findElement(By.xpath("//a[contains(@href,'/listy-zakupowe/')]"));
    shoppingListBtn.click();
    return new ShoppingListPage(this);
  }

  public String getProductTitle() {
    return getTextFromElement(By.xpath("//div[@class='product__title']"));
  }

  public void clickCloseWindowBtn() {
    WebElement closeWindowBtn = findElement(By.xpath("//div[contains(@class,'mt-btn text-bold') and contains(.,'Zamknij okno')]"));
    closeWindowBtn.click();
  }

  public void clickListBtn(String listName) {
    WebElement clicList = webDriver.findElement(By.xpath("//div[contains(@class,'list-name') and contains(.,'" + listName + "')]"));
    clicList.click();
  }

  public String getEan() {
    WebElement productEan = findElement(By.xpath("//div[contains(text(),'Kod EAN produktu')]/../div[2]"), "productEan");
    return getTextFromElement(productEan);
  }

  public void checkProductEan(String ean, List<String> errors) {
    String productEan = getEan();
    if (!Objects.equals(productEan, ean)) {
      errors.add(MessageFormat.format("Pobrany ean {0} nie zgadza się z tym po którym wyszukiwano {1}", productEan, ean));
    }
  }

  public void typeAmountOfProducts(String amountOfProducts) {
    waitForDataIsLoaded();
    WebElement listBtn = findElement(By.cssSelector(".cart__input.ng-pristine"));
    listBtn.click();
    listBtn.sendKeys(amountOfProducts);
    WebElement clickTitle = findElement(By.cssSelector(".pp-producer-area .title"));
    clickTitle.click();
    waitForDataIsLoaded();
  }

}
