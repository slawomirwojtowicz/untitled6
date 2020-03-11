package pl.ninebits.qa.eurocash.site.vendorportal.pages;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pl.ninebits.qa.automated.tests.core.utils.TextUtils;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;
import pl.ninebits.qa.eurocash.site.vendorportal.constants.DashboardBoxPeriod;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.components.SideMenu;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.List;

@Slf4j
public class VendorDashboardPage extends VendorPortalBasePage {

  public static final String SHOW_ALL_COMPLAINTS_LINK_LOC = ".box-view-more[routerlink='/complaints']";

  VendorDashboardPage(BasePage pageObject) {
    super(pageObject);
    waitForPageToLoad();
    closeCookies();
  }

  private void closeCookies() {
    try {
      waitForElementToBeVisibleIgnoredException(By.cssSelector(".cookies-close"), 1);
      if (isElementPresent(By.cssSelector(".cookies-close"))) {
        WebElement closeCookiesBtn = findElement(By.cssSelector(".cookies-close img"));
        closeCookiesBtn.click();
        waitForElementToBeInvisible(closeCookiesBtn);
      }
    } catch (Exception e) {
      log.info(MessageFormat.format("Exception while closing cookies on VendorDashboardPage\n{0}", e));
    }
  }

  public final SideMenu sideMenu = new SideMenu(createPageObject(SideMenu.class));

  public void checkUserLoggedIn(List<String> errors) {
    try {
      waitForElementToBeVisibleIgnoredException(By.xpath("//span[contains(.,'Wyloguj')]"), 10);
    } catch (Exception e) {
      errors.add("Użytkownik nie został zalogowany do portalu vendora");
    }
  }

  public VendorsPage clickVendorsIcon() {
    WebElement vendorsIconBtn = findElement(By.cssSelector("a[ng-reflect-message='Sprzedawcy']"));
    vendorsIconBtn.click();

    return new VendorsPage(this);
  }

  public void checkSalesSummaryBox(List<String> errors) {
    WebElement salesSummaryBox = findElement(By.cssSelector("[ng-reflect-header='Podsumowanie sprzedaży']"));
    if (getTextFromElement(salesSummaryBox).isEmpty()) {
      errors.add("Box 'podsumowanie sprzedaży' nie zawiera żadnych danych.");
    }
  }

  public void checkAverageCartValueBox(List<String> errors) {
    WebElement averageCartBox = findElement(By.cssSelector("[ng-reflect-header='Średnia wartość koszyka']"));
    if (getTextFromElement(averageCartBox).isEmpty()) {
      errors.add("Box 'Średnia wartość koszyka' nie zawiera żadnych danych.");
    }
  }

  public void checkActiveDealsBox(List<String> errors) {
    WebElement activeDealsBox = findElement(By.xpath("//h3[@class='box-header'][.='Aktywne deale']"));
    if (getTextFromElement(activeDealsBox).isEmpty()) {
      errors.add("Box 'Aktywne deale' nie zawiera żadnych danych.");
    }

    List<WebElement> groupOffersLink = webDriver.findElements(By.cssSelector(".box-view-more[routerlink='/group-offers']"));
    if (groupOffersLink.isEmpty()) {
      errors.add("Brak linku 'zobacz wszystkie' w boxie aktywne deale.");
    }
  }

  public void checkOpinionsBox(List<String> errors) {
    WebElement opinionsBox = findElement(By.cssSelector("ven-dashboard-rating"));
    if (getTextFromElement(opinionsBox).isEmpty()) {
      errors.add("Box 'Opinie' nie zawiera żadnych danych.");
    }

    List<WebElement> opinionsLink = webDriver.findElements(By.cssSelector(".box-view-more[routerlink='/rating']"));
    if (opinionsLink.isEmpty()) {
      errors.add("Brak linku 'zobacz wszystkie' w boxie opinie.");
    }
  }

  public void checkComplaintsBox(List<String> errors) {
    WebElement complaintsBox = findElement(By.cssSelector("ven-dashboard-complaints-table"));
    if (getTextFromElement(complaintsBox).isEmpty()) {
      errors.add("Box 'Reklamacje' nie zawiera żadnych danych.");
    }

    List<WebElement> complaintsLink = webDriver.findElements(By.cssSelector(SHOW_ALL_COMPLAINTS_LINK_LOC));
    if (complaintsLink.isEmpty()) {
      errors.add("Brak linku 'zobacz wszystkie' w boxie reklamacje.");
    }
  }

  public void checkMacroRegionsPopularityBox(List<String> errors) {
    List<WebElement> macroRegionsBoxHeader = webDriver.findElements(By.xpath("//h3[@class='box-header'][.='Popularność makroregionów']"));
    if (macroRegionsBoxHeader.isEmpty()) {
      errors.add("Brak boxa 'Popularność makroregionów'");
    }

    List<WebElement> regions = webDriver.findElements(By.cssSelector(".map-container .region"));
    if (regions.isEmpty()) {
      errors.add("Brak mapki z makroregionami");
    }
  }

  public void changeSalesSummaryPeriod(DashboardBoxPeriod dashboardBoxPeriod) {
    WebElement summarySalesInputVal = findElement(By.cssSelector("[ng-reflect-header='Podsumowanie sprzedaży'] ven-select input"));
    summarySalesInputVal.click();

    WebElement salesSummaryMenuVal =
      findElement(By.xpath("//ven-dashboard-summary[@ng-reflect-header='Podsumowanie sprzedaży']//p[text()='" + dashboardBoxPeriod.getVal() + "']"));
    salesSummaryMenuVal.click();
    sleep(1000);
  }

  public BigDecimal getSalesSummaryVal() {
    WebElement saleSummaryVal = findElement(By.cssSelector("[ng-reflect-header='Podsumowanie sprzedaży'] p:nth-of-type(2)"));
    return new BigDecimal(TextUtils.priceToStringReadyToBeDoubleFormat(getTextFromElement(saleSummaryVal)));
  }

  public void checkSummarySalesValChanged(BigDecimal salesSummaryVal, List<String> errors) {
    BigDecimal actualSalesSummaryVal = getSalesSummaryVal();

    if (actualSalesSummaryVal.compareTo(salesSummaryVal) == 0) {
      errors.add("Wartość podsumowania sprzedaży nie zmieniła się");
    }
  }

  public void checkNewOrdersBox(List<String> errors) {
    List<WebElement> newOrdersBoxHeader = webDriver.findElements(By.xpath("//h3[@class='box-header'][.='Nowe zamówienia']"));
    if (newOrdersBoxHeader.isEmpty()) {
      errors.add("Brak boxa 'Nowe zamówienia'");
    }

    List<WebElement> newOrders = webDriver.findElements(By.cssSelector("ven-dashboard-orders-table table tr"));
    if (newOrders.isEmpty()) {
      errors.add("Brak zamówień w boxie Nowe zamówienia");
    }
  }

  public OrderDetailsPage clickOrderInNewOrdersBox() throws Exception {
    List<WebElement> newOrders = webDriver.findElements(By.cssSelector("ven-dashboard-orders-table table tr"));
    if (!newOrders.isEmpty()) {
      newOrders.get(0).click();
      waitVendorForDataIsLoaded();
    } else {
      throw new Exception("Box Nowe zamówienia nie posiada elementów z zamówieniami.");
    }
    return new OrderDetailsPage(this);
  }

  public DealDataPage clickDealInActiveDealsBox() throws Exception {
    List<WebElement> activeDeals = webDriver.findElements(By.cssSelector("ven-dashboard-deals-table td"));
    if (!activeDeals.isEmpty()) {
      activeDeals.get(0).click();
      waitVendorForDataIsLoaded();
    } else {
      throw new Exception("Box z aktywnymi dealami nie posiada elementów z dealami.");
    }

    return new DealDataPage(this);
  }

  public GroupOffersPage clickShowAllDeals() throws Exception {
    List<WebElement> groupOffersLink = webDriver.findElements(By.cssSelector(".box-view-more[routerlink='/group-offers']"));
    if (!groupOffersLink.isEmpty()) {
      groupOffersLink.get(0).click();
    } else {
      throw new Exception("Brak linku 'Zobacz wszystkie' w boxie Aktywne deale");
    }

    return new GroupOffersPage(this);
  }

  public RatingPage clickShowOpinionsDetails() throws Exception {
    List<WebElement> opinionsLink = webDriver.findElements(By.cssSelector(".box-view-more[routerlink='/rating']"));
    if (!opinionsLink.isEmpty()) {
      opinionsLink.get(0).click();
    } else {
      throw new Exception("Brak linku 'Zobacz wszystkie' na opiniach");
    }

    return new RatingPage(this);
  }

  public BigDecimal getAverageCartVal() {
    WebElement averageCartVal = findElement(By.cssSelector("[ng-reflect-header='Średnia wartość koszyka'] p:nth-of-type(2)"));
    return new BigDecimal(TextUtils.priceToStringReadyToBeDoubleFormat(getTextFromElement(averageCartVal)));
  }

  public void changeAverageCartValuePeriod(DashboardBoxPeriod dashboardBoxPeriod) {
    WebElement averageCartValInputVal = findElement(By.cssSelector("[ng-reflect-header='Średnia wartość koszyka'] ven-select input"));
    averageCartValInputVal.click();

    WebElement averageCartValMenuVal =
      findElement(By.xpath("//ven-dashboard-summary[@ng-reflect-header='Średnia wartość koszyka']//p[text()='" + dashboardBoxPeriod.getVal() + "']"));
    averageCartValMenuVal.click();
    sleep(1000);
  }

  public void checkAverageCartValChanged(BigDecimal averageCartVal, List<String> errors) {
    BigDecimal currentAverageCartVal = getAverageCartVal();
    if (currentAverageCartVal.compareTo(averageCartVal) == 0) {
      errors.add("Srednia wartość koszyka nie zmieniła się.");
    }
  }

  public EditComplaintPage clickComplaintInComplaintsBox() throws Exception {
    List<WebElement> complaints = webDriver.findElements(By.cssSelector("ven-dashboard-complaints-table tbody tr"));
    if (!complaints.isEmpty()) {
      complaints.get(0).click();
      waitVendorForDataIsLoaded();
    } else {
      throw new Exception("Box z reklamacjami nie posiada reklamacji.");
    }

    return new EditComplaintPage(this);
  }

  public String getComplaintNumberFromComplaintsBox() throws Exception {
    List<WebElement> complaints = webDriver.findElements(By.cssSelector("ven-dashboard-complaints-table tbody tr td:nth-of-type(1)"));
    if (!complaints.isEmpty()) {
      return getTextFromElement(complaints.get(0));
    } else {
      throw new Exception("Brak numerów reklamacji.");
    }
  }

  public ComplaintsPage clickShowAllComplaints() throws Exception {
    List<WebElement> complaintsLink = webDriver.findElements(By.cssSelector(SHOW_ALL_COMPLAINTS_LINK_LOC));
    if (!complaintsLink.isEmpty()) {
      complaintsLink.get(0).click();
      return new ComplaintsPage(this);
    } else {
      throw new Exception("Brak przycisku 'Zobacz wszystkie' w boxie z reklamacjami.");
    }
  }

}
