package pl.ninebits.qa.eurocash.site.ehurt.pages.shopping;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import pl.ninebits.qa.automated.tests.core.utils.RandomUtils;
import pl.ninebits.qa.automated.tests.core.utils.TextUtils;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;
import pl.ninebits.qa.eurocash.site.ehurt.forms.ProductForm;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtBasePage;

import java.text.MessageFormat;
import java.util.List;

public class OfferTilesPage extends EhurtBasePage {
  public OfferTilesPage(BasePage pageObject) {
    super(pageObject);
    waitForDataIsLoaded();
    closeRODOIfPresent();
    checkPageLoaded();
  }

  private void checkPageLoaded() {
    try {
      waitForElementToBeVisible(By.xpath("//div[@class='offer-boxes']"));
    } catch (Exception e) {
      Assert.fail(MessageFormat.format("Strona {0} nie została wczytana poprawnie", OfferTilesPage.class.getSimpleName()));
    }
  }

  public ProductForm openProductForm(String productName) {
    WebElement productLink = findVisibleElement(By.xpath("//a[@title='" + productName + "']"));
    productLink.click();
    sleep();
    waitForDataIsLoaded();
    return new ProductForm(this);
  }

  public ProductForm openProductForm() {
    WebElement productLink = findVisibleElement(By.xpath("//ech-offer//ech-product-box//span[@openproductdetails]"));
    productLink.click();
    sleep();
    waitForDataIsLoaded();
    return new ProductForm(this);
  }

  public String getRandomProductName() {
    List<WebElement> titlesElems = webDriver.findElements(By.xpath("//ech-offer//ech-product-box//span[@openproductdetails]"));
    List<String> titles = TextUtils.getAttributesFromWebElementsListWithoutTheQuotes(titlesElems, "title");

    return titles.get(RandomUtils.randomInt(0, 12));
  }

  public ProductForm openFirstProductPopupForm() {
    WebElement productBox = findClickableElement(By.xpath("//ech-offer//ech-product-box//span[@openproductdetails]"));
    productBox.click();
    sleep();
    waitForDataIsLoaded();
    return new ProductForm(this);
  }

  public void clearSearchFilters() {
    if (isElementPresent(By.xpath("//div[contains(@class,'close')]/span[contains(text(),'Wyczyść wszystkie')]"))) {
      WebElement clearFilterBtn = webDriver.findElement(By.xpath("//div[contains(@class,'close')]/span[contains(text(),'Wyczyść wszystkie')]"));
      sleep(1000);
      clearFilterBtn.click();
      waitForElementToBeInvisible(clearFilterBtn);
      waitForDataIsLoaded();
    }
  }

  public void enterSearchPhrase(String searchPhrase) {
    WebElement offerSearch = findVisibleElement(By.id("offerSearch"));
    offerSearch.clear();
    offerSearch.sendKeys(searchPhrase);
    offerSearch.sendKeys(Keys.RETURN);
    waitForDataIsLoaded();
    sleep(2000);
  }

  public void checkSearchResults(String searchedString, List<String> errors) {
    //TODO przerobić na waita
    sleep(1000);
    List<WebElement> searchResults = webDriver.findElements(By.xpath("//div[@class='offer-boxes']//h4[@class='product-box__title lcmb product-details-opener']"));
    searchedString = searchedString.toUpperCase();
    for (WebElement searchResult : searchResults) {
      String extractedSearchResult = searchResult.getAttribute("title");
      extractedSearchResult = extractedSearchResult.replaceAll("[\\s]{2}", " ");
      if (!extractedSearchResult.contains(searchedString)) {
        errors.add(MessageFormat.format("Przy szukaniu po {0} odnaleziono wartość {1}", searchedString, extractedSearchResult));
      }
    }
  }

  public void clickPromoFilter(String promotionName) {
    findVisibleElement(By.xpath("//div[@class='promotions-list']//span[contains(text(),'" + promotionName + "')]")).click();
    waitForDataIsLoaded();
  }


  public void checkProductsFilteredByPromo(String promotionName, List<String> errors) {
    waitForDataIsLoaded();
    int productTiles = webDriver.findElements(By.cssSelector(".product-box")).size();
    int promoIcons = webDriver.findElements(By.cssSelector(".product-box__promotions")).size();
    int promoRecomended = webDriver.findElements(By.cssSelector(".product-box__recommendation")).size();
    if (productTiles != promoIcons + promoRecomended) {
      errors.add(MessageFormat.format("Nie wszystkie produkty w widoku kafli wyfiltrowane po {0} są w promocji", promotionName));
    }
  }
}
