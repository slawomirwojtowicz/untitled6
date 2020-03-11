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

public class C190AddProductsByNameAndIndex extends EhurtTestTemplate {

  @Test(groups = {"production", "preprod", "uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C190")
  public void addProductsByNameAndIndexTest() throws Exception {
    final List<String> searchPhrases = Arrays.asList("WODKA", "677");
    final String packageAmount = "10";

    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    CartHelper.clearCart(homePage);

    FastShoppingPage fastShoppingPage = homePage.openFastShoppingPage();
    fastShoppingPage.typeSearchPhrase(searchPhrases.get(0));
    String productName = fastShoppingPage.getProductName();
    fastShoppingPage.enterUnits(packageAmount);
    fastShoppingPage.clickAddToCart();
    fastShoppingPage.checkProductPresent(productName, errors);

    fastShoppingPage.typeSearchPhrase(searchPhrases.get(1));
    fastShoppingPage.checkIndexPresent(searchPhrases.get(1), errors);
    fastShoppingPage.enterUnits(packageAmount);
    fastShoppingPage.clickAddToCart();
    fastShoppingPage.checkIndexInGridPresent(searchPhrases.get(1), errors);

    fastShoppingPage.clearCart();
    fastShoppingPage.checkFastShoppingListCleared(errors);

    checkConditions();
  }
}
