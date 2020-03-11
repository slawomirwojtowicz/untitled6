package pl.ninebits.qa.eurocash.site.vendorportal.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;
import pl.ninebits.qa.eurocash.site.vendorportal.forms.AddingNewCorrectionForm;

import java.text.MessageFormat;
import java.util.List;

public class EditComplaintPage extends VendorPortalBasePage {

  public EditComplaintPage(BasePage pageObject) {
    super(pageObject);
  }

  public LineDetailsPage clickFilterBtn() {
    WebElement filterBtn = findElement(By.cssSelector("[ng-reflect-data-row] .mat-button-wrapper"));
    filterBtn.click();
    return new LineDetailsPage(this);
  }

  public void checkComplaint(String complaint, List<String> errors) {
    String complaintNumber = getTextFromElement(findElement(By.xpath("//span[@class='breadcrumb' and contains(.,'Reklamacja nr')]")));
    if (!complaintNumber.contains(complaint)) {
      errors.add(MessageFormat.format("Wyszukiwano po numerze reklmacji {0} a znaleziono rekord z {1}", complaint, complaintNumber));
    }
  }

  public LineDetailsPage clickRejectedBtn() {
    WebElement rejectedBtn = findElement(By.xpath("//a[contains(@ng-reflect-router-link,'edit') and contains(.,'Odrzucon')]"));
    moveToElement(rejectedBtn);
    rejectedBtn.click();
    waitVendorForDataIsLoaded();
    return new LineDetailsPage(this);
  }

  public AddingNewCorrectionForm addNewCorrection() {
    WebElement addNewCorrection = findElement(By.cssSelector(".no-left-margin .mat-button-wrapper"));
    addNewCorrection.click();
    return new AddingNewCorrectionForm(this);
  }

  public void checkRecognitionOfComplaints(List<String> errors) {
    if (!isElementPresent(By.xpath("//div[@class='snackbar'  and contains(.,' Reklamacja została uznana')]"))) {
      errors.add("Brak potwierdzenia uznania reklmacji");
    }
  }

  public void checkComplaintOpened(String complaintNumber, List<String> errors) {
    String openedComplaintNumber = getTextFromElement(By.cssSelector("ven-edit-complaint .page-title"));
    openedComplaintNumber = openedComplaintNumber.substring(0, openedComplaintNumber.indexOf('\n')).replaceAll("[^\\d]", "");
    if (!openedComplaintNumber.contains(complaintNumber)) {
      errors.add("Otwarto nieprawidłową reklamację.");
    }
  }
}
