package pl.ninebits.qa.eurocash.site.ehurt.pages.myaccount;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pl.ninebits.qa.automated.tests.core.utils.HttpsUtils;
import pl.ninebits.qa.automated.tests.core.utils.TextUtils;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtBasePage;

import java.util.List;

public class ShopAccountPage extends EhurtBasePage {
  public ShopAccountPage(BasePage pageObject) {
    super(pageObject);
  }


  public String getAccountNumber() throws Exception {
    ensurePayersDataIsLoaded();
    return findVisibleElement(By.xpath("//div[@class='shop-info__table']//td[contains(text(),'Subkonto')]/../td[2]"))
      .getText().replaceAll("[\\s\\u00A0]", "");
  }

  public void ensurePayersDataIsLoaded() throws Exception {
    List<String> payersData = TextUtils.getTextFromWebElementsList(
      webDriver.findElements(By.xpath("//h4[text()='Płatnik']/../..//div[@class='shop-info__table']//tr/td[2]")));

    for (int i = 1; i <= 10; i++) {
      int j = 0;
      for (String payerData : payersData) {
        if (payerData.trim().equals("-")) {
          j++;
        }
      }
      if (j > 2) {
        sleep(1000);
        payersData = TextUtils.getTextFromWebElementsList(
          webDriver.findElements(By.xpath("//h4[text()='Płatnik']/../..//div[@class='shop-info__table']//tr/td[2]")));
      } else {
        break;
      }
    }
  }

  public boolean checkPdfFilesToDownloadPresent(List<String> errors) {
    List<WebElement> downloadBtn = webDriver.findElements(By.xpath("//a[contains(@href,'pdf')]"));
    if (downloadBtn.size() < 1) {
      errors.add("Brak plików pdf do pobrania");
      return false;
    } else {
      return true;
    }
  }

  public void checkCanDownloadPdfFiles(List<String> errors) {
    List<WebElement> pdfLinks = webDriver.findElements(By.xpath("//a[contains(@href,'pdf')]"));
    List<String> hrefs = TextUtils.getAttributesFromWebElementsList(pdfLinks, "href");
    for (String href : hrefs) {
      HttpsUtils.sendGetRequest(href, errors);
    }
  }

  public boolean checkAgreementsBoxesPresent(List<String> errors) {
    List<WebElement> agrCheckboxes = webDriver.findElements(By.xpath("//label[contains(@for,'agreement')]"));
    if (agrCheckboxes.size() < 3) {
      errors.add("Brak 3 checboxów dotyczących kanałów komunikacji marketingowych");
      return false;
    } else {
      return true;
    }
  }

  public void clearAllAgreements() {
    List<WebElement> chgAgreementBtns = webDriver.findElements(By.cssSelector(".shop-info__agreements-"));
    if (!chgAgreementBtns.isEmpty()) {
      for (WebElement chgAgreementBtn : chgAgreementBtns) {
        chgAgreementBtn.click();
        findClickableElement(By.xpath("//div[@class='popup-buttons']//a[contains(text(),'Usuń')]"), 5).click();
        waitForElementToBeInvisible(By.xpath("//div[@class='popup-buttons']//a[contains(text(),'Usuń')]"), 5);
      }
    }
  }

  public void agreeForEmail(String email, List<String> errors) {
    findClickableElement(By.xpath("//label[@for='agreement-18']")).click();
    waitForElementToBeVisible(By.cssSelector(".popup"));
    List<WebElement> emailInput = webDriver.findElements(By.xpath("//input[@type='email']"));
    if (!emailInput.isEmpty()) {
      WebElement elem = emailInput.get(0);
      elem.clear();
      elem.sendKeys(email);
      findClickableElement(By.xpath("//a[contains(text(),'Zapisz')]")).click();
      waitForElementToBeInvisible(By.xpath("//a[contains(text(),'Zapisz')]"));
      sleep(1000); //overlay
    } else {
      errors.add("Brak pola email w popupie zgody marketingowej");
      findClickableElement(By.cssSelector(".close")).click();
      waitForElementToBeInvisible(By.cssSelector(".close"));
      sleep(1000); //overlay
    }
  }

  public void agreeForSMS(String phoneNumber, List<String> errors) {
    findClickableElement(By.xpath("//label[@for='agreement-19']")).click();
    waitForElementToBeVisible(By.cssSelector(".popup"));
    List<WebElement> phoneInput = webDriver.findElements(By.xpath("//input[@type='tel']"));
    if (!phoneInput.isEmpty()) {
      WebElement elem = phoneInput.get(0);
      elem.clear();
      elem.sendKeys(phoneNumber);
      findClickableElement(By.xpath("//a[contains(text(),'Zapisz')]")).click();
      waitForElementToBeInvisible(By.xpath("//a[contains(text(),'Zapisz')]"));
      sleep(1000); //overlay
    } else {
      errors.add("Brak pola numer telefonu w popupie zgody na sms");
      findClickableElement(By.cssSelector(".close")).click();
      waitForElementToBeInvisible(By.cssSelector(".close"));
      sleep(1000); //overlay
    }
  }

  public void agreeForCalls(String phoneNumber, List<String> errors) {
    findClickableElement(By.xpath("//label[@for='agreement-20']")).click();
    waitForElementToBeVisible(By.cssSelector(".popup"));
    List<WebElement> phoneInput = webDriver.findElements(By.xpath("//input[@type='tel']"));
    if (!phoneInput.isEmpty()) {
      WebElement elem = phoneInput.get(0);
      elem.clear();
      elem.sendKeys(phoneNumber);
      findClickableElement(By.xpath("//a[contains(text(),'Zapisz')]")).click();
      waitForElementToBeInvisible(By.xpath("//a[contains(text(),'Zapisz')]"));
      sleep(1000); //overlay
    } else {
      errors.add("Brak pola numer telefonu w popupie zgody na połączenia");
      findClickableElement(By.cssSelector(".close")).click();
      waitForElementToBeInvisible(By.cssSelector(".close"));
      sleep(1000); //overlay
    }
  }

  public void checkAgreementsPresent(boolean shouldBePresent, List<String> errors) {
    List<WebElement> agreementsEditBtns = webDriver.findElements(By.cssSelector(".shop-info__agreements-"));
    if (shouldBePresent) {
      if (agreementsEditBtns.size() < 3) {
        errors.add("Nie wszystkie zgody marketingowe zostały dodane");
      }
    } else {
      if (agreementsEditBtns.size() > 0) {
        errors.add("Nie wszystkie zgody marketingowe zostały usunięte");
      }
    }
    sleep(1000);
    waitUntil(ExpectedConditions.invisibilityOfAllElements(webDriver.findElements(By.cssSelector(".snackbar-message"))));
  }

  public void agreeForEmailCancel(List<String> errors) {
    //todo
  }

  public void agreeForSMSCancel(List<String> errors) {
    //todo
  }

  public void agreeForCallsCancel(List<String> errors) {
    //todo
  }
}
