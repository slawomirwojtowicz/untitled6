package pl.ninebits.qa.eurocash.site.marketplace.tests.shoppinglists;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUserRole;
import pl.ninebits.qa.eurocash.site.marketplace.forms.ListForm;
import pl.ninebits.qa.eurocash.site.marketplace.helpers.LoginMarketHelper;
import pl.ninebits.qa.eurocash.site.marketplace.pages.MarketplaceDashboardPage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.MarketplaceLoginPage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.ShoppingListPage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.ShoppingPage;
import pl.ninebits.qa.eurocash.site.marketplace.tests.MarketplaceTestTemplate;

public class C3669AddProductToListFromSearch extends MarketplaceTestTemplate {

  @Test(groups = {"uat", "dev"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C3669")
  public void addProductToListFromSearchTest() throws Exception {
    String listName = "TestyAutomatyczne" + RandomStringUtils.randomAlphabetic(9);

    EhurtAppUser marketUser = getEhurtAppUser(EhurtAppUserRole.MARKETPLACE);
    MarketplaceLoginPage mLoginPage = loadMarketplaceLoginPage();
    MarketplaceDashboardPage mDashboardPage = LoginMarketHelper.doLogin(mLoginPage, marketUser);
    ShoppingListPage shoppingListPage = mDashboardPage.clickShoppinListgBtn();
    shoppingListPage.removeLists();

    ListForm listForm = shoppingListPage.clickNewShoppingLisBtn();
    listForm.typeListQuery(listName);
    listForm.clickAddList();
    listForm.clickCloseWindowBtn();

    ShoppingPage shoppingPage = mDashboardPage.header.clickShoppingBtn();
    shoppingPage.clickListOnFirstProduct();
    listForm.clickListBtn(listName);
    listForm.clickCloseWindowBtn();
    String productTitle = shoppingPage.getFirstProductTitle();

    shoppingListPage = mDashboardPage.clickShoppinListgBtn();
    shoppingListPage.checkProductNamePresent(productTitle, errors);

    checkConditions();
  }
}