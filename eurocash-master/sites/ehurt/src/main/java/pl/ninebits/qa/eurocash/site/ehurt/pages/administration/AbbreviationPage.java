package pl.ninebits.qa.eurocash.site.ehurt.pages.administration;

import org.openqa.selenium.By;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtBasePage;

import java.util.List;

public class AbbreviationPage extends EhurtBasePage {

  public AbbreviationPage(BasePage pageObject)
  {super(pageObject);}

  public void checkAbbreviationPresent(List<String> errors){
    if (!isElementPresent(By.xpath("//table[@id='ctl00_MainContent_panelImport']"))) {
      errors.add("Strona z skrótami nie została wczytana poprawnie");
    }
  }

}
