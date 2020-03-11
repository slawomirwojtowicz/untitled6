package pl.ninebits.qa.eurocash.site.ehurt.tests.shoppinglists;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.site.ehurt.helpers.EhurtAppLoginHelper;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtHomePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtStartPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.OfferPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.ShoppingListPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.ShoppingListsDashboardPage;
import pl.ninebits.qa.eurocash.site.ehurt.tests.EhurtTestTemplate;

public class C148CreateListFromOffer extends EhurtTestTemplate {

  @Test(groups = {"production", "preprod", "uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C148")
  public void createListFromOfferTest() throws Exception {
    final String listName = RandomStringUtils.random(10, true, true);

    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    ShoppingListsDashboardPage shoppingListsDashboardPage = homePage.openShoppingListsPage();
    shoppingListsDashboardPage.removeLists();

    homePage = shoppingListsDashboardPage.goToHomePage();
    OfferPage offerPage = homePage.openOfferPage();
    offerPage.saveAsList(listName);

    homePage = offerPage.goToHomePage();
    shoppingListsDashboardPage = homePage.openShoppingListsPage();
    shoppingListsDashboardPage.checkListPresent(listName, errors);
    ShoppingListPage shoppingListPage = shoppingListsDashboardPage.clickOpenList(listName);
    shoppingListPage.checkListNotEmpty(listName, errors);

    shoppingListsDashboardPage = shoppingListPage.clickBackToLists();
    shoppingListsDashboardPage.removeLists();

    checkConditions();
  }
}
