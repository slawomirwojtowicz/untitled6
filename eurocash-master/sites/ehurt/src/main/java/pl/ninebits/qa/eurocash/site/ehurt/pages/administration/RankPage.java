package pl.ninebits.qa.eurocash.site.ehurt.pages.administration;

import org.openqa.selenium.By;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtBasePage;

import java.util.List;

public class RankPage extends EhurtBasePage {
  public RankPage (BasePage pageObject)
  {super(pageObject);}

  public void checkRankPagePresent(List<String> errors){
    if (!isElementPresent(By.cssSelector(".dxtlDataTable"))) {
      errors.add("Strona z rangami nie została wczytana poprawnie");
    }
  }
}
