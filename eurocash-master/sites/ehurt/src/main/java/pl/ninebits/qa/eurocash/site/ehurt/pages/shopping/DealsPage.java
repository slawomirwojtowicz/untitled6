package pl.ninebits.qa.eurocash.site.ehurt.pages.shopping;

import org.openqa.selenium.By;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtBasePage;

import java.util.List;

public class DealsPage extends EhurtBasePage {
  public DealsPage(BasePage pageObject) {
    super(pageObject);
  }


  public void checkDealsPagePresent(List<String> errors) {
    if (!isElementPresent(By.cssSelector("ech-deals"))) {
      errors.add("Strona z dealami nie została wczytana poprawnie");
    }
  }
}
