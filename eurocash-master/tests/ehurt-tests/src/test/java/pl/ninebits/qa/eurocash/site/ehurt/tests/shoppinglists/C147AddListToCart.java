package pl.ninebits.qa.eurocash.site.ehurt.tests.shoppinglists;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.site.ehurt.helpers.EhurtAppLoginHelper;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtHomePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtStartPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.CartStep1Page;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.ShoppingListEditPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.ShoppingListPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.ShoppingListsDashboardPage;
import pl.ninebits.qa.eurocash.site.ehurt.tests.EhurtTestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class C147AddListToCart extends EhurtTestTemplate {

  @Test(groups = {"production", "preprod", "uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C147")
  public void addListToCartTest() throws Exception {
    final String listName = RandomStringUtils.random(10, true, true);
    final List<String> searchPhrases = Arrays.asList("WODKA", "WINO", "GIN");
    final String packageAmount = "10";

    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    ShoppingListsDashboardPage shoppingListsDashboardPage = homePage.openShoppingListsPage();
    shoppingListsDashboardPage.removeLists();

    ShoppingListEditPage shoppingListEditPage = shoppingListsDashboardPage.clickNewShoppingListBtn();
    shoppingListEditPage.enterListName(listName);
    shoppingListEditPage.checkListNamePresentInHeader(listName, errors);
    shoppingListEditPage.clickSaveListBtn();

    List<String> barCodes = new ArrayList<>();
    for (String searchPhrase : searchPhrases) {
      shoppingListEditPage.addProductsToList(searchPhrase, packageAmount, barCodes, errors);
    }

    shoppingListsDashboardPage = shoppingListEditPage.clickBackToAllListsBtn();
    shoppingListsDashboardPage.checkListPresent(listName, errors);
    ShoppingListPage shoppingListPage = shoppingListsDashboardPage.clickOpenList(listName);

    shoppingListPage.selectAllProducts();
    shoppingListPage.clickAddToCart();
    CartStep1Page cartStep1Page = shoppingListPage.clickGoToCart();
    cartStep1Page.checkProductsInCartByBarcode(barCodes, errors);

    cartStep1Page.clearCart();
    homePage = cartStep1Page.goToHomePage();
    shoppingListsDashboardPage = homePage.openShoppingListsPage();
    shoppingListsDashboardPage.removeList(listName);
    shoppingListsDashboardPage.checkListRemoved(listName, errors);

    checkConditions();
  }
}
