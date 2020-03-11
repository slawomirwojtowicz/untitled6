package pl.ninebits.qa.eurocash.site.vendorportal.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;
import pl.ninebits.qa.eurocash.site.vendorportal.constants.Product;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.components.SideMenu;

import java.text.MessageFormat;
import java.util.List;

public class ProductsPage extends VendorPortalBasePage {

  public ProductsPage(BasePage pageObject) {
    super(pageObject);
  }

  public final SideMenu sideMenu = new SideMenu(createPageObject(SideMenu.class));

  public void searchProduct(Product product) {
    WebElement searchInput = findElement(By.cssSelector("input[placeholder^='Wyszukaj produkt']"), "searchInput");
    searchInput.click();
    searchInput.clear();
    searchInput.sendKeys(product.getEAN());
    clickSearchBtn();
  }

  public void searchProduct(String query) {
    WebElement searchInput = findElement(By.cssSelector("input[placeholder^='Wyszukaj produkt']"), "searchInput");
    searchInput.click();
    searchInput.clear();
    searchInput.sendKeys(query);
    clickSearchBtn();
  }

  private void clickSearchBtn() {
    WebElement searchBtn = findElement(By.cssSelector("[ng-reflect-icon-name='lupa']"), "searchBtn");
    searchBtn.click();
    waitForPageToLoad();
  }

  public void checkProductPresent(String ean) {
    try {
      waitForElementToBeVisible(By.xpath("//td[contains(text(),'" + ean + "')]"), 10);
    } catch (Exception e) {
      throw new NoSuchElementException(MessageFormat.format("Brak wyszukanego produktu o ean = {0}", ean));
    }
  }

  public ProductDetailsPage clickEditProduct(Product product) {
    WebElement editProductIcon = findElement(By.xpath("//td[contains(text(),'" + product.getEAN() + "')]/..//ven-icon[@ng-reflect-icon-name='edit']"), "editProductIcon");
    editProductIcon.click();
    waitForPageToLoad();

    return new ProductDetailsPage(this);
  }

  public void activateProduct(List<String> errors) {
    List<WebElement> selectAllCheckbox = webDriver.findElements(By.cssSelector(".mat-checkbox"));
    if (!selectAllCheckbox.isEmpty()) {
      selectAllCheckbox.get(0).click();
      waitVendorForDataIsLoaded();

      WebElement statusesMenu = findElement(By.cssSelector(".status-changer-select"));
      statusesMenu.click();
      waitVendorForDataIsLoaded();

      WebElement activateLink = findElement(By.xpath("//div[contains(@class,'select__content--expanded')]//p[.='Aktywny']"));
      activateLink.click();
      waitVendorForDataIsLoaded();

      WebElement acceptBtn = findElement(By.xpath("//ven-popup//span[contains(text(),'Akceptuj')]"));
      acceptBtn.click();
      waitVendorForDataIsLoaded();
      waitForElementToBeInvisible(By.cssSelector("ven-popup"));

    } else {
      errors.add("Nie odnaleziono elementu 'zaznacz wszystkie' w widoku Listy produktów w portalu vendora");
    }
  }

  public void checkProductActive(List<String> errors) {
    List<WebElement> activeStatus = webDriver.findElements(By.xpath("//div[contains(text(),'Aktywny')]"));
    if (activeStatus.isEmpty()) {
      errors.add("Produkt nie został ponownie aktywowany");
    }
  }

  public List<String> getActiveProducts() {


    return null;
  }
}
