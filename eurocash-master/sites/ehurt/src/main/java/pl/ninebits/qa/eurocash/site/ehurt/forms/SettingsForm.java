package pl.ninebits.qa.eurocash.site.ehurt.forms;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;

import java.util.ArrayList;
import java.util.List;

public class SettingsForm extends EhurtBaseForm {
  public SettingsForm(BasePage page) {
    super(page);
  }

  public void clickColumnCheckbox(String columnHeader) {
    String columnCheckBoxXpath = "//div[@class='popup-content grid-settings-content']//label[contains(text(),'" + columnHeader + "')]/i";

    waitForElementToBeClickable(By.xpath(columnCheckBoxXpath));
    WebElement settingCheckbox = webDriver.findElement(By.xpath(columnCheckBoxXpath));
    moveToElement(settingCheckbox);
    settingCheckbox.click();
  }

  public void clickChooseBtnInSettingPopup() {
    WebElement saveBtn = findClickableElement(By.id("popupSave"));
    saveBtn.click();
    waitForElementToBeInvisible(By.xpath("//div[@class='popup-content grid-settings-content']"));
    waitForDataIsLoaded();
  }

  public void enterNumberOfRows(String numberOfRows) {
    waitForElementToBeVisible(By.id("rowNumber"));
    WebElement numberOfRowsInput = webDriver.findElement(By.id("rowNumber"));
    Actions actions = new Actions(webDriver);
    actions.click(numberOfRowsInput).sendKeys(numberOfRowsInput, Keys.BACK_SPACE).
      sendKeys(numberOfRowsInput, Keys.BACK_SPACE).sendKeys(numberOfRowsInput, Keys.BACK_SPACE).sendKeys(numberOfRowsInput, numberOfRows).perform();
    waitForDataIsLoaded();
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
}
