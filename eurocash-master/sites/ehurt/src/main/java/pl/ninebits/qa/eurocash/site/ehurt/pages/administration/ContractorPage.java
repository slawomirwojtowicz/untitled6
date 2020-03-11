package pl.ninebits.qa.eurocash.site.ehurt.pages.administration;

import org.openqa.selenium.By;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtBasePage;

import java.util.List;

public class ContractorPage extends EhurtBasePage {
  public ContractorPage (BasePage pageObject)
  {super(pageObject);}

  public void checkContractorPresent(List<String> errors){
    if (!isElementPresent(By.xpath("//a[contains(text(),'Lista kontrahentów')]"))) {
      errors.add("Strona z kontraktorami nie została wczytana poprawnie");
    }
  }
}
