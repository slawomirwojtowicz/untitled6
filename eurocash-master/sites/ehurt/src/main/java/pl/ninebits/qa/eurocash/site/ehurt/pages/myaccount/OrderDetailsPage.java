package pl.ninebits.qa.eurocash.site.ehurt.pages.myaccount;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import pl.ninebits.qa.automated.tests.core.utils.RandomUtils;
import pl.ninebits.qa.automated.tests.core.utils.TextUtils;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtBasePage;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailsPage extends EhurtBasePage {

  public OrderDetailsPage(BasePage pageObject) {
    super(pageObject);
    waitForDataIsLoaded();
    closeRODOIfPresent();
    closeTutorial();
  }


  public String getIndexNumber() {
    List<WebElement> productIndexes = webDriver.findElements(By.xpath("//kendo-grid-list//td[2]"));
    String index = "";

    if (productIndexes.size() > 0) {
      index = productIndexes.get(RandomUtils.randomInt(0, productIndexes.size())).getText().trim();
    } else {
      Assert.fail("Brak indeksów w dokumencie");
    }

    return index;
  }

  public OrdersHistoryPage clickOrdersLink() {
    scrollToTopOfPage();
    WebElement ordersLink = findClickableElement(By.xpath("//div[@id='breadcrumbs']//span[contains(text(),'Zamówienia')]"));
    ordersLink.click();

    return new OrdersHistoryPage(this);
  }


  public void checkIndexPresent(String index, List<String> errors) {
    List<String> indexes = getIndexes();
    Integer pagesNumber = getNumberOfPages();

    for (int i = 1; i <= pagesNumber; i++) {
      if (indexes.contains(index)) {
        break;
      } else if (i == pagesNumber) {
        errors.add(MessageFormat.format("Brak indeksu {0}", index));
        break;
      } else {
        clickNextPageBtn(i + 1);
        sleep(5000);
        indexes = getIndexes();
      }
    }
  }

  public void clickNextPageBtn(int nextPageNumber) {
    WebElement nextPageLink = findClickableElement(By.xpath("//kendo-pager-next-buttons/a[@title='Go to the next page']"));
    moveToElement(nextPageLink);
    ((JavascriptExecutor) webDriver).executeScript("window.scrollBy(0, 60)");
    nextPageLink.click();
    waitForElementToBeVisible(By.xpath(MessageFormat.format(
      "//kendo-pager-numeric-buttons//a[contains(@class,'selected') and contains(text(),''{0}'')]", nextPageNumber)));
    waitForDataIsLoaded();
  }

  public List<String> getIndexes() {
    List<WebElement> indexesElems = webDriver.findElements(By.xpath("//table[@class='k-grid-table']//td[2]"));
    List<String> indexes = new ArrayList<>();

    for (WebElement indexesElem : indexesElems) {
      indexes.add(indexesElem.getText().trim());
    }
    return indexes;
  }

  public Integer getNumberOfPages() {
    String numberOfPages = getTextFromElement(By.xpath("//kendo-pager//span[contains(text(),'Strona')]"));
    numberOfPages = numberOfPages.replaceAll("Strona 1 z", "");
    numberOfPages = numberOfPages.substring(0, numberOfPages.indexOf("(")).trim();

    return Integer.valueOf(numberOfPages);
  }

  public void checkSearchProductPresent(String searchProductPhrase, List<String> errors) {
    searchProductPhrase = TextUtils.stripAccents(searchProductPhrase).toUpperCase();
    Integer pagesNumber = getNumberOfPages();
    List<String> productNames = getProductNames();
    Boolean productPresent = false;

    for (int i = 1; i <= pagesNumber; i++) {
      for (String productName : productNames) {
        if (productName.contains(searchProductPhrase)) {
          productPresent = true;
          break;
        }
      }
      if (!productPresent) {
        if (i == pagesNumber) {
          errors.add(MessageFormat.format("W dokumencie nie odnalezion produktu {0}", searchProductPhrase));
          break;
        }
        clickNextPageBtn(i + 1);
        sleep(5000);
        productNames = getProductNames();
      }
    }
  }

  private List<String> getProductNames() {
    List<WebElement> productNamesCells = webDriver.findElements(By.xpath("//kendo-grid-list//td[3]/span"));
    List<String> productNames = new ArrayList<>();

    for (WebElement productNameCell : productNamesCells) {
      productNames.add(productNameCell.getText().trim());
    }
    return productNames;
  }

  public void checkOrderNumberPresent(String orderNumber, List<String> errors) {
    String orderNumberHeaderXpath = "//div[@id='breadcrumbs']/strong";
    String orderNumberTableXpath = "//td[contains(text(),'Numer zamówienia')]/..//td[@class='labelTxt']/strong";

    if (isElementPresent(By.xpath(orderNumberHeaderXpath))) {
      if (!webDriver.findElement(By.xpath(orderNumberHeaderXpath)).getText().contains(orderNumber)) {
        errors.add(MessageFormat.format("Na stronie ze szczegółami zamówienia w nagłówku brakuje numeru zamówienia {0}", orderNumber));
      }
    } else {
      errors.add(MessageFormat.format("Na stronie ze szczegółami zamówienia brak nagłówka z numerem dokumentu {0}", orderNumber));
    }

    if (isElementPresent(By.xpath(orderNumberTableXpath))) {
      if (!webDriver.findElement(By.xpath(orderNumberTableXpath)).getText().contains(orderNumber)) {
        errors.add(MessageFormat.format("W tabelce ze szczegółami zamówienia brak numeru zamówienia {0}", orderNumber));
      }
    } else {
      errors.add(MessageFormat.format("W tabelce ze szczegółami zamówienia brak numeru zamówienia {0}", orderNumber));
    }
  }

  public void checkOriginValueInHeaderPresent(String originValue, List<String> errors) {
    originValue = mapOriginValue(originValue);
    String originValueInHeaderXpath = "//div[@id='breadcrumbs']/strong";

    if (isElementPresent(By.xpath(originValueInHeaderXpath))) {
      if (!webDriver.findElement(By.xpath(originValueInHeaderXpath)).getText().contains(originValue)) {
        errors.add(MessageFormat.format("W nagłówku brak informacji o pochodzeniu {0}", originValue));
      }
    } else {
      errors.add("Brak nagłówka");
    }
  }

  public void checkOriginValueInBoxPresent(String originValue, List<String> errors) {
    originValue = mapOriginValue(originValue);
    String originValueInBoxXpath = "//td[contains(text(),'Numer zamówienia')]/..//td[@class='labelTxt']/strong";

    if (isElementPresent(By.xpath(originValueInBoxXpath))) {
      if (!webDriver.findElement(By.xpath(originValueInBoxXpath)).getText().contains(originValue)) {
        errors.add(MessageFormat.format("W boksie brak informacji o pochodzeniu {0}", originValue));
      }
    } else {
      errors.add("Brak elementu boksa z pochodzeniem zamówienia");
    }
  }

  private String mapOriginValue(String originValue) {
    switch (originValue) {
      case "Mobilne":
        originValue = "eBiz";
        break;
      case "eurocash.pl":
        originValue = "Online";
        break;
      case "PH ECD":
        originValue = "eBiz";
        break;
      case "eurocash.pl mobile":
        originValue = "eHurt Mobile";
        break;
      case "eurocash.pl pulpit":
        originValue = "Desktop";
        break;
      case "COK":
        originValue = "SAP";
        break;
      default:
        break;
    }
    return originValue;
  }

  public void checkNetValue(String netValue, List<String> errors) {
    String netValueOnOrder = getTextFromElement(By.xpath("//td[contains(text(),'Wartość zamówienia netto')]/..//span"));
    if (!netValueOnOrder.equals(netValue)) {
      errors.add(MessageFormat.format("W szczegółach zamówienia wyświetla się wartość netto {0} a w historii {1}", netValueOnOrder, netValue));
    }
  }

  public void checkGrossValue(String grossValue, List<String> errors) {
    String grossValueOnOrder = getTextFromElement(By.xpath("//td[contains(text(),'Wartość zamówienia brutto')]/..//span"));
    if (!grossValueOnOrder.equals(grossValue)) {
      errors.add(MessageFormat.format("W szczegółach zamówienia wyświetla się wartość brutto {0} a w historii {1}", grossValueOnOrder, grossValue));
    }
  }
}
