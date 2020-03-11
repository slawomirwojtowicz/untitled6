package pl.ninebits.qa.eurocash.site.ehurt.tests.shoppinglists;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.site.ehurt.helpers.EhurtAppLoginHelper;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtHomePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtStartPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.ShoppingListEditPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.ShoppingListPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.ShoppingListsDashboardPage;
import pl.ninebits.qa.eurocash.site.ehurt.tests.EhurtTestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class C160RemoveSelectedProductsFromList extends EhurtTestTemplate {

  @Test(groups = {"production", "preprod", "uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C160")
  public void removeSelectedProductsFromListTest() throws Exception {
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
    shoppingListPage.clickSelectProducts(2);
    int listSize = shoppingListPage.getListSize();
    shoppingListPage.clickRemoveSelected();
    int listSizeAfterDeletion = shoppingListPage.getListSize();
    shoppingListPage.checkProductsRemoved(listSize, listSizeAfterDeletion, 2, errors);

    shoppingListsDashboardPage = shoppingListPage.clickBackToLists();
    shoppingListsDashboardPage.removeLists();

    checkConditions();
  }
}
