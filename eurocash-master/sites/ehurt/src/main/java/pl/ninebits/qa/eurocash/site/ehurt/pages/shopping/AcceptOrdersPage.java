package pl.ninebits.qa.eurocash.site.ehurt.pages.shopping;

import org.openqa.selenium.By;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtBasePage;

import java.util.List;

public class AcceptOrdersPage extends EhurtBasePage {
  public AcceptOrdersPage(BasePage pageObject) {
    super(pageObject);
  }


  public void checkAcceptOrdersPagePresent(List<String> errors) {
    if (!isElementPresent(By.cssSelector("#orderConfirmationGrid"))) {
      errors.add("Strona z zamówieniami do akceptacji nie została wczytana poprawnie");
    }
  }
}
