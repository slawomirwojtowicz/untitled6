package pl.ninebits.qa.eurocash.site.marketplace.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import pl.ninebits.qa.automated.tests.core.utils.RandomUtils;
import pl.ninebits.qa.automated.tests.core.utils.TextUtils;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;
import pl.ninebits.qa.eurocash.site.marketplace.forms.ListForm;
import pl.ninebits.qa.eurocash.site.marketplace.pages.components.Header;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

public class ShoppingPage extends MarketplaceBasePage {
  public ShoppingPage(BasePage pageObject) {
    super(pageObject);
  }

  public Header header = new Header(createPageObject(Header.class));

  public void checkProductFound(String productName, List<String> errors) {
    waitForDataIsLoaded();
    try {
      String title = getTextFromElement(findElement(By.xpath("//a[contains(@class,'tile__name')][contains(text(),'" + productName + "')]")));
      if (!title.equals(productName)) {
        errors.add(MessageFormat.format("Odnaleziono produkt {0} gdy szukano {1}", title, productName));
      }
    } catch (Exception e) {
      errors.add(MessageFormat.format("Produkt {0} nie został odnaleziony", productName));
    }
  }

  public void checkProductPrice(String productName, Double newProductPrice, List<String> errors) {
    try {
      String price = getTextFromElement(findElement(By.xpath("//div[contains(@class,'price')]/span[1]")));
      price = TextUtils.priceToStringReadyToBeDoubleFormat(price);
      if (!Objects.equals(Double.valueOf(price), newProductPrice)) {
        errors.add(MessageFormat.format("Cena pobrana {0} jest różna od oczekiwanej {1}", price, newProductPrice));
      }
    } catch (Exception e) {
      errors.add(String.format("Brak ceny produktu %s", productName));
    }
  }

  public void checkNothingFound(List<String> errors) {
    waitForDataIsLoaded();
    if (!isElementPresent(By.cssSelector(".no-data"))) {
      errors.add("Brak informacji o braku zwróconych wyników");
    }
  }

  public ProductPage clickRandomProduct() throws Exception {
    List<WebElement> productTitles = webDriver.findElements(By.cssSelector(".tile-container a.tile__name"));
    if (productTitles.isEmpty()) {
      throw new Exception("Brak wyszukanych produktów");
    }
    productTitles.get(RandomUtils.randomInt(0, productTitles.size())).click();

    return new ProductPage(this);
  }

  public ProductPage clickFirstProduct() throws Exception {
    List<WebElement> productTitles = webDriver.findElements(By.cssSelector(".tile__right .cursor-pointer"));
    if (productTitles.isEmpty()) {
      throw new Exception("Brak wyszukanych produktów");
    }
    productTitles.get(0).click();

    return new ProductPage(this);
  }

  public void clickAddToCartBtn() throws Exception {
    WebElement addContainerToCartBtn = findElement(By.xpath("//p[@class='cart__input--title' and contains(text(),'Opk.')]/..//span[contains(@class,'plus')]"),
      "addContainerToCartBtn");
    addContainerToCartBtn.click();
    try {
      waitForElementToBeVisible(By.cssSelector(".btn__text--incart"));
    } catch (Exception e) {
      throw new Exception("Produkt nie został dodany do koszyka");
    }
  }

  public ListForm clickListOnFirstProduct() throws Exception {
    List<WebElement> listBtn = webDriver.findElements(By.cssSelector(".btn__heart"));
    if (listBtn.isEmpty()) {
      throw new Exception("Brak produktów z listami");
    }
    listBtn.get(0).click();
    return new ListForm(this);
  }

  public String getFirstProductTitle() {
    String productTitle = getTextFromElement(By.xpath("//img[@class='icons-full-heart']//..//..//..//..//..//..//..//..//..//..//..//a[@class='tile__name cursor-pointer']"));
    return productTitle;
  }


  public void filterByMerchant(String merchantName) {
    WebElement merchantFilter = findElement(By.xpath("//div[@class='label-text'][contains(text(),'" + merchantName + "')]"));
    merchantFilter.click();
    waitForDataIsLoaded();
  }

  public String getProductName() throws Exception {
    List<WebElement> productTitles = findElements(By.cssSelector(".uiKit-d-none > .cursor-pointer.tile__name"), "productTitles");
    return getTextFromElement(productTitles.get(RandomUtils.randomInt(0, productTitles.size())));
  }

  public String getProducer() throws Exception {
    List<WebElement> productProducers = findElements(By.cssSelector(".delivery__name"), "productProducers");
    return getTextFromElement(productProducers.get(RandomUtils.randomInt(0, productProducers.size())));
  }

  public void checkProductsFoundByProducer(String producer, List<String> errors) throws Exception {
    waitForDataIsLoaded();
    List<WebElement> products = findElements(By.cssSelector(".tile-container"), "product");

    products.forEach(product -> {
      String producerName = getTextFromElement(product.findElement(By.cssSelector(".delivery__from")));
      if (!producer.contains(producerName)) {
        String distributorName = getTextFromElement(product.findElement(By.cssSelector(".tile__count--const .text-medium")));
        if (!producer.contains(distributorName)) {
          errors.add(MessageFormat.format("Wyszukiwano po producencie {0} a znaleziono rekord z {1}", producer, producerName));
        }
      }
    });
  }

  public void checkOneProductFound(List<String> errors) throws Exception {
    waitForDataIsLoaded();
    List<WebElement> productsFoundList = findElements(By.cssSelector(".item-list"), "productsFoundList");
    if (productsFoundList.size() > 1) {
      errors.add("Znaleziono więcej niż jeden produkt");
    }
  }

  public String getRandomProducerFromFacet() throws Exception {
    List<WebElement> producerFilters = findElements(By.xpath("//app-uni-sidemenu-filters//div[text()='Producenci i dystrybutorzy']/..//div[@class='label-text']"),
      "producerFilters");
    return getTextFromElement(producerFilters.get(RandomUtils.randomInt(0, producerFilters.size())));
  }

  public String getRandomBrandFromFacet() throws Exception {
    List<WebElement> brandFilters = findElements(By.xpath("//app-uni-sidemenu-filters//div[text()='Marki']/..//div[@class='label-text']"),
      "brandFilters");
    List<String> brands = TextUtils.getTextFromWebElementsList(brandFilters);
    return brands.get(RandomUtils.randomInt(0, brands.size()));
  }

  public void clickProducerFilterCheckBox(String producer) {
    WebElement producerFilterCheckbox = findElement(By.xpath("//input[@name='Distributor']/..//div[text()='" + producer + "']"));
    actionStream()
      .moveToElement(producerFilterCheckbox)
      .sendKeys(Keys.ARROW_DOWN, Keys.ARROW_DOWN, Keys.ARROW_DOWN)
      .perform();
    sleep(1000);
    producerFilterCheckbox.click();
    waitForDataIsLoaded();
  }

  public void clearFilters() {
    WebElement clearAllFiltersBtn = findElement(By.cssSelector(".filter-label--clear-all"));
    clearAllFiltersBtn.click();
    waitForDataIsLoaded();
  }

  public void filterByBrand(String brand) {
    WebElement searchByBrandInput = findElement(By.cssSelector("app-uni-sidemenu-filters .search-input"));
    actionStream()
      .moveToElement(searchByBrandInput)
      .sendKeys(Keys.ARROW_DOWN, Keys.ARROW_DOWN, Keys.ARROW_DOWN)
      .perform();
    sleep(1000);
    searchByBrandInput.click();
    searchByBrandInput.clear();
    searchByBrandInput.sendKeys(brand);

    WebElement brandCheckbox = findElement(By.xpath("//input[@name='Brand']/..//div[text()='" + brand + "']"));
    actionStream()
      .moveToElement(brandCheckbox)
      .sendKeys(Keys.ARROW_DOWN, Keys.ARROW_DOWN, Keys.ARROW_DOWN)
      .perform();
    brandCheckbox.click();
    waitForDataIsLoaded();
  }

  public void checkProductsFoundByBrand(String brand, List<String> errors) throws Exception {
    List<WebElement> brandElems = findElements(By.cssSelector(".tile-container .tile__count--const .text-medium"), "brandElems");

    brandElems.forEach(brandElem -> {
      String brandExtracted = getTextFromElement(brandElem);
      if (!brandExtracted.contains(brand)) {
        errors.add(MessageFormat.format("Filtując po marce {0}, znaleziono produkt z marka {1}", brand, brandExtracted));
      }
    });
  }
}
