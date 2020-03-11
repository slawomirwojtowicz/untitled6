package pl.ninebits.qa.eurocash.site.marketplace.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.components.Footer;

public class MyAccountPage extends MarketplaceBasePage {
  public MyAccountPage(BasePage pageObject) {
    super(pageObject);
  }

  public final Footer footer = new Footer(this);

  public DeliveryPage clickDeliverPage() {
    reloadPage();
    WebElement deliveryPageBtn = findElement(By.linkText("Dostawy"));
    deliveryPageBtn.click();
    return new DeliveryPage(this);
  }

  public InvoicePage clickInvoicePage() {
    reloadPage();
    WebElement invoicePageBtn = findElement(By.linkText("Faktury"));
    invoicePageBtn.click();
    return new InvoicePage(this);
  }

  public ComplaintPage clickComplaintBtn() {
    reloadPage();
    WebElement complaintPage = findElement(By.linkText("Reklamacje"));
    complaintPage.click();
    return new ComplaintPage(this);
  }

}
