package pl.ninebits.qa.eurocash.site.ehurt.pages.myaccount;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import pl.ninebits.qa.automated.tests.core.utils.DateUtils;
import pl.ninebits.qa.automated.tests.core.utils.TextUtils;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtBasePage;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ComplaintsPage extends EhurtBasePage {

  public ComplaintsPage(BasePage pageObject) {
    super(pageObject);
    waitForDataIsLoaded();
  }


  public boolean checkPopupLinkPresent(List<String> errors) {
    if (isElementPresent(By.xpath("//span[contains(@class,'actionBtn')][text()='Zobacz wysłane reklamacje']"))) {
      return true;
    } else {
      errors.add("Brak przycisku 'Wysłane reklamacje'");
      return false;
    }
  }

  public void clickSentComplaintsLink() {
    findClickableElement(By.xpath("//span[contains(@class,'actionBtn')][text()='Zobacz wysłane reklamacje']")).click();
    waitForDataIsLoaded();
    waitForElementToBeVisible(By.xpath("//div[contains(@class,'dx-toolbar')]/div[text()='Wysłane reklamacje']"), 5);
  }

  public void checkSentComplaintsPopupDisplayed(List<String> errors) {
    if (webDriver.findElements(By.xpath("//dx-data-grid[@id='dxDataGridPopup']//tbody//tr")).size() < 1){
      errors.add("Popup z danymi o wysłanych reklamacjach nie wyświetla się poprawnie");
    }
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

  private List<String> getStartDates() throws Exception {
    List<WebElement> elements = webDriver.findElements(By.xpath("//td[@class='numeric lato date-spacer']/span[@class='padding-left-2']"));
    return TextUtils.getTextFromWebElementsList(elements);
  }

  public void checkComplaintsFilteredByStartDate(String startDate, List<String> errors) throws Exception {
    startDate = DateUtils.changeDateFromSearchToGridFormat(startDate);
    Date date = new SimpleDateFormat("dd-MM-yyyy").parse(startDate);
    List<String> startDates = getStartDates();
    for (String startDateExtr : startDates) {
      if (date.after(new SimpleDateFormat("dd-MM-yyyy").parse(startDateExtr))) {
        errors.add(MessageFormat.format("Znaleziono datę początku wcześniejszą od {0}", date.toString()));
      }
    }
  }
}
