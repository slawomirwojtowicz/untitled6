package pl.ninebits.qa.eurocash.site.ehurt.pages.myaccount;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtBasePage;

import java.util.List;

public class PackagesPage extends EhurtBasePage {
  public PackagesPage(BasePage pageObject) {
    super(pageObject);
  }


  public String getPackagesSumValue() {
    return findVisibleElement(By.xpath("//div[@class='packages-info__total-info']/div[1]"))
      .getText().replaceAll("[\\s\\u00A0zł]", "").replaceAll(",", ".");
  }

  public void openAllSectionsInGrid() {
    List<WebElement> openeer = webDriver.findElements(By.cssSelector(".dx-datagrid-group-closed"));

    while (!openeer.isEmpty()) {
      openeer.get(0).click();
      waitForDataIsLoaded();
      openeer = webDriver.findElements(By.cssSelector(".dx-datagrid-group-closed"));
    }
  }

  public void checkSumsFromSectionsEqualTotalSum(String packagesSumValue, List<String> errors) {
    double totalSumFromSections = getTotalSumFromSections();
    if (totalSumFromSections != Double.valueOf(packagesSumValue)) {
      errors.add("Suma wartości opakowań w nagłówku nie zgadza się z sumą poszczególnych sekcji");
    }
  }

  private double getTotalSumFromSections() {
    List<WebElement> sumsTxt = webDriver.findElements(By.xpath("//td[contains(@aria-label, 'Watość netto, Value')]/div"));
    return sumValuesFromGrid(sumsTxt);
  }

  private double sumValuesFromGrid(List<WebElement> elements) {
    double sum = 0;
    for (WebElement sumTxt : elements) {
      String tmp = sumTxt.getText().replaceAll("[A-Zzł]", "");
      tmp = tmp.replaceAll("\\s", "");
      tmp = tmp.replaceAll(",", ".");

      sum = sum + Double.valueOf(tmp);
    }

    return Math.round(sum * 100) / 100.00;
  }

  public void checkProductsInSectionsPresent(List<String> errors) {
    if (webDriver.findElements(By.xpath("//td[contains(@aria-label, 'Rodzaj opakowania, Value')]")).size() < 1) {
      errors.add("Brak produktów powiązanych z opakowaniami");
    }
  }

  public void checkSumsFromSectionElements(String packagesSumValue, List<String> errors) {
    List<WebElement> textValues = webDriver.findElements(By.xpath("//td[contains(@aria-label,'Watość netto, Value')]//div[not(contains(@class,'summary'))]"));
    double sum = sumValuesFromGrid(textValues);
    if (sum != Double.valueOf(packagesSumValue)) {
      errors.add("Suma poszczególnych elementów opakowań jest inna niż suma w nagłówku");
    }
  }
}
