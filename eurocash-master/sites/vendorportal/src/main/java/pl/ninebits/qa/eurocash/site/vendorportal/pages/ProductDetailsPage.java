package pl.ninebits.qa.eurocash.site.vendorportal.pages;

import lombok.val;
import org.openqa.selenium.By;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.components.SideMenu;

import java.util.List;


public class ProductDetailsPage extends ProductBasePage {
  ProductDetailsPage(BasePage pageObject) {
    super(pageObject);
  }

  public final SideMenu sideMenu = new SideMenu(createPageObject(SideMenu.class));


  public String getProductName() throws Exception {
    val prodName = getTextFromElement(By.cssSelector(".section-title"));

    if (prodName.length() > 0) {
      return prodName;
    } else throw new Exception("Nie pobrano nazwy produktu");
  }

  public void editProductName(String newProductName, List<String> errors) throws Exception {
    checkNameEditable();
    //todo

  }

  private void checkNameEditable() throws Exception {
    if (isElementPresent(By.cssSelector("[ng-reflect-label='Nazwa produktu'] input[ng-reflect-is-disabled='true']"))) {
      throw new Exception("Nazwa produktu jest nieedytowalna");
    }
  }
}
