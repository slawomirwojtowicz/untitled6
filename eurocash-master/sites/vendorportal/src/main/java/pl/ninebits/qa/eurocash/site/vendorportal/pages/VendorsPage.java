package pl.ninebits.qa.eurocash.site.vendorportal.pages;

import com.google.common.collect.Ordering;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import pl.ninebits.qa.automated.tests.core.utils.RandomUtils;
import pl.ninebits.qa.automated.tests.core.utils.TextUtils;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;

import java.text.MessageFormat;
import java.util.List;

public class VendorsPage extends VendorPortalBasePage {

  public static final String VENDOR_NAME_SELECTOR = ".mother-table tr td:nth-of-type(2)";
  public static final String VENDOR_NIP_SELECTOR = ".mother-table tr td:nth-of-type(3)";
  public static final String VENDOR_MULTIPLIER_SELECTOR = ".mother-table tr td:nth-of-type(4)";
  public static final String VENDOR_ADD_DATE_SELECTOR = ".mother-table tr td:nth-of-type(5)";

  public VendorsPage(BasePage pageObject) {
    super(pageObject);
  }


  public String getVendorName() throws Exception {
    List<WebElement> nameElements = findElements(By.cssSelector(VENDOR_NAME_SELECTOR), "nameElements");
    return getTextFromElement(nameElements.get(RandomUtils.randomInt(0, nameElements.size())));
  }

  public String getVendorNip() throws Exception {
    List<WebElement> nipElements = findElements(By.cssSelector(VENDOR_NIP_SELECTOR), "nipElements");
    return getTextFromElement(nipElements.get(RandomUtils.randomInt(0, nipElements.size())));
  }

  public void performSearch(String searchPhrase) {
    WebElement searchInput = findElement(By.cssSelector("input[placeholder='Wyszukaj Sprzedawcę']"));
    searchInput.clear();
    searchInput.sendKeys(searchPhrase, Keys.RETURN);
    waitVendorForDataIsLoaded();
  }

  public void checkSearchResultsByName(String vendorName, List<String> errors) throws Exception {
    List<String> nameElements = TextUtils.getTextFromWebElementsList(findElements(By.cssSelector(VENDOR_NAME_SELECTOR),
      "nameElements"));

    nameElements.forEach(n -> {
      n = n.toLowerCase();
      if (!n.contains(vendorName.toLowerCase())) {
        errors.add(MessageFormat.format("Rekord {0} nie zawiera nazwy {1}.", n, vendorName));
      }
    });
  }

  public void checkSearchResultsByNip(String vendorNip, List<String> errors) throws Exception {
    List<String> nipElements = TextUtils.getTextFromWebElementsList(findElements(By.cssSelector(VENDOR_NIP_SELECTOR), "nipElements"));
    final String finalVendorNip = vendorNip.replaceAll("-", "");
    nipElements.forEach(n -> {
      if (n.contains("-")) {
        n = n.replace("-", "");
      }
      if (!n.contains(finalVendorNip)) {
        errors.add(MessageFormat.format("Rekord {0} nie zawiera NIP-u {1}.", n, finalVendorNip));
      }
    });
  }

  public void sortByName() {
    sortByColumn("nazwa");
  }

  public void sortByNip() {
    sortByColumn("nip");
  }

  public void sortByMultiplier() {
    sortByColumn("mnożnik");
  }

  public void sortByAddDate() {
    sortByColumn("data dodania");
  }

  private void sortByColumn(String columnHeader) {
    WebElement sorter = findElement(By.xpath("//span[contains(text(),'" + columnHeader + "')]"));
    sorter.click();
    waitVendorForDataIsLoaded();
  }

  public void checkDataSortedByMultiplierAscending(List<String> errors) throws Exception {
    List<String> multiplierElements = TextUtils.getTextFromWebElementsList(findElements(By.cssSelector(VENDOR_MULTIPLIER_SELECTOR),
      "multiplierElements"));

    if (!Ordering.natural().isOrdered(multiplierElements)) {
      errors.add(MessageFormat.format("Lista mnożników nie jest posortowana rosnąco\n{0}", multiplierElements));
    }
  }

  public void checkDataSortedByMultiplierDescending(List<String> errors) throws Exception {
    List<String> multiplierElements = TextUtils.getTextFromWebElementsList(findElements(By.cssSelector(VENDOR_MULTIPLIER_SELECTOR),
      "multiplierElements"));

    if (!Ordering.natural().reverse().isOrdered(multiplierElements)) {
      errors.add(MessageFormat.format("Lista mnożników nie jest posortowana malejąco\n{0}", multiplierElements));
    }
  }

  public void checkDataSortedByNameDescending(List<String> errors) throws Exception {
    List<String> multiplierElements = TextUtils.getTextFromWebElementsList(findElements(By.cssSelector(VENDOR_NAME_SELECTOR),
      "multiplierElements"));

    if (!Ordering.natural().reverse().isOrdered(multiplierElements)) {
      errors.add(MessageFormat.format("Lista mnożników nie jest posortowana rosnąco\n{0}", multiplierElements));
    }
  }

  public AddVendorPage clickAddVendorBtn() {
    WebElement addVendorBtn = findElement(By.xpath("//button/span[contains(text(),'Dodaj Sprzedawcę')]"));
    addVendorBtn.click();

    return new AddVendorPage(this);
  }
}
