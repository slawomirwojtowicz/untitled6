package pl.ninebits.qa.eurocash.site.vendorportal.forms;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.EditComplaintPage;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.VendorPortalBasePage;

public class AddingNewCorrectionForm extends VendorPortalBasePage {

  public AddingNewCorrectionForm(BasePage pageObject) {
    super(pageObject);
  }

  public void typeCorrectionNumberValue(String correctionNumber) {
    WebElement input = findElement(By.cssSelector(".input__input"));
    input.click();
    input.clear();
    input.sendKeys(correctionNumber);
  }

  public void typeCurrentDate(String date) {
    WebElement input = findElement(By.cssSelector("[formcontrolname='issue_date']"));
    input.click();
    input.click();
    input.clear();
    input.sendKeys(date);
  }

  public void clickCheckboxBtn() {
    WebElement clickCheckboxBtn = findElement(By.cssSelector(".mat-checkbox-inner-container-no-side-margin"));
    clickCheckboxBtn.click();
  }

  public EditComplaintPage clickApproveBtn() {
    WebElement approveBtn = findElement(By.cssSelector("form .mat-button-wrapper"));
    approveBtn.click();
    return new EditComplaintPage( this);
  }
}
