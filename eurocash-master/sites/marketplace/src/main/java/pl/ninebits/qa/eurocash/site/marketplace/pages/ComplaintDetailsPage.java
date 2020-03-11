package pl.ninebits.qa.eurocash.site.marketplace.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;
import pl.ninebits.qa.eurocash.site.marketplace.constants.ProductName;

import java.util.List;

public class ComplaintDetailsPage extends MarketplaceBasePage {
  public ComplaintDetailsPage(BasePage pageObject) {
    super(pageObject);
  }


  public void checkInvoiceNumber(String invoiceNumber, List<String> errors) {
    if (!isElementPresent(By.xpath("(//div[contains(@class,'mt-dark-main text-medium')]//div[contains(.,'Numer faktury:')]/div[contains(.,'" + invoiceNumber + "')])[2]"))) {
      errors.add("Brak numeru szukanej faktury");
    }
  }

  public void checkProductName(ProductName name, List<String> errors) {
    if (!isElementPresent(By.xpath("//div[contains(@class,'complaint-name') and contains(.,'" + name.getName() + "') ]"))) {
      errors.add("Nazwa produktu nie zgadza się z nazwą produktu reklamowanego");
    }
  }

  public String geComplaintNumber() {
    String complaintNumber = getTextFromElement(By.xpath("(//div[contains(@class,'uiKit-flex')]//div[contains(@class,'text-right ') " +
      "and contains(.,('Numer reklamacji:'))]//..//div)[2]"));
    return complaintNumber;
  }

  public void checkComplaintNumber(String complaintNumber, List<String> errors) {
    if (!isElementPresent(By.xpath("//div[contains(@class,'text-right') and contains(.,'Numer reklamacji')]//..//div[contains(.,'"+complaintNumber+"')]"))) {
      errors.add("Brak numeru szukanej reklamacji");
    }
  }

  public void checkRejectedComplaint(List<String> errors) {
    waitForDataIsLoaded();
    if (!isElementPresent(By.xpath("//div[@class='reject-color' and contains(.,'Odrzucona')]"))) {
      errors.add("Brak statusu odrzucona reklmacja");
    }
  }

  public void clickReplayBtn() {
    WebElement replaypBtn = findElement(By.xpath("//div[contains(@class,'mt-green cursor-pointer') and contains(.,'Odpowiedz')]"));
    moveToElement(replaypBtn);
    replaypBtn.click();
    waitForElementToBeVisible(By.cssSelector(".chat-box-text"), 10);
  }

  public void checkSendMessage(String message, List<String> errors) {
    if (!isElementPresent(By.xpath("//span[@class='chat-box-text' and contains(.,'"+message+"')]"))) {
      errors.add("Brak wysłanej wiadomości");
    }
  }

}
