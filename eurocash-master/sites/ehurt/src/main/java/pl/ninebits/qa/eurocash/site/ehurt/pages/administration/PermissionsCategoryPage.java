package pl.ninebits.qa.eurocash.site.ehurt.pages.administration;

import org.openqa.selenium.By;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtBasePage;

import java.util.List;

public class PermissionsCategoryPage extends EhurtBasePage {

  public PermissionsCategoryPage(BasePage pageObject)
  {super(pageObject);}

  public void checkPemissionsCategoryPresent(List<String> errors){
    if (!isElementPresent(By.xpath("//div[@id='ctl00_MainContent_gvListaKategorii_grouppanel']"))) {
      errors.add("Strona z uprawnieniami kategori nie została wczytana poprawnie");
    }
  }
}
