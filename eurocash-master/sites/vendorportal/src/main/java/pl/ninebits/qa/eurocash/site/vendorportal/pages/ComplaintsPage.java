package pl.ninebits.qa.eurocash.site.vendorportal.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;

import java.text.MessageFormat;
import java.util.List;

public class ComplaintsPage extends VendorPortalBasePage {

  public ComplaintsPage(BasePage pageObject) {
    super(pageObject);
  }

  public void searchProduct(String complaints) {
    WebElement searchInput = findElement(By.cssSelector(".input-property.input-property__value"), "searchInput");
    searchInput.click();
    searchInput.clear();
    searchInput.sendKeys(complaints);
    clickSearchBtn();
  }

  private void clickSearchBtn() {
    WebElement searchBtn = findElement(By.cssSelector("[ng-reflect-icon-name='lupa']"), "searchBtn");
    searchBtn.click();
    waitForPageToLoad();
  }

  public void checkComplaint(String complaint, List<String> errors) {
    String complaintNumber = getTextFromElement(findElement(By.xpath("(//td[contains(@class,'mother-table__cell relative')])[2]")));
    if (!complaint.contains(complaintNumber)) {
      errors.add(MessageFormat.format("Wyszukiwano po numerze reklamacji {0} a znaleziono rekord z {1}.", complaint, complaintNumber));
    }
  }

  public void checkNoneComplaint(List<String> errors) {
    waitVendorForDataIsLoaded();
    if (isElementPresent(By.xpath("(//td[contains(@class,'mother-table__cell relative')])[2]"))) {
      errors.add("Pokazała się reklamacja, mimo że nie powinna.");
    }
  }

  public EditComplaintPage clickEditComplaintBtn() {
    WebElement editBtn = findElement(By.cssSelector("[ng-reflect-icon-name='edit'] .ven-icon"));
    editBtn.click();
    return new EditComplaintPage(this);
  }

  public void checkComplaintsListPresent(List<String> errors) {
    List<WebElement> complaintsList = webDriver.findElements(By.cssSelector("ven-complaints-list tr"));
    if (complaintsList.isEmpty()) {
      errors.add("Brak reklamacji w tabeli Reklamacje i zwroty.");
    }
  }
}
