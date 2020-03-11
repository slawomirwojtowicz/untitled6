package pl.ninebits.qa.eurocash.site.ehurt.pages.administration;

import org.openqa.selenium.By;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtBasePage;

import java.util.List;

public class UsersGroupPage extends EhurtBasePage {
  public UsersGroupPage (BasePage pageObject)
  {super(pageObject);}

  public void checkUsersGroupPresent(List<String> errors){
    if (!isElementPresent(By.xpath("//table[@id='ctl00_MainContent_gvListaGrup_DXMainTable']"))) {
      errors.add("Strona z grupami użytkowników nie została wczytana poprawnie");
    }
  }
}
