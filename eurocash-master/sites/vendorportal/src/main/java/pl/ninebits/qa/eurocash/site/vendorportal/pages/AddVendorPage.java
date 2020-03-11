package pl.ninebits.qa.eurocash.site.vendorportal.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;

import java.util.List;

public class AddVendorPage extends VendorPortalBasePage {

  public static final String EMAIL_FIELD_LOCATOR = "[ng-reflect-name='email']";
  public static final String POSTAL_CODE_FIELD_LOCATOR = "[ng-reflect-name='postcode']";
  public static final String REGON_FIELD_LOCATOR = "[ng-reflect-name='regon']";
  public static final String EMAIL_FOR_CORRECTIONS_FIELD_LOCATOR = "[ng-reflect-label='Adres e-mail do przesłania kor']";
  public static final String ACCOUNT_FIELD_LOCATOR = "[ng-reflect-name='bankaccount_number']";
  public static final String WEBSITE_FIELD_LOCATOR = "[ng-reflect-name='website']";
  public static final String INPUT_ERROR_MESSAGE = " .input-error-message";
  public static final String INPUT_LOCATOR = " input";

  public AddVendorPage(BasePage pageObject) {
    super(pageObject);
  }


  public void clickSaveBtn() throws Exception {
    List<WebElement> saveVendorButtons = findElements(By.xpath("//span[contains(text(),'Zapisz')]"), "saveVendorButtons");
    saveVendorButtons.get(0).click();
  }

  public void checkDataRequiredValidationsPresent(List<String> errors) {
    waitVendorForDataIsLoaded();
    checkNameRequired(errors);
    checkLastNameRequired(errors);
    checkEmailRequired(errors);
    checkPhoneRequired(errors);
    checkFullNameRequired(errors);
    checkNipRequired(errors);
  }

  private void checkNameRequired(List<String> errors) {
    if (!isElementPresent(By.cssSelector("[ng-reflect-name='firstname'] .error-container"))) {
      errors.add("Brak błędu na pustym polu Imię.");
    }
  }

  private void checkLastNameRequired(List<String> errors) {
    if (!isElementPresent(By.cssSelector("[ng-reflect-name='lastname'] .error-container"))) {
      errors.add("Brak błędu na pustym polu Nazwisko.");
    }
  }

  private void checkEmailRequired(List<String> errors) {
    if (!isElementPresent(By.cssSelector(EMAIL_FIELD_LOCATOR + " .error-container"))) {
      errors.add("Brak błędu na pustym polu E-mail.");
    }
  }

  private void checkPhoneRequired(List<String> errors) {
    if (!isElementPresent(By.cssSelector("[ng-reflect-name='phone'] .error-container"))) {
      errors.add("Brak błędu na pustym polu Telefon.");
    }
  }

  private void checkFullNameRequired(List<String> errors) {
    if (!isElementPresent(By.cssSelector("[ng-reflect-name='fullname'] .error-container"))) {
      errors.add("Brak błędu na pustym polu Pełna Nazwa Firmy.");
    }
  }

  private void checkNipRequired(List<String> errors) {
    if (!isElementPresent(By.cssSelector("[ng-reflect-name='nip'] .error-container"))) {
      errors.add("Brak błędu na pustym polu NIP.");
    }
  }

  public void typeEmail(String email) {
    WebElement emailInput = findElement(By.cssSelector(EMAIL_FIELD_LOCATOR + INPUT_LOCATOR));
    emailInput.click();
    emailInput.sendKeys(email);
  }

  public void checkWrongEmailMsgPresent(List<String> errors) {
    List<WebElement> emailErrMsg = webDriver.findElements(By.cssSelector(EMAIL_FIELD_LOCATOR + INPUT_ERROR_MESSAGE));

    if (emailErrMsg.isEmpty()) {
      errors.add("Brak komunikatu błędu na polu e-mail");
    } else {
      if (!getTextFromElement(emailErrMsg.get(0)).equals("Niepoprawnie wypełniony adres e-mail")) {
        errors.add("Błędny komunikat o błędzie na niepoprawnie wypełnionym polu e-mail");
      }
    }
  }

  public void typePostalCode(String postalCode) {
    WebElement postalCodeInput = findElement(By.cssSelector(POSTAL_CODE_FIELD_LOCATOR + INPUT_LOCATOR));
    postalCodeInput.clear();
    postalCodeInput.sendKeys(postalCode);
  }

  public void checkWrongPostalCodeMsgPresent(List<String> errors) {
    List<WebElement> postalCodeErrMsg = webDriver.findElements(By.cssSelector(POSTAL_CODE_FIELD_LOCATOR + INPUT_ERROR_MESSAGE));

    if (postalCodeErrMsg.isEmpty()) {
      errors.add("Brak komunikatu błędu na polu e-mail");
    } else {
      if (!getTextFromElement(postalCodeErrMsg.get(0)).equals("Niepoprawnie wypełniony kod pocztowy")) {
        errors.add("Błędny komunikat o błędzie na niepoprawnie wypełnionym polu Kod pocztowy");
      }
    }
  }

  public void typeRegon(String regon) {
    WebElement regonInput = findElement(By.cssSelector(REGON_FIELD_LOCATOR + INPUT_LOCATOR));
    regonInput.clear();
    regonInput.sendKeys(regon);
  }

  public void checkWrongRegonMsgPresent(List<String> errors) {
    List<WebElement> regonErrMsg = webDriver.findElements(By.cssSelector(REGON_FIELD_LOCATOR + INPUT_ERROR_MESSAGE));

    if (regonErrMsg.isEmpty()) {
      errors.add("Brak komunikatu błędu na polu REGON");
    } else {
      if (!getTextFromElement(regonErrMsg.get(0)).equals("Regon powinien zawierać 9 lub 14 znaków")) {
        errors.add("Błędny komunikat o błędzie na niepoprawnie wypełnionym polu REGON");
      }
    }
  }

  public void typeEmailForInvoiceCorrections(String emailForInvoiceCorrections) {
    WebElement emailForInvoiceCorrectionsInput = findElement(By.cssSelector(EMAIL_FOR_CORRECTIONS_FIELD_LOCATOR + INPUT_LOCATOR));
    emailForInvoiceCorrectionsInput.clear();
    emailForInvoiceCorrectionsInput.sendKeys(emailForInvoiceCorrections);
  }

  public void checkWrongEmailForInvoiceCorrectionsMsgPresent(List<String> errors) {
    List<WebElement> emailErrMsg = webDriver.findElements(By.cssSelector(EMAIL_FOR_CORRECTIONS_FIELD_LOCATOR + INPUT_ERROR_MESSAGE));

    if (emailErrMsg.isEmpty()) {
      errors.add("Brak komunikatu błędu na polu e-mail do przesłania korekt FV");
    } else {
      if (!getTextFromElement(emailErrMsg.get(0)).equals("Niepoprawnie wypełniony adres e-mail")) {
        errors.add("Błędny komunikat o błędzie na niepoprawnie wypełnionym polu e-mail do przesłania korekt FV");
      }
    }
  }

  public void typeAccountNumber(String accountNumber) {
    WebElement accountInput = findElement(By.cssSelector(ACCOUNT_FIELD_LOCATOR + INPUT_LOCATOR));
    accountInput.clear();
    accountInput.sendKeys(accountNumber);
  }

  public void checkWrongAccountNumberMsgPresent(List<String> errors) {
    List<WebElement> bankAccountErrMsg = webDriver.findElements(By.cssSelector(ACCOUNT_FIELD_LOCATOR + INPUT_ERROR_MESSAGE));

    if (bankAccountErrMsg.isEmpty()) {
      errors.add("Brak komunikatu błędu na polu Numer konta");
    } else {
      if (!getTextFromElement(bankAccountErrMsg.get(0)).equals("Minimalna ilość znaków tego pola to 26")) {
        errors.add("Błędny komunikat o błędzie na niepoprawnie wypełnionym polu Numer konta");
      }
    }
  }

  public void typeWebsite(String website) {
    WebElement websiteInput = findElement(By.cssSelector(WEBSITE_FIELD_LOCATOR + INPUT_LOCATOR));
    websiteInput.clear();
    websiteInput.sendKeys(website);
  }

  public void checkWrongWebsiteMsgPresent(List<String> errors) {
    List<WebElement> websiteErrMsg = webDriver.findElements(By.cssSelector(WEBSITE_FIELD_LOCATOR + INPUT_ERROR_MESSAGE));

    if (websiteErrMsg.isEmpty()) {
      errors.add("Brak komunikatu błędu na polu Strona firmy www");
    } else {
      if (!getTextFromElement(websiteErrMsg.get(0)).equals("Niepoprawnie wypełniony adres WWW")) {
        errors.add("Błędny komunikat o błędzie na niepoprawnie wypełnionym polu Strona firmy www");
      }
    }
  }

  public void checkErrorSnackBarPresent(List<String> errors) {
    WebElement errorSnackBar = findElement(By.cssSelector("simple-snack-bar span"));
    if (!getTextFromElement(errorSnackBar).contains("Zapisanie formularza nie powiodło się")) {
      errors.add("Brak snackbara z błedem zapisu formularza");
    }
  }
}
