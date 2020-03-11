package pl.ninebits.qa.eurocash.site.ehurt.pages.shopping;

import org.openqa.selenium.By;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtBasePage;

import java.util.List;

public class EPapersPage extends EhurtBasePage {
  public EPapersPage(BasePage pageObject) {
    super(pageObject);
  }


  public void checkEPapersPageLoaded(List<String> errors) {
    if(!isElementPresent(By.cssSelector(".newspapers"))){
      errors.add("Strona z eGazetkami nie została wczytana poprawnie");
    }
  }
}
