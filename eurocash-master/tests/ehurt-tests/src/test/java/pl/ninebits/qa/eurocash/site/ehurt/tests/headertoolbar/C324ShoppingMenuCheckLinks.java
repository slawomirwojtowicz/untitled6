package pl.ninebits.qa.eurocash.site.ehurt.tests.headertoolbar;

import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.site.ehurt.helpers.EhurtAppLoginHelper;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtHomePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtStartPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.myaccount.OrdersHistoryPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.AcceptOrdersPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.ActionsDefinitionsPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.CartStep1Page;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.CommercialActionsPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.DealsPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.EPapersPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.FastShoppingPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.ForecastedPriceListPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.OfferPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.PromotionsPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.ShoppingListEditPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.TemplatesPage;
import pl.ninebits.qa.eurocash.site.ehurt.tests.EhurtTestTemplate;

public class C324ShoppingMenuCheckLinks extends EhurtTestTemplate {

  @Test(groups = {"production", "preprod", "uat"}, enabled = false)
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C324")
  public void shoppingMenuCheckLinksTest() throws Exception {
    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();
    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    OfferPage offerPage = homePage.clickOffersLink_shoppingMenu(errors);
    if (offerPage != null) {
      offerPage.checkOfferPageLoaded(errors);
      homePage = offerPage.goToHomePage();
    }

    PromotionsPage promotionsPage = homePage.clickPromotionsLink_shoppingMenu(errors);
    if (promotionsPage != null) {
      promotionsPage.checkPromotionPageLoaded(errors);
      homePage = promotionsPage.goToHomePage();
    }

    EPapersPage ePapersPage = homePage.clickEPapersLink_shoppingMenu(errors);
    if (ePapersPage != null) {
      ePapersPage.checkEPapersPageLoaded(errors);
      homePage = ePapersPage.goToHomePage();
    }

    CommercialActionsPage commercialActionsPage = homePage.clickCommercialActionsLink_shoppingMenu(errors);
    if (commercialActionsPage != null) {
      commercialActionsPage.checkCommercialActionsPageLoaded(errors);
      homePage = commercialActionsPage.goToHomePage();
    }

    ShoppingListEditPage shoppingListEditPage = homePage.clickShoppingListsLink_shoppingMenu(errors);
    if (shoppingListEditPage != null) {
      shoppingListEditPage.checkShoppingListPagePresent(errors);
      homePage = shoppingListEditPage.goToHomePage();
    }

    TemplatesPage templatesPage = homePage.clickTemplatesLink_shoppingMenu(errors);
    if (templatesPage != null) {
      templatesPage.checkTemplatesPagePresent(errors);
      homePage = templatesPage.goToHomePage();
    }

    FastShoppingPage fastShoppingPage = homePage.clickFastShoppingLink_shoppingMenu(errors);
    if (fastShoppingPage != null) {
      fastShoppingPage.checkFastShoppingPagePresent(errors);
      homePage = fastShoppingPage.goToHomePage();
    }

    DealsPage dealsPage = homePage.clickDealsLink_shoppingMenu(errors);
    if (dealsPage != null) {
      dealsPage.checkDealsPagePresent(errors);
      homePage = dealsPage.goToHomePage();
    }

    ActionsDefinitionsPage actionsDefinitionsPage = homePage.clickActionsDefinitionsLink_shoppingMenu(errors);
    if (actionsDefinitionsPage != null) {
      actionsDefinitionsPage.checkActionsDefinitionsPagePresent(errors);
      homePage = actionsDefinitionsPage.goToHomePage();
    }

    ForecastedPriceListPage forecastedPriceListPage = homePage.clickForecastedPriceListLink_shoppingMenu(errors);
    if (forecastedPriceListPage != null) {
      forecastedPriceListPage.checkForecastedPriceListPagePresent(errors);
      homePage = forecastedPriceListPage.goToHomePage();
    }

    OrdersHistoryPage ordersHistoryPage = homePage.clickOrdersImportLink_MyAccountMenu(errors);
    if (ordersHistoryPage != null) {
      ordersHistoryPage.checkOrdersImportPopupOpen(errors);
      ordersHistoryPage.closeOrdersImportPopup();
      homePage = ordersHistoryPage.goToHomePage();
    }

    AcceptOrdersPage acceptOrdersPage = homePage.clickAcceptOrdersLink_shoppingMenu(errors);
    if (acceptOrdersPage != null) {
      acceptOrdersPage.checkAcceptOrdersPagePresent(errors);
      homePage = acceptOrdersPage.goToHomePage();
    }

    // homePage.clickOrderLink_shoppingMenu(errors); //todo: aby kliknięcie miało sens w koszyku muszą być rzeczy, chyba

    offerPage = homePage.clickStartOrderBigGreenBtn_shoppingMenu(errors);
    if (offerPage != null) {
      offerPage.checkOfferPageLoaded(errors);
      homePage = offerPage.goToHomePage();
    }

    CartStep1Page cartStep1Page = homePage.clickShowCartBigGreenBtn_shoppingMenu(errors);
    if (cartStep1Page != null) {
      cartStep1Page.checkCartStep1PageLoaded(errors);
      homePage = cartStep1Page.goToHomePage();
    }

    promotionsPage = homePage.clickCheckPromotionsBigGreenBtn_shoppingMenu(errors);
    if (promotionsPage != null) {
      promotionsPage.checkPromotionPageLoaded(errors);
    }

    checkConditions();
  }
}
