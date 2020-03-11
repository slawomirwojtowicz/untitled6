package pl.ninebits.qa.eurocash.site.ehurt.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;

import java.util.List;

public class EroundCompetitionPage extends EhurtBasePage {
  public EroundCompetitionPage(BasePage pageObject) {
    super(pageObject);
    waitForDataIsLoaded();
  }

  public void checkCompetitionTabActive(List<String> errors) {
    sleep(2000);
    if (!isElementPresent(By.xpath("//div[contains(@class,'dx-tab-selected')]//span[.='Konkurencja']"))) {
      errors.add("Tab Konkurencja nie jest domyślnie aktywny");
    }
  }

  public void checkShopListPresent(List<String> errors) {
    List<WebElement> comBoxes = webDriver.findElements(By.xpath("//div[contains(@class,'shop-list')]//div[@class='competitor-box']"));
    if (comBoxes.isEmpty()) {
      errors.add("Brak listy sklepów konkurencji");
    }
  }

  public CompetitionPapersPage clickShowPaper(List<String> errors) {
    List<WebElement> comBoxes = webDriver.findElements(By.xpath("//div[contains(@class,'shop-list')]//div[@class='competitor-box']//div[@class='leaflet-link']"));
    if (!comBoxes.isEmpty()) {
      comBoxes.get(0).click();
    } else {
      errors.add("Brak linków do gazetek konkurencji");
    }
    return new CompetitionPapersPage(this);
  }
}
