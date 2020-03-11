package pl.ninebits.qa.eurocash.site.ehurt.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;

import java.util.List;

public class CompetitionPapersPage extends EhurtBasePage {
  public CompetitionPapersPage(BasePage pageObject) {
    super(pageObject);
    waitForDataIsLoaded();
  }


  public void checkPaperLoaded(List<String> errors) {
    List<WebElement> newspaperTitle = webDriver.findElements(By.cssSelector(".newspaper-title"));
    if(newspaperTitle.isEmpty()){
      errors.add("Gazetka konkurencji nie załadowała się");
    }
  }

}
