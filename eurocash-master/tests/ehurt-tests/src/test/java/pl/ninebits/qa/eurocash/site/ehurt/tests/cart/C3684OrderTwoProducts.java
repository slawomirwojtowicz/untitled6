package pl.ninebits.qa.eurocash.site.ehurt.tests.cart;

import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.automated.tests.core.tests.TestDurationLogger;
import pl.ninebits.qa.eurocash.site.ehurt.helpers.CartHelper;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtHomePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtStartPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.myaccount.OrdersHistoryPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.myaccount.PaymentsPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.CartStep1Page;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.CartStep2Page;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.OfferPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.OrderConfirmationPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.PromotionsPage;
import pl.ninebits.qa.eurocash.site.ehurt.tests.EhurtTestTemplate;

public class C3684OrderTwoProducts extends EhurtTestTemplate {

  @Test(groups = {"production"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C3684")
  @TestDurationLogger(breakpoints = {"dashboard", "offerPage", "product1", "product2", "promotionPage", "orderHistoryPage", "paymentsPage", "cartStep1Page",
    "cartStep2Page", "orderConfirmationPage"})
  public void OrderTwoProductsTest() throws Exception {
    final String amountOfUnits = "1";
    final String userLogin = "Monit24.off";
    final String userPass = "monit24";
    final String productSearchPhrase1 = "KAWA ROZPUSZCZALNA NESCORE 100G NESTLE PUSZ";
    final String productSearchPhrase2 = "OLEJ KUJAWSKI 1L KRUSZWICA";

    EhurtStartPage startPage = loadEhurtStartPage();
    startPage.typeLogin(userLogin);
    startPage.typePassword(userPass);
    EhurtHomePage homePage = (EhurtHomePage) startPage.clickLoginBtn();
    setTimestamp("dashboard");
    homePage.closePopups();
    CartHelper.clearCart(homePage);

    setTimestamp("offerPage", true);
    OfferPage offerPage = homePage.openOfferPage();
    setTimestamp("offerPage");

    setTimestamp("product1", true);
    offerPage.enterSearchPhrase(productSearchPhrase1);
    offerPage.confirmSearch();
//    offerPage.checkFilterBoxPresent(productSearchPhrase1, errors);
    offerPage.enterUnitsForFullProductName(productSearchPhrase1, amountOfUnits);
    offerPage.clearSearchFilters();
    setTimestamp("product1");

    setTimestamp("product2", true);
    offerPage = homePage.openOfferPage();
    offerPage.enterSearchPhrase(productSearchPhrase2);
    offerPage.confirmSearch();
//    offerPage.checkFilterBoxPresent(productSearchPhrase2, errors);
    offerPage.enterUnitsForFullProductName(productSearchPhrase2, amountOfUnits);
    offerPage.clearSearchFilters();
    setTimestamp("product2");

    setTimestamp("promotionPage", true);
    PromotionsPage promotionsPage = homePage.clickPromotionsLink_shoppingMenu(errors);
    if (promotionsPage != null) {
      promotionsPage.checkPromotionPageLoaded(errors);
    }
    setTimestamp("promotionPage");

    //TODO nie działa wyszukiwania promocji po nazwie GAZETKA

    // promotionsPage.clickPromoFilter(promoName);
    // promotionsPage.enterSearchPhrase(promoName);
    // promotionsPage.checkPromoDescriptions(promoName, errors);
    // promotionsPage.clearFilters();

    offerPage = homePage.clickOffersLink_shoppingMenu(errors);
    if (offerPage != null) {
      offerPage.checkOfferPageLoaded(errors);
    }

    setTimestamp("orderHistoryPage", true);
    OrdersHistoryPage ordersHistoryPage = homePage.clickOrdersImportLink_MyAccountMenu(errors);
    if (ordersHistoryPage != null) {
      ordersHistoryPage.checkOrderHistoryPageLoaded(errors);
    }
    setTimestamp("orderHistoryPage");

    offerPage = homePage.clickOffersLink_shoppingMenu(errors);
    if (offerPage != null) {
      offerPage.checkOfferPageLoaded(errors);
    }

    setTimestamp("paymentsPage", true);
    PaymentsPage paymentsPage = homePage.clickPaymentLink_MyAccountMenu(errors);
    if (paymentsPage != null) {
      paymentsPage.checkGoToPaymentPlatformBtn(errors);
    }
    setTimestamp("paymentsPage");

    offerPage = homePage.clickOffersLink_shoppingMenu(errors);
    if (offerPage != null) {
      offerPage.checkOfferPageLoaded(errors);
    }

    setTimestamp("cartStep1Page", true);
    CartStep1Page cartStep1Page = offerPage.clickGoToCartLink();
    //  cartStep1Page.checkProductInCartPresent(productSearchPhrase1, true, errors);
    // cartStep1Page.checkProductInCartPresent(productSearchPhrase2, true, errors);
    setTimestamp("cartStep1Page");

    setTimestamp("cartStep2Page", true);
    CartStep2Page cartStep2Page = cartStep1Page.clickAcceptOrderBtn();
    cartStep2Page.checkCartStep2PageLoaded(errors);
    setTimestamp("cartStep2Page");

    setTimestamp("orderConfirmationPage", true);
    OrderConfirmationPage orderConfirmationPage = cartStep2Page.clickAcceptOrderBtn();
    orderConfirmationPage.checkOrderConfirmationPageLoaded(errors);
    setTimestamp("orderConfirmationPage");

    checkConditions();
  }
}
