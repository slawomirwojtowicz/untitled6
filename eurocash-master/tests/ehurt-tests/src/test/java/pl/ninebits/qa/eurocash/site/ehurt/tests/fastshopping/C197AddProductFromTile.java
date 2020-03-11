package pl.ninebits.qa.eurocash.site.ehurt.tests.fastshopping;

import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.site.ehurt.helpers.CartHelper;
import pl.ninebits.qa.eurocash.site.ehurt.helpers.EhurtAppLoginHelper;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtHomePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtStartPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.FastShoppingPage;
import pl.ninebits.qa.eurocash.site.ehurt.tests.EhurtTestTemplate;

public class C197AddProductFromTile extends EhurtTestTemplate {

  @Test(groups = {"production", "preprod", "uat"}, enabled = false)
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C197")
  public void addProductFromTileTest() throws Exception {
    final String packageAmount = "2";

    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    CartHelper.clearCart(homePage);
    String productName = homePage.getProductNameFromInPromoTile();
    homePage.enterAmountInPromoTile(packageAmount);

    FastShoppingPage fastShoppingPage = homePage.openFastShoppingPage();
    fastShoppingPage.checkProductPresent(productName, errors);
    fastShoppingPage.clearCart();

    checkConditions();
  }
}
