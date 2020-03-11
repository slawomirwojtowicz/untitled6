package pl.ninebits.qa.eurocash.site.ehurt.pages.myaccount;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtBasePage;

public class DeliveryPage extends EhurtBasePage {

  public DeliveryPage(BasePage pageObject) {
    super(pageObject);
    checkPageLoaded();
  }

  private void checkPageLoaded() {
    try {
      waitForDataIsLoaded();
      waitForElementToBeVisible(By.cssSelector(".k-grid-header-wrap"));
    } catch (Exception e) {
      throw new NoSuchElementException("Strona 'Dostawy' nie została wczytana poprawnie'");
    }
  }

}
