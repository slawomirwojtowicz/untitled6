package pl.ninebits.qa.eurocash.site.marketplace.tests.search;

import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUserRole;
import pl.ninebits.qa.eurocash.site.marketplace.helpers.LoginMarketHelper;
import pl.ninebits.qa.eurocash.site.marketplace.pages.MarketplaceDashboardPage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.ShoppingPage;
import pl.ninebits.qa.eurocash.site.marketplace.tests.MarketplaceTestTemplate;

public class C3702Facets extends MarketplaceTestTemplate {

  @Test(groups = {"uat", "dev"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C3702")
  public void facetsTest() throws Exception {
    MarketplaceDashboardPage dashboardPage = LoginMarketHelper.doLogin(loadMarketplaceLoginPage(), getEhurtAppUser(EhurtAppUserRole.MARKETPLACE));
    ShoppingPage shoppingPage = dashboardPage.header.clickShoppingBtn();

    String producer = shoppingPage.getRandomProducerFromFacet();
    shoppingPage.clickProducerFilterCheckBox(producer);
    shoppingPage.checkProductsFoundByProducer(producer, errors);
    shoppingPage.clearFilters();

    String brand = shoppingPage.getRandomBrandFromFacet();
    shoppingPage.filterByBrand(brand);
    shoppingPage.checkProductsFoundByBrand(brand, errors);

    checkConditions();
  }
}
