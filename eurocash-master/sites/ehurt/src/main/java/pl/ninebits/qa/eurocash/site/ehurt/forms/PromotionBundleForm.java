package pl.ninebits.qa.eurocash.site.ehurt.forms;


import org.openqa.selenium.By;
import pl.ninebits.qa.automated.tests.core.utils.DateUtils;
import pl.ninebits.qa.automated.tests.core.utils.TextUtils;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PromotionBundleForm extends EhurtBaseForm {
  public PromotionBundleForm(BasePage page) {
    super(page);
  }


  public void checkPromosPresent(List<String> errors) {
    int promos = webDriver.findElements(By.xpath("//td[contains(@aria-label,'ID Promocji, Value')]")).size();
    if (promos == 0) {
      errors.add("Brak promocji w formularzu z wieloma promocjami");
    }
  }

  public void closeForm() {
    findClickableElement(By.cssSelector(".dx-closebutton")).click();
    waitForElementToBeInvisible(By.cssSelector(".dx-closebutton"));
  }

  public void checkPricesPresent(List<String> errors) {
    if (webDriver.findElements(By.xpath("//td[contains(@aria-label,'Cena netto, Value')]/div")).isEmpty()) {
      errors.add("Brak cen w popupie z promocjami");
    }
  }

  public void checkPromosActive(List<String> errors) throws Exception {
    String currentDate = DateUtils.getCurrentDate("dd.MM.yyyy");
    Date dateEnd = new SimpleDateFormat("dd.MM.yyyy").parse(currentDate);
    //dateEnd = DateUtils.getDate(dateEnd, 1);
    List<String> endDates = TextUtils.getTextFromWebElementsList(webDriver.findElements(By.xpath("//td[contains(@aria-label,'Data do, Value')]")));

    for (int i = 0; i < endDates.size(); i++) {
      Date extracted = new SimpleDateFormat("dd.MM.yyyy").parse(endDates.get(i));
      if (dateEnd.after(extracted)) {
        errors.add(MessageFormat.format("Znaleziono promocję zakończoną {0} czyli wcześniej niż {1}", extracted, dateEnd));
      }
    }
  }

  public void clickPromotionLink() {
    findClickableElement(By.xpath("//td[contains(@aria-label,'ID Promocji, Value')]//span")).click();
    waitForElementToBeVisible(By.cssSelector(".header-promo"));
    sleep(1000);
  }
}
