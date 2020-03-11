package pl.ninebits.qa.eurocash.site.ehurt.tests.cart;

import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.site.ehurt.helpers.EhurtAppLoginHelper;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtHomePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtStartPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.CartStep1Page;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.OfferPage;
import pl.ninebits.qa.eurocash.site.ehurt.tests.EhurtTestTemplate;

import java.util.List;

public class C163RemoveProductFromCart extends EhurtTestTemplate {

  @Test(groups = {"production", "preprod", "uat"}, enabled = false)
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C163")
  public void removeProductFromCartTest() throws Exception {
    final String amountOfUnits = "1";

    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    OfferPage offerPage = homePage.openOfferPage();
    offerPage.clearCart();
    offerPage.sortColumnDescending("Indeks");
    List<String> productNames = offerPage.getProductNames(1, 7);

    for (int i = 1; i <= productNames.size(); i++) {
      offerPage.enterUnits(i + 1, amountOfUnits);
    }

    CartStep1Page cartStep1Page = offerPage.clickGoToCartLink();
    List<String> productNamesInCart = cartStep1Page.getProductNames();
    cartStep1Page.removeFirstProduct();
    cartStep1Page.checkProductInCartPresent(productNamesInCart.get(0), false, errors);
    cartStep1Page.clearCart();


    checkConditions();
  }
}
