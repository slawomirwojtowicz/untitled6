package pl.ninebits.qa.eurocash.site.marketplace.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;
import pl.ninebits.qa.eurocash.site.marketplace.forms.FilterForm;

import java.util.List;

public class ComplaintPage extends MarketplaceBasePage {
  public ComplaintPage(BasePage pageObject) {
    super(pageObject);
  }


  public NewComplaintPage clickReturnArticleBtn() {
    WebElement returnBtn = findElement(By.xpath("//span[contains(.,'Zgłoś nową reklamację')]"));
    returnBtn.click();
    return new NewComplaintPage(this);
  }

  public String getComplaintNumber() {
    String complaintNumber = getTextFromElement(By.xpath("(//a[contains(@href,'reklamacja/szczegol')])[1]"));
    return complaintNumber;
  }

  public FilterForm clickFilterBtn() {
    WebElement filterBtn = findElement(By.cssSelector(".filter-button"));
    filterBtn.click();
    return new FilterForm(this);
  }

  public void checComplaintNumber(String complaintNumber, List<String> errors) {
    if (!isElementPresent(By.xpath("//table[contains(@class,'mt-table')]//a[contains(@href,'reklamacja') and contains(.,'" + complaintNumber + "')]"))) {
      errors.add("Brak numeru szukanej reklamacji na liście");
    }
  }

  public ComplaintDetailsPage clickComplaintDetails(String complaintNumber) {
    WebElement complaintDetailsBtn = findElement(By.xpath("//table[contains(@class,'mt-table')]//a[contains(@href,'reklamacja') and contains(.,'" + complaintNumber + "')]"));
    complaintDetailsBtn.click();
    return new ComplaintDetailsPage(this);
  }
}
