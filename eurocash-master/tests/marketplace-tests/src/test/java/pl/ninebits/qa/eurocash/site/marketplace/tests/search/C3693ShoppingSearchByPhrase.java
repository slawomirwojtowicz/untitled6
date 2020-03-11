package pl.ninebits.qa.eurocash.site.marketplace.tests.search;

import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUserRole;
import pl.ninebits.qa.eurocash.site.marketplace.forms.ShoppingMenu;
import pl.ninebits.qa.eurocash.site.marketplace.helpers.LoginMarketHelper;
import pl.ninebits.qa.eurocash.site.marketplace.pages.MarketplaceDashboardPage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.ProductPage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.ShoppingPage;
import pl.ninebits.qa.eurocash.site.marketplace.tests.MarketplaceTestTemplate;

public class C3693ShoppingSearchByPhrase extends MarketplaceTestTemplate {

  @Test(groups = {"uat", "dev"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C3693")
  public void shoppingSearchByPhraseTest() throws Exception {
    MarketplaceDashboardPage dashboardPage = LoginMarketHelper.doLogin(loadMarketplaceLoginPage(), getEhurtAppUser(EhurtAppUserRole.MARKETPLACE));
    ShoppingPage shoppingPage = dashboardPage.header.clickShoppingBtn();

    String productName = shoppingPage.getProductName();
    String producer = shoppingPage.getProducer();

    ProductPage productPage = shoppingPage.clickFirstProduct();
    String ean = productPage.getEan();

    ShoppingMenu shoppingMenu = productPage.header.spanShoppingMenu();
    shoppingMenu.typeSearchQuery(productName);
    shoppingPage.checkProductFound(productName, errors);

    shoppingMenu = productPage.header.spanShoppingMenu();
    shoppingMenu.typeSearchQuery(producer);
    shoppingPage.checkProductsFoundByProducer(producer, errors);

    shoppingMenu = productPage.header.spanShoppingMenu();
    shoppingMenu.typeSearchQuery(ean);
    shoppingPage.checkOneProductFound(errors);
    productPage = shoppingPage.clickFirstProduct();
    productPage.checkProductEan(ean, errors);

    checkConditions();
  }
}
