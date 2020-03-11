package pl.ninebits.qa.eurocash.site.ehurt.tests.cart;

import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.automated.tests.core.tests.TestDurationLogger;
import pl.ninebits.qa.eurocash.site.ehurt.constants.ProductsName;
import pl.ninebits.qa.eurocash.site.ehurt.helpers.CartHelper;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtHomePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtStartPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.CartStep1Page;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.CartStep2Page;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.OfferPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.OrderConfirmationPage;
import pl.ninebits.qa.eurocash.site.ehurt.tests.EhurtTestTemplate;

import java.text.MessageFormat;

public class C3683OrderTwentyThreeProduct extends EhurtTestTemplate {

  @Test(groups = {"production"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C3683")
  @TestDurationLogger(breakpoints = {"dashboard", "offerPage", "productTotal", "product1", "product2", "product3", "product4", "product5", "product6",
    "product7", "product8", "product9", "product10", "product11", "product12", "product13", "product14", "product15", "product16", "product17", "product18",
    "product19", "product20", "product21", "product22", "product23", "cartStep1Page", "cartStep2Page", "orderConfirmationPage"})
  public void orderTwentyThreeProductTest() throws Exception {
    final String amountOfUnits = "1";
    final String userLogin = "Monit24.off";
    final String userPass = "monit24";
    ProductsName[] values = ProductsName.values();

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

    setTimestamp("productTotal", true);
    for (int i = 0; i < values.length; i++) {
      setTimestamp(MessageFormat.format("product{0}", (i + 1)), true);
      offerPage.enterSearchPhrase(values[i]);
      offerPage.confirmSearch();
      offerPage.enterUnitsForFullProductName(values[i], amountOfUnits);

      //INFO: ujednolicenie do Offline - nie działa na offline wielokrotne wyszukiwanie bez czyszczenia filtra
      offerPage.clearSearchFilters();
      setTimestamp(MessageFormat.format("product{0}", (i + 1)));
    }
    setTimestamp("productTotal");

    setTimestamp("cartStep1Page", true);
    CartStep1Page cartStep1Page = offerPage.clickGoToCartLink();
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
