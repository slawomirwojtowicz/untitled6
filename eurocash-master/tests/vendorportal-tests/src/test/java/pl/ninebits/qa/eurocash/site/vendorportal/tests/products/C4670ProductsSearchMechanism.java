package pl.ninebits.qa.eurocash.site.vendorportal.tests.products;

import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.VendorPortalUserRole;
import pl.ninebits.qa.eurocash.site.vendorportal.helpers.LoginHelper;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.AdminProductsPage;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.VendorDashboardPage;
import pl.ninebits.qa.eurocash.site.vendorportal.tests.VendorPortalTestTemplate;

public class C4670ProductsSearchMechanism extends VendorPortalTestTemplate {

  @Test(groups = {"production", "preprod", "uat"}, description = "check products can be filtered by name, ean and vendor")
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C4670")
  public void productsSearchMechanismTest() throws Exception {
    VendorPortalUserRole role = VendorPortalUserRole.ADMIN;

    VendorDashboardPage vendorDashboardPage = LoginHelper.doLogin(loadVendorLoginPage(), getVendorPortalUser(role));
    AdminProductsPage productsPage = (AdminProductsPage) vendorDashboardPage.sideMenu.clickProductsSideLink(role);

    String supplier = productsPage.getSupplier();
    productsPage.searchProducts(supplier);
    productsPage.checkProductsFilteredBySupplier(supplier, errors);
    productsPage.clearFilters();

    String ean = productsPage.getEan();
    productsPage.searchProducts(ean);
    productsPage.checkProductsFilteredByEan(ean, errors);
    productsPage.clearFilters();

    String productName = productsPage.getProductName();
    productsPage.searchProducts(productName);
    productsPage.checkProductsFilteredByProductName(productName, errors);
    productsPage.clearFilters();

    checkConditions();
  }
}
