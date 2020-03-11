package pl.ninebits.qa.eurocash.site.vendorportal.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pl.ninebits.qa.automated.tests.core.utils.RandomUtils;
import pl.ninebits.qa.automated.tests.core.utils.TextUtils;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;
import pl.ninebits.qa.eurocash.site.vendorportal.constants.ProductStatus;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.components.SideMenu;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

public class AdminProductsPage extends VendorPortalBasePage {

  private static final String WRONG_SUPPLIER_FOUND_MSG = "Znaleziono dostawcę {0} filtrując po {1}.";

  public AdminProductsPage(BasePage pageObject) {
    super(pageObject);
  }

  public final SideMenu sideMenu = new SideMenu(createPageObject(SideMenu.class));


  public void checkProductsFilteredByStatus(ProductStatus status, List<String> errors) throws Exception {
    List<String> statuses = TextUtils.getTextFromWebElementsList(webDriver.findElements(By.cssSelector("ven-product-table-status div:nth-of-type(2)")));

    if (!statuses.isEmpty()) {
      statuses.forEach(s -> {
        if (!s.toLowerCase().contains(status.getName().toLowerCase())) {
          errors.add(MessageFormat.format("Znaleziono status produktu {0} filtrując po {1}.", s, status.getName()));
        }
      });
    } else {
      throw new Exception("Nie pobrano statusów produktów");
    }
  }

  public String getSupplier() throws Exception {
    List<String> suppliers = TextUtils.getTextFromWebElementsList(webDriver.findElements(By.cssSelector("ven-product-list-table td:nth-of-type(3)")));
    suppliers = suppliers.stream().filter(s -> s.length() > 5).collect(Collectors.toList());
    if (!suppliers.isEmpty()) {
      return suppliers.get(0);
    } else {
      throw new Exception("Nie udało się pobrać dostawców z tabeli.");
    }
  }

  public void openSuppliersMenu() {
    WebElement suppMenu = findElement(By.cssSelector("#user_group_id"));
    suppMenu.click();
    waitForElementToBeVisible(By.cssSelector("#user_group_id .select__input"));
  }

  public String getSupplierFromMenu() throws Exception {
    sleep(1000);
    List<String> suppliers = TextUtils.getTextFromWebElementsList(webDriver.findElements(By.cssSelector(".select__content-row p")).subList(0, 5));
    suppliers = suppliers.stream().filter(s -> s.length() >5).collect(Collectors.toList());
    if (suppliers.size() > 1) {
      return suppliers.get(1);
    } else {
      throw new Exception("Brak dostawców w menu Dostawca.");
    }
  }

  public void clickSupplierInMenu(String supplier) {
    WebElement supplierInMenu = findElement(By.xpath("//p[text()='" + supplier + "']"));
    supplierInMenu.click();
    waitVendorForDataIsLoaded();
  }

  public void checkProductsFilteredBySupplier(String supplier, List<String> errors) throws Exception {
    List<String> suppliers = TextUtils.getTextFromWebElementsList(webDriver.findElements(By.cssSelector("ven-product-list-table td:nth-of-type(3)")));
    int initErrSize = errors.size();
    if (!suppliers.isEmpty()) {
      for (String s : suppliers) {
        if (!s.toLowerCase().contains(supplier.toLowerCase())) {
          errors.add(MessageFormat.format(WRONG_SUPPLIER_FOUND_MSG, s, supplier));
        }
        if (errors.size() > initErrSize) break;
      }
    } else {
      throw new Exception("Nie pobrano dostawców z tabeli.");
    }
  }

  public void clearFilters() {
    WebElement clearFiltersBtn = findElement(By.cssSelector(".clear-filters__label"));
    clearFiltersBtn.click();
    waitVendorForDataIsLoaded();
  }

  public void searchForSupplierInMenuInput(String supplier) {
    WebElement suppliersMenuInput = findElement(By.cssSelector("#user_group_id input.autocomp-select"));
    suppliersMenuInput.clear();
    suppliersMenuInput.sendKeys(supplier);

    clickSupplierInMenu(supplier);
  }

  public void openMacroRegionSearchMenu() {
    WebElement macroRegionMenu = findElement(By.cssSelector("#macroregion input"));
    macroRegionMenu.click();
    waitForElementToBeVisible(By.cssSelector("#macroregion .select__content--expanded"));
  }

  public void clickMacroRegionInMenu(String macroRegion) {
    WebElement macroRegionInMenu = findElement(By.xpath("//p[text()='" + macroRegion + "']"));
    scrollToElement(macroRegionInMenu);
    macroRegionInMenu.click();
    waitVendorForDataIsLoaded();
  }

  public void checkProductsFilteredByMacroRegion(String macroRegion, List<String> errors) throws Exception {
    List<WebElement> editButtons = findElements(By.cssSelector("[ng-reflect-icon-name='edit']"), "editButtons");

    editButtons.get(RandomUtils.randomInt(0, editButtons.size())).click();
    waitVendorForDataIsLoaded();

    WebElement logisticsButton = findElement(By.cssSelector("[ng-reflect-router-link='/products/add-product/logistic']"));
    logisticsButton.click();
    waitVendorForDataIsLoaded();

    List<WebElement> macroRegionCheckbox = webDriver.findElements(By.xpath("//td[.='" + macroRegion + "']/..//input[@aria-checked='true']"));
    if (macroRegionCheckbox.isEmpty()) {
      errors.add(MessageFormat.format("Makroregion {0} nie jest zaznaczony dla badanego produktu.", macroRegion));
    }
  }

  public void searchProducts(String searchQuery) {
    WebElement searchInput = findElement(By.cssSelector("[ng-reflect-placeholder='Wyszukaj produkt markę lub pro'] input"));
    searchInput.sendKeys(searchQuery);
    clickSearchBtn();
  }

  private void clickSearchBtn() {
    WebElement searchBtn = findElement(By.cssSelector("[ng-reflect-icon-name='lupa']"));
    searchBtn.click();
    waitVendorForDataIsLoaded();
  }

  public String getEan() throws Exception {
    List<String> eanNumbers = TextUtils.getTextFromWebElementsList(webDriver.findElements(By.cssSelector("ven-product-list-table td:nth-of-type(5)")));
    if (!eanNumbers.isEmpty()) {
      return eanNumbers.get(0);
    } else {
      throw new Exception("Nie udało się pobrać numerów EAN z tabeli.");
    }
  }

  public void checkProductsFilteredByEan(String ean, List<String> errors) throws Exception {
    List<String> eans = TextUtils.getTextFromWebElementsList(webDriver.findElements(By.cssSelector("ven-product-list-table td:nth-of-type(5)")));

    if (!eans.isEmpty()) {
      eans.forEach(e -> {
        if (!e.contains(ean)) {
          errors.add(MessageFormat.format(WRONG_SUPPLIER_FOUND_MSG, e, ean));
        }
      });
    } else {
      throw new Exception("Nie pobrano numerów EAN z tabeli.");
    }
  }

  public String getProductName() throws Exception {
    List<String> productNames = TextUtils.getTextFromWebElementsList(webDriver.findElements(By.cssSelector("ven-product-list-table td:nth-of-type(7)")));
    if (!productNames.isEmpty()) {
      return productNames.get(0);
    } else {
      throw new Exception("Nie udało się pobrać nazw produktów z tabeli.");
    }
  }

  public void checkProductsFilteredByProductName(String productName, List<String> errors) throws Exception {
    List<String> productNames = TextUtils.getTextFromWebElementsList(webDriver.findElements(By.cssSelector("ven-product-list-table td:nth-of-type(7)")));
    productNames = productNames.stream().filter(p -> p.length() > 5).collect(Collectors.toList());
    if (!productNames.isEmpty()) {
      productNames.forEach(p -> {
        if (!p.toLowerCase().contains(productName.toLowerCase())) {
          errors.add(MessageFormat.format(WRONG_SUPPLIER_FOUND_MSG, p, productName));
        }
      });
    } else {
      throw new Exception("Nie pobrano numerów EAN z tabeli.");
    }
  }

  public void openStatusMenu() {
    WebElement statusMenu = findElement(By.cssSelector("#status input"));
    statusMenu.click();
    waitForElementToBeVisible(By.cssSelector("#status .select__content--expanded"));
  }

  public void clickStatusInMenu(ProductStatus status) {
    WebElement statusInMenu = findElement(By.xpath("//p[.='" + status.getName() + "']"));
    statusInMenu.click();
    waitVendorForDataIsLoaded();
  }
}
