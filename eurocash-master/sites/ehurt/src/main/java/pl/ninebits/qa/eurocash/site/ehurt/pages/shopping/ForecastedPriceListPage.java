package pl.ninebits.qa.eurocash.site.ehurt.pages.shopping;

import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import pl.ninebits.qa.automated.tests.core.utils.RandomUtils;
import pl.ninebits.qa.automated.tests.core.utils.TextUtils;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;
import pl.ninebits.qa.eurocash.site.ehurt.forms.ForecastedPriceListsFiltersForm;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtBasePage;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ForecastedPriceListPage extends EhurtBasePage {
  public ForecastedPriceListPage(BasePage pageObject) {
    super(pageObject);
    clearFilters();
  }

  public void clearFilters() {
    waitForDataIsLoaded();
    if (isElementPresent(By.xpath("//span[contains(text(),'Wyczyść filtry')]"))) {
      findClickableElement(By.xpath("//span[contains(text(),'Wyczyść filtry')]")).click();
      waitForElementToBeInvisible(By.xpath("//span[contains(text(),'Wyczyść filtry')]"));
      waitForDataIsLoaded();
    }
  }

  public void checkForecastedPriceListPagePresent(List<String> errors) {
    if (!isElementPresent(By.xpath("//form[contains(@action,'cennikiPrognozowane')]"))) {
      errors.add("Strona z prognozowanymi cennikami nie została wczytana poprawnie");
    }
  }

  public ForecastedPriceListsFiltersForm clickShowFilters() {
    WebElement showFiltersBtn = webDriver.findElement(By.xpath("//span[.='Pokaż filtry']"));
    showFiltersBtn.click();
    waitForElementToBeInvisible(showFiltersBtn);

    return new ForecastedPriceListsFiltersForm(this);
  }

  public String getIndex() {
    List<String> indexes = TextUtils.getAttributesFromWebElementsListWithoutTheQuotes(
      webDriver.findElements(By.xpath("//td[contains(@aria-label,'Indeks, Value')]")), "aria-label");
    String index = indexes.get(RandomUtils.randomInt(0, indexes.size()));
    index = index.replaceAll("[A-Za-z,\\s]", "");

    return index;
  }

  public String getProductName() {
    List<String> productNames = TextUtils.getAttributesFromWebElementsListWithoutTheQuotes(
      webDriver.findElements(By.xpath("//td[contains(@aria-label,'Nazwa towaru, Value')]")), "aria-label");
    String productName = productNames.get(RandomUtils.randomInt(0, productNames.size()));
    productName = productName.substring(productName.indexOf("V"));
    productName = productName.replaceAll("Value ", "");
    productName = productName.substring(0, productName.indexOf(" ")); //pierwszy wyraz

    return productName;
  }

  public void enterSearchPhrase(String searchPhrase) {
    WebElement searchInput = findVisibleElement(By.xpath("//input[@placeholder='wpisz indeks lub nazwę']"));
    searchInput.clear();
    searchInput.sendKeys(searchPhrase);
    searchInput.sendKeys(Keys.RETURN);
    waitForDataIsLoaded();
  }

  public void checkPricelistsFiltered(String filterColumn, String filterValue, List<String> errors) {
    List<String> extractedFilterValues = TextUtils.getAttributesFromWebElementsListWithoutTheQuotes(
      webDriver.findElements(By.xpath("//td[contains(@aria-label,'" + filterColumn + ", Value')]")), "aria-label");
    for (String extracted : extractedFilterValues) {
      if (!extracted.contains(filterValue)) {
        errors.add(MessageFormat.format("Na liście znajduje się {0} o wartości {1} podczas gdy filtrowano po (2)",
          filterColumn, extracted, filterValue));
      }
    }
  }

  public void sortColumnAsc(String header) {
    WebElement sortHeader = findClickableElement(By.xpath("//div[contains(text(),'" + header + "')]/span[contains(@class,'notSort')]"));
    moveToElement(sortHeader);
    sortHeader.click();
    waitForElementToBeVisible(By.xpath("//div[contains(text(),'" + header + "')]/span[contains(@class,'ascSort')]"));
    waitForDataIsLoaded();
  }

  public void sortColumnDesc(String header) {
    if (isElementPresent(By.xpath("//div[contains(text(),'" + header + "')]/span[contains(@class,'notSort')]"))) {
      sortColumnAsc(header);
    }

    WebElement sortHeader = findClickableElement(By.xpath("//div[contains(text(),'" + header + "')]/span[contains(@class,'ascSort')]"));
    moveToElement(sortHeader);
    sortHeader.click();
    waitForElementToBeVisible(By.xpath("//div[contains(text(),'" + header + "')]/span[contains(@class,'descSort')]"));
    waitForDataIsLoaded();
  }

  public void checkIndexesSortedAsc(List<String> errors) throws Exception {
    List<Double> indexes = getIndexes();
    if (!Ordering.natural().isOrdered(indexes)) {
      errors.add("Dane nie są posortowane rosnąco po indeksie");
    }
  }

  public void checkIndexesSortedDesc(List<String> errors) throws Exception {
    List<Double> indexes = getIndexes();
    if (!Ordering.natural().reverse().isOrdered(indexes)) {
      errors.add("Dane nie są posortowane malejąco po indeksie");
    }
  }

  private List<Double> getIndexes() throws Exception {
    List<String> stringValues = TextUtils.getTextFromWebElementsList(webDriver.findElements(By.xpath("//td[contains(@aria-label,'Indeks, Value')]")));
    List<Double> numValues = new ArrayList<>();

    for (String stringValue : stringValues) {
      stringValue = stringValue.replaceAll("\\s", "");
      numValues.add(Double.valueOf(stringValue));
    }
    return numValues;
  }

  public void checkProductNamesSortedAsc(List<String> errors) {
    List<String> productNames = getProductNames();
    if (!Ordering.natural().isOrdered(productNames)) {
      errors.add("Dane nie są posortowane rosnąco po Nazwie towaru");
    }
  }

  public void checkProductNamesSortedDesc(List<String> errors) {
    List<String> productNames = getProductNames();
    if (!Ordering.natural().reverse().isOrdered(productNames)) {
      errors.add("Dane nie są posortowane malejąco po Nazwie towaru");
    }
  }

  private List<String> getProductNames() {
    List<String> productNames = TextUtils.getAttributesFromWebElementsListWithoutTheQuotes(
      webDriver.findElements(By.xpath("//td[contains(@aria-label,'Nazwa towaru, Value')]")), "aria-label");

    return productNames;
  }

  public void checkPercentageChangesSortedAsc(List<String> errors) {
    List<Double> percentages = getPercentages();
    if (!Ordering.natural().isOrdered(percentages)) {
      errors.add("Dane nie są posortowane rosnąco po Procencie zmiany");
    }
  }

  public void checkPercentageChangesSortedDesc(List<String> errors) {
    List<Double> percentages = getPercentages();
    if (!Ordering.natural().reverse().isOrdered(percentages)) {
      errors.add("Dane nie są posortowane malejaco po Procencie zmiany");
    }
  }

  private List<Double> getPercentages() {
    List<WebElement> percentageElems = webDriver.findElements(By.xpath("//td[contains(@aria-label,'Proc. zmiany, Value')]/div"));
    List<Double> percentages = new ArrayList<>();
    for (WebElement percentageElem : percentageElems) {
      Double tmp = Double.valueOf(percentageElem.getText().replaceAll("[\\s%]", ""));
      if (percentageElem.getAttribute("class").contains("makeMeRed")) {
        percentages.add(-tmp);
      } else {
        percentages.add(tmp);
      }
    }

    return percentages;
  }

  public void checkProducersSortedAsc(List<String> errors) {
    List<String> producers = getProducers();
    if (!Ordering.natural().isOrdered(producers)) {
      errors.add("Dane nie są posortowane rosnąco po producencie");
    }
  }

  public void checkProducersSortedDesc(List<String> errors) {
    List<String> producers = getProducers();
    if (!Ordering.natural().reverse().isOrdered(producers)) {
      errors.add("Dane nie są posortowane malejąco po producencie");
    }
  }

  private List<String> getProducers() {
    List<String> producers = TextUtils.getAttributesFromWebElementsList(
      webDriver.findElements(By.xpath("//td[contains(@aria-label,'Producent, Value')]")), "aria-label");
    return producers;
  }

  public void checkStockSortedAsc(List<String> errors) {
    List<String> stocks = getStocks();
    if (!Ordering.natural().isOrdered(stocks)) {
      errors.add("Dane nie są posortowane rosnąco po asortymencie");
    }
  }

  public void checkStockSortedDesc(List<String> errors) {
    List<String> stocks = getStocks();
    if (!Ordering.natural().reverse().isOrdered(stocks)) {
      errors.add("Dane nie są posortowane malejąco po asortymencie");
    }
  }

  private List<String> getStocks() {
    List<String> stocks = TextUtils.getAttributesFromWebElementsListWithoutTheQuotes(
      webDriver.findElements(By.xpath("//td[contains(@aria-label,'Asortyment, Value')]")), "aria-label");
    return stocks;
  }

  public void checkGroupSortedAsc(List<String> errors) {
    List<String> groups = getGroups();
    if (!Ordering.natural().isOrdered(groups)) {
      errors.add("Dane nie są posortowane rosnąco po grupie");
    }
  }

  public void checkGroupSortedDesc(List<String> errors) {
    List<String> groups = getGroups();
    if (!Ordering.natural().reverse().isOrdered(groups)) {
      errors.add("Dane nie są posortowane malejąco po grupie");
    }
  }

  private List<String> getGroups() {
    List<String> groups = TextUtils.getAttributesFromWebElementsListWithoutTheQuotes(
      webDriver.findElements(By.xpath("//td[contains(@aria-label,'Grupa, Value')]")), "aria-label");
    return groups;
  }

  public void checkSubGroupSortedAsc(List<String> errors) {
    List<String> subGroups = getSubGroups();
    List<String> temp = new ArrayList<>();
    temp.addAll(subGroups);
    TextUtils.sortListWithPolishChars(temp);
    if (!temp.equals(subGroups)) {
      errors.add("Dane nie są posortowane rosnąco po podgrupie");
    }
  }

  public void checkSubGroupSortedDesc(List<String> errors) {
    List<String> subGroups = getSubGroups();
    List<String> temp = new ArrayList<>();
    temp.addAll(subGroups);
    TextUtils.sortListWithPolishChars(temp);
    temp = Lists.reverse(temp);
    if (!temp.equals(subGroups)) {
      errors.add("Dane nie są posortowane malejąco po podgrupie");
    }
  }

  private List<String> getSubGroups() {
    List<String> groups = TextUtils.getAttributesFromWebElementsListWithoutTheQuotes(
      webDriver.findElements(By.xpath("//td[contains(@aria-label,'Podgrupa, Value')]")), "aria-label");
    return groups;
  }

  public String getDate() throws Exception {
    List<String> dates = TextUtils.getTextFromWebElementsList(webDriver.findElements(By.xpath("//td[contains(@aria-label,'Data zmiany, Value')]")));
    return dates.get(RandomUtils.randomInt(0, dates.size()));
  }

  public void enterStartDate(String date) {
    List<WebElement> dateInputs = webDriver.findElements(By.xpath("//dx-date-box[contains(@class,'dx-datebox-date')]//input[@class='dx-texteditor-input']"));
    WebElement startDateInput = dateInputs.get(0);

    startDateInput.clear();
    startDateInput.sendKeys(date);
    startDateInput.sendKeys(Keys.RETURN);
    waitForDataIsLoaded();
  }

  public void enterEndDate(String date) {
    List<WebElement> dateInputs = webDriver.findElements(By.xpath("//dx-date-box[contains(@class,'dx-datebox-date')]//input[@class='dx-texteditor-input']"));
    WebElement endDateInput;

    if (dateInputs.size() > 1) {
      endDateInput = dateInputs.get(1);
    } else {
      endDateInput = dateInputs.get(0);
    }

    endDateInput.clear();
    endDateInput.sendKeys(date);
    endDateInput.sendKeys(Keys.RETURN);
    waitForDataIsLoaded();
  }

  public void checkChangeDatesSortedAsc(List<String> errors) throws Exception {
    List<Date> changeDates = getChangeDates();
    if (!Ordering.natural().isOrdered(changeDates)) {
      errors.add("Dane nie są posortowane rosnąco po Dacie zmiany");
    }
  }

  public void checkChangeDatesSortedDesc(List<String> errors) throws Exception {
    List<Date> changeDates = getChangeDates();
    if (!Ordering.natural().reverse().isOrdered(changeDates)) {
      errors.add("Dane nie są posortowane malejąco po Dacie zmiany");
    }
  }

  private List<Date> getChangeDates() throws Exception {
    List<String> extracted = TextUtils.getTextFromWebElementsList(webDriver.findElements(By.xpath("//td[contains(@aria-label,'Data zmiany, Value')]")));
    List<Date> dates = new ArrayList<>();
    for (String date : extracted) {
      dates.add(new SimpleDateFormat("yyyy-MM-dd").parse(date));
    }
    return dates;
  }
}
