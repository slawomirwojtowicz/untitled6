package pl.ninebits.qa.eurocash.site.ehurt.tests.prereleasecheck;

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

public class C317CheckGrossValueInCart extends EhurtTestTemplate {

  @Test(groups = {"production", "preprod", "uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C317")
  public void checkGrossValueInCartTest() throws Exception {
    final String amountOfUnits = "1";
    final int productAmount = 7;

    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    CartHelper.clearCart(homePage);
    OfferPage offerPage = homePage.openOfferPage();
    offerPage.sortColumnDescending("Indeks");
    List<String> productNames = offerPage.getProductNames(1, productAmount);
    //todo: sprawdzenie wartości netto/brutto w koszyku
    List<String> productNetPrices = offerPage.getProductNetPrices(1,productAmount);
    List<String> productGrossPrices = offerPage.getProductGrossPrices(1, productAmount);

    for (int i = 1; i <= productNames.size(); i++) {
      offerPage.enterUnits(i + 1, amountOfUnits);
    }

    offerPage.checkGrossValueLargerOnCartTabInFooter(errors);
    CartStep1Page cartStep1Page = offerPage.clickGoToCartLink();
    cartStep1Page.checkGrossValueLarger(errors);
    cartStep1Page.clearCart();

    checkConditions();
  }
}
