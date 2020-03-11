package pl.ninebits.qa.eurocash.site.ehurt.pages.myaccount;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import pl.ninebits.qa.automated.tests.core.utils.RandomUtils;
import pl.ninebits.qa.automated.tests.core.utils.TextUtils;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtBasePage;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class DocumentDetailsPage extends EhurtBasePage {
  public DocumentDetailsPage(BasePage pageObject) {
    super(pageObject);
    waitForDataIsLoaded();
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

  public DocumentsHistoryPage clickDocsLink() {
    WebElement documentsLink = findClickableElement(By.xpath("//div[@id='breadcrumbs']//span[contains(text(),'Dokumenty')]"));
    documentsLink.click();

    return new DocumentsHistoryPage(this);
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
        indexes = getIndexes();
      }
    }
  }

  public void clickNextPageBtn(int nextPageNumber) {
    WebElement nextPageLink = webDriver.findElement(By.xpath("//kendo-pager-next-buttons/a[@title='Go to the next page']"));
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


  //TODO: wrzucić do basePage() z xpathem w parametrze lub bez
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

  public void checkDocNumberPresent(String docNumber, List<String> errors) {
    String docNumberXpath = "//div[@class='bread-strong flex-inline flex-ac']/strong";

    if (isElementPresent(By.xpath(docNumberXpath))) {
      if (!webDriver.findElement(By.xpath(docNumberXpath)).getText().contains(docNumber)) {
        errors.add(MessageFormat.format("Na stronie ze szczegółami dokumentów nagłówku brakuje numeru dokumentu {0}", docNumber));
      }
    } else {
      errors.add(MessageFormat.format("Na stronie ze szczegółami pdokumentów brak nagłówka z numerem dokumentu {0}", docNumber));
    }
  }

  public void checkNetValue(String netValue, List<String> errors) {
    String netValueOnDoc = webDriver.findElement(By.xpath("//span[contains(text(),'Wartość netto')]/span")).getText().trim();
    if (!netValueOnDoc.equals(netValue)) {
      errors.add(MessageFormat.format("W szczegółach dokumentu wyświetla się wartość netto {0} a w historii {1}", netValueOnDoc, netValue));
    }
  }

  public void checkGrossValue(String grossValue, List<String> errors) {
    String grossValueOnDoc = webDriver.findElement(By.xpath("//span[contains(text(),'Wartość brutto')]/span")).getText().trim();
    if (!grossValueOnDoc.equals(grossValue)) {
      errors.add(MessageFormat.format("W szczegółach dokumentu wyświetla się wartość brutto {0} a w historii {1}", grossValueOnDoc, grossValue));
    }
  }

}
