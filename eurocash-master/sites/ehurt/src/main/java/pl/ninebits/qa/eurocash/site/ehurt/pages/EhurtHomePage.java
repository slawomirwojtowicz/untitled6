package pl.ninebits.qa.eurocash.site.ehurt.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import pl.ninebits.qa.automated.tests.core.utils.HttpsUtils;
import pl.ninebits.qa.automated.tests.core.utils.TextUtils;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;
import pl.ninebits.qa.eurocash.site.ehurt.constants.Contractor;
import pl.ninebits.qa.eurocash.site.ehurt.pages.myaccount.ComplaintsPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.myaccount.DocumentsHistoryPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.myaccount.OrdersHistoryPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.myaccount.PackagesPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.OfferPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.PromotionsPage;

import java.text.MessageFormat;
import java.util.List;

public class EhurtHomePage extends EhurtBasePage {

  public EhurtHomePage(WebDriver webDriver, int actionTimeout) {
    super(webDriver, actionTimeout);
  }

  public EhurtHomePage(BasePage basePage) {
    super(basePage);
    checkNewGuiPresent();
    closeAdBannerIfPresent();
    closeRODOIfPresent();
    closeOfferIfPresent();
    closeInfoIfPresent();
    closeNotifications();
    closeDocumentIfPresent();
    if (!getCurrentUrl().contains("https://eurocash.pl/")) {
      ensureProperContractorChosen(Contractor.DOMWESELNY.getContractorId());
    }
  }

  private void checkNewGuiPresent() {
    if (getCurrentUrl().contains("preprod")) {
      waitForPageToLoad();
      waitForDataIsLoaded();
      if (!isElementPresent(By.xpath("//ech-dashboard"))) {
        Assert.fail("Wczytane zostało stare gui");
      }
    }
  }

/*  private void closeRODOIfPresent() {
    sleep(1000);
    if (isElementPresent(By.cssSelector(".close .pointer"))) {
      WebElement closePopupBtn = webDriver.findElement(By.cssSelector(".close .pointer"));
      closePopupBtn.click();
      waitForElementToBeInvisible(By.cssSelector(".introjs-overlay"));
    }
  }*/

  private void closeInfoIfPresent() {
    waitForDataIsLoaded();
    waitForPageToLoad();
    sleep();
    List<WebElement> closeInfo = webDriver.findElements(By.cssSelector(".snackbar-close-x"));
    while (closeInfo.size() > 0) {
      closeInfo.get(0).click();
      sleep(1000);
      closeInfo = webDriver.findElements(By.cssSelector(".snackbar-close-x"));
    }
  }

  private void closeOfferIfPresent() {
    List<WebElement> closeOffer = webDriver.findElements(By.xpath("//div[contains(@id,'btnOfertaTargowaNie')]/span"));
    while (closeOffer.size() > 0) {
      closeOffer.get(0).click();
      sleep(1000);
      closeOffer = webDriver.findElements(By.xpath("//div[contains(@id,'btnOfertaTargowaNie')]/span"));
    }
  }

  private void closeDocumentIfPresent() {
    List<WebElement> closeDoc = webDriver.findElements(By.xpath("//span[@class='cursor-pointer']"));
    while (closeDoc.size() > 0) {
      closeDoc.get(0).click();
      sleep(1000);
      closeDoc = webDriver.findElements(By.xpath("//span[@class='cursor-pointer']"));
    }
  }


  private void closeAdBannerIfPresent() {
    List<WebElement> closeBanner = webDriver.findElements(By.xpath("//tr[contains(@id,'Promocja')]//a[contains(@class,'powrotoferta')]"));
    while (closeBanner.size() > 0) {
      closeBanner.get(0).click();
      sleep(1000);
      closeBanner = webDriver.findElements(By.xpath("//tr[contains(@id,'Promocja')]//a[contains(@class,'powrotoferta')]"));
    }
  }

  public DocumentsHistoryPage openDocsHistoryPage() {
    String url = getCurrentUrl();
    url = url.replace("ang/dashboard", "ang/zakupy/dokumentyHistoria");
    webDriver.navigate().to(url);
    closeTutorial();
    closeRODOIfPresent();
    return new DocumentsHistoryPage(this);
  }

  public OrdersHistoryPage openOrdersHistoryPage() {
    String url = getCurrentUrl();
    url = url.replace("ang/dashboard", "ang/zakupy/zamowieniaHistoria");
    webDriver.navigate().to(url);
    closeRODOIfPresent();
    return new OrdersHistoryPage(this);
  }

  public void checkMyEurocashBoxPresent(List<String> errors) {
    if (!isElementPresent(By.xpath("//ech-box-item[contains(@htmltitle,'Wiem Więcej')]"))) {
      errors.add("Brak boxa 'Wiem Więcej' na dashboardzie");
    }
  }

  public void checkMyEurocashBoxNotEmpty(List<String> errors) {
    scrollToBottomOfPage();
    List<WebElement> elements = webDriver.findElements(By.xpath("//ech-box-item[contains(@htmltitle,'Wiem Więcej')]//div[@class='article']"));
    if (elements.isEmpty()) {
      errors.add("Brak linków do moj.eurocash.pl w boxie");
    }
  }

  public EroundCompetitionPage clickShowCompetitionsPapers(List<String> errors) {
    List<WebElement> elements = webDriver.findElements(By.xpath("//a[contains(@class,'map-btn') and @href='/ang/eround?tab=konkurencja']"));
    if (!elements.isEmpty()) {
      webDriver.navigate().to(elements.get(0).getAttribute("href"));
      return new EroundCompetitionPage(this);
    } else {
      errors.add("Brak linku do gazetek konkurencji");
      return null;
    }
  }

  public void checkCheaperInEhurtBoxPresent(List<String> errors) {
    scrollToBottomOfPage();
    List<WebElement> elements = webDriver.findElements(By.id("cheaper-in-ehurt-dashboard"));
    if (elements.isEmpty()) {
      errors.add("Brak boxa Taniej w Ehurt");
    }
  }

  public void checkAlertsInYourInboxPresent(List<String> errors) {
    if (!isElementPresent(By.xpath("//h4[contains(text(),'Twoja skrzynka odbiorcza')]"))) {
      errors.add("Brak boxa 'Twoja skrzynka odbiorcza' na dashboardzie");
    }
  }

  public void checkAlertsInYourInboxNotEmpty(List<String> errors) {
    scrollToBottomOfPage();
    sleep();
    List<WebElement> elements = webDriver.findElements(By.xpath("//span[@class='mail__date']"));
    if (elements.isEmpty()) {
      errors.add("Brak alertów w skrzynce odbiorczej");
    }
  }

  public boolean isCartNotEmpty() {
    String cartNetValue = findVisibleElement(
      By.xpath("//div[@class='cart__tab']//div[@class='value-line'][1]/span")).getText().replaceAll("[\\s\\u00A0zł]", "");
    cartNetValue = cartNetValue.replace(",", ".");
    return Double.valueOf(cartNetValue) > 0.0;
  }

  public String getProductNameFromInPromoTile() {
    return findVisibleElement(
      By.xpath("//ech-dashboard-current-promo//div[contains(@class,'swiper-slide-active')]//span[@openproductdetails]")).getAttribute("title");
  }

  public void enterAmountInPromoTile(String packageAmount) {
    WebElement input = findVisibleElement(
      By.xpath("//ech-dashboard-current-promo//div[contains(@class,'swiper-slide-active')]//div[@class='product-input']//input"));
    input.click();
    input.sendKeys(packageAmount);
    input.sendKeys(Keys.RETURN);
    sleep(1000);
  }

  public PromotionsPage openPromotionsPage() {
    String url = getCurrentUrl();
    url = url.replace("ang/dashboard", "ang/promocje/lista");
    webDriver.navigate().to(url);

    return new PromotionsPage(this);
  }

  public ComplaintsPage openComplaintsPage() {
    String url = getCurrentUrl();
    url = url.replace("ang/dashboard", "ang/reklamacje");
    webDriver.navigate().to(url);

    return new ComplaintsPage(this);
  }

  public boolean checkMyOfficeBoxPresent(List<String> errors) {
    waitForDataIsLoaded();
    if (isElementPresent(By.xpath("//ech-dashboard-my-office"))) {
      return true;
    } else {
      errors.add("Brak boxa 'Moje biuro na dashboardzie'");
      return false;
    }
  }

  public void clickMyOfficePackagesTab() {
    WebElement officePackagesTab = findClickableElement(By.xpath("//ech-dashboard-my-office//span[contains(text(),'Opakowania')]"));
    moveToElement(officePackagesTab);
    scrollToElement(officePackagesTab);
    scrollDownFooter();
    officePackagesTab.click();
    waitForElementToBeVisible(By.cssSelector(".packages-info__total-info"));
  }

  public void checkPackagesSumInBox(String packagesSumValue, List<String> errors) {
    String extractedPackagesValue = findVisibleElement(By.xpath("//div[@class='packages-info__total-info']/div[@class='value']"))
      .getText().replaceAll("[\\s\\u00A0zł]", "").replaceAll(",", ".");
    if (!extractedPackagesValue.equals(packagesSumValue)) {
      errors.add("Nieprawidłowa wartość wartości opakowań w boxie");
    }
  }

  public PackagesPage clickMyOfficePackageDetails() {
    WebElement showDetailsBtn = findVisibleElement(By.xpath("//ech-dashboard-my-office//a[@href='/ang/opakowania' and contains(.,'Zobacz szczegóły')]"));
    moveToElement(showDetailsBtn);
    scrollDownByWebElementHeight(showDetailsBtn);
    showDetailsBtn.click();
    return new PackagesPage(this);
  }

  public void checkSpeciallyForYouBoxPresent(List<String> errors) {
    if (!isElementPresent(By.xpath("//ech-dashboard-my-offer[@id='my-offer-dashboard']//a[@class='actionBtn lcm']"))) {
      errors.add("Brak boxa 'Specjalnie dla Ciebie' na dashboardzie");
    }
  }

  public OfferPage clickSpeciallyForYouBox() {
    WebElement showDetailsBtn = findVisibleElement(By.xpath("//ech-dashboard-my-offer[@id='my-offer-dashboard']//a[@class='actionBtn lcm']"));
    moveToElement(showDetailsBtn);
    showDetailsBtn.click();
    return new OfferPage(this);
  }


  private void scrollDownFooter() {
    scrollDown(webDriver.findElement(By.cssSelector(".left-side")).getSize().height + 50);
    sleep();
  }

  public void checkAllHeaderLinks(List<String> errors) {
    List<WebElement> links = webDriver.findElements(By.xpath("//header-link//a[@href]"));
    checkUrlList(links, errors);
  }

  public void checkAllDashboardLinks(List<String> errors) {
    List<WebElement> links = webDriver.findElements(By.xpath("//ech-dashboard//a[not(contains(@href,'mailto'))]"));
    checkUrlList(links, errors);
    checkUrlsWithGet(links, errors);
  }

  private void checkUrlsWithGet(List<WebElement> links, List<String> errors) {
    List<String> urls = TextUtils.getAttributesFromWebElementsList(links, "href");
    urls.forEach(url -> {
      webDriver.get(url);
      waitForPageToLoad();
      if (isElementPresent(By.cssSelector("ech-page-not-found"))) {
        errors.add(MessageFormat.format("Pod adresem {0} wyświetla się 404", url));
      }
    });
  }

  private void checkUrlList(List<WebElement> links, List<String> errors) {
    List<String> hrefs = TextUtils.getAttributesFromWebElementsList(links, "href");
    hrefs.forEach(System.out::println);
    System.out.println(MessageFormat.format("Ilość linków do sprawdzenia: {0}", links.size()));
    if (!hrefs.isEmpty()) {
      hrefs.forEach(href -> HttpsUtils.sendGetRequest(href, errors));
    } else {
      errors.add("Nie pobrano żadnych linków do sprawdzenia");
    }
  }

  public void closePopups() {
    List<WebElement> popups = webDriver.findElements(By.cssSelector(".dx-popup-content"));
    if (popups.size() > 0) {
      popups.forEach(popup -> {
        if (isElementPresent(By.cssSelector(".dx-closebutton"))) {
          WebElement closePopupBtn = webDriver.findElement(By.cssSelector(".dx-closebutton"));
          closePopupBtn.click();
          waitForElementToBeInvisible(closePopupBtn);
          sleep(1000);
        }
      });
    }
  }

  public void clearCartIfNotEmpty() {
    if (isCartNotEmpty()) {
      clearCart();
    }
  }

}
