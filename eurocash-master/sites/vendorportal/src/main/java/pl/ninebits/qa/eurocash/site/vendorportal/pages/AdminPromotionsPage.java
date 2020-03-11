package pl.ninebits.qa.eurocash.site.vendorportal.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import pl.ninebits.qa.automated.tests.core.utils.TextUtils;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;
import pl.ninebits.qa.eurocash.site.vendorportal.constants.PromotionStatus;
import pl.ninebits.qa.eurocash.site.vendorportal.constants.PromotionType;

import java.text.MessageFormat;
import java.util.List;

public class AdminPromotionsPage extends VendorPortalBasePage {
  public AdminPromotionsPage(BasePage pageObject) {
    super(pageObject);
  }


  public void openPromotionTypeMenu() {
    WebElement promoTypeMenu = findElement(By.xpath("//label[.='TYP:']/..//input"));
    promoTypeMenu.click();
    waitForElementToBeVisible(By.cssSelector(".select__content--expanded"));
  }

  public void clickPromotionType(PromotionType promotionType) {
    WebElement promoType = findElement(By.xpath("//p[.='" + promotionType.getName() + "']"));
    promoType.click();
    waitVendorForDataIsLoaded();
  }

  public void checkResultsFilteredByPromotionType(PromotionType promotionType, List<String> errors) throws Exception {
    List<WebElement> promotionTypes = findElements(By.cssSelector("[ng-reflect-edit-link^='/promotions/add-promotion'] td:nth-of-type(5)"),
      "promotionTypes");
    List<String> promoTxts = TextUtils.getTextFromWebElementsList(promotionTypes);

    if (!promoTxts.isEmpty()) {
      promoTxts.forEach(pt -> {
        if (!pt.equals(promotionType.getName())) {
          errors.add(MessageFormat.format("W tabeli promocji obecny jest typ {0} gdy filtrowano po {1}.", pt, promotionType.getName()));
        }
      });
    } else {
      throw new Exception("Nie pobrano typów promocji z tabeli.");
    }
  }

  public void openPromotionStatusMenu() {
    WebElement statusMenu = findElement(By.xpath("//label[.='STATUS:']/..//input"));
    statusMenu.click();
    waitForElementToBeVisible(By.cssSelector(".select__content--expanded"));
  }

  public void clickStatusType(PromotionStatus status) {
    WebElement promotionStatusType = findElement(By.xpath("//p[.='" + status.getName() + "']"));
    promotionStatusType.click();
    waitVendorForDataIsLoaded();
  }

  public void checkResultsFilteredByPromotionStatus(PromotionStatus status, List<String> errors) throws Exception {
    List<WebElement> promotionStatuses = findElements(By.cssSelector("[ng-reflect-edit-link^='/promotions/add-promotion'] td:nth-of-type(9)"),
      "promotionStatuses");
    List<String> statusTexts = TextUtils.getTextFromWebElementsList(promotionStatuses);

    if (!statusTexts.isEmpty()) {
      statusTexts.forEach(ps -> {
        if (!ps.equals(status.getTableName())) {
          errors.add(MessageFormat.format("W tabeli promocji obecny jest typ {0} gdy filtrowano po {1}.", ps, status.getTableName()));
        }
      });
    } else {
      throw new Exception("Nie pobrano statusów promocji z tabeli.");
    }
  }

  public void typeSearchQuery(String query) {
    WebElement queryInput = findElement(By.cssSelector("input[placeholder='Wyszukaj promocję']"));
    queryInput.clear();
    queryInput.sendKeys(query, Keys.RETURN);
    waitVendorForDataIsLoaded();
  }

  public void checkPromotionsFilteredByName(String promoName, List<String> errors) throws Exception {
    List<WebElement> promoNames = findElements(By.cssSelector("[ng-reflect-edit-link^='/promotions/add-promotion'] td:nth-of-type(4)"),
      "promoNames");
    List<String> promoNameTexts = TextUtils.getTextFromWebElementsList(promoNames);

    if (!promoNameTexts.isEmpty()) {
      promoNameTexts.forEach(pn -> {
        if (!pn.equals(promoName)) {
          errors.add(MessageFormat.format("W tabeli promocji obecna jest nazwa promocji {0} gdy filtrowano po {1}.", pn, promoName));
        }
      });
    } else {
      throw new Exception("Nie pobrano nazw promocji z tabeli.");
    }
  }

  public void checkPromotionsFilteredByProductName(String promoProduct, List<String> errors) throws Exception {
    List<WebElement> promoProductsNames = findElements(By.cssSelector("[ng-reflect-edit-link^='/promotions/add-promotion'] td:nth-of-type(6)"),
      "promoProductsNames");
    List<String> productsTexts = TextUtils.getTextFromWebElementsList(promoProductsNames);

    if (!productsTexts.isEmpty()) {
      productsTexts.forEach(pn -> {
        if (!pn.equals(promoProduct)) {
          errors.add(MessageFormat.format("W tabeli promocji obecna jest produkt w promocji {0} gdy filtrowano po {1}.", pn, promoProduct));
        }
      });
    } else {
      throw new Exception("Nie pobrano nazw produktów w promocji z tabeli.");
    }
  }

  public void checkNoProductsFound(List<String> errors) {
    List<WebElement> tableRows = webDriver.findElements(By.cssSelector("[ng-reflect-edit-link^='/promotions/add-promotion']"));
    if (!tableRows.isEmpty()) {
      errors.add("Zostały wyszukane promocje, a nie powinny.");
    }
  }
}
