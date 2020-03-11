package pl.ninebits.qa.eurocash.site.ehurt.forms;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;

public class ForecastedPriceListsFiltersForm extends EhurtBaseForm {

  public ForecastedPriceListsFiltersForm(BasePage page) {
    super(page);
  }

  public String chooseProducer() {
    spanProducerMenu();
    return chooseFirstProducer();
  }

  private String chooseFirstProducer() {
    WebElement firstProd = findClickableElement(By.xpath("//div[contains(@class,'dx-list-item-content')]"));
    String producer = firstProd.getText();
    producer = producer.replaceAll("\"", "");
    firstProd.click();
    waitForDataIsLoaded();
    return producer;
  }

  private void spanProducerMenu() {
    findClickableElement(By.xpath("//dx-select-box[@placeholder='PRODUCENT']")).click();
  }

  public String chooseStock() {
    spanStockMenu();
    return chooseFirstStock();
  }

  private String chooseFirstStock() {
    WebElement lastStock = findClickableElement(By.xpath("(//div[contains(@class,'dx-list-item-content')])[last()]"));
    String stock = lastStock.getText();
    stock = stock.replaceAll("\"", "");
    lastStock.click();
    waitForDataIsLoaded();
    return stock;
  }

  private void spanStockMenu() {
    findClickableElement(By.xpath("//dx-select-box[@placeholder='ASORTYMENT']")).click();
  }
}
