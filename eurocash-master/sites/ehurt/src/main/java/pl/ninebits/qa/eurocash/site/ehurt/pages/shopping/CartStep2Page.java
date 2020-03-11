package pl.ninebits.qa.eurocash.site.ehurt.pages.shopping;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtBasePage;

import java.util.List;

public class CartStep2Page extends EhurtBasePage {

  public CartStep2Page(BasePage pageObject) {
    super(pageObject);
    waitForDataIsLoaded();
  }

  public void checkCartStep2PageLoaded(List<String> errors) {
    if (!isElementPresent(By.cssSelector(".k-master-row"))) {
      errors.add("Druga strona z koszykiem nie została wczytana poprawnie");
    }
  }

  public OrderConfirmationPage clickAcceptOrderBtn() {
    WebElement acceptOrderBtn = findElement(By.cssSelector(".confirm-wrapper__button--accept"));
    acceptOrderBtn.click();
    return new OrderConfirmationPage(this);
  }

}
