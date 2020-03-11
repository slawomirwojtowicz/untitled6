package pl.ninebits.qa.eurocash.site.ehurt.pages.shopping;

import org.openqa.selenium.By;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtBasePage;

import java.util.List;

public class BudgetPage extends EhurtBasePage {
  public BudgetPage(BasePage pageObject) {
    super(pageObject);
  }

  public void checkBudgetList(List<String> errors) {
    if (!isElementPresent(By.xpath("//tr[contains(@class,'budget-table__main-row')]"))) {
      errors.add("Brak listy z budżetem klienta");
    }
  }

  public void clickDetailsBudget() {
    findClickableElement(By.xpath("(//strong[contains(@class,'link-animated')])[1]")).click();
    waitForDataIsLoaded();
    waitForElementToBeVisible(By.xpath("//tr[contains(@class,'dx-row dx-data-row dx-selection')]"));
    findVisibleElement(By.xpath("//span[@class='link-animated lcm']")).click();
  }

  public OfferPage clickBudgetPromotionsLink(List<String> errors) {
    findClickableElement(By.xpath("//a[@class='link-animated lcm' and contains(text(),'Przejdź do')]")).click();
    sleep(2000);
    waitForDataIsLoaded();
    if (!isElementPresent(By.xpath("//div[contains(@class,'filter-list-item')]/span[contains(text(),'Budżet')]"))) {
      errors.add("Brak listy z budżetem klienta");
    }
    return new OfferPage(this);
  }

}
