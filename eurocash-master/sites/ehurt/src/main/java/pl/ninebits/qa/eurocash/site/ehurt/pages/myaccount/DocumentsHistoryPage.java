package pl.ninebits.qa.eurocash.site.ehurt.pages.myaccount;

import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import org.openqa.selenium.By;
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
import pl.ninebits.qa.eurocash.site.ehurt.forms.FiltersForm;
import pl.ninebits.qa.eurocash.site.ehurt.forms.SettingsForm;
import pl.ninebits.qa.eurocash.site.ehurt.forms.TutorialForm;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtBasePage;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DocumentsHistoryPage extends EhurtBasePage {

  public DocumentsHistoryPage(BasePage pageObject) {
    super(pageObject);
    checkPageLoaded();
    closeTutorial();
    clickNormalView();
    closeRODOIfPresent();
    ensureDataForTestsPresent();
  }

  private void checkPageLoaded() {
    try {
      waitForDataIsLoaded();
      waitForElementToBeVisible(By.xpath("//div[@id='documentHistoryGrid']"));
    } catch (Exception e) {
      throw new NoSuchElementException("Strona 'Historia Dokumentów' nie została wczytana poprawnie'");
    }
  }

  private List<String> getColumnHeaders() {
    List<WebElement> elements = webDriver.findElements(By.xpath("//th[contains(@class,'k-header')]"));
    List<String> headers = new ArrayList<>();

    if (!elements.isEmpty()) {
      for (WebElement element : elements) {
        headers.add(element.getText());
      }
    } else {
      Assert.fail("Nie udało się pobrać nazw kolumn gridu 'Historia dokumentów'");
    }
    return headers;
  }

  private Integer getColumnHeaderIndex(String columnHeader) {
    return getColumnHeaders().indexOf(columnHeader) + 1;
  }

  private void ensureDataForTestsPresent() {
    if (getNumberOfRecords() < 40) {
      enterDaysBack("20");
    }
  }

  public void ensureGivenColumnDisplayed(String columnHeader) {
    columnHeader = changeExtendedFilterLabelToGridHeader(columnHeader);
    int columnIndex = getColumnHeaderIndex(columnHeader);
    if (columnIndex == 0) {
      SettingsForm settingsForm = clickSettingsBtn();
      settingsForm.clickColumnCheckbox(columnHeader);
      settingsForm.clickChooseBtnInSettingPopup();
      waitForDataIsLoaded();
    }
  }

  public void ensureAllColumnsDisplayed() {
    SettingsForm settingsForm = clickSettingsBtn();
    List<String> columnHeaders = settingsForm.getAllColumns();
    settingsForm.clickChooseBtnInSettingPopup();
    List<String> columnsToFix = new ArrayList<>();

    for (String columnHeader : columnHeaders) {
      if (getColumnHeaderIndex(columnHeader) == 0) {
        columnsToFix.add(columnHeader);
      }
    //  scrollHorizontalBar(-getXoffset());
    }
    if (columnsToFix.size() > 0) {
      settingsForm = clickSettingsBtn();
      for (String columnToFix : columnsToFix) {
        settingsForm.clickColumnCheckbox(columnToFix);
      }
      settingsForm.clickChooseBtnInSettingPopup();
    }
  }

  private void scrollHorizontalBar(int offset) {
    Actions actions = new Actions(webDriver);
    WebElement horizontalScroller = webDriver.findElement(By.xpath("//div[@class='ps__thumb-x']"));
    actions.dragAndDropBy(horizontalScroller, offset, 0).perform(); //839
  }

  private Integer getXoffset() {
    String offset = webDriver.findElement(By.xpath("//div[@class='ps__thumb-x']")).getAttribute("style");
    offset = offset.substring(offset.indexOf(" ") + 1, offset.indexOf("p"));

    return Integer.valueOf(offset);
  }

  public void typeSearchPhrase(String searchPhrase) {
    WebElement searchInput = webDriver.findElement(By.xpath("//input[@name='wyszukajTowar' and @placeholder]"));
    searchInput.clear();
    searchInput.sendKeys(searchPhrase);
    searchInput.sendKeys(Keys.RETURN);
    waitForDataIsLoaded();
  }

  public DocumentDetailsPage openDocumentWithNumber(String documentNumber) {
    WebElement docNumberLink = findClickableElement(By.xpath("//a[contains(@class,'split-wrap__txt') and contains(@href,'" + documentNumber + "')]"));
    docNumberLink.click();
    return new DocumentDetailsPage(this);
  }

  public DocumentDetailsPage openDocumentWithDisplay(String documentNumber) {
    WebElement docNumberLink = webDriver.findElement(By.xpath("//a[@class='ec_link linkPokaz' and contains(@href,'/pokaz?id=" + documentNumber + "')]"));
    docNumberLink.click();
    return new DocumentDetailsPage(this);
  }

  public String getRandomDocNumber() throws Exception {
    waitUntil(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//a[contains(@class,'split-wrap__txt') and contains(@href,'dokumentyHistoria/pokaz')]")));
    List<WebElement> docsNumbersElems = webDriver.findElements(By.xpath("//a[contains(@class,'split-wrap__txt') and contains(@href,'dokumentyHistoria/pokaz')]"));
    String docNumber = "";
    List<String> docNumbers = TextUtils.getTextFromWebElementsList(docsNumbersElems);

    if (docNumbers.size() > 0) {
      docNumber = docNumbers.get(RandomUtils.randomInt(0, docNumbers.size()));
    } else {
      Assert.fail("Nie udało się pobrać numeru dokumentu");
    }
    return docNumber;
  }

  public DocumentDetailsPage openRandomVisibleDocument() {
    List<WebElement> docsNumbersElems = webDriver.findElements(By.xpath("//a[contains(@class,'split-wrap__txt') and contains(@href,'dokumentyHistoria/pokaz')]"));
    docsNumbersElems.get(RandomUtils.randomInt(0, docsNumbersElems.size())).click();

    return new DocumentDetailsPage(this);
  }

  public void enterStartDate(String date) {
    WebElement startDateInput = webDriver.findElement(By.xpath("//span[@id='lblDateFrom']//input[@class='dx-texteditor-input']"));
    closeTutorial();
    startDateInput.click();
    startDateInput.clear();
    startDateInput.sendKeys(date);
    startDateInput.sendKeys(Keys.ENTER);
    waitForDataIsLoaded();
  }

  public void enterEndDate(String date) {
    WebElement endDateInput = webDriver.findElement(By.xpath("//span[@id='lblDateTo']//input[@class='dx-texteditor-input']"));
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

  public void checkDocumentsSortedByDateOfIssueAsc(String startDate, String endDate, List<String> errors) throws Exception {
    String startDateInGridFormat = DateUtils.changeDateFromSearchToGridFormat(startDate);
    String endDateInGridFormat = DateUtils.changeDateFromSearchToGridFormat(endDate);
    List<String> dateOfIssueDates = getDateOfIssueDates();
    boolean isSorted = Ordering.natural().isOrdered(dateOfIssueDates);

    checkFirstElementIssueDate(startDateInGridFormat, errors);
    if (!isSorted) {
      errors.add("Dane nie są posortowane rosnąco po dacie wydania dokumentu");
    }
    clickLastPage();
    dateOfIssueDates.addAll(getDateOfIssueDates());
    checkLastElementIssueDate(endDateInGridFormat, errors);
    if (!isSorted) {
      errors.add("Dane nie są posortowane rosnąco po dacie wydania dokumentu");
    }
  }

  public void checkDocumentsSortedByDateDesc(String startDate, String endDate, List<String> errors) throws Exception {
    String startDateInGridFormat = DateUtils.changeDateFromSearchToGridFormat(startDate);
    String endDateInGridFormat = DateUtils.changeDateFromSearchToGridFormat(endDate);
    List<String> dateOfIssueDates = getDateOfIssueDates();
    boolean isSorted = Ordering.natural().reverse().isOrdered(dateOfIssueDates);

    checkFirstElementIssueDate(startDateInGridFormat, errors);
    if (!isSorted) {
      errors.add("Dane nie są posortowane malejąco po dacie wydania dokumentu");
    }
    clickLastPage();
    dateOfIssueDates.addAll(getDateOfIssueDates());
    checkLastElementIssueDate(endDateInGridFormat, errors);
    if (!isSorted) {
      errors.add("Dane nie są posortowane malejąco po dacie wydania dokumentu");
    }
  }

  private void checkLastElementIssueDate(String endDate, List<String> errors) throws ParseException {
    String extractedLastIssueDate = webDriver.findElement(By.xpath("(//table//td[" + getColumnHeaderIndex("Data wyst.") + "])[last()]")).getText().trim();
    Date extractedDate = new SimpleDateFormat("dd-MM-yyyy").parse(extractedLastIssueDate);
    Date endDateInDateFormat = new SimpleDateFormat("dd-MM-yyyy").parse(endDate);

    if (extractedDate.after(endDateInDateFormat)) {
      errors.add(MessageFormat.format("Ostatni dokument na liście nie posiada datę wystawienia póżniejszą niż {0}", endDate));
    }
  }

  private void checkFirstElementIssueDate(String startDate, List<String> errors) {
    waitForDataIsLoaded();
    sleep(1000);
    Integer columnHeaderIndex = getColumnHeaderIndex("Data wyst.");
    String xpath = "(//table[@class='k-grid-table']//td[" + columnHeaderIndex + "])[1]";
    if (isElementPresent(By.xpath(xpath))) {
      String extractedFirstIssueDate = webDriver.findElement(By.xpath(xpath)).getText().trim();
      if (!extractedFirstIssueDate.equals(startDate)) {
        errors.add(MessageFormat.format("Pierwszy dokument na liście nie posiada daty wystawienia {0}", startDate));
      }
    } else if (isElementPresent(By.className("no-records-info"))) {
      errors.add("Brak wyników wyszukiwania po dacie wydania");
    } else {
      errors.add("Nie odnaleziono elementu z datą");
    }
  }

  public void clickLastPage() {
    WebElement lastPageBtn = webDriver.findElement(By.xpath("//span[@aria-label='Go to the last page']"));
    lastPageBtn.click();
    waitForDataIsLoaded();
  }

  public void clickNextPage() {
    WebElement nextPageBtn = webDriver.findElement(By.xpath("//span[@class='k-icon k-i-arrow-e']"));
    nextPageBtn.click();
    waitForDataIsLoaded();
  }

  public void clickFirstPage() {
    String firstPageBtn = "//a[@class='k-link k-pager-nav k-pager-first']//span[@aria-label='Go to the first page']";

    if (isElementPresent(By.xpath(firstPageBtn))) {
      WebElement clickFirstBtn = webDriver.findElement(By.xpath(firstPageBtn));
      clickFirstBtn.click();
      waitForDataIsLoaded();
    }
  }

  private List<String> getDateOfIssueDates() throws Exception {
    List<WebElement> datesOfIssueElems = webDriver.findElements(By.xpath("//table//td[" + getColumnHeaderIndex("Data wyst.") + "]"));

    return TextUtils.getTextFromWebElementsList(datesOfIssueElems);
  }

  public void clearSearchFilters() {
    String clearFiltersBtnXpath = "//span[@class='headerItem']//span[contains(text(),'Wyczyść filtry')]";

    if (isElementPresent(By.xpath(clearFiltersBtnXpath))) {
      WebElement clearFiltersBtn = webDriver.findElement(By.xpath(clearFiltersBtnXpath));
      clearFiltersBtn.click();
      waitForDataIsLoaded();
    }
  }

  public void clickSimpleView() {
    WebElement clickStraightViewBtn = webDriver.findElement(By.xpath("//i[contains(@class,'pozycje')]"));
    clickStraightViewBtn.click();
    waitForDataIsLoaded();
    waitForElementToBeVisible(By.xpath("//i[contains(@class,'pozycje')]"));
  }

  public void checkListSimpleView(List<String> errors) {
    List<WebElement> docsNumbersElems = webDriver.findElements(By.xpath("//a[contains(@class,'split-wrap__txt') and contains(@href,'dokumentyHistoria/pokaz')]"));
    if (docsNumbersElems.size() != 5) {
      errors.add("W widoku prostym nie pokazuje się lista 5 dokumentów");
    }
    waitForDataIsLoaded();
  }

  public void checkDocumentListView(int numberOfRows, List<String> errors) {
    List<WebElement> docsNumbersElems = webDriver.findElements(By.xpath("//a[contains(@class,'split-wrap__txt') and contains(@href,'dokumentyHistoria/pokaz')]"));
    sleep(1000);
    if (docsNumbersElems.size() != numberOfRows) {
      errors.add(MessageFormat.format("W widoku pełnym nie pokazuje się lista {0} dokumentów", numberOfRows));
    }
    waitForDataIsLoaded();
  }

  public void clickSortByIssueDateBtn() {
    String ascSortXpath = "//a[contains(.,'Data wyst.')]/span[contains(@class,'sort-asc')]";
    String descSortXpath = "//a[contains(.,'Data wyst.')]/span[contains(@class,'sort-desc')]";

    if (isElementPresent(By.xpath(ascSortXpath))) {
      WebElement ascSortBtn = webDriver.findElement(By.xpath(ascSortXpath));
      ascSortBtn.click();
      waitForDataIsLoaded();
      waitForElementToBeVisible(By.xpath(descSortXpath));
    } else if (isElementPresent(By.xpath(descSortXpath))) {
      WebElement descSortBtn = webDriver.findElement(By.xpath(descSortXpath));
      descSortBtn.click();
      waitForDataIsLoaded();
      waitForElementToBeVisible(By.xpath(ascSortXpath));
    } else {
      Assert.fail("Brak przycisku do sortowania po dacie");
    }
  }

  public void clickSortByWords(String columnHeader) {
    String sorterXpath = "//table//th[" + getColumnHeaderIndex(columnHeader) + "]/a/span";
    WebElement columnSorter = webDriver.findElement(By.xpath(sorterXpath));
    sort(columnSorter, sorterXpath);
  }

  private void sort(WebElement columnSorter, String sorterXpath) {
    if (columnSorter.getAttribute("class").contains("asc")) {
      columnSorter.click();
      waitForDataIsLoaded();
      waitForElementToBeVisible(By.xpath(sorterXpath + "[contains(@class,'desc')]"));
    } else if (columnSorter.getAttribute("class").contains("desc")) {
      columnSorter.click();
      waitForDataIsLoaded();
      waitForElementToBeVisible(By.xpath(sorterXpath + "[not(contains(@class,'desc')) and not(contains(@class,'asc'))]"));
    } else {
      columnSorter.click();
      waitForDataIsLoaded();
      waitForElementToBeVisible(By.xpath(sorterXpath + "[contains(@class,'asc')]"));
    }

  }

  public void checkDocumentsSortedByWordsAsc(String columnHeader, List<String> errors) {
    List<String> columnValues = getValuesFromGivenColumn(columnHeader);
    List<String> temp = new ArrayList<>();
    boolean isSorted = Ordering.natural().isOrdered(columnValues);

    if (!isSorted) {
      errors.add(MessageFormat.format("Dane na pierwszej stronie nie są posortowane rosnąco po {0}", columnHeader));
    }
    clickLastPage();
    columnValues.clear();
    columnValues.addAll(getValuesFromGivenColumn(columnHeader));
    temp.addAll(columnValues);
    temp = TextUtils.capitalizeStringsOnList(temp);
    TextUtils.sortListWithPolishChars(temp);
    TextUtils.clearNullsFromList(temp);
    TextUtils.clearNullsFromList(columnValues);
    if (!temp.equals(columnValues)) {
      errors.add(MessageFormat.format("Dane na ostatniej stronie nie są posortowane rosnąco po {0}", columnHeader));
    }
  }

  public void checkDocumentsSortedByWordsDesc(String columnHeader, List<String> errors) {
    List<String> columnValues = TextUtils.capitalizeStringsOnList(getValuesFromGivenColumn(columnHeader));
    List<String> temp = new ArrayList<>();
    temp.addAll(columnValues);
    temp = TextUtils.capitalizeStringsOnList(temp);
    TextUtils.sortListWithPolishChars(temp);
    temp = Lists.reverse(temp);
    TextUtils.clearNullsFromList(temp);
    TextUtils.clearNullsFromList(columnValues);
    Collections.replaceAll(temp, "-", "");
    if (!temp.equals(columnValues)) {
      errors.add(MessageFormat.format("Dane na pierwszej stronie nie są posortowane malejąco po {0}", columnHeader));
      System.out.println(temp);
      System.out.println(columnValues);
    }

    clickLastPage();
    columnValues.clear();
    columnValues.addAll(TextUtils.capitalizeStringsOnList(getValuesFromGivenColumn(columnHeader)));
    temp.clear();
    temp.addAll(columnValues);
    temp = TextUtils.capitalizeStringsOnList(temp);
    TextUtils.sortListWithPolishChars(temp);
    temp = Lists.reverse(temp);
    TextUtils.clearNullsFromList(temp);
    TextUtils.clearNullsFromList(columnValues);
    Collections.replaceAll(temp, "-", "");
    if (!temp.equals(columnValues)) {
      errors.add(MessageFormat.format("Dane na ostatniej stronie nie są posortowane malejąco po {0}", columnHeader));
    }
  }

  public void checkDocumentsSortedByNumberAsc(String columnHeader, List<String> errors) {
    List<Double> columnValues = getNumberValuesFromGivenColumn(columnHeader);
    boolean isSorted = Ordering.natural().isOrdered(columnValues);

    if (!isSorted) {
      errors.add(MessageFormat.format("Dane na pierwszej stronie nie są posortowane rosnąco po {0}", columnHeader));
    }
    clickLastPage();
    columnValues.clear();
    columnValues.addAll(getNumberValuesFromGivenColumn(columnHeader));
    isSorted = Ordering.natural().isOrdered(columnValues);
    if (!isSorted) {
      errors.add(MessageFormat.format("Dane na ostatniej stronie nie są posortowane rosnąco po {0}", columnHeader));
    }
  }

  public void checkDocumentFoundByGivenCriteria(String inputLabel, String inputValue, List<String> errors) {
    inputLabel = changeExtendedFilterLabelToGridHeader(inputLabel);
    List<WebElement> values = webDriver.findElements(By.xpath("//tbody[@kendogridtablebody]//td[" + getColumnHeaderIndex(inputLabel) + "]"));
    Integer numberOfPages = getNumberOfPages();

    if (values.isEmpty()) {
      Assert.fail(MessageFormat.format("Brak wyszukanych dokumentów (szukano po {0} {1})", inputLabel, inputValue));
    } else if (numberOfPages > 10) {
      numberOfPages = 3;
      for (int i = 1; i <= numberOfPages; i++) {
        for (WebElement value : values) {
          if (!value.getText().toLowerCase().contains(inputValue.toLowerCase())) {
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
          if (!value.getText().toLowerCase().contains(inputValue.trim().toLowerCase())) {
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

  private String changeExtendedFilterLabelToGridHeader(String inputLabel) {
    switch (inputLabel) {
      case "Nazwa KH":
        inputLabel = "Kontrahent";
        break;
      case "Makroregion":
        inputLabel = "Makr.";
        break;
      default:
        break;
    }
    return inputLabel;
  }

  public void checkDocumentsSortedByNumberDesc(String columnHeader, List<String> errors) {
    List<Double> columnValues = getNumberValuesFromGivenColumn(columnHeader);
    boolean isSorted = Ordering.natural().reverse().isOrdered(columnValues);

    if (!isSorted) {
      errors.add(MessageFormat.format("Dane na pierwszej nie są posortowane malejąco po {0}", columnHeader));
    }
    clickLastPage();
    columnValues.clear();
    columnValues.addAll(getNumberValuesFromGivenColumn(columnHeader));
    isSorted = Ordering.natural().reverse().isOrdered(columnValues);
    if (!isSorted) {
      errors.add(MessageFormat.format("Dane na ostatniej stronie nie są posortowane malejąco po {0}", columnHeader));
    }
  }

  private List<Double> getNumberValuesFromGivenColumn(String columnHeader) {
    List<WebElement> columnValuesElems = webDriver.findElements(By.xpath("//table[@class='k-grid-table']//td[" + getColumnHeaderIndex(columnHeader) + "]"));
    List<Double> columnValues = new ArrayList<>();

    for (WebElement columnValueElem : columnValuesElems) {
      String elemText = columnValueElem.getText().replaceAll("zł", "");
      elemText = elemText.replaceAll(" ", "");
      elemText = elemText.replaceAll(",", ".");
      columnValues.add(Double.valueOf(elemText));
    }
    return columnValues;
  }

  private List<String> getValuesFromGivenColumn(String columnHeader) {
    List<WebElement> columnValuesElems = webDriver.findElements(By.xpath("//table[@class='k-grid-table']//td[" + getColumnHeaderIndex(columnHeader) + "]"));
    List<String> columnValues = new ArrayList<>();

    for (WebElement paymentDateElem : columnValuesElems) {
      columnValues.add(paymentDateElem.getText().toUpperCase(Locale.getDefault()).trim());
    }
    return columnValues;

  }

  public FiltersForm clickShowFiltersBtn() {
    String showFiltersBtnId = "sidebarToggleBtn";

    if (isElementPresent(By.id(showFiltersBtnId))) {
      WebElement showFiltersBtn = webDriver.findElement(By.id(showFiltersBtnId));
      showFiltersBtn.click();
      waitForElementToBeVisible(By.xpath("//div[@id='sidebar']"));
    } else {
      Assert.fail("Brak przycisku Pokaż filtry!");
    }
    sleep(1000);
    return new FiltersForm(this);
  }

  public String getRandomContractorName() throws Exception {
    List<WebElement> contractorsElems = webDriver.findElements(By.xpath("//table[@class='k-grid-table']//td[" + getColumnHeaderIndex("Kontrahent") + "]/span"));
    String contractorName = "";
    List<String> contractors = TextUtils.getTextFromWebElementsList(contractorsElems);

    if (contractors.size() > 0) {
      contractorName = contractors.get(RandomUtils.randomInt(0, contractors.size()));
    } else {
      Assert.fail("Nie udało się pobrać nazwy kontrahenta");
    }

    return contractorName;
  }

  public Integer getNumberOfPages() {
    String numberOfPages = webDriver.findElement(By.xpath("//kendo-pager//span[contains(text(),'Strona')]")).getText();
    numberOfPages = numberOfPages.replaceAll("Strona 1 z", "");
    numberOfPages = numberOfPages.substring(0, numberOfPages.indexOf("(")).trim();

    return Integer.valueOf(numberOfPages);
  }

  public void clickNextPageBtn(int nextPageNumber) {
    WebElement nextPageLink = webDriver.findElement(By.xpath("//kendo-pager-next-buttons/a[@title='Go to the next page']"));
    nextPageLink.click();
    waitForElementToBeVisible(By.xpath(MessageFormat.format(
      "//kendo-pager-numeric-buttons//a[contains(@class,'selected') and contains(text(),''{0}'')]", nextPageNumber)));
    waitForDataIsLoaded();
  }

  public String getRandomPayerNumber() throws Exception {
    List<WebElement> payersElems = webDriver.findElements(By.xpath("//table[@class='k-grid-table']//td[" + getColumnHeaderIndex("Płatnik") + "]"));
    String payerName = "";
    List<String> payers = TextUtils.getTextFromWebElementsList(payersElems);

    if (payers.size() > 0) {
      payerName = payers.get(RandomUtils.randomInt(0, payers.size()));
    } else {
      Assert.fail("Nie pobrano żadnych numerów platników!");
    }
    return payerName;
  }

  public String getRandomKhCodeNumber() throws Exception {
    List<WebElement> khCodeElems = webDriver.findElements(By.xpath("//table[@class='k-grid-table']//td[" + getColumnHeaderIndex("Kod KH") + "]"));
    String khCode = "";
    List<String> khCodes = TextUtils.getTextFromWebElementsList(khCodeElems);

    if (khCodes.size() > 0) {
      khCode = khCodes.get(RandomUtils.randomInt(0, khCodes.size()));
    } else {
      Assert.fail("Nie pobrano żadnych kodów KH!");
    }
    return khCode;
  }

  public String getRandomDepartmentNumber() throws Exception {
    List<WebElement> departmentElems = webDriver.findElements(By.xpath("//table[@class='k-grid-table']//td[" + getColumnHeaderIndex("Oddział") + "]/span"));
    String departmentName = "";
    List<String> departments = TextUtils.getTextFromWebElementsList(departmentElems);

    if (departments.size() > 0) {
      departmentName = departments.get(RandomUtils.randomInt(0, departments.size()));
    } else {
      Assert.fail("Nie pobrano żadnych oddziałów!");
    }
    return departmentName;
  }

  public void checkDocumentOfType(String deliveryDocument, List<String> errors) {
    List<WebElement> documentTypes = webDriver.findElements(By.xpath("//table[@class='k-grid-table']//td[" + getColumnHeaderIndex("Typ dok.") + "]/span"));
    Integer numberOfPages = getNumberOfPages();

    if (documentTypes.isEmpty()) {
      Assert.fail(MessageFormat.format("Brak wyszukanych dokumentów (szukano po oddziale {0})", deliveryDocument));
    } else if (numberOfPages > 10) {
      numberOfPages = 3;
      for (int i = 1; i <= numberOfPages; i++) {
        for (WebElement documentType : documentTypes) {
          if (!documentType.getText().trim().equals(deliveryDocument)) {
            errors.add(MessageFormat.format("Na liście znajduje się typ dokumentu {0} gdy wyszukiwano po {1}", documentType.getText(), deliveryDocument));
          }
        }
        if (i == 1) {
          clickNextPageBtn(i + 1);
          documentTypes = webDriver.findElements(By.xpath("//table[@class='k-grid-table']//td[" + getColumnHeaderIndex("Typ dok.") + "]/span"));
        }
        if (i == 2) {
          clickLastPage();
          documentTypes = webDriver.findElements(By.xpath("//table[@class='k-grid-table']//td[" + getColumnHeaderIndex("Typ dok.") + "]/span"));
        }
      }
    } else {
      for (int i = 1; i <= numberOfPages; i++) {
        for (WebElement documentType : documentTypes) {
          if (!documentType.getText().trim().equals(deliveryDocument)) {
            errors.add(MessageFormat.format("Na liście znajduje się typ dokumentu {0} gdy wyszukiwano po {1}", documentType.getText(), deliveryDocument));
          }
        }
        if (i != numberOfPages) {
          clickNextPageBtn(i + 1);
          documentTypes = webDriver.findElements(By.xpath("//table[@class='k-grid-table']//td[" + getColumnHeaderIndex("Typ dok.") + "]/span"));
        }
      }
    }
  }

  public String getRandomKhAbbreviationNumber() throws Exception {
    List<WebElement> abbreviationKhElems = webDriver.findElements(By.xpath("//table[@class='k-grid-table']//td[" + getColumnHeaderIndex("Skrót KH") + "]"));
    String abbreviationKhName = "";
    List<String> dump = TextUtils.getTextFromWebElementsList(abbreviationKhElems);

    if (abbreviationKhElems.size() > 0) {
      abbreviationKhName = dump.get(RandomUtils.randomInt(0, dump.size()));
    } else {
      Assert.fail("Nie pobrano żadnych skrótów KH!");
    }
    return abbreviationKhName;
  }

  public void checkSearchDepartmentPresent(String searchDepartment, List<String> errors) throws Exception {
    Integer pagesNumber = getNumberOfPages();
    List<String> departments = getDepartments();
    boolean departmentPresent = false;

    for (int i = 1; i <= pagesNumber; i++) {
      for (String department : departments) {
        if (department.contains(searchDepartment.toUpperCase())) {
          departmentPresent = true;
          break;
        }
      }
      if (!departmentPresent) {
        if (i == pagesNumber) {
          errors.add(MessageFormat.format("W dokumencie nie odnaleziono odziału {0}", searchDepartment));
          break;
        }
        clickNextPageBtn(i + 1);
        departments = getDepartments();
      }
    }
  }

  private List<String> getDepartments() throws Exception {
    List<WebElement> departmentsCells = webDriver.findElements(By.xpath("//table[@class='k-grid-table']//td[" + getColumnHeaderIndex("Oddział") + "]/span"));

    return TextUtils.getTextFromWebElementsList(departmentsCells);
  }

  public String chooseRandomDaysBackValue() {
    Integer maxDaysBackValue = getMaxDaysBackValue();
    Integer actualDaysBackValue = Integer.valueOf(getDaysBack());

    return Integer.toString(RandomUtils.randomInt(actualDaysBackValue + 1, maxDaysBackValue));
  }

  private Integer getMaxDaysBackValue() {
    return Integer.valueOf(getAttributeFromElement(By.xpath("//span[@id='lblPreviousDays']//input"), "aria-valuemax"));
  }

  public void enterDaysBack(String daysBack) {
    WebElement daysBackInput = webDriver.findElement(By.xpath("//*[@class='numeric-input']"));
    daysBackInput.clear();
    sleep(1000);
    daysBackInput.sendKeys("0", daysBack);
    daysBackInput.sendKeys(Keys.RETURN);
    waitForDataIsLoaded();
  }

  public String getStartDate() {
    return webDriver.findElement(By.cssSelector("#lblDateFrom [type=\"hidden\"]")).getAttribute("value");
  }

  public String getEndDate() {
    return webDriver.findElement(By.cssSelector("#lblDateTo [type=\"hidden\"]")).getAttribute("value");
  }

  public String getDaysBack() {
    return getAttributeFromElement(By.xpath("//span[@id='lblPreviousDays']//input"), "aria-valuenow");
  }

  public void checkDates(String initialEndDate, String daysBack, List<String> errors) {
    String startDate = DateUtils.getPastDateDashFormat(Integer.valueOf(daysBack));
    String currentStartDate = getStartDate();
    String currentEndDate = getEndDate();
    initialEndDate = DateUtils.getPastDateDashFormat(0);

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

  public void clickSortByPaymentDate() {
    String sorterXpath = "//table//th[" + getColumnHeaderIndex("Termin płat.") + "]/a/span";
    WebElement paymentDateSorter = webDriver.findElement(By.xpath(sorterXpath));
    sort(paymentDateSorter, sorterXpath);
  }

  public void checkDocumentsSortedByPaymentDateAsc(List<String> errors) throws ParseException {
    List<Date> paymentDates = getPaymentDates();
    boolean isSorted = Ordering.natural().isOrdered(paymentDates);

    if (!isSorted) {
      errors.add("Dane nie są posortowane rosnąco po terminie płatności");
    }
    clickLastPage();
    paymentDates.clear();
    paymentDates.addAll(getPaymentDates());
    isSorted = Ordering.natural().isOrdered(paymentDates);
    if (!isSorted) {
      errors.add("Dane nie są posortowane rosnąco po terminie płatności");
    }
  }

  public void checkDocumentsSortedByPaymentDateDesc(List<String> errors) throws ParseException {
    List<Date> paymentDates = getPaymentDates();
    boolean isSorted = Ordering.natural().reverse().isOrdered(paymentDates);

    if (!isSorted) {
      errors.add("Dane nie są posortowane malejąco po terminie platności");
    }
    clickLastPage();
    paymentDates.clear();
    paymentDates.addAll(getPaymentDates());
    isSorted = Ordering.natural().reverse().isOrdered(paymentDates);
    if (!isSorted) {
      errors.add("Dane nie są posortowane malejąco po terminie płatności");
    }
  }

  private List<Date> getPaymentDates() throws ParseException {
    List<WebElement> paymentDatesElems = webDriver.findElements(By.xpath("//table//td[" + getColumnHeaderIndex("Termin płat.") + "]"));
    List<Date> paymentDates = new ArrayList<>();

    for (WebElement paymentDateElem : paymentDatesElems) {
      paymentDates.add(new SimpleDateFormat("dd-MM-yyyy").parse(paymentDateElem.getText().trim()));
    }
    return paymentDates;
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
    String prevMonthDaysXpath = "((//table//th[.='wt.'])[4])//..//..//..//td[contains(@class,'dx-calendar-today')  and " +
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
    WebElement dateToCalendarSpanner = webDriver.findElement(By.xpath("//span[@id='lblDateTo']//div[@class='dropDownButton']"));
    dateToCalendarSpanner.click();
    sleep(1000);
  }

  public void clickNormalView() {
    WebElement normalViewBtn = webDriver.findElement(By.cssSelector(".eh-i-widok-naglowki"));
    normalViewBtn.click();
    waitForDataIsLoaded();
  }

  public SettingsForm clickSettingsBtn() {
    WebElement settingsBtn = findClickableElement(By.cssSelector(".popup-anchor .fa-cog"));
    settingsBtn.click();
    waitForElementToBeVisible(By.xpath("//div[@class='popup-content grid-settings-content']"));
    return new SettingsForm(this);
  }

  public void checkColumnNotVisible(String columnHeader, List<String> errors) {
    waitForDataIsLoaded();
    if (getColumnHeaderIndex(columnHeader) != 0) {
      errors.add(MessageFormat.format("Kolumna {0} nadal widoczna po odznaczeniu", columnHeader));
    }
  }

  public List<String> getColumnsToUncheck() throws Exception {
    List<WebElement> columnLabelsToUncheck = webDriver.findElements(By.xpath("//div[@class='popup-content grid-settings-content']" +
      "//label[not(@for='draggableColumns') and @for]"));
    List<String> columnLabels = new ArrayList<>();

    if (columnLabelsToUncheck.isEmpty()) {
      Assert.fail("Brak opcji do odznacznia kolumn");
    } else {
      columnLabels = TextUtils.getTextFromWebElementsList(columnLabelsToUncheck);
    }
    return columnLabels;
  }

  public void checkColumnIsVisible(String columnHeader, List<String> errors) {
    waitForDataIsLoaded();
    if (getColumnHeaderIndex(columnHeader) == 0) {
      errors.add(MessageFormat.format("Kolumna {0} nie jest widoczna po zaznaczeniu", columnHeader));
    }
  }

  public String getNetValue(String docNumber) {
    return getTextFromElement(By.xpath("//a[contains(@class,'split-wrap__txt') and contains(@href,'" + docNumber + "')]/../../../td[" + getColumnHeaderIndex("Wart. netto") +
      "]"));
  }

  public String getGrossValue(String docNumber) {
    return getTextFromElement(By.xpath("//a[contains(@class,'split-wrap__txt') and contains(@href,'" + docNumber + "')]/../../../td[" + getColumnHeaderIndex("Wart. brutto") +
      "]"));
  }

  public DocumentDetailsPage actionsOpenDocDetails(String docNumber) {
    spanActionsMenu(docNumber);
    WebElement openDocDetailsLink = webDriver.findElement(By.xpath("//a[contains(@class,'ec_link') and contains(text(),'" + docNumber + "')]/../../.." +
      "//ech-action-menu//div[contains(@class,'popup-content')]//span[contains(text(),'Przejdź do szczegółów dokumentu')]"));
    moveToElement(openDocDetailsLink);
    openDocDetailsLink.click();
    return new DocumentDetailsPage(this);
  }

  private void spanActionsMenu(String docNumber) {
    WebElement actionsBtn = webDriver.findElement(By.xpath("//a[contains(@class,'ec_link') and contains(text(),'" + docNumber + "')]/../../..//ech-action-menu"));
    moveToElement(actionsBtn);
    actionsBtn.click();
    waitForElementToBeVisible(By.xpath("//a[contains(@class,'ec_link') and contains(text(),'" + docNumber + "')]" +
      "/../../..//ech-action-menu//div[contains(@class,'popup-content')]"));
  }

  public OrdersHistoryPage clickOrderHistory() {
    WebElement ordersHistory = webDriver.findElement(By.xpath("//div[@class='actionBarRight']//a[@href='/ang/zakupy/zamowieniaHistoria']"));
    ordersHistory.click();

    return new OrdersHistoryPage(this);
  }

  public DeliveryPage clickDelivery() {
    WebElement delivery = webDriver.findElement(By.xpath("//div[@class='actionBarRight']//a[@href='/ang/dostawy/lista']"));
    delivery.click();

    return new DeliveryPage(this);
  }

  public void openTutorial() {
    WebElement tutorial = webDriver.findElement(By.xpath("//span[@class='show-intro fancy-btn']"));
    tutorial.click();
    waitForElementToBeVisible(By.xpath("//a[contains(text(),'NASTĘPNY')]"));
  }

  public TutorialForm clickTutorialLink() {
    WebElement tutorialBtn = findClickableElement(By.xpath("//span[contains(@class,'show-intro')]"));
    tutorialBtn.click();
    waitForElementToBeVisible(By.xpath("//div[@class='introjs-tooltip']"));

    return new TutorialForm(this);
  }

  public void checkNumberOfPageOfDocument(String number, List<String> errors) {
    String numberOfDocument = "//span[@class='introjs-helperNumberLayer' and contains(text(),'" + number + "')]";
    if (!isElementPresent(By.xpath(numberOfDocument))) {
      errors.add(MessageFormat.format("Nie wyświetla się {0} strona samouczka", number));
    }
  }

  public void tutorialNextPage() {
    WebElement nextPage = webDriver.findElement(By.xpath("//a[contains(text(),'NASTĘPNY')]"));
    nextPage.click();
    sleep(1000);
  }

  public boolean checkCorrectDateChosenInCalendar(String date, String dateChosen, List<String> errors) {
    date = date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6);
    if (!date.equals(dateChosen)) {
      errors.add(MessageFormat.format("Oczekiwana data {0} nie zgadza się z wybraną w polu {1}", date, dateChosen));
      return false;
    } else {
      return true;
    }
  }

  public void closeHelp() {
    WebElement closeHelpBtn = webDriver.findElement(By.xpath("//a[@class='introjs-button introjs-skipbutton introjs-donebutton']"));
    closeHelpBtn.click();
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

  public Integer getNumberOfRecords() {
    String numberOfPages = webDriver.findElement(By.xpath("//kendo-pager//span[contains(text(),'Strona')]")).getText();
    if (numberOfPages.contains("(")) {
      //TODO: ckopko, ten pattern się powtarza - do TextUtils
      Pattern pattern = Pattern.compile("(?<=\\()[^]]+(?=\\))");
      Matcher matcher = pattern.matcher(numberOfPages);
      if (matcher.find()) {
        numberOfPages = matcher.group();
      }
    }
    numberOfPages = numberOfPages.replaceAll(" elementów", "");
    return Integer.valueOf(numberOfPages);
  }

  public Boolean checkSearchResultsPresent(String searchPhrase, List<String> errors) {
    if (isElementPresent(By.className("no-records-info"))) {
      errors.add(MessageFormat.format("Brak wyników wyszukiwania po {0}", searchPhrase));
      return false;
    } else {
      return true;
    }
  }

  public String checkCorrectStartDateEntered(String startDate, String extractedStartDate, List<String> errors) {
    if (!startDate.equals(extractedStartDate)) {
      errors.add(MessageFormat.format("Oczekiwana data od {0} nie zgadza się z wyświetlaną {1}", startDate, extractedStartDate));
      startDate = extractedStartDate;
    }
    return startDate;
  }

  public String checkCorrectEndDateEntered(String endDate, String extractedEndDate, List<String> errors) {
    if (!endDate.equals(extractedEndDate)) {
      errors.add(MessageFormat.format("Oczekiwana data do {0} nie zgadza się z wyświetlaną {1}", endDate, extractedEndDate));
      endDate = extractedEndDate;
    }

    return endDate;
  }

  public Boolean checkIfEnoughRecordsToPagination(Integer records, List<String> errors) {
    if (records >= 3) {
      return true;
    } else {
      errors.add(MessageFormat.format("Liczba rekordów {0} jest niewystarczająca do testu stronicowania", records));
      return false;
    }
  }
}
