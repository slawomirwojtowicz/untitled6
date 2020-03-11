package pl.ninebits.qa.eurocash.site.ehurt.pages.administration;

import org.openqa.selenium.By;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtBasePage;

import java.util.List;

public class MessagesPage extends EhurtBasePage {

  public MessagesPage (BasePage pageObject)
  {super(pageObject);}

  public void checkMessagesPresent(List<String> errors){
    if (!isElementPresent(By.xpath("//div[@id='ctl00_MainContent_divLista']"))) {
      errors.add("Strona z przekazami nie została wczytana poprawnie");
    }
  }
}
