package pl.ninebits.qa.eurocash.site.ehurt.forms;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import pl.ninebits.qa.automated.tests.core.utils.RandomUtils;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;

import java.util.ArrayList;
import java.util.List;

public class OrdersSettingsForm extends EhurtBaseForm {
  public OrdersSettingsForm(BasePage page) {
    super(page);
  }

  public List<String> getRandomColumns(int amountOfColumns) {
    List<WebElement> columnLabelsToUncheck = webDriver.findElements(By.xpath("//div[@class='popup-content grid-settings-content']" +
      "//label[not(@for='draggableColumns') and @for]"));
    List<String> columnLabels = new ArrayList<>();

    if (amountOfColumns > columnLabelsToUncheck.size()) {
      amountOfColumns = columnLabelsToUncheck.size();
    }

    if (columnLabelsToUncheck.isEmpty()) {
      Assert.fail("Brak opcji do odznacznia kolumn");
    } else {
      for (int i = 0; i < amountOfColumns; i++) {
        int randomColumnIndex = RandomUtils.randomInt(0, columnLabelsToUncheck.size() - 1);
        columnLabels.add(columnLabelsToUncheck.get(randomColumnIndex).getText().trim());
      }
    }
    return columnLabels;
  }

  public List<String> getAllColumns() {
    List<WebElement> columnLabelsToUncheck = webDriver.findElements(By.xpath("//div[@class='popup-content grid-settings-content']" +
      "//label[not(@for='draggableColumns') and @for]"));
    List<String> columnLabels = new ArrayList<>();

    if (columnLabelsToUncheck.isEmpty()) {
      Assert.fail("Brak opcji do odznacznia kolumn");
    } else {
      for (WebElement columnLabel : columnLabelsToUncheck) {
        columnLabels.add(columnLabel.getText().trim());
      }
    }

    return columnLabels;
  }

  public void clickChooseBtnInSettingPopup() {
    WebElement saveBtn = webDriver.findElement(By.id("popupSave"));
    saveBtn.click();
    waitForElementToBeInvisible(By.xpath("//div[@class='popup-content grid-settings-content']"));
    waitForDataIsLoaded();
  }

  public void clickColumnCheckbox(String columnHeader) {
    String columnCheckBoxXpath = "//div[@class='popup-content grid-settings-content']//label[contains(text(),'" + columnHeader + "')]/i";

    waitForElementToBeClickable(By.xpath(columnCheckBoxXpath));
    WebElement settingCheckbox = webDriver.findElement(By.xpath(columnCheckBoxXpath));
    scrollPopup(settingCheckbox);
    moveToElement(settingCheckbox);
    settingCheckbox.click();
  }

  public void enterNumberOfRows(String numberOfRows) {
    WebElement numberOfRowsInput = webDriver.findElement(By.xpath("//input[@id='rowNumber']"));
    Actions actions = new Actions(webDriver);
    actions
      .click(numberOfRowsInput)
      .sendKeys(numberOfRowsInput, Keys.BACK_SPACE)
      .sendKeys(numberOfRowsInput, Keys.BACK_SPACE)
      .sendKeys(numberOfRowsInput, Keys.BACK_SPACE)
      .sendKeys(numberOfRowsInput, numberOfRows).perform();
  }

  private void scrollPopup(WebElement element) {
    ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", element);
  }
}
