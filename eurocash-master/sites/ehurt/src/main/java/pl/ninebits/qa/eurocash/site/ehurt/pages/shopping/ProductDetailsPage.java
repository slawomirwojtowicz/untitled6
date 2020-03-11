package pl.ninebits.qa.eurocash.site.ehurt.pages.shopping;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtBasePage;

import java.text.MessageFormat;
import java.util.List;

public class ProductDetailsPage extends EhurtBasePage {
  public ProductDetailsPage(BasePage pageObject) {
    super(pageObject);
  }


  public String getProductIndex() {
    return findVisibleElement(By.xpath("//span[text()='Indeks:']/../..//div[@class='cel-2']/span")).getText().trim();
  }

  public void checkCorrectIndexPresent(String productIndex, List<String> errors) {
    if(!getProductIndex().equals(productIndex)){
      errors.add(MessageFormat.format("W szczegółach produktu nie odnaleziono indeksu {0}", productIndex));
    }
  }

  public CartStep1Page closeProductDetails() {
    WebElement closeBtn = findClickableElement(By.cssSelector(".close"));
    closeBtn.click();
    waitForDataIsLoaded();

    return new CartStep1Page(this);
  }
}
