package pl.ninebits.qa.eurocash.site.ehurt.pages.shopping;

import org.openqa.selenium.By;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtBasePage;

import java.util.List;

public class TemplatesPage extends EhurtBasePage {
  public TemplatesPage(BasePage pageObject) {
    super(pageObject);
  }


  public void checkTemplatesPagePresent(List<String> errors) {
    if (!isElementPresent(By.xpath("//form[contains(@action,'szablony')]"))) {
      errors.add("Strona z szablonami nie została wczytana poprawnie");
    }
  }
}
