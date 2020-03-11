package pl.ninebits.qa.eurocash.site.ehurt.pages.shopping;

import com.google.common.collect.Ordering;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import pl.ninebits.qa.automated.tests.core.utils.DateUtils;
import pl.ninebits.qa.automated.tests.core.utils.RandomUtils;
import pl.ninebits.qa.automated.tests.core.utils.TextUtils;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;
import pl.ninebits.qa.eurocash.site.ehurt.forms.PromotionForm;
import pl.ninebits.qa.eurocash.site.ehurt.forms.SettingsForm;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtBasePage;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class PromotionsPage extends EhurtBasePage {
  public PromotionsPage(BasePage pageObject) {
    super(pageObject);
    waitForDataIsLoaded();
  }


  public void checkPromotionPageLoaded(List<String> errors) {
    if (!isElementPresent(By.cssSelector("#promotionsContainer"))) {
      errors.add("Strona z promocjami nie została wczytana poprawnie");
    }
  }

  public boolean checkPromotionsPresent(List<String> errors) {
    waitForDataIsLoaded();
    if (webDriver.findElements(By.cssSelector(".dx-data-row")).size() < 2) {
      errors.add("Brak wystarczających danych do testów promocji");
      return false;
    } else {
      return true;
    }
  }

  public String getRandomPromoName() {
    List<WebElement> elements = webDriver.findElements(By.xpath("//td[contains(@aria-label,'Nazwa promocji')]/span[@title]"));
    List<String> promoNames = TextUtils.getAttributesFromWebElementsListWithoutTheQuotes(elements, "title");
    int randMax = promoNames.size();
    if (promoNames.size() > 20) {
      randMax = 20;
    }
    return promoNames.get(RandomUtils.randomInt(0, randMax));
  }

  public String getRandomPromoDescription() {
    List<WebElement> elements = webDriver.findElements(By.xpath("//td[contains(@aria-label,'Opis promocji')]/span[@title]"));
    List<String> promoNames = TextUtils.getAttributesFromWebElementsListWithoutTheQuotes(elements, "title");
    int randMax = promoNames.size();
    if (promoNames.size() > 20) {
      randMax = 20;
    }
    return promoNames.get(RandomUtils.randomInt(0, randMax));
  }

  public void enterSearchPhrase(String searchPhrase) {
    WebElement searchInput = findVisibleElement(By.xpath("//div[@class='promotions-search']/input"));
    searchInput.clear();
    searchInput.sendKeys(searchPhrase);
    searchInput.sendKeys(Keys.RETURN);
    waitForDataIsLoaded();
  }

  public void checkPromoNames(String promoName, List<String> errors) {
    List<WebElement> elements = webDriver.findElements(By.xpath("//td[contains(@aria-label,'Nazwa promocji')]/span[@title]"));
    List<String> promoNames = TextUtils.getAttributesFromWebElementsListWithoutTheQuotes(elements, "title");
    for (String promoNameOnList : promoNames) {
      if (!promoNameOnList.equals(promoName)) {
        errors.add(MessageFormat.format("Na liście znajduje się promocja {0} gdy wyszukiwano po nazwie {1}", promoNameOnList, promoName));
      }
    }
  }

  public void clearFilters() {
    findClickableElement(By.xpath("//span[contains(text(),'Wyczyść filtry')]")).click();
    waitForDataIsLoaded();
    waitForPageToLoad();
    sleep(1000);
  }

  public void checkPromoDescriptions(String promoDescr, List<String> errors) {
    List<WebElement> elements = webDriver.findElements(By.xpath("//td[contains(@aria-label,'Opis promocji')]/span[@title]"));
    List<String> promoDescrs = TextUtils.getAttributesFromWebElementsListWithoutTheQuotes(elements, "title");
    for (String promoDescrOnList : promoDescrs) {
      promoDescr = promoDescr.toLowerCase();
      if (!promoDescrOnList.toLowerCase().contains(promoDescr)) {
        errors.add(MessageFormat.format("Na liście znajduje się promocja {0} gdy wyszukiwano po opisie {1}", promoDescrOnList, promoDescr));
      }
    }
  }

  public PromotionForm clickFirstPromotion() {
    findClickableElement(By.xpath("//td[contains(@aria-label,'Nazwa promocji')]/span[@title]")).click();
    waitForDataIsLoaded();
    return new PromotionForm(this);
  }

  public void choosePromoType(String promoType) {
    findClickableElement(By.xpath("//div[@data-dx_placeholder='Rodzaj promocji']/../input")).click();
    findClickableElement(By.xpath("//div[@role='option']/div[contains(text(),'" + promoType + "')]")).click();
    waitForDataIsLoaded();
  }

  public void checkPromotionsFilteredByType(String promoType, List<String> errors) {
    List<WebElement> elements = webDriver.findElements(By.xpath("//td[contains(@aria-label,'Rodzaj promocji')]//span[@title]"));
    List<String> promoTypesFromList = TextUtils.getAttributesFromWebElementsListWithoutTheQuotes(elements, "title");
    for (String promoTypeFromList : promoTypesFromList) {
      if (!promoTypeFromList.equals(promoType)) {
        errors.add(MessageFormat.format("Element nie ma typu promocji {0}", promoType));
      }
    }
  }

  public void chooseProducer(String producerName) {
    WebElement producerInput = findClickableElement(By.xpath("//div[@data-dx_placeholder='Producent']/../input"));
    producerInput.click();
    producerInput.clear();
    producerInput.sendKeys(producerName);
    sleep(2000);
    findVisibleElement(By.xpath("//div[contains(@class,'dx-list-item-content')][contains(text(),'" + producerName + "')]")).click();
    producerInput.sendKeys(Keys.RETURN);
    waitForDataIsLoaded();
    waitForPageToLoad();
  }

  public void chooseProductGroup(String productGroup) {
    WebElement producerInput = findClickableElement(By.xpath("//div[@data-dx_placeholder='Grupa produktowa']/../input"));
    producerInput.click();
    sleep(1000);
    producerInput.sendKeys(productGroup);
    sleep(1000);
    findVisibleElement(By.xpath("//div[contains(@class,'dx-list-item-content')][contains(text(),'" + productGroup + "')]")).click();
    waitForDataIsLoaded();
    waitForPageToLoad();
  }

  public List<String> getPromoNamesNotContainingPhrase(String productGroup) {
    List<WebElement> elements = webDriver.findElements(By.xpath("//td[contains(@aria-label,'Nazwa promocji')]/span[@title]"));
    List<String> promoNames = TextUtils.getAttributesFromWebElementsListWithoutTheQuotes(elements, "title");
    List<String> promosToCheck = new ArrayList<>();
    for (String promoName : promoNames) {
      if (!promoName.contains(productGroup.toUpperCase())) {
        promosToCheck.add(promoName);
      }
    }
    return promosToCheck;
  }

  public PromotionForm clickPromotion(String promoToCheck) {
    findClickableElement(By.xpath("//td[contains(@aria-label,'Nazwa promocji')]/span[@title='" + promoToCheck + "']")).click();
    waitForDataIsLoaded();
    return new PromotionForm(this);
  }

  public List<String> getAvailableStartEndDates() throws Exception {
    sleep(2000);
    List<String> startDates = getStartDates();
    List<String> endDates = getEndDates();
    int index = RandomUtils.randomInt(0, startDates.size());
    List<String> date = Arrays.asList(startDates.get(index), endDates.get(index));

    return date;
  }

  private List<String> getStartDates() throws Exception {
    List<WebElement> elements = webDriver.findElements(By.xpath("//td[@role='gridcell' and contains(@aria-label,'Ważna od')]"));
    return TextUtils.getTextFromWebElementsList(elements);
  }

  private List<String> getEndDates() throws Exception {
    List<WebElement> elements = webDriver.findElements(By.xpath("//td[@role='gridcell' and contains(@aria-label,'Ważna do')]"));
    return TextUtils.getTextFromWebElementsList(elements);
  }

  public void enterStartDate(String startDate) {
    startDate = DateUtils.changeDateFromGridToSearchFormat(startDate);
    WebElement startDateInput = findVisibleElement(By.xpath("//div[@data-dx_placeholder='Ważne od']/../input"));
    startDateInput.sendKeys(startDate);
    startDateInput.sendKeys(Keys.RETURN);
    waitForDataIsLoaded();
  }

  public void enterEndDate(String endDate) {
    endDate = DateUtils.changeDateFromGridToSearchFormat(endDate);
    WebElement endDateInput = findVisibleElement(By.xpath("//div[@data-dx_placeholder='Ważne do']/../input"));
    endDateInput.sendKeys(endDate);
    endDateInput.sendKeys(Keys.RETURN);
    waitForDataIsLoaded();
  }

  public void checkPromotionsFilteredByStartDate(String startDate, List<String> errors) throws Exception {
    Date date = new SimpleDateFormat("dd-MM-yyyy").parse(startDate);
    DateUtils.getDate(date, -1);
    List<String> startDates = getStartDates();
    for (String startDateExtr : startDates) {
      if (date.after(new SimpleDateFormat("dd-MM-yyyy").parse(startDateExtr))) {
        errors.add(MessageFormat.format("Znaleziono datę początku wcześniejszą od {0}", date.toString()));
      }
    }
  }

  public void checkPromotionsFilterByDates(String startDate, String endDate, List<String> errors) throws Exception {
    Date dateStart = new SimpleDateFormat("dd-MM-yyyy").parse(startDate);
    Date dateEnd = new SimpleDateFormat("dd-MM-yyyy").parse(endDate);
    dateStart = DateUtils.getDate(dateStart, -1);
    dateEnd = DateUtils.getDate(dateEnd, 1);
    List<String> startDates = getStartDates();
    List<String> endDates = getEndDates();
    for (int i = 0; i < startDates.size(); i++) {
      if (dateStart.after(new SimpleDateFormat("dd-MM-yyyy").parse(startDates.get(i))) &&
        dateEnd.before(new SimpleDateFormat("dd-MM-yyyy").parse(endDates.get(i)))) {
        errors.add(MessageFormat.format("Znaleziono rpomocję nie mieszczącą się w zakresie {0} - {1}", dateStart, dateEnd));
      }
    }
  }


  public void sortColumnAscending(String columnHeader, List<String> errors) {
    WebElement column = null;
    int i = 0;
    Boolean isSorterPresent = isElementPresent(By.xpath("//td[@role='columnheader']/div[contains(text(),'" + columnHeader + "')]"));

    while (!isSorterPresent && i < 3) {
      scrollHorizontalBar(119);
      i++;
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

  public SettingsForm clickSettingsBtn() {
    WebElement settingsFormBtn = findClickableElement(By.cssSelector(".popup-anchor .fa-cog"));
    settingsFormBtn.click();
    waitForElementToBeVisible(By.cssSelector(".grid-settings-content"));

    return new SettingsForm(this);
  }

  public void ensureGivenColumnDisplayed(String columnHeader) {
    int columnIndex = getColumnHeaderIndex(columnHeader);
    if (columnIndex == 0) {
      SettingsForm settingsForm = clickSettingsBtn();
      settingsForm.clickChooseBtnInSettingPopup();
      waitForDataIsLoaded();
    }
  }

  private void scrollHorizontalBar(int offset) {
    Actions actions = new Actions(webDriver);
    WebElement horizontalScroller = webDriver.findElement(By.xpath("//div[contains(@class,'horizontal')]//div[@class='dx-scrollable-scroll-content']"));
    actions.dragAndDropBy(horizontalScroller, offset, 0).perform();
  }

  private List<String> getColumnStringValues(String columnHeader) {
    List<WebElement> elements = webDriver.findElements(By.xpath("//tr[not(contains(@class,'recommended'))]" +
      "//td[@role='gridcell' and contains(@aria-label,'" + columnHeader + "')]"));
    elements = elements.subList(0, elements.size() / 2);
    List<String> stringValues = TextUtils.getAttributesFromWebElementsListWithoutTheQuotes(elements, "aria-label");

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

  public void checkDataSortedByWords(String columnHeader, String sortingType, List<String> errors) {
    List<String> columnValues = getColumnStringValues(columnHeader);

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

  public void sortColumnDescending(String columnHeader) {
    WebElement column = findClickableElement(By.xpath("//td[@role='columnheader']/div[contains(text(),'" + columnHeader + "')]"));
    contextClickElement(column);
    WebElement sortDescBtn = findClickableElement(By.xpath("//i[contains(@class,'sort-desc')]"));
    sortDescBtn.click();
    waitForElementToBeInvisible(sortDescBtn);
    waitForDataIsLoaded();
  }

  public void clickPromoFilter(String promotionName) {
    findVisibleElement(By.xpath("//div[@class='promotions-list']//span[contains(text(),'" + promotionName + "')]")).click();
    waitForDataIsLoaded();
  }

}
