package pl.ninebits.qa.eurocash.site.vendorportal.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pl.ninebits.qa.automated.tests.core.utils.TextUtils;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;

import java.text.MessageFormat;
import java.util.List;

public class AdminLogisticsPage extends VendorPortalBasePage {
  public AdminLogisticsPage(BasePage pageObject) {
    super(pageObject);
  }


  public String getVendorName() throws Exception {
    List<WebElement> vendorNameBoxes = webDriver.findElements(By.cssSelector("ven-logistic-list-table tr td:nth-of-type(2)"));
    if (!vendorNameBoxes.isEmpty()) {
      return getTextFromElement(vendorNameBoxes.get(0));
    } else {
      throw new Exception("Nie pobrano nazw vendorów z tabeli Logistyka.");
    }
  }

  public void spanNameSearchInput() {
    WebElement nameSearchSelect = findElement(By.cssSelector("#user_group_id .select__input"), "nameSearchSelect");
    nameSearchSelect.click();
    waitForElementToBeVisible(By.cssSelector(".autocomp-select"));
  }

  public void typeVendorNameInSearchInput(String vendor) {
    WebElement searchInput = findElement(By.cssSelector(".autocomp-select"), "searchInput");
    searchInput.sendKeys(vendor);
    sleep(1000);
  }

  public void checkVendorsAutoCompletedInSelectMenu(String vendor, List<String> errors) throws Exception {
    List<String> vendors = TextUtils.getTextFromWebElementsList(
      findElements(By.cssSelector("[ng-reflect-label='Sprzedawca'] [ng-reflect-klass='select__content'] p"), "vendors"));

    vendors.forEach(v -> {
      if ((!v.contains(vendor)) && (!v.equals("Wszyscy"))) {
        errors.add(MessageFormat.format("Błędny vendor w autocomplete {0} vs {1}", v, vendor));
      }
    });
  }

  public void clickVendorInSelect(String vendorName) {
    WebElement vendorNameInSelectMenu = findElement(By.xpath("//p[contains(text(),'" + vendorName + "')]"));
    vendorNameInSelectMenu.click();
    waitForElementToBeInvisible(vendorNameInSelectMenu);
  }

  public void checkVendorsFound(String vendor, List<String> errors) throws Exception {
    List<String> vendorNames = TextUtils.getTextFromWebElementsList(findElements(By.cssSelector("ven-logistic-list-table tr td:nth-of-type(2) div"),
      "vendorNames"));

    vendorNames.forEach(v -> {
      if (!v.contains(vendor)) {
        errors.add(MessageFormat.format("W tabeli znaleziono vendora {0} filtrując po {1}", v, vendor));
      }
    });
  }

  public void checkVendorsNotAutoCompletedInSelectMenu(List<String> errors) throws Exception {
    List<WebElement> vendors = findElements(By.cssSelector("[ng-reflect-label='Sprzedawca'] [ng-reflect-klass='select__content'] p"), "vendors");

    if (vendors.size() != 1) {
      errors.add("Menu autocomplete zawiera więcej niż jeden element ('Wszyscy').");
    }
  }

  public int getVendorCount() {
    return webDriver.findElements(By.cssSelector("ven-logistic-list-table tr")).size();
  }

  public void checkVendorCount(int expectedVendorCount, List<String> errors) {
    waitVendorForDataIsLoaded();
    if (getVendorCount() != expectedVendorCount) {
      errors.add("Nieprawidłowa ilość vendorów w tabeli.");
    }
  }

  public void spanMacroRegionSearchMenu() {
    WebElement macroRegionSearchMenu = findElement(By.cssSelector("[label='MAKROREGION:'] input"));
    macroRegionSearchMenu.click();
    waitForElementToBeVisible(By.cssSelector(".select__content--expanded"));
  }

  public void clickMacroRegionInSearchMenu(String macroRegion) throws Exception {
    List<WebElement> macroRegionInMenu = webDriver.findElements(By.xpath("//p[contains(text(),'" + macroRegion + "')]"));
    if (!macroRegionInMenu.isEmpty()) {
      macroRegionInMenu.get(0).click();
      waitVendorForDataIsLoaded();
    } else {
      throw new Exception(MessageFormat.format("Brak makroregionu {0} w menu.", macroRegion));
    }
  }

  public void checkMacroRegions(String macroRegion, List<String> errors) throws Exception {
    List<WebElement> spanners = webDriver.findElements(By.cssSelector("[ng-reflect-icon-name='chevron-up']"));

    if (!spanners.isEmpty()) {
      for (int i = 0; i < spanners.size(); i++) {
        spanners.get(i).click();
        sleep();
        List<WebElement> searchedMacroRegion = webDriver.findElements(By.xpath("//div[.='Makroregiony']/../div[contains(text(),'" + macroRegion + "')]"));
        if (searchedMacroRegion.isEmpty()) {
          errors.add(MessageFormat.format("Znaleziono vendora nieposiadającego makroregionu {0}.", macroRegion));
        }
      }
    } else {
      throw new Exception(MessageFormat.format("Brak wyników dla makroregionu {0}.", macroRegion));
    }
  }
}
