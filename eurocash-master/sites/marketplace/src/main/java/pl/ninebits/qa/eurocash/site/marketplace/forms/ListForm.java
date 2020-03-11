package pl.ninebits.qa.eurocash.site.marketplace.forms;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;

public class ListForm extends MarketplaceBaseForm {
  public ListForm(BasePage page) {
    super(page);
  }

  public void typeListQuery(String listQuery) {
    waitForDataIsLoaded();
    WebElement listInput = findElement(By.cssSelector(".tooltip-input"));
    listInput.clear();
    listInput.sendKeys(listQuery);
  }

  public void clickAddList() {
    WebElement listBtn = findElement(By.xpath("//div[contains(@class,'mt-btn--custom')]"));
    listBtn.click();
    waitForElementToBeVisible(By.xpath("//div[contains(@class,'mt-btn text-bold') and contains(.,'Zamknij okno')]"));
    sleep(1000);
  }

  public void clickCloseWindowBtn() {
    WebElement closeWindowBtn = findElement(By.xpath("//div[contains(@class,'mt-btn text-bold') and contains(.,'Zamknij okno')]"));
    closeWindowBtn.click();
  }

  public void clickListBtn(String listName) {
    WebElement clicList = webDriver.findElement(By.xpath("//div[contains(@class,'list-name') and contains(.,'" + listName + "')]"));
    clicList.click();
  }

}
