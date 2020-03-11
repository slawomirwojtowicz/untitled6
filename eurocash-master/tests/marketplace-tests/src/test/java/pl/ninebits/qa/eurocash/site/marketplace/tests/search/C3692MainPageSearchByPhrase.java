package pl.ninebits.qa.eurocash.site.marketplace.tests.search;

import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUserRole;
import pl.ninebits.qa.eurocash.site.marketplace.helpers.LoginMarketHelper;
import pl.ninebits.qa.eurocash.site.marketplace.pages.MarketplaceDashboardPage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.ProductPage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.ShoppingPage;
import pl.ninebits.qa.eurocash.site.marketplace.tests.MarketplaceTestTemplate;

public class C3692MainPageSearchByPhrase extends MarketplaceTestTemplate {

  @Test(groups = {"uat", "dev"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C3692")
  public void mainPageSearchByPhraseTest() throws Exception {
    MarketplaceDashboardPage dashboardPage = LoginMarketHelper.doLogin(loadMarketplaceLoginPage(), getEhurtAppUser(EhurtAppUserRole.MARKETPLACE));
    ShoppingPage shoppingPage = dashboardPage.header.clickShoppingBtn();

    String productName = shoppingPage.getProductName();
    String producer = shoppingPage.getProducer();

    ProductPage productPage = shoppingPage.clickFirstProduct();
    String ean = productPage.getEan();

    shoppingPage = productPage.header.searchForProduct(productName);
    shoppingPage.checkProductFound(productName, errors);

    shoppingPage = productPage.header.searchForProduct(producer);
    shoppingPage.checkProductsFoundByProducer(producer, errors);

    shoppingPage = productPage.header.searchForProduct(ean);
    shoppingPage.checkOneProductFound(errors);
    productPage = shoppingPage.clickFirstProduct();
    productPage.checkProductEan(ean, errors);

    checkConditions();
  }
}
