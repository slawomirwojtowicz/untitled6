package pl.ninebits.qa.eurocash.site.ehurt.tests.cart;

import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.site.ehurt.helpers.CartHelper;
import pl.ninebits.qa.eurocash.site.ehurt.helpers.EhurtAppLoginHelper;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtHomePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtStartPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.CartStep1Page;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.OfferPage;
import pl.ninebits.qa.eurocash.site.ehurt.tests.EhurtTestTemplate;

import java.util.List;

public class C189ClearCartFromFooter extends EhurtTestTemplate {

  @Test(groups = {"production", "preprod", "uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C189")
  public void clearCartFromFooterTest() throws Exception {
    final String amountOfUnits = "1";

    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    CartHelper.clearCart(homePage);
    OfferPage offerPage = homePage.openOfferPage();
    offerPage.sortColumnDescending("Indeks");
    List<String> productNames = offerPage.getProductNames(1, 3);

    for (int i = 1; i <= productNames.size(); i++) {
      offerPage.enterUnits(i + 1, amountOfUnits);
    }
    productNames.clear();

    offerPage.clearCart();
    offerPage.checkCartCleared(errors);
    CartStep1Page cartStep1Page = offerPage.clickGoToCartLink();
    cartStep1Page.checkCartEmpty(errors);

    checkConditions();
  }
}
