package pl.ninebits.qa.eurocash.site.ehurt.pages.administration;

import org.openqa.selenium.By;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtBasePage;

import java.util.List;

public class MarketingConsentsPage extends EhurtBasePage {
  public MarketingConsentsPage(BasePage pageObject)
  {super(pageObject);}

  public void checkMarketingConsentsPresent(List<String> errors){
    if (!isElementPresent(By.xpath("//h4[contains(text(),'Lista Zgód')]"))) {
      errors.add("Strona z zgodami marketingowymi nie została wczytana poprawnie");
    }
  }
}
