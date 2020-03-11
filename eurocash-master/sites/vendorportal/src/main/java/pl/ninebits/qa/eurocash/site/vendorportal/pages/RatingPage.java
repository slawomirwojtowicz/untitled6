package pl.ninebits.qa.eurocash.site.vendorportal.pages;

import org.openqa.selenium.By;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;

import java.util.List;

public class RatingPage extends VendorPortalBasePage {
  public RatingPage(BasePage pageObject) {
    super(pageObject);
  }


  public void checkAdminRatingsPresent(List<String> errors) {
    if (!isElementPresent(By.cssSelector("ven-admin-rating"))) {
      errors.add("Nie wczytała się strona z opiniami");
    }
  }
}
