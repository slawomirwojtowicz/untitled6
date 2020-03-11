package pl.ninebits.qa.eurocash.site.ehurt.forms;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import pl.ninebits.qa.automated.tests.core.utils.RandomUtils;
import pl.ninebits.qa.automated.tests.core.utils.TextUtils;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;

import java.util.List;

public class OfferFiltersForm extends EhurtBaseForm {
  public OfferFiltersForm(BasePage page) {
    super(page);
  }

  public void clickHideFilter() {
    WebElement hideBtn = webDriver.findElement(By.xpath("//a[@id='sidebarToggleBtn']/span[contains(text(),'Ukryj')]"));
    hideBtn.click();
    waitForElementToBeVisible(By.xpath("//a[@id='sidebarToggleBtn']/span[contains(text(),'Pokaż')]"));
  }

  public void spanProducersDropdown() {
    WebElement producersMenuSpanner = webDriver.findElement(By.xpath("//p[.='Producent:']/..//kendo-combobox[@name='manufacturerCmbx']" +
      "//span[contains(@class,'arrow')]"));
    sleep(1000);
    waitForElementToBeVisible(By.xpath("//p[.='Producent:']/..//kendo-combobox[@name='manufacturerCmbx']//span[contains(@class,'arrow')]"));
    producersMenuSpanner.click();
  }

  public void spanMarksDropdown() {
    String markSpannerXpath = "//p[.='Marka:']/..//kendo-combobox[@name='manufacturerCmbx']//span[contains(@class,'arrow')]";
    WebElement markMenuSpanner = webDriver.findElement(By.xpath(markSpannerXpath));
    waitForElementToBeVisible(By.xpath(markSpannerXpath));
    sleep(1000);
    markMenuSpanner.click();
  }

  public void enterProducer(String producer) {
    WebElement producerInput = webDriver.findElement(By.xpath("//p[.='Producent:']/..//kendo-combobox[@name='manufacturerCmbx']//input"));
    producerInput.sendKeys(producer);
    producerInput.sendKeys(Keys.RETURN);
  }

  public void enterMark(String mark) {
    WebElement markInput = webDriver.findElement(By.xpath("//p[.='Marka:']/..//kendo-combobox[@name='manufacturerCmbx']//input"));
    markInput.sendKeys(mark);
    markInput.sendKeys(Keys.RETURN);
  }

  public void clickCategory(String categoryLevel, String categoryName) {
    WebElement menuElem = findVisibleElement(By.xpath("//ul[@class='ul-cm tree-ul level0" + categoryLevel + "']//a[contains(text(),'" + categoryName + "')]"));
    menuElem.click();
    waitForDataIsLoaded();
  }

  public String chooseRandomProducerValue() throws Exception {
    waitUntil(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//ul[@role='listbox']/li")));
    List<WebElement> elements = webDriver.findElements(By.xpath("//ul[@role='listbox']/li"));
    List<String> dropdownValues = TextUtils.getTextFromWebElementsList(elements);
    int i = 0;

    if (!dropdownValues.isEmpty()) {
      i = RandomUtils.randomInt(0, dropdownValues.size());
      String tmp = dropdownValues.get(i);
      if (tmp.startsWith("\"")) {
        tmp = tmp.substring(1, tmp.indexOf("\""));
      }
      enterProducer(tmp);
    } else {
      Assert.fail("Nie udało się pobrać wartości z dropdowna");
    }
    return dropdownValues.get(i);
  }

  public String chooseRandomMarkValue() throws Exception {
    waitUntil(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//ul[@role='listbox']/li")));
    List<WebElement> elements = webDriver.findElements(By.xpath("//ul[@role='listbox']/li"));
    List<String> dropdownValues = TextUtils.getTextFromWebElementsList(elements);
    int i = 0;

    if (!dropdownValues.isEmpty()) {
      i = RandomUtils.randomInt(0, dropdownValues.size());
      String tmp = dropdownValues.get(i);
      if (tmp.startsWith("\"")) {
        tmp = tmp.substring(1, tmp.indexOf("\""));
      }
      enterMark(tmp);
    } else {
      Assert.fail("Nie udało się pobrać wartości z dropdowna");
    }
    return dropdownValues.get(i);
  }

  public String getRandomCategoryName(String categoryLevel) throws Exception {
    sleep();
    List<WebElement> category = webDriver.findElements(By.xpath("//div[@id='sidebar']/div//ul[@class='ul-cm tree-ul level0" + categoryLevel + "']/li"));
    String categoryName = "";
    List<String> dump = TextUtils.getTextFromWebElementsList(category);

    if (dump.size() > 0) {
      categoryName = dump.get(RandomUtils.randomInt(0, dump.size())).replaceAll("[0-9,\\),\\(]", "").trim();
    } else {
      Assert.fail("Nie pobrano żadnych nazw kategorii");
    }
    return categoryName;
  }

}
