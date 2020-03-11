package pl.ninebits.qa.eurocash.site.ehurt.pages.administration;

import org.openqa.selenium.By;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtBasePage;

import java.util.List;

public class UsersPage extends EhurtBasePage {
  public UsersPage (BasePage pageObject)
  {super(pageObject);}

  public void checkUsersPresent(List<String> errors){
    if (!isElementPresent(By.cssSelector("#content .dxgvControl_ehurt"))) {
      errors.add("Strona z użytkownikami nie została wczytana poprawnie");
    }
  }
}
