package pl.ninebits.qa.eurocash.site.ehurt.pages.shopping;

import org.openqa.selenium.By;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtBasePage;

import java.util.List;

public class ActionsDefinitionsPage extends EhurtBasePage {
  public ActionsDefinitionsPage(BasePage pageObject) {
    super(pageObject);
  }


  public void checkActionsDefinitionsPagePresent(List<String> errors) {
    if (!isElementPresent(By.xpath("//form[contains(@action,'DefinicjeAkcji')]"))) {
      errors.add("Strona z Definicje Akcji nie została wczytana poprawnie");
    }
  }
}
