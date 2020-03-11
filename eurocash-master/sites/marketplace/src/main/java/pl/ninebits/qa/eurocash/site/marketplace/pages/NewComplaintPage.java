package pl.ninebits.qa.eurocash.site.marketplace.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;
import pl.ninebits.qa.eurocash.site.marketplace.constants.ProductName;
import pl.ninebits.qa.eurocash.site.marketplace.forms.ComplaintForm;

import java.util.List;

public class NewComplaintPage extends MarketplaceBasePage {
  public NewComplaintPage(BasePage pageObject) {
    super(pageObject);
  }

  public void clickReturnArticleBtn() {
    WebElement returnBtn = findElement(By.xpath("//div[contains(@class,'big-grid')]//div[contains(.,'Chcę zwrócić towar')]"));
    returnBtn.click();
  }

  public void clickIWantToReportLackBtn() {
    WebElement reportLackBtn = findElement(By.xpath("//div[contains(@class,'big-grid')]//div[contains(.,'Chcę zgłosić brak')]"));
    reportLackBtn.click();
  }

  public void clickIWantToReportSurplusBtn() {
    WebElement reportSurplusBtn = findElement(By.xpath("//div[contains(@class,'big-grid')]//div[contains(.,'Chcę zgłosić nadwyżkę')]"));
    reportSurplusBtn.click();
  }

  public void clickIWantToReportIncorrectPriceBtn() {
    WebElement incorrectPriceBtn = findElement(By.xpath("//div[contains(@class,'big-grid')]//div[contains(.,'Chcę zgłosić nieprawidłową cenę')]"));
    incorrectPriceBtn.click();
  }

  public void clickIWantToReportAnotherProblemBtn() {
    WebElement anotherProblemBtn = findElement(By.xpath("//div[contains(@class,'big-grid')]//div[contains(.,'Chcę zgłosić inny problem')]"));
    anotherProblemBtn.click();
  }

  public ComplaintForm selectComplaint() {
    WebElement complatint = findElement(By.cssSelector(".ng-invalid.ng-pristine.ng-untouched > .pointer.sort-select"));
    complatint.click();
    return new ComplaintForm(this);
  }

  public void clickSendComplaint() {
    waitForDataIsLoaded();
    WebElement sendBtn = findElement(By.cssSelector("#sendComplaint"));
    sendBtn.click();
  }

  public void checkThankYouPresent(List<String> errors) {
    waitForDataIsLoaded();
    if (!isElementPresent(By.cssSelector(".complaint-summary"))) {
      errors.add("Strona z podziękowaniem za dodanie reklamacji nie wczytała się poprawnie");
    }
  }

  public ComplaintDetailsPage clickComplaintDetails() {
    WebElement complaintDetailsBtn = findElement(By.xpath("//div[contains(@class,'mt-green') and contains(.,'Reklamacj')]"));
    complaintDetailsBtn.click();
    sleep(500);
    return new ComplaintDetailsPage(this);
  }

  public void checkInvoiceNumber(String invoiceNumber, List<String> errors) {
    if (!isElementPresent(By.xpath("//div[contains(@class,'uiKit-inline-flex')]//span[contains(.,'" + invoiceNumber + "')]"))) {
      errors.add("Brak numeru szukanej faktury");
    }
  }

  public void checkProductName(ProductName name, List<String> errors) {
    if (!isElementPresent(By.xpath("//div[@class='complaint-summary__product']/span[contains(.,'" + name.getName() + "')]"))) {
      errors.add("Nazwa produktu nie zgadza się z nazwą reklamowanego");
    }
  }

  public void checkThankYouForAddingComplaint(List<String> errors) {
    waitForDataIsLoaded();
    if (!isElementPresent(By.cssSelector(".complaint-header"))) {
      errors.add("Strona z podziękowaniem za dodanie reklamacji nie wczytała się poprawnie");
    }
  }
}
