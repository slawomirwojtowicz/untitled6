package pl.ninebits.qa.eurocash.site.ehurt.tests.shoppinglists;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.site.ehurt.helpers.EhurtAppLoginHelper;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtHomePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtStartPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.ShoppingListEditPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.ShoppingListsDashboardPage;
import pl.ninebits.qa.eurocash.site.ehurt.tests.EhurtTestTemplate;

public class C144AddNewShoppingList extends EhurtTestTemplate {

  @Test(groups = {"production", "preprod", "uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C144")
  public void addNewShoppingListTest() throws Exception {
    final String listName = RandomStringUtils.random(10, true, true);

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
    shoppingListsDashboardPage.checkListPresent(listName, errors);
    shoppingListsDashboardPage.removeList(listName);
    shoppingListsDashboardPage.checkListRemoved(listName, errors);

    checkConditions();
  }
}
