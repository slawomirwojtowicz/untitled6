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
import pl.ninebits.qa.eurocash.site.vendorportal.pages.ProductDetailsPage;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.ProductOfferPage;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.ProductsPage;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.VendorDashboardPage;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.VendorLoginPage;

import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

@Slf4j
public class C3663UpdateProductPrice extends MarketplaceTestTemplate {

  @Test(groups = {"uat", "dev"}, enabled = false) //todo: do poprawy
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C3663")
  public void updateProductPriceTest() throws Exception {
    EhurtAppUser marketUser = getEhurtAppUser(EhurtAppUserRole.MARKETPLACE);
    VendorPortalUser vendorUser = getVendorPortalUser(VendorPortalUserRole.VENDOR);
    Product product = Product.STOCK_VODKA;
    val ean = product.getEAN();
    val mName = product.getMarketName();
    val productPrice = product.getPrice();

    VendorLoginPage vLoginPage = loadVendorLoginPage();
    VendorDashboardPage vDashboardPage = LoginHelper.doLogin(vLoginPage, vendorUser);

    ProductsPage productsPage = (ProductsPage) vDashboardPage.sideMenu.clickProductsSideLink();
    productsPage.searchProduct(product);
    productsPage.checkProductPresent(ean);

    ProductDetailsPage detailsPage = productsPage.clickEditProduct(product);
    ProductOfferPage offerPage = detailsPage.clickOfferLink();
    val newProductPrice = offerPage.editPrice(productPrice + 1);

    offerPage.clickNextBtn();
    vLoginPage = offerPage.logOut();
    int timeout = 10;
    log.info(MessageFormat.format("Waiting {0} minutes for change in product price to be recalculated", timeout));
    TimeUnit.MINUTES.sleep(timeout);

    MarketplaceLoginPage mLoginPage = loadMarketplaceLoginPage();
    MarketplaceDashboardPage mDashboardPage = LoginMarketHelper.doLogin(mLoginPage, marketUser);

    ShoppingPage shoppingPage = mDashboardPage.header.clickShoppingBtn();
    shoppingPage.header.searchForProduct(ean);
    shoppingPage.filterByMerchant("Test_1"); //todo: merchantName do excelka z userkami
    shoppingPage.checkProductFound(mName, errors);
    shoppingPage.checkProductPrice(mName, newProductPrice, errors);

    //cleanup
    vLoginPage = loadVendorLoginPage();
    vDashboardPage = LoginHelper.doLogin(vLoginPage, vendorUser);
    productsPage = (ProductsPage) vDashboardPage.sideMenu.clickProductsSideLink();
    productsPage.searchProduct(product);
    productsPage.checkProductPresent(ean);
    detailsPage = productsPage.clickEditProduct(product);
    offerPage = detailsPage.clickOfferLink();
    offerPage.editPrice(productPrice);
    offerPage.clickNextBtn();

    checkConditions();
  }
}
