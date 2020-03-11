package pl.ninebits.qa.eurocash.site.ehurt.pages.administration;

import org.openqa.selenium.By;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtBasePage;

import java.util.List;

public class DynamicFiltersPage extends EhurtBasePage {
  public DynamicFiltersPage(BasePage pageObject)
  {super(pageObject);}

  public void checkDynamicPagePresent(List<String> errors){
    if (!isElementPresent(By.cssSelector(".dxrpAW"))) {
      errors.add("Strona z menu nie została wczytana poprawnie");
    }
  }
}
