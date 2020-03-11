package pl.ninebits.qa.eurocash.site.ehurt.pages.shopping;

import org.openqa.selenium.By;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtBasePage;

import java.util.List;

public class OrderConfirmationPage extends EhurtBasePage {

  public OrderConfirmationPage(BasePage pageObject) {
    super(pageObject);
    waitForDataIsLoaded();
  }

  public void checkOrderConfirmationPageLoaded(List<String> errors) {
    if (!isElementPresent(By.cssSelector(".k-grid-table"))) {
      errors.add("Strona z podsumowaniem zamówienia nie została wczytana poprawnie");
    }
  }

}
