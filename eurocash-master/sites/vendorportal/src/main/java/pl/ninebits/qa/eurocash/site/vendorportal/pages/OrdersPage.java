package pl.ninebits.qa.eurocash.site.vendorportal.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pl.ninebits.qa.automated.tests.core.utils.TextUtils;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.components.SideMenu;

import java.text.MessageFormat;
import java.util.List;

public class OrdersPage extends VendorPortalBasePage {

  public OrdersPage(BasePage pageObject) {
    super(pageObject);
  }

  public final SideMenu sideMenu = new SideMenu(createPageObject(SideMenu.class));

  public void searchOrder(String order) {
    WebElement searchInput = findElement(By.cssSelector("input[placeholder^='Wyszukaj zamówienie lub NIP']"), "searchInput");
    searchInput.click();
    searchInput.clear();
    searchInput.sendKeys(order);
    clickSearchBtn();
  }

  private void clickSearchBtn() {
    WebElement searchBtn = findElement(By.cssSelector("[ng-reflect-icon-name='lupa']"), "searchBtn");
    searchBtn.click();
    waitForPageToLoad();
  }

  public OrderDetailsPage clickEditOrder(String order) {
    WebElement clickEditBtn = findElement(By.xpath("//td[contains(@class,'ng-star-inserted') and contains(.,'" + order + "')]//..//ven-icon[@class='icon']"));
    clickEditBtn.click();
    return new OrderDetailsPage(this);
  }

  public void checkOrderPresent(String deliveryId, List<String> errors) throws Exception {
    List<WebElement> deliveryIdsElements = findElements(By.cssSelector("ven-orders-list-table tr td:nth-of-type(2)"), "deliveryIdsElements");
    List<String> deliveryIds = TextUtils.getTextFromWebElementsList(deliveryIdsElements);

    if (!deliveryIds.isEmpty()) {
      deliveryIds.forEach(d -> {
        if (!d.equals(deliveryId)) {
          errors.add(MessageFormat.format("Na liście znajduje się numer dostawy {0} gdy szukano po numerze {1}", d, deliveryId));
        }
      });
    } else {
      errors.add("Nie pobrano numerów dostaw z tabeli.");
    }
  }
}
