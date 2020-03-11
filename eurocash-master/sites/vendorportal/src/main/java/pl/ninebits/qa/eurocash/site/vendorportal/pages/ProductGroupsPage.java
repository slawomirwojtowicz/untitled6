package pl.ninebits.qa.eurocash.site.vendorportal.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import pl.ninebits.qa.automated.tests.core.utils.TextUtils;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;

import java.text.MessageFormat;
import java.util.List;

public class ProductGroupsPage extends VendorPortalBasePage {
  public ProductGroupsPage(BasePage pageObject) {
    super(pageObject);
  }

  public int getProductGroupsCount() {
    return webDriver.findElements(By.cssSelector("ven-product-groups-list tr")).size();
  }

  public String getGroupName() throws Exception {
    return getGroupNames().get(0);
  }

  private List<String> getGroupNames() throws Exception {
    return TextUtils.getTextFromWebElementsList(webDriver.findElements(By.cssSelector("ven-product-groups-list tr td:nth-of-type(3)")));
  }

  public void searchForGroup(String groupName) {
    WebElement searchInput = findElement(By.cssSelector("input[placeholder='Wyszukaj grupę lub produkt']"));
    searchInput.clear();
    searchInput.sendKeys(groupName);
    clickSearchBtn();
  }

  public void checkGroupFound(String groupName, List<String> errors) throws Exception {
    List<String> groupNames = getGroupNames();

    groupNames.forEach(name -> {
      if (!name.toLowerCase().contains(groupName.toLowerCase())) {
        errors.add(MessageFormat.format("Odnaleziono nieprawidłową grupę {0} szukając po {1}", name, groupName));
      }
    });
  }

  public void clearSearch(String inputString) {
    int backspaceCount = inputString.length();
    WebElement searchInput = findElement(By.cssSelector("input[placeholder='Wyszukaj grupę lub produkt']"));
    searchInput.click();
    for (int i = 0; i < backspaceCount; i++) {
      searchInput.sendKeys(Keys.BACK_SPACE);
    }

    clickSearchBtn();
  }

  private void clickSearchBtn() {
    WebElement searchBtn = findElement(By.cssSelector("[ng-reflect-icon-name='lupa']"));
    searchBtn.click();
    actionStream().doubleClick(searchBtn);
    waitVendorForDataIsLoaded();
  }

  public void checkAllGroupsPresent(int groupsCount, List<String> errors) {
    int currentGroupsCount = getProductGroupsCount();
    if (currentGroupsCount != groupsCount) {
      errors.add(MessageFormat.format("Oczekiwano {0} wyników a jest {1}", groupsCount, currentGroupsCount));
    }
  }

}
