package pl.ninebits.qa.eurocash.site.ehurt.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import pl.ninebits.qa.automated.tests.core.utils.TextUtils;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;

import java.util.List;

public class DynamicReportsPage extends EhurtBasePage {
  public DynamicReportsPage(BasePage pageObject) {
    super(pageObject);
  }


  public boolean chooseRodoReport(List<String> errors) {
    findClickableElement(By.xpath("//td[contains(@id,'selectRaportPanel_ddNodes')]//img")).click(); //spanner
    findVisibleElement(By.xpath("//div[contains(@id,'selectRaportPanel_ddNodes')]//span[.='eHURT']")).click(); //span eHurt node
    sleep(1000);
    List<WebElement> rodoReport = webDriver.findElements(By.xpath("//div[contains(@id,'selectRaportPanel_ddNodes')]//span[contains(text(),'RODO')]"));
    if (!rodoReport.isEmpty()) {
      rodoReport.get(0).click();
      sleep(1000);
      waitForElementToBeVisible(By.xpath("//span[text()='Wyczyść wszystko']"));
      return true;
    } else {
      errors.add("Brak raportu RODO do wyboru");
      return false;
    }
  }

  public boolean chooseMarketingAgreementsReport(List<String> errors) {
    findClickableElement(By.xpath("//td[contains(@id,'selectRaportPanel_ddNodes')]//img")).click(); //spanner
    findVisibleElement(By.xpath("//div[contains(@id,'selectRaportPanel_ddNodes')]//span[.='eHURT']")).click(); //span eHurt node
    sleep(1000);
    List<WebElement> marketingReport = webDriver.findElements(By.xpath("//div[contains(@id,'selectRaportPanel_ddNodes')]//span[contains(text(),'Zgody Marketingowe')]"));
    if (!marketingReport.isEmpty()) {
      marketingReport.get(0).click();
      sleep(1000);
      waitForElementToBeVisible(By.xpath("//span[text()='Wyczyść wszystko']"));
      return true;
    } else {
      errors.add("Brak raportu Zgody marketingowe do wyboru");
      return false;
    }
  }

  public void clearAll() {
    findVisibleElement(By.xpath("//span[text()='Wyczyść wszystko']")).click();
  }

  public void checkAllAndGenerateReport() {
    int attempt = 0;
    while ((notAllFieldsFilled() || noDataGenerated()) && attempt < 4) {
      checkOnlineOnly();
      clickGenerateBtn();
      attempt++;
    }
    if (isElementPresent(By.xpath("//span[contains(.,'Nie wszystkie pola wymagane są uzupełnione!')]"))) {
      Assert.fail("Nie udało się wygenerować raportu");
    }
  }

  private boolean notAllFieldsFilled() {
    return isElementPresent(By.xpath("//span[contains(.,'Nie wszystkie pola wymagane są uzupełnione!')]"));
  }

  private boolean noDataGenerated() {
    return isElementPresent(By.xpath("//tr[contains(@id,'MainContent_gvRaport_DXEmptyRow')]//div[contains(text(),'Brak danych do wyświetlenia')]"));
  }

  private void clickGenerateBtn() {
    if (isElementPresent(By.xpath("//span[text()='Generowanie']"))) {
      webDriver.findElement(By.xpath("//span[text()='Generowanie']")).click();
      waitForDataIsLoaded();
      waitForPageToLoad();
      sleep(1000);
    }
  }

  private void checkOnlineOnly() {
    findClickableElement(By.xpath("//label[text()='eurocash.pl']/../..//td[1]/span")).click();
    sleep(1000);
    try {
      waitForElementToBeVisible(By.xpath("//span[text()='Generowanie']"), 3);
    } catch (Exception e) {
      //
    }
  }

  public boolean checkDataPresent(List<String> errors) {
    List<WebElement> dataRows = webDriver.findElements(By.xpath("//table[contains(@id,'MainContent_gvRaport')]//tr[contains(@id,'DXDataRow')]"));
    if (!dataRows.isEmpty()) {
      return true;
    } else {
      errors.add("Brak danych raportu");
      return false;
    }
  }

  public boolean onDynamicReportsPage() {
    String currentUrl = getCurrentUrl();
    return currentUrl.contains("/RaportyDynamiczne.aspx");
  }

  public void checkUserEmailConsentPresent(String login, boolean shouldBePresent, String email, List<String> errors) throws Exception {
    filterByUser(login);
    waitForDataIsLoaded();
    sleep(2000);
    List<String> consentsIds = getConsentsIds();
    int rowNumber = consentsIds.indexOf("18");
    if (rowNumber >= 0) {
      if (shouldBePresent) {
        if (!getEmail(rowNumber + 1).contains(email)) {
          errors.add("Brak wpisu zgody na email w raporcie");
        }
      } else {
        if (!getEmail(rowNumber + 1).replaceAll("[\\s\\u00A0]", "").isEmpty()) {
          errors.add("Dla usera z usuniętą zgodą na email, nadal istnieje wpis w raporcie z adresem");
        }
      }
    } else {
      errors.add("Nie udało się pobrać wiersza ze zgodą na email");
    }
  }

  public void checkUserCallConsentPresent(String login, boolean shouldBePresent, String phoneNumber, List<String> errors) throws Exception {
    sleep(2000);
    int rowNumber = getConsentsIds().indexOf("19");
    if (rowNumber >= 0) {
      if (shouldBePresent) {
        if (!getPhoneNumber(rowNumber + 1).contains(phoneNumber)) {
          errors.add("Brak wpisu zgody na SMS w raporcie ");
        }
      } else {
        if (!getPhoneNumber(rowNumber + 1).replaceAll("[\\s\\u00A0]", "").isEmpty()) {
          errors.add("Dla usera z usuniętą zgodą na SMS, nadal istenieje wpis w raporcie");
        }
      }
    } else {
      errors.add("Nie udało się pobrać wiersza ze zgodą na SMS");
    }
  }

  public void checkUserSmsConsentPresent(String login, boolean shouldBePresent, String phoneNumber, List<String> errors) throws Exception {
    int rowNumber = getConsentsIds().indexOf("20");
    if (rowNumber >= 0) {
      if (shouldBePresent) {
        if (!getPhoneNumber(rowNumber + 1).contains(phoneNumber)) {
          errors.add("Brak wpisu zgody na połączenia telefoniczne w raporcie ");
        }
      } else {
        if (!getPhoneNumber(rowNumber + 1).replaceAll("[\\s\\u00A0]", "").isEmpty()) {
          errors.add("Dla usera z usuniętą zgodą na połącznenia telefoniczne, nadal istenieje wpis w raporcie");
        }
      }
    } else {
      errors.add("Nie udało się pobrać wiersza ze zgodą na połączenia telefoniczne");
    }
  }

  private void filterByUser(String login) {
    try {
      findClickableElement(By.xpath("//td[.='Uzytkownik']/..//img[contains(@class,'HeaderFilter')]"), 10).click();
      findClickableElement(By.xpath("//tr[contains(@class,'dxeListBoxItemRow_ehurt')]/td[contains(text(),'" + login + "')]"), 10).click();
      sleep(1000);
    } catch (Exception e) {
      Assert.fail("Nie udało się pofiltrować raportu zgód marketingowych po loginie");
    }
  }

  private List<String> getLogins() throws Exception {
    waitForDataIsLoaded();
    sleep(1000);
    List<String> logins = TextUtils.getTextFromWebElementsList(webDriver.findElements(By.xpath("//tr[contains(@id,'MainContent_gvRaport_DXDataRow')]//td[4]")));
    for (int i = 0; i < logins.size(); i++) {
      String tmp = logins.get(i).replaceAll("[(0-9)]{4,10}", "").trim();
      logins.set(i, tmp);
    }
    return logins;
  }

  private List<String> getConsentsIds() throws Exception {
    waitForDataIsLoaded();
    sleep(1000);
    return TextUtils.getTextFromWebElementsList(webDriver.findElements(
      By.xpath("//tr[contains(@id,'MainContent_gvRaport_DXDataRow')]//td[3]")));
  }

  private String getEmail(int rowNumber) {
    waitForDataIsLoaded();
    sleep(1000);
    return getTextFromElement(By.xpath("(//tr[contains(@id,'MainContent_gvRaport_DXDataRow')]//td[6])[" + rowNumber + "]"));
  }

  private String getPhoneNumber(int rowNumber) {
    waitForDataIsLoaded();
    sleep(1000);
    return getTextFromElement(By.xpath("(//tr[contains(@id,'MainContent_gvRaport_DXDataRow')]//td[5])[" + rowNumber + "]"));
  }
}
