package pl.ninebits.qa.eurocash.site.ehurt.pages.administration;

import org.openqa.selenium.By;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtBasePage;

import java.util.List;

public class NewAccessManuPage extends EhurtBasePage {
  public NewAccessManuPage(BasePage pageObject)
  {super(pageObject);}

  public void checkNewAccessMenuPagePresent(List<String> errors){
    if (!isElementPresent(By.xpath("//table[@id='ctl00_MainContent_panelDostep_gvDostep']"))) {
      errors.add("Strona z nowym menu dostęp nie została wczytana poprawnie");
    }
  }

}
