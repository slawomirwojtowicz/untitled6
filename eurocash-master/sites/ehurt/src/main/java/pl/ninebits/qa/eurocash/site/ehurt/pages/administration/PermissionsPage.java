package pl.ninebits.qa.eurocash.site.ehurt.pages.administration;

import org.openqa.selenium.By;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtBasePage;

import java.util.List;

public class PermissionsPage extends EhurtBasePage {
  public PermissionsPage (BasePage pageObject)
  {super(pageObject);}

  public void checkPermissionsPresent(List<String> errors){
    if (!isElementPresent(By.xpath("//div[@id='ctl00_MainContent_gvListaUprawnien_grouppanel']"))) {
      errors.add("Strona z uprawnienia nie została wczytana poprawnie");
    }
  }

}
