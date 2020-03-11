package pl.ninebits.qa.eurocash.site.ehurt.pages.myaccount;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import pl.ninebits.qa.automated.tests.core.utils.DateUtils;
import pl.ninebits.qa.automated.tests.core.utils.RandomUtils;
import pl.ninebits.qa.automated.tests.core.utils.TextUtils;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;
import pl.ninebits.qa.eurocash.site.ehurt.forms.FiltersForm;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtBasePage;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PaymentsPage extends EhurtBasePage {
  public PaymentsPage(BasePage pageObject) {
    super(pageObject);
    waitForDataIsLoaded();
  }


  public String getAccountNumber() {
    return findVisibleElement(By.xpath("(//div[contains(@class,'bank-account-data')]//h3)[1]")).getText().replaceAll("[\\s\\u00A0]", "");
  }

  public void checkAccountNumber(String accountNumber, List<String> errors) {
    String extractedAccNumber = getAccountNumber();
    if (!extractedAccNumber.equals(accountNumber)) {
      errors.add(MessageFormat.format("Numery kont sie nie zgadzają. Oczekiwano {0} a pobrano {1}", accountNumber, extractedAccNumber));
    }
  }

  public String getContractorId() throws Exception {
    List<WebElement> elements = webDriver.findElements(By.xpath("//tbody//td[4]/span"));
    List<String> values = TextUtils.getTextFromWebElementsList(elements.subList(0, 20));
    return values.get(RandomUtils.randomInt(0, 20));
  }

  public String getContractorName() throws Exception {
    List<WebElement> elements = webDriver.findElements(By.xpath("//tbody//td[5]/span"));
    List<String> values = TextUtils.getTextFromWebElementsList(elements).subList(0, 20);
    return values.get(RandomUtils.randomInt(0, 20));
  }

  public String getContractorAbrr() throws Exception {
    List<WebElement> elements = webDriver.findElements(By.xpath("//tbody//td[6]/span"));
    List<String> values = TextUtils.getTextFromWebElementsList(elements).subList(0, 20);
    return values.get(RandomUtils.randomInt(0, 20));
  }

  public FiltersForm clickShowFilters() {
    findClickableElement(By.xpath("//a[@id='sidebarToggleBtn']/span")).click();
    waitForElementToBeVisible(By.xpath("//a[@id='sidebarToggleBtn']/span[contains(text(),'Ukryj')]"));
    return new FiltersForm(this);
  }

  public void checkPaymentsFilterByContractorId(String contractorId, List<String> errors) throws Exception {
    List<WebElement> elements = webDriver.findElements(By.xpath("//tbody//td[4]/span"));
    List<String> values = TextUtils.getTextFromWebElementsList(elements);
    for (String value : values) {
      if (!value.contains(contractorId)) {
        errors.add(MessageFormat.format("Na liście znajduje się Kod KH {0} podczas gdy szukano po {1}", value, contractorId));
      }
    }
  }

  public void checkPaymentsFilterByContractorName(String contractorName, List<String> errors) throws Exception {
    List<WebElement> elements = webDriver.findElements(By.xpath("//tbody//td[5]/span"));
    List<String> values = TextUtils.getTextFromWebElementsList(elements);
    for (String value : values) {
      if (!value.contains(contractorName)) {
        errors.add(MessageFormat.format("Na liście znajduje się Nazwa KH {0} podczas gdy szukano po {1}", value, contractorName));
      }
    }
  }

  public void checkPaymentsFilterByContractorAbrr(String contractorAbrr, List<String> errors) throws Exception {
    List<WebElement> elements = webDriver.findElements(By.xpath("//tbody//td[6]/span"));
    List<String> values = TextUtils.getTextFromWebElementsList(elements);
    for (String value : values) {
      if (!value.contains(contractorAbrr)) {
        errors.add(MessageFormat.format("Na liście znajduje się Skrót KH {0} podczas gdy szukano po {1}", value, contractorAbrr));
      }
    }
  }

  public void checkTypeOfDocument(String documentType, List<String> errors) throws Exception {
    List<WebElement> elements = webDriver.findElements(By.xpath("//tbody//td[3]/span"));
    List<String> values = TextUtils.getTextFromWebElementsList(elements);
    for (String value : values) {
      if (!value.contains(documentType)) {
        errors.add(MessageFormat.format("Na liście znajduje się typ dokumentu{0} podczas gdy szukano po {1}", value, documentType));
      }
    }
  }


  public void enterStartDate(String date) {
    WebElement startDateInput = webDriver.findElement(By.xpath("//span[@id='lblDateFrom']//input[@class='dx-texteditor-input']"));
    closeTutorial();
    startDateInput.click();
    startDateInput.clear();
    startDateInput.sendKeys(date);
    startDateInput.sendKeys(Keys.ENTER);
    waitForDataIsLoaded();
  }

  public void enterEndDate(String date) {
    WebElement endDateInput = webDriver.findElement(By.xpath("//span[@id='lblDateTo']//input[@class='dx-texteditor-input']"));
    closeTutorial();
    endDateInput.clear();
    endDateInput.sendKeys(date);
    endDateInput.sendKeys(Keys.ENTER);
    waitForDataIsLoaded();
  }

  private List<String> getStartDates() throws Exception {
    List<WebElement> elements = webDriver.findElements(By.xpath("//td[@class='numeric lato date-spacer']/span[@class='padding-left-2']"));
    return TextUtils.getTextFromWebElementsList(elements);
  }

  public void checkPaymentFilteredByStartDate(String startDate, List<String> errors) throws Exception {
    startDate = DateUtils.changeDateFromSearchToGridFormat(startDate);
    Date date = new SimpleDateFormat("dd-MM-yyyy").parse(startDate);
    List<String> startDates = getStartDates();
    for (String startDateExtr : startDates) {
      if (date.after(new SimpleDateFormat("dd-MM-yyyy").parse(startDateExtr))) {
        errors.add(MessageFormat.format("Znaleziono datę początku wcześniejszą od {0}", date.toString()));
      }
    }
  }

  public void clickClearFilters() {
    findClickableElement(By.xpath("//span[contains(text(),'Wyczyść filtry')]")).click();
    waitForDataIsLoaded();
  }

  public boolean markDocuments(int amountOfDocs, List<String> errors) {
    waitForPageToLoad();
    waitForDataIsLoaded();
    sleep(1000);
    List<WebElement> checkboxes = webDriver.findElements(By.xpath("//input[@type='checkbox']/..//span"));
    if (checkboxes.isEmpty()) {
      errors.add("Brak dokumentów płatności do zaznaczenia");
      return false;
    } else if (checkboxes.size() >= amountOfDocs) {
      for (int i = 0; i < amountOfDocs; i++) {
        checkboxes.get(i).click();
        sleep(1000);
      }
      return true;
    } else {
      Assert.fail("Liczba płatności na liście jest mniejsza niż w przekazanym parametrze");
      return false;
    }
  }

  public void clickPrintPaymentsListBtn() {
    findClickableElement(By.xpath("//a[contains(.,'Wydrukuj listę płatności')]")).click();
  }

  public void checkPrintOut(String sum, List<String> errors) throws Exception {
    clickPrintPaymentsListBtn();
    ArrayList<String> tabs = new ArrayList<String>(webDriver.getWindowHandles());
    webDriver.switchTo().window(tabs.get(1));
    sleep(1000);
    checkPrintBtnPresent(errors);
    checkTableToPrintPresent(errors);
    checkMoneyAmountCorrect(sum, errors);
  }

  //kwota na wydruku
  private void checkMoneyAmountCorrect(String sum, List<String> errors) throws Exception {
    if (webDriver.findElements(By.id("tablePlatnosci")).size() == 1) {
      List<String> amountsToPay = TextUtils.getTextFromWebElementsList(webDriver.findElements(By.xpath("//tr//td[8]")));
      List<Double> money = new ArrayList<>();
      for (String amountToPay : amountsToPay) {
        amountToPay = amountToPay.replaceAll("[\\szł\\u00A0]", "");
        amountToPay = amountToPay.replaceAll(",", ".");
        money.add(Double.valueOf(amountToPay));
      }

      double sumTmp = 0;
      for (double i : money) {
        sumTmp += i;
      }
      double sumTemp = Math.round(sumTmp * 100) / 100.00;
      if (sumTemp != Double.valueOf(sum)) {
        errors.add("Suma na wydruku nie zgadza się z sumą zaznaczonych płatności");
      }
    }
  }

  private void checkTableToPrintPresent(List<String> errors) {
    waitForPageToLoad();
    if (webDriver.findElements(By.id("tablePlatnosci")).size() < 1) {
      errors.add("Brak tabelki z płatnościami");
    }
  }

  private void checkPrintBtnPresent(List<String> errors) {
    if (webDriver.findElements(By.id("btnDrukuj_CD")).size() < 1) {
      errors.add("Brak przycisku 'Drukuj' na stronie wydruku płatności");
    }
  }

  public String getSumOfMarked() {
    String sum = getTextFromElement(By.xpath("//h2[.='Zaznaczone']/..//label[contains(text(),'Suma')]/span"));
    sum = sum.replaceAll("[\\szł\\u00A0]", "");
    sum = sum.replaceAll(",", ".");
    return sum;
  }

  public void clickPrintTransferForm() {
    findClickableElement(By.xpath("//a[contains(.,'Wydrukuj blankiet przelewu')]")).click();
  }

  public void checkTransferForm(String sum, String accountNumber, List<String> errors) {
    clickPrintTransferForm();
    ArrayList<String> tabs = new ArrayList<String>(webDriver.getWindowHandles());
    webDriver.switchTo().window(tabs.get(1));
    sleep(1000);
    checkPrintBtnPresent(errors);
    checkTransferFormPresent(errors);
    checkSumOnTransferForm(sum, errors);
    checkAccountNumberOnTransferForm(accountNumber, errors);
  }

  private void checkAccountNumberOnTransferForm(String accountNumber, List<String> errors) {
    List<WebElement> accNumber = webDriver.findElements(By.id("txtNrRachunkuOdbiorcy1"));

    if (!accNumber.isEmpty()) {
      String accountNumberOnForm = getTextFromElement(By.id("txtNrRachunkuOdbiorcy1"));
      accountNumberOnForm = accountNumberOnForm.replaceAll(",", ".");
      if (!Double.valueOf(accountNumberOnForm).equals(Double.valueOf(accountNumber))) {
        errors.add("Numer konta na górnym odcinku blankietu jest różny od numeru konta ze strony płatności");
      }

      accountNumberOnForm = getTextFromElement(By.id("txtNrRachunkuOdbiorcy2"));
      accountNumberOnForm = accountNumberOnForm.replaceAll(",", ".");
      if (!Double.valueOf(accountNumberOnForm).equals(Double.valueOf(accountNumber))) {
        errors.add("Numer konta na dolnym odcinku blankietu jest różny od numeru konta ze strony płatności");
      }
    } else {
      errors.add("Brak numeru konta na formularzu do przelewu");
    }
  }

  private void checkSumOnTransferForm(String sum, List<String> errors) {
    List<WebElement> sums = webDriver.findElements(By.id("txtKwota1"));
    if (!sums.isEmpty()) {
      String sumOnForm = getTextFromElement(By.id("txtKwota1"));
      sumOnForm = sumOnForm.replaceAll(",", ".");
      if (!Double.valueOf(sumOnForm).equals(Double.valueOf(sum))) {
        errors.add("Suma na górnym odcinku blankietu jest różna od sumy zaznaczonych płatnosci");
      }

      sumOnForm = getTextFromElement(By.id("txtKwota2"));
      sumOnForm = sumOnForm.replaceAll(",", ".");
      if (!Double.valueOf(sumOnForm).equals(Double.valueOf(sum))) {
        errors.add("Suma na dolnym odcinku blankietu jest różna od sumy zaznaczonych płatnosci");
      }
    } else {
      errors.add("Brak sumy przelewu na formularzu do przelewu");
    }
  }

  private void checkTransferFormPresent(List<String> errors) {
    if (webDriver.findElements(By.xpath("//img[contains(@src,'przelew')]")).size() != 2) {
      errors.add("Brak pełnego blankietu do przelewu");
    }
  }

  public void checkGoToPaymentPlatformBtn(List<String> errors) {
    if (!isElementPresent(By.xpath("//a[contains(@class,'ppercase') and contains(text(),'Przejdź do platformy płatności')]"))) {
      errors.add("Nie pokazuje się przycisk przejdź do platformy płatności");
    }
  }

}
