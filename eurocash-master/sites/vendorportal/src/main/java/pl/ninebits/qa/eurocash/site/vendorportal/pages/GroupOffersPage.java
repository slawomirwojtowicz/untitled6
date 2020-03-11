package pl.ninebits.qa.eurocash.site.vendorportal.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;

import java.util.List;

public class GroupOffersPage extends VendorPortalBasePage {
  public GroupOffersPage(BasePage pageObject) {
    super(pageObject);
  }

  public void checkGroupOffersPagePresent(List<String> errors) {
    if (!isElementPresent(By.cssSelector("ven-group-offers-list"))) {
      errors.add("Nie wczytała się strona z ofertami");
    }

    List<WebElement> groupOffers = webDriver.findElements(By.cssSelector("ven-group-offers-list tr"));
    if (groupOffers.isEmpty()) {
      errors.add("Brak ofert w tabeli");
    }
  }

  public AddOfferPage clickCreateANewDealPage() {
    WebElement createANewDealPBtn = findElement(By.cssSelector(".filter-button.filter-item.mat-button--sharp.mat-flat-button.mat-primary  a"));
    createANewDealPBtn.click();
    return new AddOfferPage(this);
  }

}
