package pl.ninebits.qa.eurocash.site.ehurt.pages.administration;

import org.openqa.selenium.By;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtBasePage;

import java.util.List;

public class ContractorGroupPage extends EhurtBasePage {
  public ContractorGroupPage (BasePage pageObject)
  {super(pageObject);}

  public void checkContractorGroupPresent(List<String> errors){
    if (!isElementPresent(By.xpath("//table[@id='ctl00_MainContent_gvListaGrup_DXMainTable']"))) {
      errors.add("Strona z grupami kontrahentów nie została wczytana poprawnie");
    }
  }
}
