package pl.ninebits.qa.eurocash.site.ehurt.forms;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import pl.ninebits.qa.automated.tests.core.utils.RandomUtils;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;

import java.util.ArrayList;
import java.util.List;

public class FiltersForm extends EhurtBaseForm {
  public FiltersForm(BasePage page) {
    super(page);
  }

  private List<String> getInputLabels() {
    List<WebElement> inputLabelsElems = webDriver.findElements(By.xpath("//div[@id='sidebar']//p"));
    List<String> inputLabels = new ArrayList<>();

    if (!inputLabelsElems.isEmpty()) {
      for (WebElement inputLabelsElem : inputLabelsElems) {
        inputLabels.add(inputLabelsElem.getText().replaceAll(":", "").trim());
      }
    } else {
      Assert.fail("Nie udało się pobrać nagłówków pól filtru rozszerzonego");
    }
    return inputLabels;
  }

/*  private Integer getLabelIndex(String label) {
    return getInputLabels().indexOf(label) + 1;
  }*/

  private Integer getLabelIndex(String label) {
    return getInputLabels().indexOf(label);
  }


  public void confirmSearch() {
    WebElement docNumberInput = webDriver.findElement(By.xpath("//div[@id='sidebar']//input[@name='nrDokumentu']"));
    docNumberInput.sendKeys(Keys.RETURN);
    waitForDataIsLoaded();
    hideFiltersTab();
  }

  public void hideFiltersTab() {
    if (isElementPresent(By.xpath("//a[@id='sidebarToggleBtn']/span[contains(text(),'Ukryj')]"))) {
      WebElement hideFiltersBtn = webDriver.findElement(By.xpath("//a[@id='sidebarToggleBtn']/span"));
      hideFiltersBtn.click();
      waitForElementToBeInvisible(By.xpath("//a[@id='sidebarToggleBtn' and @class='visible']/span"));
    }
  }

  public void typeInputValue(String inputLabel, String inputValue) {
    WebElement input = webDriver.findElement(By.xpath("(//div[@id='sidebar']//input)[" + getLabelIndex(inputLabel) + "]"));
    moveToElement(input);
    input.clear();
    input.sendKeys(inputValue);
    input.sendKeys(Keys.RETURN);
    waitForDataIsLoaded();
  }

  public void typeMacroregionName(String macroregion) {
    String makroregionInputXpath = "//div[@id='sidebar']//input[@name='makroregion']";

    waitForElementToBeVisible(By.xpath(makroregionInputXpath));
    WebElement macroregionNameInput = webDriver.findElement(By.xpath(makroregionInputXpath));
    macroregionNameInput.clear();
    macroregionNameInput.sendKeys(macroregion);
  }

  public void typeOfDocument() {
    WebElement hideFiltersBtn = webDriver.findElement(By.xpath("//div[@class='dx-dropdowneditor-input-wrapper dx-selectbox-container']"));
    hideFiltersBtn.click();
    waitForElementToBeVisible(By.xpath("//div[@class='dx-item-content dx-list-item-content' and contains(text(),'Faktura')]"));
    WebElement deliveryDocumentBtn = webDriver.findElement(By.xpath("//div[@class='dx-item-content dx-list-item-content' and contains(text(),'Faktura')]"));
    deliveryDocumentBtn.click();
  }

  public List<String> getRandomTypesValues(int amountOfValues) {
    WebElement docTypeMenu = webDriver.findElement(By.xpath("//div[@id='sidebar']//kendo-multiselect[@name='documentType']/div"));
    docTypeMenu.click();

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
      Assert.fail("Nie pobrano żadnych pozycji z mnenu Typ dokumentu");
    }
    return chosenValues;
  }

  public void chooseStatus(String statusValue, int position) {
    WebElement statusMenu = webDriver.findElement(By.xpath("//div[@id='sidebar']//kendo-multiselect[@name='documentType']/div"));
    statusMenu.click();
    waitForElementToBeVisible(By.xpath("//kendo-popup//ul[@role='listbox']//li[1]"));

    WebElement statusInput = webDriver.findElement(By.xpath("//div[@id='sidebar']//kendo-multiselect[@name='documentType']//kendo-searchbar[@class='k-searchbar']/input[@role='listbox']"));
    statusInput.click();
    for (int i = 1; i <= position; i++) {
      statusInput.sendKeys(Keys.ARROW_DOWN);
      sleep();
    }
    WebElement statusElem = webDriver.findElement(By.xpath("//kendo-popup//ul[@role='listbox']//li[contains(.,'" + statusValue + "')]"));
    moveToElement(statusElem);
    statusElem.click();
    waitForElementToBeInvisible(By.xpath("//kendo-popup//ul[@role='listbox']//li[contains(.,'" + statusValue + "')]"));
    waitForDataIsLoaded();
  }

}
