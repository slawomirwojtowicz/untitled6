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

public class C162AddProductToCartFromOffer extends EhurtTestTemplate {

  @Test(groups = {"production", "preprod", "uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C162")
  public void addProductToCartFromOfferTest() throws Exception {
    final String amountOfUnits = "1";

    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    OfferPage offerPage = homePage.openOfferPage();
    offerPage.clearCart();
    offerPage.sortColumnDescending("Indeks");
    List<String> productBarcodes = offerPage.getProductBarCodes(1, 7);

    for (int i = 1; i <= productBarcodes.size(); i++) {
      offerPage.enterUnits(i + 1, amountOfUnits);
    }
//TODO: niemożliwym jest spradzenie zaokrąglania - konieczne zmiany na froncie aplikacji
    CartStep1Page cartStep1Page = offerPage.clickGoToCartLink();
    cartStep1Page.checkProductsInCartByBarcode(productBarcodes, errors);
    cartStep1Page.clearCart();

    checkConditions();
  }
}
