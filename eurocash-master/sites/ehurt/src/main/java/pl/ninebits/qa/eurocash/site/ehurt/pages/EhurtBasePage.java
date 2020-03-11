package pl.ninebits.qa.eurocash.site.ehurt.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.administration.AbbreviationPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.administration.ContractorGroupPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.administration.ContractorPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.administration.CoverageOfAccountsPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.administration.DynamicFiltersPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.administration.MarketingConsentsPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.administration.MenuPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.administration.MessagesPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.administration.NewAccessManuPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.administration.NewMenuPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.administration.PermissionsCategoryPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.administration.PermissionsPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.administration.RankPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.administration.UsersGroupPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.administration.UsersPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.myaccount.OrdersHistoryPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.myaccount.PackagesPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.myaccount.PaymentsPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.myaccount.ShopAccountPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.AcceptOrdersPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.ActionsDefinitionsPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.BudgetPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.CartStep1Page;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.CommercialActionsPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.DealsPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.EPapersPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.FastShoppingPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.ForecastedPriceListPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.OfferPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.PromotionsPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.ShoppingListEditPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.ShoppingListsDashboardPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.TemplatesPage;

import java.text.MessageFormat;
import java.util.List;

public abstract class EhurtBasePage extends BasePage {

  public EhurtBasePage(WebDriver webDriver, int actionTimeout) {
    super(webDriver, actionTimeout);
    waitForDataIsLoaded();
    waitForPageToLoad();
  }

  public EhurtBasePage(BasePage pageObject) {
    super(pageObject);
  }

  protected void waitForDataIsLoaded() {
    waitUntil(ExpectedConditions.invisibilityOfAllElements(webDriver.findElements(By.xpath("//div[contains(@class,'loading loading-panel')]"))));
    try {
      waitForElementToBeInvisible(By.cssSelector(".k-overlay"), 5);
    } catch (Exception e) {
      //
    }
    sleep(1000);
  }

  public EhurtHomePage goToHomePage() {
    WebElement ehurtLogo = findClickableElement(By.cssSelector(".fil1")); //TODO: klasa do testów automatycznych
    ehurtLogo.click();

    return new EhurtHomePage(this);
  }

  public void closeNotifications() {
    while (isElementPresent(By.cssSelector(".snackbar-close-x"))) {
      WebElement closeNotifBtn = webDriver.findElement(By.cssSelector(".snackbar-close-x"));
      closeNotifBtn.click();
      sleep(1000);
    }
  }

  public EhurtStartPage logOut() {
    WebElement logoutBtn = webDriver.findElement(By.linkText("wyloguj"));
    logoutBtn.click();
    waitForElementToBeVisible(By.id("user"));
    return new EhurtStartPage(this);
  }

  public void checkWelcomeText(String login, List<String> errors) {
    waitForElementToBeVisibleIgnoredException(By.xpath("//span[@class='active-login' and contains(text(),'" + login + "')]"), 10);
    if (!isElementPresent(By.xpath("//div[@class='active-login' and contains(text(),'" + login + "')]"))) {
      errors.add(MessageFormat.format("Użykownik {0} nie został zalogowany", login));
    }
  }

  public void closeRODOIfPresent() {
    sleep(1000);
    if (isElementPresent(By.cssSelector(".close .pointer"))) {
      WebElement closePopupBtn = webDriver.findElement(By.cssSelector(".close .pointer"));
      closePopupBtn.click();
      waitForElementToBeInvisible(By.cssSelector(".introjs-overlay"));
    }
  }

  public void closeTutorial() {
    String skipBtnLoc = ".introjs-skipbutton";
    try {
      new WebDriverWait(webDriver, 3).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(skipBtnLoc)));
    } catch (Exception e) {
      //
    }
    if (isElementPresent(By.cssSelector(skipBtnLoc))) {
      WebElement watchLaterBtn = findClickableElement(By.cssSelector(skipBtnLoc));
      watchLaterBtn.click();
      waitForElementToBeInvisible(By.cssSelector(skipBtnLoc));
      waitForElementToBeInvisible(By.cssSelector(".introjs-overlay"));
      sleep(1000);
    }
  }

  private void spanShoppingMenu() {
    Actions actions = new Actions(webDriver);
    WebElement shoppingTab = findVisibleElement(By.xpath("//span[@class='tab__extend' and contains(text(),'Zakupy')]"));
    actions.moveToElement(shoppingTab).perform();
    sleep(500);
  }

  private void spanMyAccountMenu() {
    Actions actions = new Actions(webDriver);
    WebElement shoppingTab = findVisibleElement(By.xpath("//span[@class='tab__extend' and contains(text(),'Moje Konto')]"));
    actions.moveToElement(shoppingTab).perform();
    sleep(500);
  }

  private void spanAdministrationMenu() {
    Actions actions = new Actions(webDriver);
    WebElement adminTab = findVisibleElement(By.xpath("//span[@class='tab__extend' and contains(text(),'Administracja')]"));
    actions.moveToElement(adminTab).perform();
    sleep(500);
  }

  private String getMenuPrefix() {
    return "//ul[@class='hcul ulcm']";
  }

  public OfferPage clickOffersLink_shoppingMenu(List<String> errors) {
    spanShoppingMenu();
    List<WebElement> elements = webDriver.findElements(By.xpath(getMenuPrefix() + "//a[@href='/ang/oferta']"));
    if (!elements.isEmpty()) {
      elements.get(0).click();
      return new OfferPage(this);
    } else {
      errors.add("Brak linku Oferta");
      return null;
    }
  }

  public EPapersPage clickEPapersLink_shoppingMenu(List<String> errors) {
    spanShoppingMenu();
    List<WebElement> elements = webDriver.findElements(By.xpath(getMenuPrefix() + "//a[@href='/ang/gazetka']"));
    if (!elements.isEmpty()) {
      elements.get(0).click();
      return new EPapersPage(this);
    } else {
      errors.add("Brak linku eGazetki");
      return null;
    }
  }

  public CommercialActionsPage clickCommercialActionsLink_shoppingMenu(List<String> errors) {
    spanShoppingMenu();
    List<WebElement> elements = webDriver.findElements(By.xpath(getMenuPrefix() + "//a[@href='/Trade/Akcje.aspx']"));
    if (!elements.isEmpty()) {
      elements.get(0).click();
      return new CommercialActionsPage(this);
    } else {
      errors.add("Brak linku Akcje Handlowe");
      return null;
    }
  }

  public ShoppingListEditPage clickShoppingListsLink_shoppingMenu(List<String> errors) {
    spanShoppingMenu();
    List<WebElement> elements = webDriver.findElements(By.xpath(getMenuPrefix() + "//a[@href='/ang/listyZakupowe']"));
    if (!elements.isEmpty()) {
      elements.get(0).click();
      return new ShoppingListEditPage(this);
    } else {
      errors.add("Brak linku Listy zakupowe");
      return null;
    }
  }

  public TemplatesPage clickTemplatesLink_shoppingMenu(List<String> errors) {
    spanShoppingMenu();
    List<WebElement> elements = webDriver.findElements(By.xpath(getMenuPrefix() + "//a[@href='/Zakupy/szablony.aspx']"));
    if (!elements.isEmpty()) {
      elements.get(0).click();
      return new TemplatesPage(this);
    } else {
      errors.add("Brak linku Szablony");
      return null;
    }
  }

  public FastShoppingPage clickFastShoppingLink_shoppingMenu(List<String> errors) {
    spanShoppingMenu();
    List<WebElement> elements = webDriver.findElements(By.xpath(getMenuPrefix() + "//a[@href='/ang/zakupy/szybkieZakupy']"));
    if (!elements.isEmpty()) {
      elements.get(0).click();
      return new FastShoppingPage(this);
    } else {
      errors.add("Brak linku Szybkie zakupy");
      return null;
    }
  }

  public DealsPage clickDealsLink_shoppingMenu(List<String> errors) {
    spanShoppingMenu();
    List<WebElement> elements = webDriver.findElements(By.xpath(getMenuPrefix() + "//a[@href='/ang/deale']"));
    if (!elements.isEmpty()) {
      elements.get(0).click();
      return new DealsPage(this);
    } else {
      errors.add("Brak linku Deale");
      return null;
    }
  }

  public ActionsDefinitionsPage clickActionsDefinitionsLink_shoppingMenu(List<String> errors) {
    spanShoppingMenu();
    List<WebElement> elements = webDriver.findElements(By.xpath(getMenuPrefix() + "//a[@href='/Trade/DefinicjeAkcji.aspx']"));
    if (!elements.isEmpty()) {
      elements.get(0).click();
      return new ActionsDefinitionsPage(this);
    } else {
      errors.add("Brak linku Definicje akcji");
      return null;
    }
  }

  public ForecastedPriceListPage clickForecastedPriceListLink_shoppingMenu(List<String> errors) {
    spanShoppingMenu();
    List<WebElement> elements = webDriver.findElements(By.xpath(getMenuPrefix() + "//a[@href='/Zakupy/cennikiPrognozowane.aspx']"));
    if (!elements.isEmpty()) {
      elements.get(0).click();
      return new ForecastedPriceListPage(this);
    } else {
      errors.add("Brak linku Cenniki prognozowane");
      return null;
    }
  }

  public OrdersHistoryPage clickOrdersImportLink_MyAccountMenu(List<String> errors) {
    Actions actions = new Actions(webDriver);

    spanMyAccountMenu();
    List<WebElement> elements = webDriver.findElements(By.xpath(getMenuPrefix() + "//a[@href='/ang/zakupy/zamowieniaHistoria']"));
    if (!elements.isEmpty()) {
      elements.get(0).click();
      sleep(1000);
      WebElement productBox = findElement(By.xpath("//kendo-pager//span[contains(text(),'Strona')]"));
      actions.moveToElement(productBox).click().perform();
      return new OrdersHistoryPage(this);
    } else {
      errors.add("Brak linku Import zamówień");
      return null;
    }
  }

  public PaymentsPage clickPaymentLink_MyAccountMenu(List<String> errors) {
    spanMyAccountMenu();
    List<WebElement> elements = webDriver.findElements(By.xpath(getMenuPrefix() + "//a[@href='/ang/finanse/platnosci']"));
    if (!elements.isEmpty()) {
      elements.get(0).click();
      return new PaymentsPage(this);
    } else {
      errors.add("Brak linku Płatności");
      return null;
    }
  }

  public AcceptOrdersPage clickAcceptOrdersLink_shoppingMenu(List<String> errors) {
    spanShoppingMenu();
    List<WebElement> elements = webDriver.findElements(By.xpath(getMenuPrefix() + "//a[@href='/ang/zakupy/zamowieniaZatwierdzanie']"));
    if (!elements.isEmpty()) {
      elements.get(0).click();
      return new AcceptOrdersPage(this);
    } else {
      errors.add("Brak linku Zatwierdzanie zamówień");
      return null;
    }
  }

  public PromotionsPage clickPromotionsLink_shoppingMenu(List<String> errors) {
    spanShoppingMenu();
    List<WebElement> elements = webDriver.findElements(By.xpath(getMenuPrefix() + "//a[@href='/ang/promocje/lista']"));
    if (!elements.isEmpty()) {
      elements.get(0).click();
      return new PromotionsPage(this);
    } else {
      errors.add("Brak linku Zatwierdzanie zamówień");
      return null;
    }
  }

  public OfferPage clickStartOrderBigGreenBtn_shoppingMenu(List<String> errors) {
    spanShoppingMenu();
    List<WebElement> elements = webDriver.findElements(By.xpath("//dynamic-quick-start//span[@class='bmc'][contains(text(),'Rozpocznij zamówienie')]"));
    if (!elements.isEmpty()) {
      elements.get(0).click();
      return new OfferPage(this);
    } else {
      errors.add("Brak linku Zatwierdzanie zamówień");
      return null;
    }
  }

  public CartStep1Page clickShowCartBigGreenBtn_shoppingMenu(List<String> errors) {
    spanShoppingMenu();
    List<WebElement> elements = webDriver.findElements(By.xpath("//dynamic-quick-start//span[@class='bmc'][contains(text(),'Zobacz koszyk')]"));
    if (!elements.isEmpty()) {
      elements.get(0).click();
      return new CartStep1Page(this);
    } else {
      errors.add("Brak linku Zatwierdzanie zamówień");
      return null;
    }
  }

  public PromotionsPage clickCheckPromotionsBigGreenBtn_shoppingMenu(List<String> errors) {
    spanShoppingMenu();
    List<WebElement> elements = webDriver.findElements(By.xpath("//dynamic-quick-start//span[@class='bmc'][contains(text(),'Sprawdź promocje')]"));
    if (!elements.isEmpty()) {
      elements.get(0).click();
      return new PromotionsPage(this);
    } else {
      errors.add("Brak linku Zatwierdzanie zamówień");
      return null;
    }
  }

  public MenuPage clickMenueLink_administrationMenu(List<String> errors) {
    spanAdministrationMenu();
    List<WebElement> elements = webDriver.findElements(By.xpath(getMenuPrefix() + "//a[@href='/Administracja/menu.aspx']"));
    if (!elements.isEmpty()) {
      sleep();
      elements.get(0).click();
      return new MenuPage(this);
    } else {
      errors.add("Brak linku Menue");
      return null;
    }
  }

  public RankPage clickMenueLink_RanknMenu(List<String> errors) {
    spanAdministrationMenu();
    List<WebElement> elements = webDriver.findElements(By.xpath(getMenuPrefix() + "//a[@href='/Administracja/rangi.aspx']"));
    if (!elements.isEmpty()) {
      sleep();
      elements.get(0).click();
      return new RankPage(this);
    } else {
      errors.add("Brak linku Rangi");
      return null;
    }
  }

  public DynamicFiltersPage clickMenueLink_DynamicFiltersMenu(List<String> errors) {
    spanAdministrationMenu();
    List<WebElement> elements = webDriver.findElements(By.xpath(getMenuPrefix() + "//a[@href='/Administracja/rangiFiltry.aspx']"));
    if (!elements.isEmpty()) {
      sleep();
      elements.get(0).click();
      return new DynamicFiltersPage(this);
    } else {
      errors.add("Brak linku filtry dynamiczne");
      return null;
    }
  }

  public CoverageOfAccountsPage clickMenueLink_AccountRangeFiltersMenu(List<String> errors) {
    spanAdministrationMenu();
    List<WebElement> elements = webDriver.findElements(By.xpath(getMenuPrefix() + "//a[@href='/Administracja/rangi.aspx?akcja=zasieg']"));
    if (!elements.isEmpty()) {
      sleep();
      elements.get(0).click();
      return new CoverageOfAccountsPage(this);
    } else {
      errors.add("Brak linku zasięg kont");
      return null;
    }
  }

  public NewMenuPage clickMenueLink_NewMenuFiltersMenu(List<String> errors) {
    spanAdministrationMenu();
    List<WebElement> elements = webDriver.findElements(By.xpath(getMenuPrefix() + "//a[@href='/Administracja/menu_v2.aspx']"));
    if (!elements.isEmpty()) {
      sleep();
      elements.get(0).click();
      return new NewMenuPage(this);
    } else {
      errors.add("Brak linku nowe menu");
      return null;
    }
  }

  public NewAccessManuPage clickMenueLink_NewAccessMenuFiltersMenu(List<String> errors) {
    spanAdministrationMenu();
    List<WebElement> elements = webDriver.findElements(By.xpath(getMenuPrefix() + "//a[@href='/Administracja/menuDostep_v2.aspx']"));
    if (!elements.isEmpty()) {
      sleep();
      elements.get(0).click();
      return new NewAccessManuPage(this);
    } else {
      errors.add("Brak linku nowe menu dostęp");
      return null;
    }
  }

  //TODO dorobić page
  public UsersPage clickMenueLink_UsersPageFiltersMenu(List<String> errors) {
    spanAdministrationMenu();
    List<WebElement> elements = webDriver.findElements(By.xpath(getMenuPrefix() + "//a[@href='/Administracja/uzytkownicy.aspx']"));
    if (!elements.isEmpty()) {
      sleep();
      elements.get(0).click();
      return new UsersPage(this);
    } else {
      errors.add("Brak linku użytkownicy");
      return null;
    }
  }

  public ContractorPage clickMenueLink_ContractorFiltersMenu(List<String> errors) {
    spanAdministrationMenu();
    List<WebElement> elements = webDriver.findElements(By.xpath(getMenuPrefix() + "//a[@href='/Administracja/kontrahenci.aspx']"));
    if (!elements.isEmpty()) {
      sleep();
      elements.get(0).click();
      return new ContractorPage(this);
    } else {
      errors.add("Brak linku kontrahent");
      return null;
    }
  }

  public UsersGroupPage clickMenueLink_UsersGroupFiltersMenu(List<String> errors) {
    spanAdministrationMenu();
    List<WebElement> elements = webDriver.findElements(By.xpath(getMenuPrefix() + "//a[@href='/Administracja/grupy.aspx']"));
    if (!elements.isEmpty()) {
      sleep();
      elements.get(0).click();
      return new UsersGroupPage(this);
    } else {
      errors.add("Brak linku grupa użytkowników");
      return null;
    }
  }

  public ContractorGroupPage clickMenueLink_ContractorGroupFiltersMenu(List<String> errors) {
    spanAdministrationMenu();
    List<WebElement> elements = webDriver.findElements(By.xpath(getMenuPrefix() + "//a[@href='/Administracja/grupyKontrahentow.aspx']"));
    if (!elements.isEmpty()) {
      sleep();
      elements.get(0).click();
      return new ContractorGroupPage(this);
    } else {
      errors.add("Brak linku grupa kontrahentów");
      return null;
    }
  }

  public MessagesPage clickMenueLink_MessagesFiltersMenu(List<String> errors) {
    spanAdministrationMenu();
    List<WebElement> elements = webDriver.findElements(By.xpath(getMenuPrefix() + "//a[@href='/Administracja/przekazy.aspx']"));
    if (!elements.isEmpty()) {
      sleep();
      elements.get(0).click();
      return new MessagesPage(this);
    } else {
      errors.add("Brak linku z przekazami");
      return null;
    }
  }

  public AbbreviationPage clickMenueLink_AbbreviationFiltersMenu(List<String> errors) {
    spanAdministrationMenu();
    List<WebElement> elements = webDriver.findElements(By.xpath(getMenuPrefix() + "/Administracja/skroty.aspx"));
    if (!elements.isEmpty()) {
      sleep();
      elements.get(0).click();
      return new AbbreviationPage(this);
    } else {
      errors.add("Brak linku z skrótkami");
      return null;
    }
  }

  public PermissionsPage clickMenueLink_PermissionsFiltersMenu(List<String> errors) {
    spanAdministrationMenu();
    List<WebElement> elements = webDriver.findElements(By.xpath(getMenuPrefix() + "/Administracja/uprawnienia.aspx"));
    if (!elements.isEmpty()) {
      sleep();
      elements.get(0).click();
      return new PermissionsPage(this);
    } else {
      errors.add("Brak linku z uprawnieniami");
      return null;
    }
  }

  public PermissionsCategoryPage clickMenueLink_PermissionsCategoryFiltersMenu(List<String> errors) {
    spanAdministrationMenu();
    List<WebElement> elements = webDriver.findElements(By.xpath(getMenuPrefix() + "/Administracja/uprawnieniaKategorie.aspx"));
    if (!elements.isEmpty()) {
      sleep();
      elements.get(0).click();
      return new PermissionsCategoryPage(this);
    } else {
      errors.add("Brak linku z uprawnieniami/kategoriami");
      return null;
    }
  }


  public MarketingConsentsPage clickMenueLink_MarketingConsentsFiltersMenu(List<String> errors) {
    spanAdministrationMenu();
    List<WebElement> elements = webDriver.findElements(By.xpath(getMenuPrefix() + "/Administracja/ZgodyMarketingowe.aspx"));
    if (!elements.isEmpty()) {
      sleep();
      elements.get(0).click();
      return new MarketingConsentsPage(this);
    } else {
      errors.add("Brak linku z Zgodami marketingowymi");
      return null;
    }
  }

  public ShoppingListsDashboardPage openShoppingListsPage() {
    String url = getCurrentUrl();
    url = url.replace("ang/dashboard", "ang/listyZakupowe");
    webDriver.navigate().to(url);

    return new ShoppingListsDashboardPage(this);
  }

  public OfferPage openOfferPage() {
    String url = getCurrentUrl();
    url = url.replace("ang/dashboard", "ang/oferta");
    webDriver.navigate().to(url);

    return new OfferPage(this);
  }

  public FastShoppingPage openFastShoppingPage() {
    String url = getCurrentUrl();
    url = url.replace("ang/dashboard", "ang/zakupy/szybkieZakupy");
    webDriver.navigate().to(url);

    return new FastShoppingPage(this);
  }

  public CartStep1Page clickGoToCartLink() {
    Actions actions = new Actions(webDriver);
    actions.moveToElement(findVisibleElement(By.cssSelector(".cart__tab"))).perform();
    actions.moveToElement(findClickableElement(By.xpath("//div[contains(@class,'cart')]//a[@href='/ang/koszyk']")))
      .click(findVisibleElement(By.xpath("//div[contains(@class,'cart')]//a[@href='/ang/koszyk']"))).perform();
    waitForDataIsLoaded();
    reloadPage();
    waitForDataIsLoaded();
    return new CartStep1Page(this);
  }

  public void clearCart() {
    Actions action = new Actions(webDriver);
    action.moveToElement(findVisibleElement(By.cssSelector(".cart__tab"))).perform();
    sleep(1000);
    WebElement delBtn = findVisibleElement(By.cssSelector(".delete-cart"));
    action.moveToElement(delBtn).click(delBtn).perform();
    if (isElementPresent(By.cssSelector(".dialog-confirm"))) {
      findVisibleElement(By.cssSelector(".dialog-confirm")).click();
    }
    waitForDataIsLoaded();
    // waitForCartToBeEmpty();
  }

  public void checkCartCleared(List<String> errors) {
    String cartNetValue = findVisibleElement(By.xpath("//div[@class='cart__tab']//div[@class='value-line'][1]/span")).getText().replaceAll("[\\s\\u00A0zł]", "");
    cartNetValue = cartNetValue.replace(",", ".");
    if (Double.valueOf(cartNetValue) != 0.0) {
      errors.add("Koszyk nie został wyczyszczony");
    }
  }

  public void ensureProperContractorChosen(String expectedContractorId) {
    String contractorId = getLoggedInContractorId();
    if (!contractorId.isEmpty() && !contractorId.equals(expectedContractorId)) {
      findClickableElement(By.id("shop")).click();
      findVisibleElement(By.xpath("(//thead//input)[2]")).clear();
      WebElement khInput = findVisibleElement(By.xpath("(//thead//input)[1]"));
      khInput.clear();
      khInput.sendKeys(expectedContractorId);
      khInput.sendKeys(Keys.RETURN);
      waitForDataIsLoaded();
      sleep(1000);
      findClickableElement(By.xpath("//tbody//div[@title='" + expectedContractorId + "']")).click();
      waitForDataIsLoaded();
      waitForPageToLoad();
    }

    if (contractorId.isEmpty()) {
      Assert.fail("Brak widocznego kontrahenta");
    }
  }

  private String getLoggedInContractorId() {
    String contractorId = getTextFromElement(By.cssSelector(".tab__title .title-desc span:nth-child(1)"));
    return contractorId.replaceAll("KOD: ", "").trim();
  }

  public ShopAccountPage openShopAccountPage() {
    String url = getCurrentUrl();
    url = url.replace("ang/dashboard", "ang/konto/sklep");
    webDriver.navigate().to(url);
    waitForPageToLoad();
    waitForDataIsLoaded();

    return new ShopAccountPage(this);
  }

  public PaymentsPage openPaymentsPage() {
    String url = getCurrentUrl();
    url = url.replace("ang/dashboard", "ang/finanse/platnosci");
    webDriver.navigate().to(url);

    return new PaymentsPage(this);
  }

  public PackagesPage openPackagesPage() {
    String url = getCurrentUrl();
    url = url.replace("ang/dashboard", "ang/opakowania");
    webDriver.navigate().to(url);

    return new PackagesPage(this);
  }

  public DynamicReportsPage openDynamicReportsPage() {
    String url = getCurrentUrl();
    url = url.replace("ang/dashboard", "Narzedzia/RaportyDynamiczne.aspx");
    webDriver.navigate().to(url);
    reloadPage();
    return new DynamicReportsPage(this);
  }

  public ForecastedPriceListPage openForecastedPricelistsPage() {
    String url = getCurrentUrl();
    url = url.replace("ang/dashboard", "ang/prognoza/cen");
    webDriver.navigate().to(url);

    return new ForecastedPriceListPage(this);
  }

  public BudgetPage openBudgetPage() {
    String url = getCurrentUrl();
    url = url.replace("ang/dashboard", "ang/budzet");
    webDriver.navigate().to(url);

    return new BudgetPage(this);
  }
}