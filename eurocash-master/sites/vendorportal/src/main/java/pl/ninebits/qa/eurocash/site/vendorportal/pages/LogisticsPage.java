package pl.ninebits.qa.eurocash.site.vendorportal.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.components.SideMenu;

import java.util.List;

public class LogisticsPage extends ProductBasePage {
  public LogisticsPage(BasePage pageObject) {
    super(pageObject);
  }


  public final SideMenu sideMenu = new SideMenu(createPageObject(SideMenu.class));

  public void checkLogisticsDataToUnpinPresent() throws Exception {
    if (webDriver.findElements(By.cssSelector(".mat-checkbox-layout")).isEmpty()) {
      throw new Exception("Brak checkboxów do zmiany logistyki");
    }

    List<WebElement> checked = webDriver.findElements(By.xpath("//input[@aria-checked='true']"));
    if (checked.size() != 2) {
      throw new Exception("Lista checkboxów do odpięcia ma więcej niż 2 elementy. Popraw dane");
    }

    if (!isElementPresent(By.xpath("//td[.='Błonie']/..//input[@aria-checked='true']"))
      && !isElementPresent(By.xpath("//td[.='Lublin']/..//input[@aria-checked='true']"))) {
      throw new Exception("Oczekiwano logistyki dla Błonie i Lublin. Popraw dane.");
    }
  }

  public void changeLogistics() {
    WebElement lublinCheckbox = findElement(By.xpath("//td[.='Lublin']/..//mat-checkbox"), "lublinCheckbox");
    scrollToElement(lublinCheckbox);
    scrollDownByWebElementHeight(lublinCheckbox);
    lublinCheckbox.click();
    sleep(1000);

    WebElement blonieCheckbox = findElement(By.xpath("//td[.='Błonie']/..//mat-checkbox"), "blonieCheckbox");
    scrollToElement(blonieCheckbox);
    scrollDownByWebElementHeight(blonieCheckbox);
    blonieCheckbox.click();
    sleep(1000);
  }

  public void changeOneLogistics(String logistic) {
    WebElement logisticCheckbox = findElement(By.xpath("//td[.='" + logistic + "']/..//mat-checkbox"), "logisticCheckbox");
    scrollToElement(logisticCheckbox);
    scrollDownByWebElementHeight(logisticCheckbox);
    logisticCheckbox.click();
    sleep(1000);
  }

  public void enterStartDate(String date) {
    Actions actions = new Actions(webDriver);
    WebElement startDateInput = webDriver.findElement(By.cssSelector("[formcontrolname='start_supply_date']"));
    startDateInput.click();
    sleep(500);
    startDateInput.sendKeys(Keys.ENTER);
    actions.moveToElement(startDateInput).click().keyDown(Keys.LEFT_SHIFT).sendKeys(Keys.F10).keyUp(Keys.LEFT_SHIFT).build().perform();
    startDateInput.sendKeys(date);
    startDateInput.sendKeys(Keys.ENTER);
    waitVendorForDataIsLoaded();
  }

  public void clickSendForVerificationBtn() {
    WebElement sendForVerification = findElement(By.cssSelector(".box .mat-flat-button .mat-button-wrapper"));
    sendForVerification.click();
    sleep(500);
  }

  public void checkLogisticsUnpinned() throws Exception {
    if (!webDriver.findElements(By.xpath("//input[@aria-checked='true']")).isEmpty()) {
      throw new Exception("Logistyka nie została odpięta");
    }
  }

  public void checkLogisticsPinned(List<String> errors) {
    sleep(2000);
    if (webDriver.findElements(By.xpath("//input[@aria-checked='true']")).size() != 2) {
      errors.add("Logistyka nie została przypięta");
    }
  }
}
