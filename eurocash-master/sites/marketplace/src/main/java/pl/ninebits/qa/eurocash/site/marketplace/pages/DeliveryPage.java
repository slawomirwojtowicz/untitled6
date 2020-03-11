package pl.ninebits.qa.eurocash.site.marketplace.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;
import pl.ninebits.qa.eurocash.site.marketplace.forms.FilterForm;
import pl.ninebits.qa.eurocash.site.marketplace.pages.components.Footer;

import java.util.List;

public class DeliveryPage extends MarketplaceBasePage {
  public DeliveryPage(BasePage pageObject) {
    super(pageObject);
  }

  public final Footer footer = new Footer(this);

  public DeliveryDetailsPage clickDeliveryNumberBtn() {
    WebElement deliveryNumberBtn = findElement(By.xpath("(//td[@class='uni-grid-cell mt-green']//a)[1]"));
    deliveryNumberBtn.click();
    return new DeliveryDetailsPage(this);
  }

  public DeliveryDetailsPage clickDeliveryBtn(String deliveryNumber) {
    WebElement deliveryNumberBtn = findElement(By.xpath("//a[contains(@href,'" + deliveryNumber + "')]"));
    deliveryNumberBtn.click();
    return new DeliveryDetailsPage(this);
  }

  public String getDeliveryNumber() {
    return getTextFromElement(By.xpath("(//span[.='W realizacji']//..//..//..//td[@class='uni-grid-cell mt-green']//a)[1]"));
  }

  public void clickCheckboxDelivery() {
    WebElement checkboxDelivery = findElement(By.xpath("//tr[@class='uni-grid-row  ']//label"));
    checkboxDelivery.click();
    waitForElementToBeClickable(By.xpath("//div[contains(text(),'Przenieś do koszyka')]"));
  }

  public void moveToCart() {
    WebElement moveToCart = findElement(By.xpath("//div[contains(text(),'Przenieś do koszyka')]"));
    moveToCart.click();
    waitForElementToBeVisible(By.xpath("//div[@class='info-bar__content' and contains(.,'Pomyślnie')]"));
  }

  public void clickSortUp() {
    WebElement iconUp = findElement(By.xpath("//div[@class='header-title' and contains(.,' Nr dostawy ') ]//..//i[contains(@class,'no-sort--up')]"));
    iconUp.click();
    waitForDataIsLoaded();
    sleep();
    iconUp.click();
    waitForDataIsLoaded();
    sleep();
  }

  public void sendDaysBack(String daysBack) {
    WebElement daysBackBtn = findElement(By.cssSelector(".mt-input--center"));
    daysBackBtn.clear();
    daysBackBtn.sendKeys(daysBack);
    sleep();
  }

  public FilterForm clickFilterBtn() {
    WebElement filterBtn = findElement(By.cssSelector(".filter-button"));
    filterBtn.click();
    return new FilterForm(this);
  }

  public void checkDeliveryNumber(String deliveryNumber, List<String> errors) {
    if (!isElementPresent(By.xpath("//table[contains(@class,'uni-grid-table')]//a[contains(@href,'dostawy') and contains(.,'" + deliveryNumber + "')]"))) {
      errors.add("Brak numeru szukanej dostawy na liście");
    }
  }

  public void moveToRefundToAccount(String deliveryNumber) {
    WebElement refundToAccount = findElement(By.xpath("(//a[contains(@href,'dostawy/" + deliveryNumber + "')]//..//..//div[@class='uiKit-no-wrap-text'])[4]"));
    moveToElement(refundToAccount);
    refundToAccount.click();
  }

  public void checkRefundToAccount(String deliveryNumber, List<String> errors) {
    if (!isElementPresent(By.xpath("(//a[contains(@href,'dostawy/"+deliveryNumber+"')]//..//..//div[@class='uiKit-no-wrap-text']//span[contains(.,'zł')])[4]"))) {
      errors.add("Brak zwrotu na konto");
    }
  }

  public String getDeliveryId() {
    return getTextFromElement(By.cssSelector("app-delivery-grid a[href^='/moje-konto/dostawy']"));
  }
}
