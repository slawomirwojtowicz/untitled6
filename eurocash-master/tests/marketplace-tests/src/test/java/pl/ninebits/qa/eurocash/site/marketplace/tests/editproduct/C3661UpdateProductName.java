package pl.ninebits.qa.eurocash.site.marketplace.tests.editproduct;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.api.users.VendorPortalUser;
import pl.ninebits.qa.eurocash.site.marketplace.tests.MarketplaceTestTemplate;
import pl.ninebits.qa.eurocash.site.vendorportal.constants.Product;
import pl.ninebits.qa.eurocash.site.vendorportal.helpers.LoginHelper;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.ProductDetailsPage;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.ProductsPage;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.VendorDashboardPage;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.VendorLoginPage;

import java.text.MessageFormat;


@Slf4j
public class C3661UpdateProductName extends MarketplaceTestTemplate {

  @Test(groups = {"uat", "dev"}, enabled = false)
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C3661")
  public void updateProductNameTest() throws Exception {
    EhurtAppUser marketUser = getEhurtAppUser();
    VendorPortalUser vendorUser = getVendorPortalUser();

    VendorLoginPage vLoginPage = loadVendorLoginPage();
    VendorDashboardPage vDashboardPage = LoginHelper.doLogin(vLoginPage, vendorUser);

    ProductsPage productsPage = (ProductsPage) vDashboardPage.sideMenu.clickProductsSideLink();
    Product product = Product.STOCK_VODKA;
    productsPage.searchProduct(product);
    productsPage.checkProductPresent(product.getEAN());

    ProductDetailsPage detailsPage = productsPage.clickEditProduct(product);
    val productName = detailsPage.getProductName().equals(product.getName()) ? detailsPage.getProductName() : product.getName();
    log.info(MessageFormat.format("Edytowana nazwa produktu {0} {1}", product.getEAN(), productName));

    val newProductName = productName.concat("_tests");
    detailsPage.editProductName(newProductName, errors);
    /**
     * TODO: do ustalenia dane testowe
     */



    checkConditions();
  }
}
