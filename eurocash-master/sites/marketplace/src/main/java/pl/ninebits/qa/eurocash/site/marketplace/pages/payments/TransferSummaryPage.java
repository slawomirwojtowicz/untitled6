package pl.ninebits.qa.eurocash.site.marketplace.pages.payments;

import org.openqa.selenium.By;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.MarketplaceBasePage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.components.Header;

import java.util.List;

public class TransferSummaryPage extends MarketplaceBasePage {
  public TransferSummaryPage(BasePage pageObject) {
    super(pageObject);
  }

  public Header header = new Header(createPageObject(Header.class));


  public void checkSummaryPresent(List<String> errors) {
    waitForElementToBeVisibleIgnoredException(By.cssSelector(".summary__data"), 10);
    if (!isElementPresent(By.cssSelector(".summary__data"))) {
      errors.add("Brak podsumowania z danymi do przelewu");
    }

    //todo: check na dane jak już vendor będzie dodany do płatności
  }
}
