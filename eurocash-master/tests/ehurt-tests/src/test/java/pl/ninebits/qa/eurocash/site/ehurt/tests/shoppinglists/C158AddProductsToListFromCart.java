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
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.ShoppingListEditPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.ShoppingListPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.ShoppingListsDashboardPage;
import pl.ninebits.qa.eurocash.site.ehurt.tests.EhurtTestTemplate;

import java.util.List;

public class C158AddProductsToListFromCart extends EhurtTestTemplate {

  @Test(groups = {"production", "preprod", "uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C158")
  public void addProductsToListFromCartTest() throws Exception {
    final String listName = RandomStringUtils.random(10, true, true);
    final String amountOfUnits = "1";

    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    ShoppingListsDashboardPage shoppingListsDashboardPage = homePage.openShoppingListsPage();
    shoppingListsDashboardPage.removeLists();

    ShoppingListEditPage shoppingListEditPage = shoppingListsDashboardPage.clickNewShoppingListBtn();
    shoppingListEditPage.enterListName(listName);
    shoppingListEditPage.checkListNamePresentInHeader(listName, errors);
    shoppingListEditPage.clickSaveListBtn();

    shoppingListsDashboardPage = shoppingListEditPage.clickBackToAllListsBtn();
    homePage = shoppingListsDashboardPage.goToHomePage();
    OfferPage offerPage = homePage.openOfferPage();
    offerPage.sortColumnDescending("Indeks");
    List<String> productNames = offerPage.getProductNames(1, 5);

    for (int i = 1; i <= productNames.size(); i++) {
      offerPage.enterUnits(i + 1, amountOfUnits);
    }

    CartStep1Page cartStep1Page = offerPage.clickGoToCartLink();
    cartStep1Page.clickProductCheckboxes(2);
    cartStep1Page.clickListActionMenu();
    cartStep1Page.addToListInListActionMenu(listName, errors);
    cartStep1Page.clearCart();

    homePage = cartStep1Page.goToHomePage();
    shoppingListsDashboardPage = homePage.openShoppingListsPage();
    ShoppingListPage shoppingListPage = shoppingListsDashboardPage.clickOpenList(listName);
    shoppingListPage.checkProductOnList(productNames.subList(0, 1), errors);
    shoppingListsDashboardPage = shoppingListPage.clickBackToLists();
    shoppingListsDashboardPage.removeList(listName);
  //  shoppingListsDashboardPage.checkListRemoved(listName, errors);
  //TODO Poprawić metodę  checkListRemoved
    checkConditions();
  }
}
