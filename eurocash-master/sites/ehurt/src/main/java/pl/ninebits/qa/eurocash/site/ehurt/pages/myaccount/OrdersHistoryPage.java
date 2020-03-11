package pl.ninebits.qa.eurocash.site.ehurt.pages.myaccount;

import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import pl.ninebits.qa.automated.tests.core.utils.DateUtils;
import pl.ninebits.qa.automated.tests.core.utils.RandomUtils;
import pl.ninebits.qa.automated.tests.core.utils.TextUtils;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;
import pl.ninebits.qa.eurocash.site.ehurt.forms.FilterOrdersForm;
import pl.ninebits.qa.eurocash.site.ehurt.forms.OrdersSettingsForm;
import pl.ninebits.qa.eurocash.site.ehurt.forms.TutorialForm;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtBasePage;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrdersHistoryPage extends EhurtBasePage {

  public OrdersHistoryPage(BasePage pageObject) {
    super(pageObject);
    checkPageLoaded();
    closeTutorial();
    clearSearchFilters();
    clickNormalView();
    waitForDataIsLoaded();
    ensureDataForTestsPresent();
  }

  private void checkPageLoaded() {
    try {
      waitForDataIsLoaded();
      waitForElementToBeVisible(By.xpath("//div[@id='orderHistoryGrid']"));
    } catch (Exception e) {
      throw new NoSuchElementException("Strona 'Historia Zamówień' nie została wczytana poprawnie'");
    }
  }

  private void ensureDataForTestsPresent() {
    if (getNumberOfRecords() < 40) {
      enterDaysBack("20");
    }
  }

  private int getNumberOfRecords() {
    String numberOfRecords = getTextFromElement(By.xpath("//kendo-pager//span[contains(text(),'Strona')]"));
    numberOfRecords = numberOfRecords.substring(numberOfRecords.indexOf("(") + 1, numberOfRecords.indexOf("e"));
    numberOfRecords = numberOfRecords.trim();
    return Integer.valueOf(numberOfRecords);
  }

  @Override
  public void waitForDataIsLoaded() {
    String loaderXpath = "//div[contains(@class,'loading loading-panel')]";
    String refreshDataXpath = "//h3[@class='no-records-info']/span";
    String overlayCss = ".k-overlay";

    waitForPageToLoad();
    try {
      waitForElementToBeVisible(By.xpath(loaderXpath), 3);
    } catch (Exception e) {
      // System.out.println("Nie odnaleziono loadera na stronie Historia zamówień");
    }
    waitUntil(ExpectedConditions.invisibilityOfAllElements(webDriver.findElements(By.xpath(loaderXpath))));
    try {
      waitUntil(ExpectedConditions.invisibilityOfAllElements(webDriver.findElements(By.cssSelector(overlayCss))));
    } catch (Exception e) {
      //
    }
    if (isElementPresent(By.xpath(refreshDataXpath))) {
      reloadPage();
      waitForPageToLoad();
      try {
        waitUntil(ExpectedConditions.invisibilityOfAllElements(webDriver.findElements(By.cssSelector(overlayCss))));
      } catch (Exception e) {
        //
      }
      if (isElementPresent(By.xpath(refreshDataXpath))) {
        Assert.fail("Wyświetla się komunikat 'Nie znaleziono żadnych wyników'");
      }
    }
  }

  public void scrollHorizontalBar(int offset) {
    Actions actions = new Actions(webDriver);
    WebElement horizontalScroller = webDriver.findElement(By.xpath("//div[@class='ps__rail-x']"));
    Integer initialPosition = getScrollerPosition(horizontalScroller);
    int i = 0;
    while ((getScrollerPosition(horizontalScroller) < Math.abs(initialPosition + offset)) && (i++ < 40)) {
      horizontalScroller.click();
      actions.sendKeys(Keys.ARROW_RIGHT).click().perform();
      sleep();
    }
    //TODO przywrócić jak zacznie działać drag and drop na chromie
    // actions.dragAndDropBy(horizontalScroller, offset, 0).perform(); //839
  }

  private Integer getScrollerPosition(WebElement horizontalScroller) {
    String style = horizontalScroller.getAttribute("style");
    style = style.substring(style.indexOf("left"), style.indexOf("bottom"));
    return Integer.valueOf(style.replaceAll("[^\\d]", ""));
  }

  private List<String> getColumnHeaders() {
    List<WebElement> elements = webDriver.findElements(By.xpath("//th[contains(@class,'k-header')]"));
    List<String> headers = new ArrayList<>();

    if (!elements.isEmpty()) {
      for (WebElement element : elements) {
        headers.add(element.getText());
      }
    } else {
      Assert.fail("Nie udało się pobrać nazw kolumn gridu 'Historia zamówień'");
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

  public void clearSearchFilters() {
    String clearFiltersBtnXpath = "//span[@class='headerItem']//span[contains(text(),'Wyczyść filtry')]";

    if (isElementPresent(By.xpath(clearFiltersBtnXpath))) {
      WebElement clearFiltersBtn = findClickableElement(By.xpath(clearFiltersBtnXpath));
      clearFiltersBtn.click();
      waitForDataIsLoaded();
    }
  }

  public OrderDetailsPage openOrderWithNumber(String orderNumber) {
    WebElement orderNumberLink = findClickableElement(By.xpath("//a[contains(@class,'ec_link') and contains(@href,'" + orderNumber + "')]"));
    orderNumberLink.click();
    return new OrderDetailsPage(this);
  }

  public void typeSearchPhrase(String searchPhrase) {
    String searchInputXpath = "//form[@name='belkaZamowienia']//input[@name='nameSearch']";

    waitForElementToBeVisible(By.xpath(searchInputXpath));
    WebElement searchInput = webDriver.findElement(By.xpath(searchInputXpath));
    searchInput.clear();
    searchInput.sendKeys(searchPhrase);
    searchInput.sendKeys(Keys.RETURN);
    waitForDataIsLoaded();
  }

  public OrderDetailsPage openRandomVisibleOrder() {
    List<WebElement> docsNumbersElems = webDriver.findElements(By.xpath("//a[contains(@class,'ec_link') and contains(@href,'zamowieniaHistoria/pokaz')]"));
    WebElement doc = docsNumbersElems.get(RandomUtils.randomInt(0, docsNumbersElems.size()));
    moveToElement(doc);
    ((JavascriptExecutor) webDriver).executeScript("window.scrollBy(0, 80)");
    doc.click();

    return new OrderDetailsPage(this);
  }

  public void enterStartDate(String date) {
    WebElement startDateInput = findClickableElement(By.xpath("//span[@id='lblDateFrom']//input[@class='dx-texteditor-input']"));
    closeTutorial();
    startDateInput.click();
    startDateInput.clear();
    startDateInput.sendKeys(date);
    startDateInput.sendKeys(Keys.ENTER);
    waitForDataIsLoaded();
  }

  public void enterEndDate(String date) {
    WebElement endDateInput = findClickableElement(By.xpath("//span[@id='lblDateTo']//input[@class='dx-texteditor-input']"));
    closeTutorial();
    endDateInput.clear();
    endDateInput.sendKeys(date);
    endDateInput.sendKeys(Keys.ENTER);
    waitForDataIsLoaded();
  }


  public String checkInitialDateSort() {
    String sortType = "";

    if (isElementPresent(By.xpath("//a[contains(.,'Data wyst.')]//span[contains(@class,'k-i-sort-asc')]"))) {
      sortType = "asc";
    } else if (isElementPresent(By.xpath("//a[contains(.,'Data wyst.')]//span[contains(@class,'k-i-sort-desc')]"))) {
      sortType = "desc";
    }
    return sortType;
  }

  public void checkOrdersSortedByDateOfIssueAsc(String startDate, String endDate, List<String> errors) throws ParseException {
    String startDateInGridFormat = DateUtils.changeDateFromSearchToGridFormat(startDate);
    String endDateInGridFormat = DateUtils.changeDateFromSearchToGridFormat(endDate);
    List<Date> dateOfIssueDates = getIssueDates();
    boolean isSorted = Ordering.natural().isOrdered(dateOfIssueDates);

    checkFirstElementIssueDate(startDateInGridFormat, errors);
    if (!isSorted) {
      errors.add("Dane nie są posortowane rosnąco po dacie wydania dokumentu");
    }
    clickLastPage();
    dateOfIssueDates.addAll(getIssueDates());
    checkLastElementIssueDate(endDateInGridFormat, errors);
    if (!isSorted) {
      errors.add("Dane nie są posortowane rosnąco po dacie wydania dokumentu");
    }
  }

  public void checkOrdersSortedByDateOfIssueAsc(String startDate, List<String> errors) throws ParseException {
    String startDateInGridFormat = DateUtils.changeDateFromSearchToGridFormat(startDate);
    List<Date> dateOfIssueDates = getIssueDates();
    boolean isSorted = Ordering.natural().isOrdered(dateOfIssueDates);

    checkFirstElementIssueDate(startDateInGridFormat, errors);
    if (!isSorted) {
      errors.add("Dane nie są posortowane rosnąco po dacie wydania dokumentu");
    }
    clickNextPage();
    dateOfIssueDates.addAll(getIssueDates());
    checkLastElementIssueDate(startDateInGridFormat, errors);
    if (!isSorted) {
      errors.add("Dane nie są posortowane rosnąco po dacie wydania dokumentu");
    }
  }

  public void checkOrdersSortedByDateOfIssueDesc(String startDate, String endDate, List<String> errors) throws ParseException {
    String startDateInGridFormat = DateUtils.changeDateFromSearchToGridFormat(startDate);
    String endDateInGridFormat = DateUtils.changeDateFromSearchToGridFormat(endDate);
    List<Date> dateOfIssueDates = getIssueDates();
    boolean isSorted = Ordering.natural().reverse().isOrdered(dateOfIssueDates);

    checkFirstElementIssueDate(startDateInGridFormat, errors);
    if (!isSorted) {
      errors.add("Dane nie są posortowane malejąco po dacie wydania dokumentu");
    }
    clickLastPage();
    dateOfIssueDates.clear();
    dateOfIssueDates.addAll(getIssueDates());
    checkLastElementIssueDate(endDateInGridFormat, errors);
    isSorted = Ordering.natural().reverse().isOrdered(dateOfIssueDates);
    if (!isSorted) {
      errors.add("Dane nie są posortowane malejąco po dacie wydania dokumentu");
    }
  }

  private List<Date> getIssueDates() throws ParseException {
    List<WebElement> paymentDatesElems = webDriver.findElements(By.xpath("//table//td[" + getColumnHeaderIndex("Data wyst.") + "]"));
    List<Date> paymentDates = new ArrayList<>();

    for (WebElement paymentDateElem : paymentDatesElems) {
      paymentDates.add(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(paymentDateElem.getText().trim()));
    }
    return paymentDates;
  }

  private void checkFirstElementIssueDate(String startDate, List<String> errors) {
    if (isElementPresent(By.xpath("(//table//td[" + getColumnHeaderIndex("Data wyst.") + "])[1]"))) {
      String extractedFirstIssueDate = webDriver.findElement(By.xpath("(//table//td[" + getColumnHeaderIndex("Data wyst.") + "])[1]")).getText().trim();
      if (!extractedFirstIssueDate.contains(startDate)) {
        errors.add(MessageFormat.format("Pierwsze zamówienie na liście nie posiada daty wystawienia {0}", startDate));
      }
    } else {
      Assert.fail("Brak wczytanego gridu z zamówieniami");
    }
  }

  private void checkLastElementIssueDate(String endDate, List<String> errors) throws ParseException {
    String extractedLastIssueDate = webDriver.findElement(By.xpath("(//table//td[" + getColumnHeaderIndex("Data wyst.") + "])[last()]")).getText().trim();
    Date extractedDate = new SimpleDateFormat("dd-MM-yyyy").parse(extractedLastIssueDate);
    Date endDateInDateFormat = new SimpleDateFormat("dd-MM-yyyy").parse(endDate);

    if (extractedDate.after(endDateInDateFormat)) {
      errors.add(MessageFormat.format("Ostatnie zamówienie na liście posiada datę wystawienia póxniejszą niż {0}", endDate));
    }
  }

  public void clickSortByIssueDateBtn() {
    String neutralSortXpath = "//a[contains(.,'Data wyst.')]";
    String ascSortXpath = "//a[contains(.,'Data wyst.')]/span[contains(@class,'sort-asc')]";
    String descSortXpath = "//a[contains(.,'Data wyst.')]/span[contains(@class,'sort-desc')]";

    if (isElementPresent(By.xpath(ascSortXpath))) {
      WebElement ascSortBtn = findClickableElement(By.xpath(ascSortXpath));
      ascSortBtn.click();
      waitForDataIsLoaded();
      waitForElementToBeVisible(By.xpath(descSortXpath));
    } else if (isElementPresent(By.xpath(descSortXpath))) {
      WebElement descSortBtn = findClickableElement(By.xpath(descSortXpath));
      descSortBtn.click();
      waitForDataIsLoaded();
      waitForElementToBeVisible(By.xpath(ascSortXpath));
    } else if (isElementPresent(By.xpath(neutralSortXpath))) {
      WebElement neutrSortBtn = findClickableElement(By.xpath(neutralSortXpath));
      neutrSortBtn.click();
      waitForDataIsLoaded();
      waitForElementToBeVisible(By.xpath(ascSortXpath));
    } else {
      Assert.fail("Brak przycisku do sortowania po dacie wydania");
    }
  }

  public void clickLastPage() {
    WebElement lastPageBtn = findClickableElement(By.xpath("//span[@aria-label='Go to the last page']"));
    moveToElement(lastPageBtn);
    lastPageBtn.click();
    waitForDataIsLoaded();
  }

  public void clickNextPage() {
    WebElement nextPageBtn = findClickableElement(By.xpath("//span[@class='k-icon k-i-arrow-e']"));
    nextPageBtn.click();
    waitForDataIsLoaded();
  }

  public void clickFirstPage() {
    String firstPageBtn = "//a[@class='k-link k-pager-nav k-pager-first']//span[@aria-label='Go to the first page']";
    if (isElementPresent(By.xpath(firstPageBtn))) {
      WebElement clickFirstBtn = findClickableElement(By.xpath(firstPageBtn));
      clickFirstBtn.click();
      waitForDataIsLoaded();
    }
  }

  public String getStartDate() {
    return webDriver.findElement(By.cssSelector("#lblDateFrom [type=\"hidden\"]")).getAttribute("value");
  }

  public String getEndDate() {
    return webDriver.findElement(By.cssSelector("#lblDateTo [type=\"hidden\"]")).getAttribute("value");
  }

  public boolean checkDateCorrect(String date, String dateChosen, List<String> errors) {
    date = date.replaceAll("-", "");
    date = date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6);
    if (!date.equals(dateChosen)) {
      errors.add(MessageFormat.format("Oczekiwana data {0} nie zgadza się z wybraną w polu {1}", date, dateChosen));
      return false;
    } else {
      return true;
    }
  }

  public String chooseRandomDaysBackValue() {
    Integer maxDaysBackValue = getMaxDaysBackValue();
    Integer actualDaysBackValue = Integer.valueOf(getDaysBack());

    return Integer.toString(RandomUtils.randomInt(actualDaysBackValue + 1, maxDaysBackValue));
  }

  private Integer getMaxDaysBackValue() {
    Integer maxValue = Integer.valueOf(getAttributeFromElement(By.xpath("//span[@id='lblPreviousDays']//input"), "aria-valuemax"));

    if (maxValue > 20) {
      maxValue = 20;
    }
    return maxValue;
  }

  public void enterDaysBack(String daysBack) {
    WebElement daysBackInput = webDriver.findElement(By.xpath("//input[@class='numeric-input']"));
    daysBackInput.click();
    daysBackInput.sendKeys("0", daysBack);
    daysBackInput.sendKeys(Keys.RETURN);
    waitForDataIsLoaded();
  }

  public String getDaysBack() {
    return getAttributeFromElement(By.xpath("//ech-numeric-input[contains(@class,'ng-pristine ng-valid')]//input"), "aria-valuenow");
  }

  public void checkDates(String initialEndDate, String daysBack, List<String> errors) {
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.DATE, -Integer.valueOf(daysBack));
    String startDate = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());

    String currentStartDate = getStartDate();
    String currentEndDate = getEndDate();

    if (!currentStartDate.equals(startDate)) {
      errors.add(MessageFormat.format("Obecna data początkowa {0} nie zgadza się z wyliczoną {1}", currentStartDate, startDate));
    }

    if (!currentEndDate.equals(initialEndDate)) {
      errors.add(MessageFormat.format("Obecna data końcowa {0} różni się od inicjalnej {1}", currentEndDate, initialEndDate));
    }
  }

  public void checkDefaultDaysBackValue(String initialDaysBack, List<String> errors) {
    if (!getDaysBack().equals(initialDaysBack)) {
      errors.add(MessageFormat.format("Wartość pola Dni wstecz nie powróciła do wartości początkowej {0}", initialDaysBack));
    }
  }

  public void checkDefaultStartDate(String initialStartDate, List<String> errors) {
    if (!getStartDate().equals(initialStartDate)) {
      errors.add(MessageFormat.format("Wartość pola Data od nie powróciła do wartości początkowej {0}", initialStartDate));
    }
  }

  public void checkDefaultEndDate(String initialEndDate, List<String> errors) {
    if (!getEndDate().equals(initialEndDate)) {
      errors.add(MessageFormat.format("Wartość pola Data do nie powróciła do wartości początkowej {0}", initialEndDate));
    }
  }

  public void clickSimpleView() {
    WebElement clickStraightViewBtn = findClickableElement(By.xpath("//i[contains(@class,'pozycje')]"));
    clickStraightViewBtn.click();
    waitForDataIsLoaded();
    waitForElementToBeVisible(By.xpath("//span[contains(@class,'gridOptions')]//i[contains(@class,'pozycje')]"));
  }

  public void checkListSimpleView(List<String> errors) {
    List<WebElement> docsNumbersElems = webDriver.findElements(By.xpath("//a[contains(@class,'ec_link') and contains(@href,'zamowieniaHistoria/pokaz')]"));
    if (docsNumbersElems.size() != 5) {
      errors.add("W widoku prostym nie pokazuje się lista 5 zamówień");
    }
    waitForDataIsLoaded();
  }

  public void clickNormalView() {
    WebElement normalViewBtn = findClickableElement(By.xpath("//i[contains(@class,'naglowki')]"));
    normalViewBtn.click();
    waitForDataIsLoaded();
    waitForElementToBeVisible(By.xpath("//span[@class='headerItem gridOptions']//i[contains(@class,'naglowki')]"));
  }

  public FilterOrdersForm clickShowFiltersBtn() {
    String showFiltersBtnId = "sidebarToggleBtn";

    if (isElementPresent(By.id(showFiltersBtnId))) {
      WebElement showFiltersBtn = webDriver.findElement(By.id(showFiltersBtnId));
      showFiltersBtn.click();
      waitForElementToBeVisible(By.xpath("//div[@id='sidebar' and contains(@class,'visible')]"));
    } else {
      Assert.fail("Brak przycisku Pokaż filtry!");
    }
    return new FilterOrdersForm(this);
  }

  private String changePaymentNumberToTheWord(String inputValue) {
    switch (inputValue) {
      case "przelew: Płatność do trzech dni":
        inputValue = "przelew: Płatność do 3 dni";
        break;
      default:
        break;
    }
    return inputValue;
  }

  public void checkOrdersFoundByGivenCriteria(String inputLabel, String inputValue, List<String> errors) throws Exception {
    inputLabel = changeExtendedFilterLabelToGridHeader(inputLabel);
    inputValue = changePaymentNumberToTheWord(inputValue);
    List<WebElement> values = findElements(By.xpath("//tbody[@kendogridtablebody]//td[" + getColumnHeaderIndex(inputLabel) + "]"), "values");
    int numberOfPages = getNumberOfPages();

    if (values.isEmpty()) {
      throw new Exception(MessageFormat.format("Brak wyszukanych dokumentów (szukano po {0} {1})", inputLabel, inputValue));
    } else if (numberOfPages > 10) {
      numberOfPages = 3;
      for (int i = 1; i <= numberOfPages; i++) {
        for (WebElement value : values) {
          if (!value.getText().toLowerCase().trim().contains(inputValue.toLowerCase())) {
            errors.add(MessageFormat.format("Na liście znajduje się {0} {1} gdy wyszukiwano po {2}", inputLabel, value.getText(), inputValue));
          }
        }
        if (i == 1) {
          clickNextPageBtn(i + 1);
          values = webDriver.findElements(By.xpath("//tbody[@kendogridtablebody]//td[" + getColumnHeaderIndex(inputLabel) + "]/span"));
        }
        if (i == 2) {
          clickLastPage();
          values = webDriver.findElements(By.xpath("//tbody[@kendogridtablebody]//td[" + getColumnHeaderIndex(inputLabel) + "]/span"));
        }
      }
    } else {
      for (int i = 1; i <= numberOfPages; i++) {
        for (WebElement value : values) {
          if (!value.getText().toLowerCase().trim().contains(inputValue.toLowerCase())) {
            errors.add(MessageFormat.format("Na liście znajduje się {0} {1} gdy wyszukiwano po {2}", inputLabel, value.getText(), inputValue));
          }
        }
        if (i != numberOfPages) {
          clickNextPageBtn(i + 1);
          values = webDriver.findElements(By.xpath("//tbody[@kendogridtablebody]//td[" + getColumnHeaderIndex(inputLabel) + "]/span"));
        }
      }
    }
  }

  public void checkOrdersFoundByGivenValue(String inputLabel, String inputValue, List<String> errors) {
    inputLabel = changeExtendedFilterLabelToGridHeader(inputLabel);
    List<WebElement> values = webDriver.findElements(By.xpath("//tbody[@kendogridtablebody]//td[" + getColumnHeaderIndex(inputLabel) + "]"));
    int numberOfPages = getNumberOfPages();

    if (values.isEmpty()) {
      Assert.fail(MessageFormat.format("Brak wyszukanych dokumentów (szukano po {0} {1})", inputLabel, inputValue));
    } else if (numberOfPages > 10) {
      numberOfPages = 3;
      for (int i = 1; i <= numberOfPages; i++) {
        for (WebElement value : values) {
          String tmp = value.getText().replaceAll("zł", "");
          tmp = tmp.replaceAll(",", "");
          tmp = tmp.replaceAll(" ", "");
          if (!tmp.contains(inputValue.replaceAll(",", ""))) {
            errors.add(MessageFormat.format("Na liście znajduje się {0} {1} gdy wyszukiwano po {2}", inputLabel, value.getText(), inputValue));
          }
        }
        if (i == 1) {
          clickNextPageBtn(i + 1);
          values = webDriver.findElements(By.xpath("//tbody[@kendogridtablebody]//td[" + getColumnHeaderIndex(inputLabel) + "]/span"));
        }
        if (i == 2) {
          clickLastPage();
          values = webDriver.findElements(By.xpath("//tbody[@kendogridtablebody]//td[" + getColumnHeaderIndex(inputLabel) + "]/span"));
        }
      }
    } else {
      for (int i = 1; i <= numberOfPages; i++) {
        for (WebElement value : values) {
          String tmp = value.getText().replaceAll("zł", "");
          tmp = tmp.replaceAll(",", "");
          tmp = tmp.replaceAll(" ", "");
          if (!tmp.contains(inputValue.replaceAll(",", ""))) {
            errors.add(MessageFormat.format("Na liście znajduje się {0} {1} gdy wyszukiwano po {2}", inputLabel, value.getText(), inputValue));
          }
        }
        if (i != numberOfPages) {
          clickNextPageBtn(i + 1);
          values = webDriver.findElements(By.xpath("//tbody[@kendogridtablebody]//td[" + getColumnHeaderIndex(inputLabel) + "]/span"));
        }
      }
    }
  }

  public void checkOrdersFoundByGivenNumber(String inputLabel, String inputValue, List<String> errors) throws Exception {
    inputLabel = changeExtendedFilterLabelToGridHeader(inputLabel);
    List<WebElement> values = webDriver.findElements(By.xpath(
      "//tbody[@kendogridtablebody]//td[" + getColumnHeaderIndex(changeExtendedFilterLabelToGridHeader(inputLabel)) + "]"));
    int numberOfPages = getNumberOfPages();
    List<String> valuesTxt = TextUtils.getTextFromWebElementsList(values);

    if (valuesTxt.isEmpty()) {
      Assert.fail(MessageFormat.format("Brak wyszukanych dokumentów (szukano po {0} {1})", inputLabel, inputValue));
    } else if (numberOfPages > 10) {
      numberOfPages = 3;
      for (int i = 1; i <= numberOfPages; i++) {
        for (String value : valuesTxt) {
          if (!value.contains(inputValue)) {
            errors.add(MessageFormat.format("Na liście znajduje się {0} {1} gdy wyszukiwano po {2}", inputLabel, value, inputValue));
          }
        }
        if (i == 1) {
          clickNextPageBtn(i + 1);
          values = webDriver.findElements(By.xpath("//tbody[@kendogridtablebody]//td[" + getColumnHeaderIndex(inputLabel) + "]/span"));
        }
        if (i == 2) {
          clickLastPage();
          values = webDriver.findElements(By.xpath("//tbody[@kendogridtablebody]//td[" + getColumnHeaderIndex(inputLabel) + "]/span"));
        }
      }
    } else {
      for (int i = 1; i <= numberOfPages; i++) {
        for (String value : valuesTxt) {
          if (!value.contains(inputValue)) {
            errors.add(MessageFormat.format("Na liście znajduje się {0} {1} gdy wyszukiwano po {2}", inputLabel, value, inputValue));
          }
        }
        if (i != numberOfPages) {
          clickNextPageBtn(i + 1);
          values = webDriver.findElements(By.xpath("//tbody[@kendogridtablebody]//td[" + getColumnHeaderIndex(inputLabel) + "]"));
          valuesTxt = TextUtils.getTextFromWebElementsList(values);
        }
      }
    }
  }

  private String changeExtendedFilterLabelToGridHeader(String inputLabel) {
    switch (inputLabel) {
      case "Nazwa KH":
        inputLabel = "Kontrahent";
        break;
      case "Makroregion":
        inputLabel = "Makr.";
        break;
      case "Wartość netto":
        inputLabel = "Wart. netto";
        break;
      case "Nr faktury":
        inputLabel = "Nr fakt.";
        break;
      case "Numer obcy":
        inputLabel = "Nr obcy";
        break;
      case "Wartość brutto":
        inputLabel = "Wart. brutto";
        break;
      case "Komentarz":
        inputLabel = "Koment.";
        break;
      case "Termin realizacji":
        inputLabel = "Termin real.";
        break;
      case "Numer dyspozycji":
        inputLabel = "Nr dysp.";
        break;
      case "ID płatnika":
        inputLabel = "Płatnik";
        break;
      case "Targowa":
        inputLabel = "Targowe";
        break;
      case "Płatność":
        inputLabel = "Płatn.";
        break;
      case "Nr zamówienia":
        inputLabel = "Nr zam.";
        break;
      case "Transport":
        inputLabel = "Transp.";
        break;
      default:
        break;
    }
    return inputLabel;
  }

  private Integer getNumberOfPages() {
    String numberOfPages = getTextFromElement(By.xpath("//kendo-pager//span[contains(text(),'Strona')]"));
    numberOfPages = numberOfPages.replaceAll("Strona 1 z", "");
    numberOfPages = numberOfPages.substring(0, numberOfPages.indexOf("(")).trim();

    return Integer.valueOf(numberOfPages);
  }

  private void clickNextPageBtn(int nextPageNumber) {
    WebElement nextPageLink = findClickableElement(By.xpath("//kendo-pager-next-buttons/a[@title='Go to the next page']"));
    nextPageLink.click();
    waitForElementToBeVisible(By.xpath(MessageFormat.format(
      "//kendo-pager-numeric-buttons//a[contains(@class,'selected') and contains(text(),''{0}'')]", nextPageNumber)));
    waitForDataIsLoaded();
  }

  public String getRandomKhCodeNumber() throws Exception {
    List<WebElement> khCodeElems = webDriver.findElements(By.xpath("//tbody[@kendogridtablebody]//td[" + getColumnHeaderIndex("Kod KH") + "]/span"));
    String khCode = "";
    List<String> dump = TextUtils.getTextFromWebElementsList(khCodeElems);

    if (dump.size() > 0) {
      khCode = dump.get(RandomUtils.randomInt(0, dump.size()));
    } else {
      Assert.fail("Nie pobrano żadnych kodów KH!");
    }
    return khCode;
  }

  public String getRandomKhNameNumber() throws Exception {
    List<WebElement> khNameElems = webDriver.findElements(By.xpath("//tbody[@kendogridtablebody]//td[" + getColumnHeaderIndex("Kontrahent") + "]/span"));
    String khName = "";
    List<String> dump = TextUtils.getTextFromWebElementsList(khNameElems);

    if (dump.size() > 0) {
      khName = dump.get(RandomUtils.randomInt(0, dump.size()));
    } else {
      Assert.fail("Nie pobrano żadnych Nazw KH!");
    }
    return khName;
  }

  public String getRandomOrderNumber() throws Exception {
    List<WebElement> ordersNumbersElems = webDriver.findElements(By.xpath("//a[contains(@class,'ec_link') and contains(@href,'zamowieniaHistoria/pokaz')]"));
    List<String> dump = TextUtils.getTextFromWebElementsList(ordersNumbersElems);

    if (dump.size() > 15) {
      dump = dump.subList(1, 15);
    }
    String orderNumber = "";
    if (dump.size() > 0) {
      orderNumber = dump.get(RandomUtils.randomInt(0, dump.size()));
    } else {
      Assert.fail("Nie udało się pobrać numeru zamówienia");
    }
    return orderNumber;
  }

  public String getRandomKhAbbreviationNumber() throws Exception {
    List<WebElement> abbreviationKh = webDriver.findElements(By.xpath("//tbody[@kendogridtablebody]//td[" + getColumnHeaderIndex("Skrót KH") + "]/span"));
    String abbreviationKhName = "";
    List<String> dump = TextUtils.getTextFromWebElementsList(abbreviationKh);

    if (dump.size() > 0) {
      abbreviationKhName = dump.get(RandomUtils.randomInt(0, dump.size()));
    } else {
      Assert.fail("Nie pobrano żadnych skrótów KH!");
    }
    return abbreviationKhName;
  }

  public String getRandomUser() throws Exception {
    List<WebElement> userElems = webDriver.findElements(By.xpath("//tbody[@kendogridtablebody]//td[" + getColumnHeaderIndex("Użytkownik") + "]/span"));
    String userName = "";
    List<String> dump = TextUtils.getTextFromWebElementsList(userElems);

    if (dump.size() > 0) {
      userName = dump.get(RandomUtils.randomInt(0, dump.size()));
      if (userName.contains("[")) {
        Pattern pattern = Pattern.compile("(?<=\\[)[^]]+(?=\\])");
        Matcher matcher = pattern.matcher(userName);
        if (matcher.find()) {
          userName = matcher.group();
        }
      }
    } else {
      Assert.fail("Nie pobrano żadnych Użytkowników!");
    }
    return userName;
  }

  public String getRandomMacroregion() throws Exception {
    List<WebElement> macroregionElems = webDriver.findElements(By.xpath("//tbody[@kendogridtablebody]//td[" + getColumnHeaderIndex("Makr.") + "]"));
    String macroregion = "";
    List<String> dump = TextUtils.getTextFromWebElementsList(macroregionElems);

    if (dump.size() > 0) {
      macroregion = dump.get(RandomUtils.randomInt(0, dump.size()));
    } else {
      Assert.fail("Nie pobrano żadnych makroregionów!");
    }
    return macroregion;
  }

  public String getRandomDepartment() throws Exception {
    List<WebElement> departmentElems = webDriver.findElements(By.xpath("//tbody[@kendogridtablebody]//td[" + getColumnHeaderIndex("Oddział") + "]"));
    String department = "";
    List<String> dump = TextUtils.getTextFromWebElementsList(departmentElems);

    if (departmentElems.size() > 0) {
      department = dump.get(RandomUtils.randomInt(0, dump.size())).toUpperCase();
    } else {
      Assert.fail("Nie pobrano żadnych oddziałów!");
    }
    return department;
  }

  public boolean checkResultsPresent(String originValue, List<String> errors) {
    waitForDataIsLoaded();
    if (isElementPresent(By.xpath("//tr[@class='k-grid-norecords']"))) {
      errors.add(MessageFormat.format("Brak wyników wyszukiwania po {0}", originValue));
      return false;
    } else {
      return true;
    }
  }

  public String getRandomNettoValue() throws Exception {
    List<WebElement> valueElems = webDriver.findElements(By.xpath("//tbody[@kendogridtablebody]//td[" + getColumnHeaderIndex("Wart. netto") + "]"));
    String netByValue = "";
    List<String> dump = TextUtils.getTextFromWebElementsList(valueElems);
    dump.removeIf(s -> s.equals("0,00 zł"));

    if (dump.size() > 0) {
      netByValue = dump.get(RandomUtils.randomInt(0, dump.size())).replaceAll("[\\szł]", "").trim();
    } else {
      Assert.fail("Nie pobrano ceny netto!");
    }
    return netByValue;
  }

  public String getRandomGrossValue() throws Exception {
    List<WebElement> valueElems = webDriver.findElements(By.xpath("//tbody[@kendogridtablebody]//td[" + getColumnHeaderIndex("Wart. brutto") + "]/span"));
    String grossByValue = "";
    List<String> dump = TextUtils.getTextFromWebElementsList(valueElems);
    dump.removeIf(s -> s.equals("0,00 zł"));

    if (dump.size() > 0) {
      grossByValue = dump.get(RandomUtils.randomInt(0, dump.size())).replaceAll("[\\szł]", "").trim();
    } else {
      Assert.fail("Nie pobrano ceny brutto!");
    }
    return grossByValue;
  }


  public String chooseStartDateInCalendar() {
    String prevMonthTabLoc = ".dx-calendar-navigator-previous-month";

    spanStartDateCalendar();
    findClickableElement(By.cssSelector(prevMonthTabLoc)).click();
    String year = findVisibleElement(By.xpath("//a[contains(@class,'dx-calendar-caption-button')]"))
      .getAttribute("aria-label").replaceAll("[\\D]", "");
    String monthText = findVisibleElement(By.xpath("//a[contains(@class,'dx-calendar-caption-button')]"))
      .getAttribute("aria-label").replaceAll("[0-9\\s]", "");
    String month = DateUtils.monthInCalendarToNumber(monthText);
    String day = clickRandomDay(monthText);

    return year + month + day;
  }

  public String chooseEndDateInCalendar() {
    spanEndDateCalendar();
    String year = findVisibleElement(By.xpath("(//a[contains(@class,'dx-calendar-caption-button')])[2]"))
      .getAttribute("aria-label").replaceAll("[\\D]", "");
    String monthText = findVisibleElement(By.xpath("(//a[contains(@class,'dx-calendar-caption-button')])[2]"))
      .getAttribute("aria-label").replaceAll("[0-9\\s]", "");
    String month = DateUtils.monthInCalendarToNumber(monthText);
    String day = clickRandomDay1(monthText);

    return year + month + day;
  }

  private String clickRandomDay(String monthText) {
    String prevMonthDaysXpath = "//td[@class='dx-calendar-cell' and contains(@aria-label,'" + monthText.substring(0, 3) + "')]/span";//substring bo deklinacja

    sleep(1000);
    waitUntil(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(prevMonthDaysXpath)));
    List<WebElement> daysElements = webDriver.findElements(By.xpath(prevMonthDaysXpath));

    WebElement randomDay = webDriver.findElement(By.xpath("(" + prevMonthDaysXpath + ")[" + RandomUtils.randomInt(1, daysElements.size() + 1) + "]"));
    String dayText = randomDay.getText();
    randomDay.click();
    waitForDataIsLoaded();

    if (dayText.length() == 1) {
      return "0" + dayText;
    } else {
      return dayText;
    }
  }

  private String clickRandomDay1(String monthText) {
    String prevMonthDaysXpath = "((//table//th[.='wt.'])[4])//..//..//..//td[contains(@class,'dx-calendar-today') and " +
      "contains(@aria-label,'" + monthText.substring(0, 3) + "')]/span";//substring bo deklinacja

    sleep(1000);
    waitUntil(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(prevMonthDaysXpath)));
    List<WebElement> daysElements = webDriver.findElements(By.xpath(prevMonthDaysXpath));

    WebElement randomDay = webDriver.findElement(By.xpath("(" + prevMonthDaysXpath + ")[" + RandomUtils.randomInt(1, daysElements.size() + 1) + "]"));
    String dayText = randomDay.getText();
    randomDay.click();
    waitForDataIsLoaded();

    if (dayText.length() == 1) {
      return "0" + dayText;
    } else {
      return dayText;
    }
  }

  private void spanStartDateCalendar() {
    WebElement dateFromCalendarSpanner = webDriver.findElement(By.xpath("//span[@id='lblDateFrom']//div[@class='dropDownButton']"));
    dateFromCalendarSpanner.click();
    waitForElementToBeVisible(By.xpath("//div[@class='dx-calendar-body']"));
  }

  private void spanEndDateCalendar() {
    WebElement dateToCalendarSpanner = findClickableElement(By.xpath("//span[@id='lblDateTo']//div[@class='dropDownButton']"));
    dateToCalendarSpanner.click();
    sleep(1000);
  }

  public void sortByInvoiceNumberDesc() {
    WebElement invoiceColumnSorter = webDriver.findElement(By.xpath("//a[@class='k-link' and contains(.,'Nr fakt.')]/span"));
    moveToElement(invoiceColumnSorter);
    scrollToElement(invoiceColumnSorter);
    scrollHorizontalBar(invoiceColumnSorter.getSize().width);
    scrollToTopOfPage();
    invoiceColumnSorter.click();
    waitForDataIsLoaded();
    invoiceColumnSorter.click();
    waitForDataIsLoaded();
  }

  public String getInvoiceNumber() throws Exception {
    List<WebElement> invoiceNumbersElems = webDriver.findElements(By.xpath("//tbody[@kendogridtablebody]//td[" + getColumnHeaderIndex("Nr fakt.") + "]/span"));
    String invoiceNumber = "";
    List<String> invoiceNumbers = TextUtils.getTextFromWebElementsList(invoiceNumbersElems);

    if (invoiceNumbers.size() > 0) {
      invoiceNumber = invoiceNumbers.get(RandomUtils.randomInt(0, invoiceNumbers.size())).replaceAll("[,]\\s\\d{0,10}", "");
    } else {
      Assert.fail("Nie pobrano żadnych numerów faktur!");
    }
    return invoiceNumber;
  }

  public void sortByForeignNumberDesc() {
    WebElement foreignNumberColumnSorter = webDriver.findElement(By.xpath("//a[@class='k-link' and contains(.,'Nr obcy')]"));
    moveToElement(foreignNumberColumnSorter);
    scrollToElement(foreignNumberColumnSorter);
    scrollToTopOfPage();
    foreignNumberColumnSorter.click();
    waitForDataIsLoaded();
    foreignNumberColumnSorter.click();
    waitForDataIsLoaded();
  }

  public void sortByCommentDesc() {
    WebElement commentColumnSorter = webDriver.findElement(By.xpath("//a[@class='k-link' and contains(.,'Koment.')]"));
    moveToElement(commentColumnSorter);
    scrollToElement(commentColumnSorter);
    scrollToTopOfPage();
    commentColumnSorter.click();
    waitForDataIsLoaded();
    commentColumnSorter.click();
    waitForDataIsLoaded();
  }

  public void sortByDealsDesc() {
    WebElement dealsColumnSorter = webDriver.findElement(By.xpath("//a[@class='k-link' and contains(.,'Deale')]/span"));
    moveToElement(dealsColumnSorter);
    scrollToElement(dealsColumnSorter);
    scrollToTopOfPage();
    scrollHorizontalBar(dealsColumnSorter.getSize().getWidth());
    dealsColumnSorter.click();
    waitForDataIsLoaded();
    dealsColumnSorter.click();
    waitForDataIsLoaded();
  }

  public void sortByTemplateDesc() {
    WebElement templateColumnSorter = webDriver.findElement(By.xpath("//a[@class='k-link' and contains(.,'Szablon')]"));
    moveToElement(templateColumnSorter);
    scrollToElement(templateColumnSorter);
    scrollToTopOfPage();
    scrollHorizontalBar(templateColumnSorter.getSize().getWidth());
    templateColumnSorter.click();
    waitForDataIsLoaded();
    templateColumnSorter.click();
    waitForDataIsLoaded();
  }

  public void sortByKhAbbreviationDesc() {
    WebElement dealsColumnSorter = webDriver.findElement(By.xpath("//a[@class='k-link' and contains(.,'Skrót KH')]/span"));
    moveToElement(dealsColumnSorter);
    scrollToElement(dealsColumnSorter);
    scrollToTopOfPage();
    scrollHorizontalBar(dealsColumnSorter.getSize().getWidth());
    dealsColumnSorter.click();
    waitForDataIsLoaded();
    dealsColumnSorter.click();
    waitForDataIsLoaded();
  }

  public void moveToElementOnGrid(String inputLabel) {
    WebElement elementOnGrid = webDriver.findElement(By.xpath("//a[@class='k-link' and contains(.,'" + changeExtendedFilterLabelToGridHeader(inputLabel) + "')]"));
    moveToElement(elementOnGrid);
    scrollToElement(elementOnGrid);
  }

  public String getRandomStringValueFromGrid(String inputLabel) throws Exception {
    List<WebElement> randomGridValuesElements = webDriver.findElements(By.xpath("//tbody[@kendogridtablebody]//td[" +
      getColumnHeaderIndex(changeExtendedFilterLabelToGridHeader(inputLabel)) + "]"));
    List<String> gridValues = TextUtils.getTextFromWebElementsList(randomGridValuesElements);
    String gridValue = "";


    if (gridValues.size() > 0) {
      gridValue = gridValues.get(RandomUtils.randomInt(0, gridValues.size()));
    } else {
      Assert.fail(MessageFormat.format("Nie pobrano żadnych {0}!", inputLabel));
    }

    int maxLength = 6;
    return cutGridValue(gridValue, maxLength);
  }

  private String cutGridValue(String gridValue, int maxLength) {
    if (gridValue.length() > maxLength) {
      return gridValue.substring(0, maxLength);
    } else {
      return gridValue;
    }
  }

  public void checkOrdersFoundByRealisationDate(String inputLabel, String date, List<String> errors) {
    String dateInGridFormat = DateUtils.changeDateFromSearchToGridFormat(date);
    inputLabel = changeExtendedFilterLabelToGridHeader(inputLabel);
    String valuesXpath = "//tbody[@kendogridtablebody]//td[" + getColumnHeaderIndex(inputLabel) + "]";
    List<WebElement> values = webDriver.findElements(By.xpath(valuesXpath));
    if (values.isEmpty()) {
      valuesXpath = "//tbody[@kendogridtablebody]//td[" + getColumnHeaderIndex(inputLabel) + "]/span";
      values = webDriver.findElements(By.xpath(valuesXpath));
    }
    int numberOfPages = getNumberOfPages();

    if (values.isEmpty()) {
      Assert.fail(MessageFormat.format("Brak wyszukanych dokumentów (szukano po {0} {1})", inputLabel, dateInGridFormat));
    } else if (numberOfPages > 10) {
      numberOfPages = 3;
      for (int i = 1; i <= numberOfPages; i++) {
        for (WebElement value : values) {
          if (!value.getText().trim().contains(dateInGridFormat)) {
            errors.add(MessageFormat.format("Na liście znajduje się {0} {1} gdy wyszukiwano po {2}", inputLabel, value.getText(), dateInGridFormat));
          }
        }
        if (i == 1) {
          clickNextPageBtn(i + 1);
          values = webDriver.findElements(By.xpath(valuesXpath));
        }
        if (i == 2) {
          clickLastPage();
          values = webDriver.findElements(By.xpath(valuesXpath));
        }
      }
    } else {
      for (int i = 1; i <= numberOfPages; i++) {
        for (WebElement value : values) {
          if (!value.getText().trim().contains(dateInGridFormat)) {
            errors.add(MessageFormat.format("Na liście znajduje się {0} {1} gdy wyszukiwano po {2}", inputLabel, value.getText(), dateInGridFormat));
          }
        }
        if (i != numberOfPages) {
          clickNextPageBtn(i + 1);
          values = webDriver.findElements(By.xpath(valuesXpath));
        }
      }
    }
  }

  public void clickSortByDate(String header) {
    String sortXpath = "//a[@class='k-link' and contains(.,'" + header + "')]/span";
    int i = 0;
    while (!isElementPresent(By.xpath(sortXpath)) && i <= 4) {
      scrollHorizontalBar(200);
      i++;
      if (!isElementPresent(By.xpath(sortXpath)) && i == 4) {
        Assert.fail(MessageFormat.format("Nie odnaleziono przycisku do sortowania po {0}", header));
      }
    }

    WebElement sortByDateBtn = webDriver.findElement(By.xpath(sortXpath));
    scrollHorizontalBar(20);
    sortByDateBtn.click();
    waitForDataIsLoaded();
  }

  public void checkOrdersSortedByDate(String header, String sortingType, List<String> errors) throws ParseException {
    List<Date> dates = getDatesFromGrid(header);
    Boolean isSorted = null;
    switch (sortingType) {
      case "asc":
        isSorted = Ordering.natural().isOrdered(dates);
        break;
      case "desc":
        isSorted = Ordering.natural().reverse().isOrdered(dates);
        break;
      default:
        Assert.fail("Typ sortowania może przyjmować tylko parametry 'asc' albo 'desc'");
        break;
    }

    if (!isSorted) {
      errors.add(MessageFormat.format("Dane nie są posortowane {0} po {1}", sortingType, header));
    }
    clickLastPage();
    dates.addAll(getDatesFromGrid(header));
    switch (sortingType) {
      case "asc":
        isSorted = Ordering.natural().isOrdered(dates);
        break;
      case "desc":
        isSorted = Ordering.natural().reverse().isOrdered(dates);
        break;
      default:
        Assert.fail("Typ sortowania może przyjmować tylko parametry 'asc' albo 'desc'");
        break;
    }

    if (!isSorted) {
      errors.add(MessageFormat.format("Dane nie są posortowane {0} po {1}", sortingType, header));
    }
  }

  private List<Date> getDatesFromGrid(String header) throws ParseException {
    List<WebElement> datesElems = webDriver.findElements(By.xpath("//table//td[" + getColumnHeaderIndex(header) + "]"));
    List<Date> dates = new ArrayList<>();

    if (datesElems.get(0).getText().trim().length() > 10) {
      for (WebElement date : datesElems) {
        dates.add(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(date.getText().trim()));
      }
    } else {
      for (WebElement date : datesElems) {
        dates.add(new SimpleDateFormat("dd-MM-yyyy").parse(date.getText().trim()));
      }
    }
    return dates;
  }

  public void clickSortHeader(String sortingHeader) {
    String sortXpath = "//a[@class='k-link' and contains(.,'" + sortingHeader + "')]/span";

    WebElement sorter = webDriver.findElement(By.xpath(sortXpath));
    scrollToElement(sorter);
    scrollToTopOfPage();
    scrollHorizontalBar(sorter.getSize().getWidth());
    try {
      sorter.click();
    } catch (Exception e) {
      scrollHorizontalBar(findVisibleElement(By.cssSelector(".popup-anchor")).getSize().width);
      sorter.click();
    }
    waitForDataIsLoaded();
  }

  private List<String> sortList(String sortingType, List<String> temp) {
    switch (sortingType) {
      case "asc":
        TextUtils.sortListWithPolishChars(temp);
        break;
      case "desc":
        TextUtils.sortListWithPolishChars(temp);
        temp = Lists.reverse(temp);
        break;
      default:
        Assert.fail("Typ sortowania może przyjmować tylko parametry 'asc' albo 'desc'");
        break;
    }

    return temp;
  }

  public void checkOrdersSortedByWord(String sortingHeader, String sortingType, List<String> errors) {
    List<String> values = getStringsFromGrid(sortingHeader);
    List<String> temp = new ArrayList<>();
    temp.addAll(values);
    temp = sortList(sortingType, temp);
    if (!temp.equals(values)) {
      errors.add(MessageFormat.format("Dane na pierwszej stronie nie są posortowane {0} po {1}", sortingType, sortingHeader));
    }

    clickLastPage();
    values.clear();
    temp.clear();
    values.addAll(getStringsFromGrid(sortingHeader));
    temp.addAll(values);
    temp = sortList(sortingType, temp);
    if (!temp.equals(values)) {
      errors.add(MessageFormat.format("Dane na ostatniej stronie nie są posortowane {0} po {1}", sortingType, sortingHeader));
    }
  }

  private List<String> getStringsFromGrid(String sortingHeader) {
    List<WebElement> valuesElems = webDriver.findElements(By.xpath("//table//td[" + getColumnHeaderIndex(sortingHeader) + "]"));
    List<String> values = new ArrayList<>();

    for (WebElement valuesElem : valuesElems) {
      values.add(valuesElem.getText().toUpperCase(Locale.getDefault()).trim());
    }
    return values;
  }

  public void checkOrdersSortedByValue(String header, String sortingType, List<String> errors) {
    List<Double> dates = getNumbersFromGrid(header);
    Boolean isSorted = null;
    switch (sortingType) {
      case "asc":
        isSorted = Ordering.natural().isOrdered(dates);
        break;
      case "desc":
        isSorted = Ordering.natural().reverse().isOrdered(dates);
        break;
      default:
        Assert.fail("Typ sortowania może przyjmować tylko parametry 'asc' albo 'desc'");
        break;
    }

    if (!isSorted) {
      errors.add(MessageFormat.format("Dane nie są posortowane {0} po {1}", sortingType, header));
    }
    clickLastPage();
    dates.addAll(getNumbersFromGrid(header));
    switch (sortingType) {
      case "asc":
        isSorted = Ordering.natural().isOrdered(dates);
        break;
      case "desc":
        isSorted = Ordering.natural().reverse().isOrdered(dates);
        break;
      default:
        Assert.fail("Typ sortowania może przyjmować tylko parametry 'asc' albo 'desc'");
        break;
    }

    if (!isSorted) {
      errors.add(MessageFormat.format("Dane nie są posortowane {0} po {1}", sortingType, header));
    }
  }

  private List<Double> getNumbersFromGrid(String header) {
    List<WebElement> valuesElems = webDriver.findElements(By.xpath("//kendo-grid-list//td[" + getColumnHeaderIndex(header) + "]"));
    List<Double> values = new ArrayList<>();

    for (WebElement valuesElem : valuesElems) {
      String tmp = valuesElem.getText().replaceAll("[\\[\\]zł\\s]", "");
      tmp = tmp.replaceAll(",", ".");
      if (tmp.isEmpty()) {
        tmp = "0";
      }
      values.add(Double.valueOf(tmp));
    }

    return values;
  }

  public OrdersSettingsForm clickSettingsBtn() {
    WebElement settingBtn = webDriver.findElement(By.cssSelector(".popup-anchor .fa-cog"));
    settingBtn.click();
    waitForElementToBeVisible(By.xpath("//div[@class='popup-content grid-settings-content']"));

    return new OrdersSettingsForm(this);
  }

  public void checkColumnIsVisible(String columnHeader, List<String> errors) {
    waitForDataIsLoaded();
    if (getColumnHeaderIndex(columnHeader) == 0) {
      errors.add(MessageFormat.format("Kolumna {0} nie jest widoczna po zaznaczeniu", columnHeader));
    }
    scrollHorizontalBar(-getXoffset());
  }

  public void checkColumnNotVisible(String columnHeader, List<String> errors) {
    waitForDataIsLoaded();
    if (getColumnHeaderIndex(columnHeader) != 0) {
      errors.add(MessageFormat.format("Kolumna {0} nadal widoczna po odznaczeniu", columnHeader));
    }
    scrollHorizontalBar(-getXoffset());
  }

  public Integer getXoffset() {
    String offset = webDriver.findElement(By.xpath("//div[@class='ps__thumb-x']")).getAttribute("style");
    offset = offset.substring(offset.indexOf(" ") + 1, offset.indexOf("p"));

    return Integer.valueOf(offset);
  }

  public void checkOrdersListView(String numberOfRows, List<String> errors) {
    int extractedRows = webDriver.findElements(By.xpath("//table[@class='k-grid-table']//tr")).size();

    if (extractedRows != Integer.valueOf(numberOfRows)) {
      errors.add(MessageFormat.format("Nieprawidłowa ilość wierszy w gridzie. Oczekiwano {0} a jest {1}", numberOfRows, extractedRows));
    }
  }

  public void ensureAllColumnsDisplayed() {
    OrdersSettingsForm ordersSettingsForm = clickSettingsBtn();
    List<String> columnHeaders = ordersSettingsForm.getAllColumns();
    ordersSettingsForm.clickChooseBtnInSettingPopup();
    List<String> columnsToFix = new ArrayList<>();

    for (String columnHeader : columnHeaders) {
      if (getColumnHeaderIndex(columnHeader) == 0) {
        columnsToFix.add(columnHeader);
      }
      scrollHorizontalBar(-getXoffset());
    }
    if (columnsToFix.size() > 0) {
      ordersSettingsForm = clickSettingsBtn();
      for (String columnToFix : columnsToFix) {
        ordersSettingsForm.clickColumnCheckbox(columnToFix);
      }
      ordersSettingsForm.clickChooseBtnInSettingPopup();
    }
  }

  public void ensureGivenColumnDisplayed(String columnHeader) throws Exception {
    columnHeader = changeExtendedFilterLabelToGridHeader(columnHeader);
    int columnIndex = getColumnHeaderIndex(columnHeader);
    if (columnIndex == 0) {
     /* OrdersSettingsForm ordersSettingsForm = clickSettingsBtn();
      ordersSettingsForm.clickColumnCheckbox(columnHeader);
      ordersSettingsForm.clickChooseBtnInSettingPopup();
      waitForDataIsLoaded();*/
      throw new Exception(MessageFormat.format("Brak kolumny {0}", columnHeader));
    }
  }

  public String getNetValue(String orderNumber) {
    return getTextFromElement(By.xpath("//a[@class='ec_link' and contains(@href,'" + orderNumber + "')]" +
      "/../../td[" + getColumnHeaderIndex("Wart. netto") + "]"));
  }

  public String getGrossValue(String orderNumber) {
    return getTextFromElement(By.xpath("//a[@class='ec_link' and contains(@href,'" + orderNumber + "')]" +
      "/../../td[" + getColumnHeaderIndex("Wart. brutto") + "]"));
  }

  public OrderDetailsPage actionsOpenOrderDetails(String orderNumber) {
    spanActionsMenu(orderNumber);
    WebElement openOrderDetailsLink = webDriver.findElement(By.xpath("//div[contains(text(),'Przejdź do szczegółów zamówienia')]"));
    moveToElement(openOrderDetailsLink);
    openOrderDetailsLink.click();
    return new OrderDetailsPage(this);
  }

  private void spanActionsMenu(String orderNumber) {
    WebElement actionsBtn = webDriver.findElement(By.xpath("//a[@class='ec_link' and contains(text(),'" + orderNumber + "')]/../..//ech-action-menu"));
    moveToElement(actionsBtn);
    actionsBtn.click();
    waitForElementToBeVisible(By.xpath("//a[@class='ec_link' and contains(text(),'" + orderNumber + "')]" +
      "/../..//ech-action-menu//div[contains(@class,'popup-content')]"));
  }

  public void checkDocumentListView(int numberOfRows, List<String> errors) {
    List<WebElement> docsNumbersElems = webDriver.findElements(By.xpath("//a[contains(@class,'ec_link') and contains(@href,'zamowieniaHistoria/pokaz')]"));
    if (docsNumbersElems.size() != numberOfRows) {
      errors.add(MessageFormat.format("W widoku pełnym nie pokazuje się lista {0} dokumentów", numberOfRows));
    }
    waitForDataIsLoaded();
  }

  public TutorialForm clickTutorialLink() {
    WebElement tutorialBtn = findClickableElement(By.xpath("//span[contains(@class,'show-intro')]"));
    tutorialBtn.click();
    waitForElementToBeVisible(By.xpath("//div[@class='introjs-tooltip']"));

    return new TutorialForm(this);
  }

  public Boolean checkSearchResultsPresent(String searchPhrase, List<String> errors) {
    if (isElementPresent(By.className("no-records-info"))) {
      errors.add(MessageFormat.format("Brak wyników wyszukiwania po {0}", searchPhrase));
      return false;
    } else {
      return true;
    }
  }

  public void checkOrdersImportPopupOpen(List<String> errors) {
    if (!isElementPresent(By.xpath("//a[@aria-label='Close']"))) {
      errors.add("Brak otwartego Popupa do importu zamówień");
    }
  }

  public void closeOrdersImportPopup() {
    String closePopupBtnLoc = "//a[@aria-label='Close']";

    if (isElementPresent(By.xpath(closePopupBtnLoc))) {
      WebElement closeImportPopup = findClickableElement(By.xpath(closePopupBtnLoc));
      closeImportPopup.click();
      try {
        waitForElementToBeInvisible(By.cssSelector(".k-overlay"));
      } catch (Exception e) {
        //
      }
    }
  }

  public void checkOrderHistoryPageLoaded(List<String> errors) {
    if (!isElementPresent(By.cssSelector(".k-grid-container"))) {
      errors.add("Strona Zamówienia nie została wczytana poprawnie");
    }
  }

}
