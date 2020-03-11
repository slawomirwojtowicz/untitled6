package pl.ninebits.qa.eurocash.site.marketplace.tests.editproduct;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUserRole;
import pl.ninebits.qa.eurocash.api.users.VendorPortalUser;
import pl.ninebits.qa.eurocash.api.users.VendorPortalUserRole;
import pl.ninebits.qa.eurocash.site.marketplace.helpers.LoginMarketHelper;
import pl.ninebits.qa.eurocash.site.marketplace.pages.MarketplaceDashboardPage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.MarketplaceLoginPage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.ShoppingPage;
import pl.ninebits.qa.eurocash.site.marketplace.tests.MarketplaceTestTemplate;
import pl.ninebits.qa.eurocash.site.vendorportal.constants.Product;
import pl.ninebits.qa.eurocash.site.vendorportal.helpers.LoginHelper;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.LogisticsPage;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.ProductDetailsPage;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.ProductsPage;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.VendorDashboardPage;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.VendorLoginPage;

import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

@Slf4j
public class C3662UnpinLogistics extends MarketplaceTestTemplate {

  @Test(groups = {"uat", "dev"}, enabled = false) //todo: do poprawy
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C3662")
  public void unpinLogisticsTest() throws Exception {
    EhurtAppUser marketUser = getEhurtAppUser(EhurtAppUserRole.MARKETPLACE);
    VendorPortalUser vendorUser = getVendorPortalUser(VendorPortalUserRole.VENDOR);
    Product product = Product.JACK_DANIELS;
    val ean = product.getEAN();

    VendorLoginPage vLoginPage = loadVendorLoginPage();
    VendorDashboardPage vDashboardPage = LoginHelper.doLogin(vLoginPage, vendorUser);

    ProductsPage productsPage = (ProductsPage) vDashboardPage.sideMenu.clickProductsSideLink();
    productsPage.searchProduct(product);
    productsPage.checkProductPresent(ean);

    ProductDetailsPage detailsPage = productsPage.clickEditProduct(product);
    LogisticsPage logisticsPage = detailsPage.clickLogisticsLink();
    logisticsPage.checkLogisticsDataToUnpinPresent();
    logisticsPage.changeLogistics();
    logisticsPage.checkLogisticsUnpinned();
    logisticsPage.clickNextBtn();

    logisticsPage.logOut();
    int timeout = 10;
    log.info(MessageFormat.format("Waiting {0} minutes for change in logistics to be recalculated", timeout));
    TimeUnit.MINUTES.sleep(timeout);

    MarketplaceLoginPage mLoginPage = loadMarketplaceLoginPage();
    MarketplaceDashboardPage mDashboardPage = LoginMarketHelper.doLogin(mLoginPage, marketUser);

    ShoppingPage shoppingPage = mDashboardPage.header.clickShoppingBtn();
    shoppingPage.header.searchForProduct(ean);
    shoppingPage.checkNothingFound(errors);

    vLoginPage = loadVendorLoginPage();
    vDashboardPage = LoginHelper.doLogin(vLoginPage, vendorUser);
    productsPage = (ProductsPage) vDashboardPage.sideMenu.clickProductsSideLink();
    productsPage.searchProduct(product);
    productsPage.checkProductPresent(ean);
    detailsPage = productsPage.clickEditProduct(product);
    logisticsPage = detailsPage.clickLogisticsLink();
    logisticsPage.changeLogistics();
    logisticsPage.checkLogisticsPinned(errors);
    logisticsPage.clickNextBtn();

    productsPage = (ProductsPage) logisticsPage.sideMenu.clickProductsSideLink();
    productsPage.searchProduct(product);
    productsPage.activateProduct(errors);
    productsPage.checkProductActive(errors);

    checkConditions();
  }
}
