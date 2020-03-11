package pl.ninebits.qa.eurocash.site.ehurt.pages.administration;

import org.openqa.selenium.By;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtBasePage;

import java.util.List;

public class NewMenuPage extends EhurtBasePage {
  public NewMenuPage(BasePage pageObject)
  {super(pageObject);}

  public void checkNewMenuPagePresent(List<String> errors){
    if (!isElementPresent(By.xpath("//table[@id='ctl00_MainContent_tlListaMenu_D']"))) {
      errors.add("Strona z nowym menu nie została wczytana poprawnie");
    }
  }
}
