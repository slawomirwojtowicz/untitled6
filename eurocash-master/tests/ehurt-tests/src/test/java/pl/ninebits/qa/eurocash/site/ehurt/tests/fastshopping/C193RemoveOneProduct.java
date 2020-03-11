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

import java.util.Arrays;
import java.util.List;

public class C193RemoveOneProduct extends EhurtTestTemplate {

  @Test(groups = {"production", "preprod", "uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C193")
  public void removeOneProductTest() throws Exception {
    final List<String> searchPhrases = Arrays.asList("WODKA", "PIWO", "GIN");
    final String packageAmount = "10";

    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    CartHelper.clearCart(homePage);

    FastShoppingPage fastShoppingPage = homePage.openFastShoppingPage();
    for(String searchPhrase: searchPhrases) {
      fastShoppingPage.typeSearchPhrase(searchPhrase);
      String productName = fastShoppingPage.getProductName();
      fastShoppingPage.enterUnits(packageAmount);
      fastShoppingPage.clickAddToCart();
      fastShoppingPage.checkProductPresent(productName, errors);
    }

    String productName = fastShoppingPage.getFirstProductNameFromGrid();
    fastShoppingPage.removeFirstProduct();
    fastShoppingPage.checkProductNotPresent(productName, errors);
    fastShoppingPage.clearCart();

    checkConditions();
  }
}
