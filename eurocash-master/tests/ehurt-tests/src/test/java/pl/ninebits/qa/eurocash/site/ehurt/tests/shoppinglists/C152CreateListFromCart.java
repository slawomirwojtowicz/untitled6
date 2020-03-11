package pl.ninebits.qa.eurocash.site.ehurt.tests.shoppinglists;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.site.ehurt.helpers.EhurtAppLoginHelper;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtHomePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtStartPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.CartStep1Page;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.OfferPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.ShoppingListPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.ShoppingListsDashboardPage;
import pl.ninebits.qa.eurocash.site.ehurt.tests.EhurtTestTemplate;

import java.util.List;

public class C152CreateListFromCart extends EhurtTestTemplate {

  @Test(groups = {"production", "preprod", "uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C152")
  public void createListFromCartTest() throws Exception {
    final String listName = RandomStringUtils.random(10, true, true);
    final String amountOfUnits = "1";

    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    OfferPage offerPage = homePage.openOfferPage();
    offerPage.sortColumnDescending("Producent");
    List<String> productNames = offerPage.getProductNames(1, 5);

    for (int i = 1; i <= productNames.size(); i++) {
      offerPage.enterUnits(i + 1, amountOfUnits);
    }

    CartStep1Page cartStep1Page = offerPage.clickGoToCartLink();
    cartStep1Page.makeListFromCart(listName);
    cartStep1Page.clearCart();
    homePage = cartStep1Page.goToHomePage();

    ShoppingListsDashboardPage shoppingListsDashboardPage = homePage.openShoppingListsPage();
    ShoppingListPage shoppingListPage = shoppingListsDashboardPage.clickOpenList(listName);
    shoppingListPage.checkProductOnList(productNames, errors);
    shoppingListsDashboardPage = shoppingListPage.clickBackToLists();
    shoppingListsDashboardPage.removeList(listName);
    shoppingListsDashboardPage.checkListRemoved(listName, errors);

    checkConditions();
  }
}
