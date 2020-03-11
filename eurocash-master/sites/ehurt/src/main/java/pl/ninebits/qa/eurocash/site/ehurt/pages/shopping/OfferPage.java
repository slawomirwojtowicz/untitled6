package pl.ninebits.qa.eurocash.site.ehurt.pages.shopping;

import com.google.common.collect.Ordering;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import pl.ninebits.qa.automated.tests.core.utils.RandomUtils;
import pl.ninebits.qa.automated.tests.core.utils.TextUtils;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;
import pl.ninebits.qa.eurocash.site.ehurt.constants.ProductsName;
import pl.ninebits.qa.eurocash.site.ehurt.forms.OfferFiltersForm;
import pl.ninebits.qa.eurocash.site.ehurt.forms.ProductForm;
import pl.ninebits.qa.eurocash.site.ehurt.forms.PromotionBundleForm;
import pl.ninebits.qa.eurocash.site.ehurt.forms.PromotionForm;
import pl.ninebits.qa.eurocash.site.ehurt.forms.SettingsForm;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtBasePage;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class OfferPage extends EhurtBasePage {
  public OfferPage(BasePage pageObject) {
    super(pageObject);
    waitForDataIsLoaded();
    closeTutorial();
    updateOffer();
    clearSearchFilters();
    //  setDefaultSort();
    clickNormalView();
    closeNotifications();
    closeBottomBanner();
  }

  private void closeBottomBanner() {
    String closeBtn = "//ech-recommended-bottom//div[contains(@class,'reco-close')]";

    if (isElementPresent(By.xpath(closeBtn))) {
      findVisibleElement(By.xpath(closeBtn)).click();
      waitForElementToBeInvisible(By.xpath(closeBtn));
    }
  }

  @Override
  public void waitForDataIsLoaded() {
    try {
      sleep(2000);
      waitForElementToBeInvisible(By.xpath("//div[contains(@class,'loadpanel')]"));
    } catch (Exception e) {
      Assert.fail("Strona Oferta nie została wczytana");
    }
  }

  private void updateOffer() {
    sleep(1000);
    if (isElementPresent(By.xpath("//button[@class='get-offer-btn']"))) {
      WebElement updateBtn = findClickableElement(By.xpath("//button[@class='get-offer-btn']"));
      updateBtn.click();
      try {
        Alert updateConfirmation = webDriver.switchTo().alert();
        updateConfirmation.accept();
        Alert updateComplete = webDriver.switchTo().alert();
        updateComplete.accept();
      } catch (Exception e) {
        //no alert
      }
    }

  }

  public void ensureGivenColumnDisplayed(String columnHeader) {
    int columnIndex = getColumnHeaderIndex(columnHeader);
    if (columnIndex == 0) {
      SettingsForm settingsForm = clickSettingsBtn();
      settingsForm.clickColumnCheckbox(changeHeaderToSettingsName(columnHeader));
      settingsForm.clickChooseBtnInSettingPopup();
      waitForDataIsLoaded();
    }
  }

  private String changeHeaderToSettingsName(String inputLabel) {
    switch (inputLabel) {
      case "S. pak.":
        inputLabel = "S. pak";
        break;
      case "VAT":
        inputLabel = "%VAT";
        break;
      case "Marka Producenta":
        inputLabel = "Marka";
        break;
      case "PKWi U":
        inputLabel = "PKWiU";
        break;
      case "Kod Kreskowy":
        inputLabel = "Kod kresk.";
        break;
      case "Kod kresk. opak.":
        inputLabel = "Kod opak.";
        break;
      case "Bonifikata":
        inputLabel = "Boni.";
        break;
      default:
        break;
    }
    return inputLabel;
  }

  public SettingsForm clickSettingsBtn() {
    WebElement settingsFormBtn = findClickableElement(By.cssSelector(".popup-anchor .fa-cog"));
    settingsFormBtn.click();
    waitForElementToBeVisible(By.cssSelector(".grid-settings-content"));

    return new SettingsForm(this);
  }

  public void clearSearchFilters() {
    if (isElementPresent(By.xpath("//div[contains(@class,'close')]/span[contains(text(),'Wyczyść wszystkie')]"))) {
      WebElement clearFilterBtn = webDriver.findElement(By.xpath("//div[contains(@class,'close')]/span[contains(text(),'Wyczyść wszystkie')]"));
      sleep(1000);
      clearFilterBtn.click();
      waitForElementToBeInvisible(By.xpath("//div[contains(@class,'close')]/span[contains(text(),'Wyczyść wszystkie')]"));
      waitForDataIsLoaded();
    }
  }

  public void setDefaultSort() {
    WebElement sorterSpanner = webDriver.findElement(By.xpath("//span[contains(text(),'Sortuj')]/..//span[contains(@class,'arrow')]"));
    sorterSpanner.click();
    waitForElementToBeVisible(By.xpath("//kendo-popup//ul[@role='listbox']/li[1]"));
    WebElement defaultSort = webDriver.findElement(By.xpath("//kendo-popup//ul[@role='listbox']/li[1]"));
    defaultSort.click();
    waitForElementToBeInvisible(By.xpath("//kendo-popup//ul[@role='listbox']"));
    waitForDataIsLoaded();
  }

  public void clickNormalView() {
    WebElement normalView = findVisibleElement(By.xpath("//div[contains(@class,'gridOptions')]//a[1]"));
    normalView.click();
    waitForDataIsLoaded();
  }

  public void clickThumbsView() {
    WebElement thumbsView = webDriver.findElement(By.xpath("//i[@class='eh-icon eh-i-widok-kafle']"));
    thumbsView.click();
    waitForDataIsLoaded();
  }

  public String getRandomProductName() {
    List<WebElement> products = webDriver.findElements(By.xpath("//dx-data-grid[@id='dxDataGrid']//tr[contains(@class,'data-row')]" +
      "//td[" + getColumnHeaderIndex("Nazwa produktu") + "]"));

    String productName = products.get(RandomUtils.randomInt(0, 20)).getAttribute("aria-label");
    productName = productName.substring(productName.indexOf("Value ")).trim();
    productName = productName.replace("Value ", "");

    return productName;
  }

  public String getRandomProductSearchPhrase() {
    List<WebElement> products = webDriver.findElements(By.xpath("//dx-data-grid[@id='dxDataGrid']//tr[contains(@class,'data-row')]" +
      "//td[" + getColumnHeaderIndex("Nazwa produktu") + "]"));

    String searchPhrase = products.get(RandomUtils.randomInt(0, 20)).getAttribute("aria-label");
    searchPhrase = searchPhrase.substring(searchPhrase.indexOf("Value ")).trim();
    searchPhrase = searchPhrase.replace("Value ", "");
    searchPhrase = searchPhrase.substring(0, searchPhrase.indexOf(" "));

    return searchPhrase;
  }

  private List<String> getColumnHeaders() {
    List<WebElement> elements = webDriver.findElements(By.xpath("//table[@class='dx-datagrid-table dx-datagrid-table-fixed']" +
      "//tr[contains(@class,'header')]//td"));
    List<String> headers = new ArrayList<>();

    if (!elements.isEmpty()) {
      for (WebElement element : elements) {
        String header;
        try {
          header = element.getAttribute("aria-label").replace(" Column", "").trim();
        } catch (Exception e) {
          header = "";
        }
        headers.add(header);
      }
    } else {
      Assert.fail("Nie udało się pobrać nazw kolumn gridu 'Oferta'");
    }
    return headers;
  }

  private Integer getColumnHeaderIndex(String columnHeader) {
    int columnHeaderIndex = -1;
    int i = 0;
    while (i < 10) {
      columnHeaderIndex = getColumnHeaders().indexOf(columnHeader);
      if (columnHeaderIndex != -1) {
        break;
      } else {
        i++;
        scrollHorizontalBar(200); //searching columnHeaderUnderChrome
      }
    }

    columnHeaderIndex = columnHeaderIndex + 1;
    return columnHeaderIndex;
  }

  private void scrollHorizontalBar(int offset) {
    Actions actions = new Actions(webDriver);
    WebElement horizontalScroller = webDriver.findElement(By.xpath("//div[contains(@class,'horizontal')]//div[@class='dx-scrollable-scroll-content']"));
    actions.dragAndDropBy(horizontalScroller, offset, 0).perform();
  }

  public ProductForm openProductForm(String productName) {
    WebElement productLink = webDriver.findElement(By.xpath("//div[contains(@class,'dx-pointer-events-target')]//td[contains(@aria-label,'" + productName.toUpperCase(Locale.getDefault()) + "')]"));
    productLink.click();
    waitForElementToBeVisible(By.cssSelector(".input-place .rightBtt"));
    return new ProductForm(this);
  }

  public ProductForm openFirstProductPopupForm() {
    WebElement productLink = webDriver.findElement(By.xpath("(//td[@role='gridcell' and contains(@aria-label,'Nazwa produktu') " +
      "and not(contains(@aria-label,'Specjalnie dla Ciebie')) and not(contains(@aria-label,'Oferta'))]//span[contains(@class,'ech-link')])[1]"));
    productLink.click();
    waitForElementToBeVisible(By.xpath("//ech-product-page"));
    return new ProductForm(this);
  }

  public String getRandomGridValue(String columnHeader) throws Exception {
    List<WebElement> columnValuesElems = webDriver.findElements(By.xpath("//dx-data-grid[@id='dxDataGrid']//tr[contains(@class,'data-row')]" +
      "//td[" + getColumnHeaderIndex(columnHeader) + "]"));
    List<String> columnValues = TextUtils.getTextFromWebElementsList(columnValuesElems);
    String chosenColumnValue = "";

    if (!columnValues.isEmpty()) {
      int max = columnValues.size();
      if (max > 15) {
        max = 15;
      }
      chosenColumnValue = columnValues.get(RandomUtils.randomInt(0, max));
    } else {
      Assert.fail(MessageFormat.format("Nie pobrano wartości z kolumny {0}", columnHeader));
    }

    return chosenColumnValue;
  }

  //TODO spróbować przerobić na waita
  public void moveToElementOnGrid(String inputLabel) {
    sleep(2000);
    WebElement elementOnGrid = webDriver.findElement(By.xpath("//table[@role='grid']//tr[@role='row']/td[" + getColumnHeaderIndex(inputLabel) + "]" +
      "/div[contains(@class,'dx-datagrid-text-content')]"));
    moveToElement(elementOnGrid);
    scrollToElement(elementOnGrid);
    sleep(2000);
  }

  public String getRandomProducerSearchPhrase() {
    List<WebElement> producers = webDriver.findElements(By.xpath("//dx-data-grid[@id='dxDataGrid']//tr[contains(@class,'data-row')]" +
      "//td[" + getColumnHeaderIndex("Producent") + "]"));
    String producerSearchPhrase = producers.get(RandomUtils.randomInt(0, producers.size() - 1)).getText().trim();
    producerSearchPhrase = producerSearchPhrase.substring(0, producerSearchPhrase.indexOf(" "));

    return producerSearchPhrase;
  }

  public void enterSearchPhrase(String searchPhrase) {
    WebElement offerSearch = webDriver.findElement(By.id("offerSearch"));
    offerSearch.clear();
    offerSearch.sendKeys(searchPhrase);
  }

  public void enterSearchPhrase(ProductsName productNames) {
    WebElement offerSearch = webDriver.findElement(By.id("offerSearch"));
    offerSearch.clear();
    offerSearch.sendKeys(productNames.getNamesProduct());
  }

  public void confirmSearch() {
    WebElement offerSearch = webDriver.findElement(By.id("offerSearch"));
    offerSearch.sendKeys(Keys.RETURN);
    waitForDataIsLoaded();
  }

  public void checkFilterBoxPresent(String searchPhrase, List<String> errors) {
    if (!isElementPresent(By.xpath("//div[contains(@class,'filter-list-item')]/span[contains(text(),'" + searchPhrase + "')]"))) {
      errors.add(MessageFormat.format("Brak boxa z frazą wyszukiwania {0}", searchPhrase));
    }
  }

  public void checkSearchResults(String searchedString, String gridHeader, List<String> errors) {
    //TODO przerobić na waita
    sleep(1000);
    List<WebElement> searchResults = webDriver.findElements(By.xpath("//dx-data-grid[@id='dxDataGrid']//tr[contains(@class,'data-row')]" +
      "//td[" + getColumnHeaderIndex(gridHeader) + "]"));
    searchedString = searchedString.toUpperCase();
    searchedString = searchTest(searchedString);
    if (doStripAccents(gridHeader)) {
      searchedString = TextUtils.stripAccents(searchedString);
    }
    for (WebElement searchResult : searchResults) {
      String extractedSearchResult = searchResult.getAttribute("aria-label");
      extractedSearchResult = extractedSearchResult.substring(extractedSearchResult.indexOf("Value ")).trim();
      extractedSearchResult = extractedSearchResult.replace("Value ", "");
      extractedSearchResult = extractedSearchResult.replaceAll("[\\s]{2}", " ");
      if (!extractedSearchResult.contains(searchedString)) {
        errors.add(MessageFormat.format("Przy szukaniu po {0} {1} odnaleziono wartość {2}", gridHeader, searchedString, extractedSearchResult));
      }
    }
  }

  private String searchTest(String searchedString) {
    switch (searchedString) {
      case "WĘDLINY":
        searchedString = "Wędliny";
        break;
      default:
        break;
    }
    return searchedString;
  }


  public String getRandomIndex() {
    List<WebElement> indexElems = webDriver.findElements(By.xpath("//td[@role='gridcell' and contains(@aria-label,'Indeks,')]"));
    List<String> indexes = new ArrayList<>();
    for (WebElement indexElem : indexElems) {
      indexes.add(indexElem.getAttribute("aria-label").replaceAll("\\D", ""));
    }
    TextUtils.clearNullsFromList(indexes);
    if (indexes.isEmpty()) {
      Assert.fail("Brak pobranych indeksów");
    }

    int max = indexes.size();
    if (max > 15) {
      max = 15;
    }

    return indexes.get(RandomUtils.randomInt(0, max));
  }

  public ProductForm clickRandomProduct(String index) {
    WebElement productLink = findClickableElement(By.xpath("//td[@role='gridcell' and contains(@aria-label,'" + index + "')]" +
      "/..//td[contains(@aria-label,'Nazwa produktu')]//span[contains(@class,'ech-link')]"));
    noTooltipPlease();
    moveToElement(productLink);
    productLink.click();

    return new ProductForm(this);
  }

  private void noTooltipPlease() {
    if (isElementPresent(By.id("ngTooltip"))) {
      WebElement sidebarToggleBtn = findVisibleElement(By.id("sidebarToggleBtn"));
      moveToElement(sidebarToggleBtn);
    }
  }

  public void addSearchPhrase(String searchCharacter) {
    WebElement searchInput = findClickableElement(By.id("offerSearch"));
    searchInput.click();
    searchInput.click();
    searchInput.sendKeys(searchCharacter);
    sleep(1000);
  }

  public void checkAutocompleteSuggestions(String searchPhrase, List<String> errors) {
    sleep(1000);
    List<WebElement> autocompleteElems = webDriver.findElements(By.xpath("//div[@class='ac-pname']"));

    if (autocompleteElems.isEmpty()) {
      errors.add("Brak autocomplete!!");
    }
    searchPhrase = TextUtils.stripAccents(searchPhrase);

    for (WebElement autocompleteElem : autocompleteElems) {
      String suggestionText = autocompleteElem.getText();
      if (!suggestionText.contains(searchPhrase)) {
        if (suggestionText.isEmpty()) {
          errors.add("Nie pobrano tekstu z autocomplete");
        } else {
          errors.add(MessageFormat.format("Na liście znaleziono autocomplete {0} niezawierający frazy {1}", suggestionText, searchPhrase));
        }
      }
    }
  }

  private Boolean doStripAccents(String gridHeader) {
    if (gridHeader.equals("Nazwa produktu")) {
      return true;
    } else {
      return false;
    }
  }

  public OfferFiltersForm clickShowFilter() {
    WebElement filtersBtn = findClickableElement(By.id("sidebarToggleBtn"));
    filtersBtn.click();
    waitForElementToBeVisible(By.xpath("//a[@id='sidebarToggleBtn']/span[contains(text(),'Ukryj')]"));

    return new OfferFiltersForm(this);
  }

  public String getProductNameForGivenIndex(String attributeHeader, String index) {
    String productName = getAttributeFromElement(By.xpath("//td[@role='gridcell' and contains(@aria-label,'Indeks, Value " + index + "')]" +
      "/..//td[contains(@aria-label,'" + attributeHeader + "')]"), "aria-label");
    productName = productName.substring(productName.indexOf("Value"));
    productName = productName.replaceAll("Value ", "").trim();
    productName = productName.replaceAll(" {2}", " ");
    return productName;
  }

  public void sortColumnAscending(String columnHeader, List<String> errors) {
    WebElement column = null;
    int i = 0;
    boolean isSorterPresent;
    if (columnHeader.equals("Producent")) {
      isSorterPresent = isElementPresent(By.xpath("//td[@role='columnheader']/div[text()='" + columnHeader + "']"));
    } else {
      isSorterPresent = isElementPresent(By.xpath("//td[@role='columnheader']/div[contains(text(),'" + columnHeader + "')]"));
    }

    while (!isSorterPresent && i < 3) {
      scrollHorizontalBar(119);
      i++;
      isSorterPresent = isElementPresent(By.xpath("//td[@role='columnheader']/div[contains(text(),'" + columnHeader + "')]"));
    }
    if (!isSorterPresent) {
      errors.add(MessageFormat.format("Nie odnaleziono sortera kolumny {0}", columnHeader));
    } else {
      column = findClickableElement(By.xpath("//td[@role='columnheader']/div[contains(text(),'" + columnHeader + "')]"));
    }
    if (column != null) {
      contextClickElement(column);
      try {
        WebElement sortAscBtn = findClickableElement(By.xpath("//i[contains(@class,'sort-asc')]"), 5);
        sortAscBtn.click();
        waitForElementToBeInvisible(sortAscBtn, 5);
      } catch (Exception e) {
        findVisibleElement(By.xpath("//i[contains(@class,'sort-desc')]")).sendKeys(Keys.ESCAPE); //to znaczy że kolumna już posortowana asc
      }
      waitForDataIsLoaded();
    }
  }

  public void sortColumnDescending(String columnHeader) {
    WebElement column = findClickableElement(By.xpath("//td[@role='columnheader']/div[contains(text(),'" + columnHeader + "')]"));
    contextClickElement(column);
    WebElement sortDescBtn = findClickableElement(By.xpath("//i[contains(@class,'sort-desc')]"));
    sortDescBtn.click();
    waitForElementToBeInvisible(sortDescBtn);
    waitForDataIsLoaded();
  }

  public void checkDataSortedByWords(String columnHeader, String sortingType, List<String> errors) {
    List<String> columnValues = getColumnStringValues(columnHeader);
    clearListFromTrash(columnValues);
    switch (sortingType) {
      case "asc":
        if (!Ordering.natural().isOrdered(columnValues)) {
          errors.add(MessageFormat.format("Dane nie są posortowane rosnąco po {0}", columnHeader));
        }
        break;
      case "desc":
        if (!Ordering.natural().reverse().isOrdered(columnValues)) {
          errors.add(MessageFormat.format("Dane nie są posortowane malejąco po {0}", columnHeader));
        }
        break;
      default:
        Assert.fail("Nieprawidłowy typ sortowania");
    }
  }

  private List<String> clearListFromTrash(List<String> columnValues) {
    columnValues.removeIf(s -> (s.contains("\"")));
    return columnValues;
  }

  public void checkDataSortedByValues(String columnHeader, String sortingType, List<String> errors) throws Exception {
    List<Double> columnValues = getColumnNumberValues(columnHeader);

    switch (sortingType) {
      case "asc":
        if (!Ordering.natural().isOrdered(columnValues)) {
          errors.add(MessageFormat.format("Dane nie są posortowane rosnąco po {0}", columnHeader));
        }
        break;
      case "desc":
        if (!Ordering.natural().reverse().isOrdered(columnValues)) {
          errors.add(MessageFormat.format("Dane nie są posortowane malejąco po {0}", columnHeader));
        }
        break;
      default:
        Assert.fail("Nieprawidłowy typ sortowania");
    }
  }

  private List<String> getColumnStringValues(String columnHeader) {
    waitForDataIsLoaded();
    sleep(1000);
    List<WebElement> elements = webDriver.findElements(By.xpath("//tr[not(contains(@class,'recommended'))]" +
      "//td[@role='gridcell' and contains(@aria-label,'" + columnHeader + "')]"));
    elements = elements.subList(1, elements.size() / 2);
    List<String> stringValues = TextUtils.getAttributesFromWebElementsList(elements, "aria-label");

    for (int i = 0; i < stringValues.size(); i++) {
      String stringValue = stringValues.get(i);
      stringValue = stringValue.substring(stringValue.indexOf("Value"));
      stringValue = stringValue.replaceAll("Value ", "").trim();
      int maxLength = stringValue.length();
      if (stringValue.length() > 6) {
        maxLength = 6;
      }
      stringValue = stringValue.substring(0, maxLength);
      stringValues.set(i, stringValue);
    }
    return stringValues;
  }

  private List<Double> getColumnNumberValues(String columnHeader) throws Exception {
    List<WebElement> elements = webDriver.findElements(By.xpath("(//tr[not(contains(@class,'recommended')) and not(contains(@class,'selection'))])" +
      "//td[@role='gridcell' and contains(@aria-label,'" + columnHeader + ",')]"));
    List<String> stringValues = TextUtils.getTextFromWebElementsList(elements);
    List<Double> numValues = new ArrayList<>();
    stringValues = stringValues.subList(1, stringValues.size());
    for (String stringValue : stringValues) {
      stringValue = stringValue.replaceAll("%", "");
      numValues.add(Double.valueOf(stringValue));
    }
    return numValues;
  }

  public OfferTilesPage clickTilesView() {
    WebElement tilesViewBtn = findClickableElement(By.xpath("//i[contains(@class,'widok-kafle')]"));
    tilesViewBtn.click();
    waitForDataIsLoaded();
    return new OfferTilesPage(this);
  }

  public List<String> getPromotionsNamesFromBar() throws Exception {
    List<WebElement> elements = webDriver.findElements(By.xpath("//div[@class='promotions-list']//span"));
    List<String> promotions = TextUtils.getTextFromWebElementsList(elements);
    if (promotions.isEmpty()) {
      Assert.fail("Brak promocji");
    }

    return promotions;
  }

  public String getRandomProductWithPricePromo(List<String> errors) {
    String pricePromoXpath = "//ech-promotions-icons-wrapper//img[contains(@src,'promocja_cenowa.png')]/../../..//td[contains(@aria-label,'Nazwa produktu')]";
    List<WebElement> promoElems = webDriver.findElements(By.xpath(pricePromoXpath));
    List<String> products = TextUtils.getAttributesFromWebElementsListWithoutTheQuotes(promoElems, "aria-label");
    for (int i = 0; i < products.size(); i++) {
      String productName = products.get(i).substring(products.get(i).indexOf("V")).replaceAll("Value ", "").trim();
      products.set(i, productName);
    }
    String productName = "";
    if (!products.isEmpty()) {
      int max = products.size();
      if (max > 5) {
        max = 5;
      }
      productName = products.get(RandomUtils.randomInt(0, max));
    } else {
      errors.add("Brak promocji cenowych");
    }
    return productName;
  }

  public String getRandomProductWithMultiplePromos(List<String> errors) {
    String multiPromoXpath = "//ech-promotions-icons-wrapper//img[contains(@src,'wiele_promocji.png')]/../../..//td[contains(@aria-label,'Nazwa produktu')]";
    List<WebElement> promoElems = webDriver.findElements(By.xpath(multiPromoXpath));
    List<String> products = TextUtils.getAttributesFromWebElementsListWithoutTheQuotes(promoElems, "aria-label");
    for (int i = 0; i < products.size(); i++) {
      String productName = products.get(i).substring(products.get(i).indexOf("V")).replaceAll("Value ", "").trim();
      products.set(i, productName);
    }
    String productName = "";
    if (!products.isEmpty()) {
      int max = products.size();
      if (max > 6) {
        max = 6;
      }
      productName = products.get(RandomUtils.randomInt(0, max));
    } else {
      errors.add("Brak produktów w wielu promocjach");
    }
    return productName;
  }

  public String getRandomProductWithPacketPromo(List<String> errors) {
    String packetPromoXpath = "//ech-promotions-icons-wrapper//img[contains(@src,'promocja_pakietowa.png')]/../../..//td[contains(@aria-label,'Nazwa produktu')]";
    List<WebElement> promoElems = webDriver.findElements(By.xpath(packetPromoXpath));
    List<String> products = TextUtils.getAttributesFromWebElementsListWithoutTheQuotes(promoElems, "aria-label");
    for (int i = 0; i < products.size(); i++) {
      String productName = products.get(i).substring(products.get(i).indexOf("V")).replaceAll("Value ", "").trim();
      products.set(i, productName);
    }
    String productName = "";
    if (!products.isEmpty()) {
      int max = products.size();
      if (max > 6) {
        max = 6;
      }
      productName = products.get(RandomUtils.randomInt(0, max));
    } else {
      errors.add("Brak promocji pakietowych");
    }
    return productName;
  }

  public PromotionForm clickPricePromoBtn(String productName) {
    WebElement promoBtn = findClickableElement(By.xpath(
      "//td[contains(@aria-label,'" + productName + "')]/../..//ech-promotions-icons-wrapper//img[contains(@src,'promocja_cenowa.png')]"));
    promoBtn.click();
    //waitForDataIsLoaded();
    sleep(2000);
    return new PromotionForm(this);
  }

  public PromotionBundleForm clickPromoBundleBtn(String productName) {
    WebElement promoBtn = findClickableElement(By.xpath("" +
      "//td[contains(@aria-label,'" + productName + "')]/../..//ech-promotions-icons-wrapper//img[contains(@src,'promocja_pakietowa.png')]"));
    promoBtn.click();
    waitForDataIsLoaded();

    return new PromotionBundleForm(this);
  }

  public void checkOfferPageLoaded(List<String> errors) {
    if (!isElementPresent(By.cssSelector("#offerGrid"))) {
      errors.add("Strona Oferta nie została wczytana poprawnie");
    }
    //TODO sleep dodany, bo czaem menu zasłania inny obiekt, do analizy
    sleep(1000);
  }

  public void clickAssortmentMarkBtn() {
    WebElement assortmen = findVisibleElement(By.cssSelector(".popup-filter-btn"));
    assortmen.click();
    waitForElementToBeVisible(By.cssSelector(".assortiment-popup__left"));
  }

  public void clickWine() {
    WebElement wine = findVisibleElement(By.cssSelector("[for='mw-Piwne Terytoria']"));
    wine.click();
    waitForElementToBeVisible(By.cssSelector(".assortiment-popup__left"));
  }

  public void clickAssortmentBtn() {
    WebElement assortment = findVisibleElement(By.xpath("//div[@class='filter-item lcm pointer']//span[contains(text(),'Wędliny')]"));
    assortment.click();
    waitForElementToBeInvisible(By.cssSelector(".assortiment-popup__left"));
  }

  public void clickFoldBtn() {
    WebElement assortmen = findVisibleElement(By.xpath("//i[@class='fa fa-angle-up ']"));
    assortmen.click();
  }

  public List<String> getIndexes(int numberOfIndexes, List<String> errors) {
    List<WebElement> elements = webDriver.findElements(By.xpath("//td[@role='gridcell' and contains(@aria-label,'Indeks')]"));
    List<String> indexes = new ArrayList<>();
    for (int i = 0; i < numberOfIndexes; i++) {
      Integer index = Integer.valueOf(elements.get(i).getAttribute("aria-label").replaceAll("[A-Za-z,.\\s]", ""));
      indexes.add(String.valueOf(index));
    }
    return indexes;
  }

  public List<String> getProductNames(int startingIndex, int productAmount) throws Exception {
    List<WebElement> elements = webDriver.findElements(By.xpath("//td[@class='name-grid-class']//span[@openproductdetails]"));
    List<String> productNames = TextUtils.getTextFromWebElementsList(elements);

    return productNames.subList(startingIndex, productAmount + startingIndex);
  }

  public void enterUnits(int productIndex, String amountOfUnits) {
    WebElement unitsInput = findVisibleElement(By.xpath("(//td[contains(@aria-label,'Jednostki')]//input)[" + productIndex + "]"));
    unitsInput.clear();
    unitsInput.sendKeys(amountOfUnits);
    unitsInput.sendKeys(Keys.RETURN);
    waitForDataIsLoaded();
  }

  public void enterUnitsForFullProductName(String productName, String amountOfUnits) throws Exception {
    try {
      WebElement unitsInput =
        findVisibleElement(By.xpath("(//div[contains(@class,'dx-pointer-events-target')]" +
          "//td[contains(@aria-label,'" + productName.toUpperCase(Locale.getDefault()) + "')]" +
          "//..//label)[1]"));
      unitsInput.sendKeys(amountOfUnits);
      unitsInput.sendKeys(Keys.RETURN);
    } catch (Exception e) {
      throw new Exception(MessageFormat.format("Produkt {0} nie został odnaleziony\n" + e, productName));
    }
    waitForDataIsLoaded();
  }

  public void enterUnitsForFullProductName(ProductsName productsName, String amountOfUnits) throws Exception {
    try {
      WebElement unitsInput = findElement(By.xpath("(//div[contains(@class,'dx-pointer-events-target')]" +
        "//td[contains(@aria-label,'" + productsName.getNamesProduct().toUpperCase(Locale.getDefault()) + "')]" +
        "//..//label)[1]"));
      unitsInput.sendKeys(amountOfUnits);
      unitsInput.sendKeys(Keys.RETURN);
    } catch (Exception e) {
      throw new Exception(MessageFormat.format("Produkt {0} nie został odnaleziony\n" + e, productsName.getNamesProduct()));
    }
    waitForDataIsLoaded();
  }


  public void saveAsList(String listName) {
    clickSaveAsList();
    sleep(2000);
    findVisibleElement(By.xpath("//input[@class='dx-texteditor-input']")).sendKeys(listName);
    findVisibleElement(By.xpath("//div[contains(text(),'Utwórz listę')]")).click();
    waitForElementToBeInvisible(By.cssSelector(".dx-overlay-modal"));
  }

  private void clickSaveAsList() {
    findClickableElement(By.xpath("//span[contains(@class,'headerItem')]//i[contains(@class,'eh-i-dodaj-liste-zakupowa')]")).click();
  }

  public List<String> getProductNetPrices(int startingIndex, int productAmount) throws Exception {
    List<WebElement> elements = webDriver.findElements(By.xpath("//td[contains(@class,'net-price')]/span"))
      .subList(startingIndex, productAmount + startingIndex);
    List<String> netPrices = TextUtils.getTextFromWebElementsList(elements);
    for (int i = 0; i < netPrices.size(); i++) {
      netPrices.set(i, netPrices.get(i).replaceAll("[\\s\\u00A0zł]", ""));
    }
    return netPrices;
  }

  public List<String> getProductGrossPrices(int startingIndex, int productAmount) throws Exception {
    List<WebElement> elements = webDriver.findElements(By.xpath("//td[@role='gridcell' and contains(@aria-label,'Cena brutto')]"))
      .subList(startingIndex, productAmount + startingIndex);
    List<String> grossPrices = TextUtils.getTextFromWebElementsList(elements);
    for (int i = 0; i < grossPrices.size(); i++) {
      grossPrices.set(i, grossPrices.get(i).replaceAll("[\\s\\u00A0zł]", ""));
    }

    return grossPrices;
  }

  public void checkGrossValueLargerOnCartTabInFooter(List<String> errors) {
    if (getGrossCartValue() <= getNetCartValue()) {
      errors.add("Wartość brutto koszyka w stopce nie jest większa od wartości netto");
    }
  }

  private double getNetCartValue() {
    String text = findVisibleElement(By.xpath("//div[@class='value-line' and contains(text(),'netto')]/span")).getText();
    text = text.replaceAll("[\\s\\u00A0zł]", "");
    text = text.replace(",", ".");
    return Double.valueOf(text);
  }

  private double getGrossCartValue() {
    String text = findVisibleElement(By.xpath("//div[@class='value-line' and contains(text(),'brutto')]/span")).getText();
    text = text.replaceAll("[\\s\\u00A0zł]", "");
    text = text.replace(",", ".");
    return Double.valueOf(text);
  }

  public void clickPromoFilter(String promotionName) {
    findVisibleElement(By.xpath("//div[@class='promotions-list']//span[contains(text(),'" + promotionName + "')]")).click();
    waitForDataIsLoaded();
  }

  public void checkProductsFilteredByPromo(String promotionName, List<String> errors) {
    int rows = webDriver.findElements(By.xpath("//tr[contains(@class,'dx-data-row')]")).size() / 2;
    if (promotionName.equals("Nowości")) {
      int newSignAmount = webDriver.findElements(By.xpath("//img[contains(@src,'nowosc.png')]")).size();
      if (rows != newSignAmount) {
        errors.add("Nie wszystkie rekordy posiadają znaczek 'Nowośc'");
      }
    } else {
      int promoIcons = webDriver.findElements(By.xpath("//ech-promotions-icons-wrapper")).size();
      if (rows != promoIcons) {
        errors.add(MessageFormat.format("Nie wszystkie produkty wyfiltrowane po {0} są w promocji", promotionName));
      }
    }
  }

  public int getOffersAmount() {
    return webDriver.findElements(By.xpath("//tr[@class='dx-row dx-data-row']")).size();
  }

  public boolean checkOffersAmount(int offersAmount, List<String> errors) {
    if (offersAmount <= 0) {
      errors.add("Nie pobrano żadnych ofert");
      return false;
    } else {
      return true;
    }
  }

  public void checkMoreDataLoaded(int offersAmount, List<String> errors) {
    List<WebElement> offers = webDriver.findElements(By.xpath("//tr[@class='dx-row dx-data-row']"));
    WebElement lastOffer = offers.get(offers.size() - 1);
    scrollToElement(lastOffer);
    waitForDataIsLoaded();

    List<WebElement> offersAfterLoad = webDriver.findElements(By.xpath("//tr[@class='dx-row dx-data-row']"));
    if (offersAfterLoad.size() <= offersAmount) {
      errors.add("Oferty nie zostały doczytane po scrollu");
    }
  }

  public List<String> getProductBarCodes(int startingIndex, int productAmount) {
    List<WebElement> elements = webDriver.findElements(By.xpath("//td[contains(@aria-label,'Kod Kreskowy, Value')]"));
    List<String> productNames = TextUtils.getAttributesFromWebElementsListWithoutTheQuotes(elements, "aria-label");
    for (int i = 0; i < productNames.size(); i++) {
      String tmp = productNames.get(i).replaceAll("[^\\d]", "");
      productNames.set(i, tmp);
    }
    return productNames.subList(startingIndex, productAmount + startingIndex);
  }

  public void clickCloseBtn() {
    WebElement close = findVisibleElement(By.cssSelector(".close"));
    close.click();
    sleep();
  }

}
