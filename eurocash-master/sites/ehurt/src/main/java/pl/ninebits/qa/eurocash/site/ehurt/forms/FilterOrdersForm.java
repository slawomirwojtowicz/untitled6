package pl.ninebits.qa.eurocash.site.ehurt.forms;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import pl.ninebits.qa.automated.tests.core.utils.RandomUtils;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;

import java.util.ArrayList;
import java.util.List;

public class FilterOrdersForm extends EhurtBaseForm {
  public FilterOrdersForm(BasePage page) {
    super(page);
  }

  private void waitForDataIsLoaded2() {
    try {
      new WebDriverWait(webDriver, 10).until(ExpectedConditions.
        visibilityOfElementLocated(By.xpath("//div[contains(@class,'loading loading-panel')]")));
    } catch (Exception e) {
      System.out.println("Nie odnaleziono loadera na stronie Historia zamówień");
    }
    waitUntil(ExpectedConditions.invisibilityOfAllElements(webDriver.findElements(By.xpath("//div[contains(@class,'loading loading-panel')]"))));
  }

  public void typeInputValue0(String inputLabel, String inputValue) throws Exception {
    WebElement input = webDriver.findElement(By.xpath("(//div[@id='sidebar']//input)[" + getLabelIndex0(inputLabel) + "]"));
    moveToElement(input);
    input.clear();
    input.sendKeys(inputValue);
    input.sendKeys(Keys.RETURN);
    waitForDataIsLoaded2();
  }

  public void typeInputValue(String inputLabel, String inputValue) throws Exception {
    WebElement input = webDriver.findElement(By.xpath("(//div[@id='sidebar']//input)[" + getLabelIndex(inputLabel) + "]"));
    moveToElement(input);
    input.clear();
    input.sendKeys(inputValue);
    input.sendKeys(Keys.RETURN);
    waitForDataIsLoaded2();
  }

  public void typeInputValue2(String inputLabel, String inputValue) throws Exception {
    WebElement input = webDriver.findElement(By.xpath("(//div[@id='sidebar']//input)[" + getLabelIndex2(inputLabel) + "]"));
    moveToElement(input);
    input.clear();
    input.sendKeys(inputValue);
    input.sendKeys(Keys.RETURN);
    waitForDataIsLoaded2();
  }

  public void typeInputValue3(String inputLabel, String inputValue) throws Exception {
    WebElement input = webDriver.findElement(By.xpath("(//div[@id='sidebar']//input)[" + getLabelIndex3(inputLabel) + "]"));
    moveToElement(input);
    input.clear();
    input.sendKeys(inputValue);
    input.sendKeys(Keys.RETURN);
    waitForDataIsLoaded2();
  }


  private List<String> getInputLabels() throws Exception {
    List<WebElement> inputLabelsElems = webDriver.findElements(By.xpath("//div[@id='sidebar']//p"));
    List<String> inputLabels = new ArrayList<>();

    if (!inputLabelsElems.isEmpty()) {
      for (WebElement inputLabelsElem : inputLabelsElems) {
        inputLabels.add(inputLabelsElem.getText().replaceAll(":", "").trim());
      }
    } else {
      throw new Exception("Nie udało się pobrać nagłówków pól filtru rozszerzonego");
    }
    return inputLabels;
  }

  private Integer getLabelIndex(String label) throws Exception {
    return getInputLabels().indexOf(label) + 1;
  }

  private Integer getLabelIndex0(String label) throws Exception {
    return getInputLabels().indexOf(label) + 0;
  }

  private Integer getLabelIndex2(String label) throws Exception {
    return getInputLabels().indexOf(label) + 2;
  }

  private Integer getLabelIndex3(String label) throws Exception {
    return getInputLabels().indexOf(label) + 3;
  }

  public List<String> getRandomOriginValues(int amountOfValues) throws Exception {
    WebElement originMenu = webDriver.findElement(By.xpath("//ech-dropdown[@name='itemOrigin']"));
    originMenu.click();

    waitUntil(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//li[contains(@id,'undefined-')]")));
    List<WebElement> menuElems = webDriver.findElements(By.xpath("//li[contains(@id,'undefined-')]"));
    List<String> chosenValues = new ArrayList<>();
    if (!menuElems.isEmpty()) {
      for (int i = 1; i <= amountOfValues; i++) {
        int randomIndex = RandomUtils.randomInt(0, menuElems.size() - 1);
        String tmp = menuElems.get(randomIndex).getAttribute("id");
        tmp = tmp.replaceAll("undefined-", "");
        chosenValues.add(tmp);
      }
    } else {
      throw new Exception("Nie pobrano żadnych pozycji z mnenu Pochodzenie");
    }
    return chosenValues;
  }

  public void hideFiltersTab() {
    if (isElementPresent(By.xpath("//a[@id='sidebarToggleBtn']/span[contains(text(),'Ukryj')]"))) {
      WebElement hideFiltersBtn = webDriver.findElement(By.xpath("//a[@id='sidebarToggleBtn']/span[contains(text(),'Ukryj')]"));
      hideFiltersBtn.click();
      waitForElementToBeInvisible(By.xpath("//a[@id='sidebarToggleBtn' and @class='visible']"));
      waitForElementToBeVisible(By.id("sidebarToggleBtn"));
      waitForDataIsLoaded2();
    }
  }

  public void chooseOriginValue(String originValue) {
    String menuValueXpath = "//li[contains(@id,'undefined-" + originValue + "')]";
    WebElement originMenu = webDriver.findElement(By.xpath("//kendo-multiselect[@name='itemOrigin']"));
    originMenu.click();
    waitForElementToBeVisible(By.xpath(menuValueXpath));

    WebElement menuValue = webDriver.findElement(By.xpath(menuValueXpath));
    menuValue.click();
    waitForDataIsLoaded();
  }

  public void clearOriginFilter() {
    String closeFilterXpath = "//kendo-multiselect[@name='itemOrigin']//span/span[contains(@class,'close')]";

    if (isElementPresent(By.xpath(closeFilterXpath))) {
      WebElement closeFilterBtn = webDriver.findElement(By.xpath(closeFilterXpath));
      closeFilterBtn.click();
      waitForDataIsLoaded();
    }
  }

  public List<String> getRandomStatusValues(int amountOfValues) {
    WebElement statusMenu = webDriver.findElement(By.xpath("//kendo-combobox[@name='status']//span[contains(@class,'arrow')]"));
    statusMenu.click();

    waitUntil(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//kendo-popup//ul[@role='listbox']//li")));
    List<WebElement> menuElems = webDriver.findElements(By.xpath("//kendo-popup//ul[@role='listbox']//li"));
    List<String> chosenValues = new ArrayList<>();
    if (!menuElems.isEmpty()) {
      for (int i = 1; i <= amountOfValues; i++) {
        int randomIndex = RandomUtils.randomInt(0, menuElems.size() - 1);
        String tmp = menuElems.get(randomIndex).getText().trim();
        chosenValues.add(tmp);
        chosenValues.add(Integer.toString(randomIndex + 1));
      }
    } else {
      Assert.fail("Nie pobrano żadnych pozycji z mnenu Status");
    }
    return chosenValues;
  }

  public void chooseStatusValue(String statusValue, int position) {
    WebElement statusMenu = webDriver.findElement(By.xpath("//ech-dropdown-autocomplete[@name='status']//div[contains(@class,'ech-dropdown__icon-wrapper')]"));
    statusMenu.click();
    waitForElementToBeVisible(By.xpath("//ul[@class='ech-dropdown__list visible']//li[1]"));

    WebElement statusInput = webDriver.findElement(By.xpath("//ech-dropdown-autocomplete[@name='status']//input"));
    statusInput.click();
    for (int i = 1; i <= position; i++) {
      statusInput.sendKeys(Keys.ARROW_DOWN);
      sleep();
    }
    WebElement statusElem = webDriver.findElement(By.xpath("//li[contains(@class,'ech-dropdown__list-item') and contains(.,'" + statusValue + "')]"));
    moveToElement(statusElem);
    statusElem.click();
    statusInput.sendKeys(Keys.ENTER);
    waitForElementToBeInvisible(By.xpath("//li[contains(@class,'ech-dropdown__list-item') and contains(.,'" + statusValue + "')]"));
    waitForDataIsLoaded();
  }

  public List<String> getRandomTransportValues(int amountOfValues) {
    waitForElementToBeVisible(By.xpath("//kendo-combobox[@name='transport']//span[contains(@class,'arrow')]"));
    WebElement paymentMenu = webDriver.findElement(By.xpath("//kendo-combobox[@name='transport']//span[contains(@class,'arrow')]"));
    paymentMenu.click();

    waitUntil(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//kendo-popup//ul[@role='listbox']//li")));
    List<WebElement> menuElems = webDriver.findElements(By.xpath("//kendo-popup//ul[@role='listbox']//li"));
    List<String> chosenValues = new ArrayList<>();
    if (!menuElems.isEmpty()) {
      for (int i = 1; i <= amountOfValues; i++) {
        int randomIndex = RandomUtils.randomInt(0, menuElems.size() - 1);
        String tmp = menuElems.get(randomIndex).getText().trim();
        chosenValues.add(tmp);
        chosenValues.add(Integer.toString(randomIndex + 1));
      }
    } else {
      Assert.fail("Nie pobrano żadnych pozycji z mnenu Transport");
    }
    return chosenValues;
  }

  public void choosePaymentValue(String paymentValue, int position) {
    WebElement paymentMenu = webDriver.findElement(By.xpath("//p[contains(.,'Płat')]//..//div//i"));
    paymentMenu.click();
    waitForElementToBeVisible(By.xpath("//ul[@class='ech-dropdown__list visible']//li[1]"));

    WebElement paymentInput = webDriver.findElement(By.xpath("//p[contains(.,'Płat')]//..//div//input"));
    paymentInput.click();
    for (int i = 1; i <= position; i++) {
      paymentInput.sendKeys(Keys.ARROW_DOWN);
      sleep();
    }
    WebElement statusElem = webDriver.findElement(By.xpath("//li[contains(@class,'ech-dropdown__list-item') and contains(.,'" + paymentValue + "')]"));
    moveToElement(statusElem);
    statusElem.click();
    paymentInput.sendKeys(Keys.ENTER);
    waitForElementToBeInvisible(By.xpath("//li[contains(@class,'ech-dropdown__list-item') and contains(.,'" + paymentValue + "')]"));
    waitForDataIsLoaded();
  }

  public void choosePaymentValue1(String originValue) {
    String menuValueXpath = "//li[contains(@id,'undefined-" + originValue + "')]";
    WebElement originMenu = webDriver.findElement(By.xpath("//kendo-combobox[@name='platnosc']"));
    originMenu.click();
    waitForElementToBeVisible(By.xpath(menuValueXpath));

    WebElement menuValue = webDriver.findElement(By.xpath(menuValueXpath));
    menuValue.click();
    waitForElementToBeVisible(By.xpath("//kendo-multiselect[@name='itemOrigin']//span[contains(.,'" + originValue + "')]"));
    waitForDataIsLoaded();
  }

  public void chooseTransportValue(String transportValues, int position) {
    WebElement transportMenu = webDriver.findElement(By.xpath("//p[contains(.,'Trans')]//..//div//i"));
    transportMenu.click();
    waitForElementToBeVisible(By.xpath("//ul[@class='ech-dropdown__list visible']//li[1]"));

    WebElement paymentInput = webDriver.findElement(By.xpath("//p[contains(.,'Trans')]//..//div//input"));
    paymentInput.click();
    for (int i = 1; i <= position; i++) {
      paymentInput.sendKeys(Keys.ARROW_DOWN);
      sleep();
    }
    WebElement statusElem = webDriver.findElement(By.xpath("//li[contains(@class,'ech-dropdown__list-item') and contains(.,'" + transportValues + "')]"));
    moveToElement(statusElem);
    statusElem.click();
    paymentInput.sendKeys(Keys.ENTER);
    waitForElementToBeInvisible(By.xpath("//li[contains(@class,'ech-dropdown__list-item') and contains(.,'" + transportValues + "')]"));
    waitForDataIsLoaded();
  }

  public void typeRealisationDate(String date) {
    WebElement dateInput = webDriver.findElement(By.xpath("//div[@id='dateRealization']//input[not(@type='hidden')]"));
    moveToElement(dateInput);
    dateInput.sendKeys(date.substring(0, 1));
    dateInput.sendKeys(Keys.BACK_SPACE);
    dateInput.sendKeys(date);
    dateInput.sendKeys(Keys.RETURN);
    waitForDataIsLoaded();
  }

  public void typePriceDate(String date) {
    WebElement dateInput = webDriver.findElement(By.xpath("//div[@id='datePrice']//input[not(@type='hidden')]"));
    moveToElement(dateInput);
    dateInput.sendKeys(date.substring(0, 1));
    dateInput.sendKeys(Keys.BACK_SPACE);
    dateInput.sendKeys(date);
    waitForDataIsLoaded();
  }
}
