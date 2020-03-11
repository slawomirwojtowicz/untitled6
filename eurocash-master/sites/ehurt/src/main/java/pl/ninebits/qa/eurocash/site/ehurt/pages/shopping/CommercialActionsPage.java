package pl.ninebits.qa.eurocash.site.ehurt.pages.shopping;

import org.openqa.selenium.By;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtBasePage;

import java.util.List;

public class CommercialActionsPage extends EhurtBasePage {
  public CommercialActionsPage(BasePage pageObject) {
    super(pageObject);
  }


  public void checkCommercialActionsPageLoaded(List<String> errors) {
    if (!isElementPresent(By.xpath("//table[contains(@id,'listaakcji')]"))) {
      errors.add("Strona z akcjami handlowymi nie została wczytana poprawnie");
    }
  }

}
