package pl.ninebits.qa.eurocash.site.ehurt.pages.administration;

import org.openqa.selenium.By;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtBasePage;

import java.util.List;

public class CoverageOfAccountsPage extends EhurtBasePage {
  public CoverageOfAccountsPage(BasePage pageObject)
  {super(pageObject);}

  public void checkCoverageOfAccountsPagePresent(List<String> errors){
    if (!isElementPresent(By.xpath("//table[@class='dxtlDataTable']"))) {
      errors.add("Strona z edycja zasięgu rang nie została wczytana poprawnie");
    }
  }
}
