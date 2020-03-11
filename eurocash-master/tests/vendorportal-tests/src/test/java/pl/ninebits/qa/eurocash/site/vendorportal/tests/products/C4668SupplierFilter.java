package pl.ninebits.qa.eurocash.site.vendorportal.tests.products;

import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.VendorPortalUserRole;
import pl.ninebits.qa.eurocash.site.vendorportal.helpers.LoginHelper;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.AdminProductsPage;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.VendorDashboardPage;
import pl.ninebits.qa.eurocash.site.vendorportal.tests.VendorPortalTestTemplate;

public class C4668SupplierFilter extends VendorPortalTestTemplate {

  @Test(groups = {"production", "preprod", "uat"}, description = "check products can be filtered by supplier")
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C4668")
  public void supplierFilterTest() throws Exception {
    VendorPortalUserRole role = VendorPortalUserRole.ADMIN;

    VendorDashboardPage vendorDashboardPage = LoginHelper.doLogin(loadVendorLoginPage(), getVendorPortalUser(role));
    AdminProductsPage productsPage = (AdminProductsPage) vendorDashboardPage.sideMenu.clickProductsSideLink(role);
    String supplier = productsPage.getSupplier();

    productsPage.openSuppliersMenu();
    String supplierFromMenu = productsPage.getSupplierFromMenu();
    productsPage.clickSupplierInMenu(supplierFromMenu);
    productsPage.checkProductsFilteredBySupplier(supplierFromMenu, errors);
    productsPage.clearFilters();

    productsPage.openSuppliersMenu();
    productsPage.searchForSupplierInMenuInput(supplier);
    productsPage.checkProductsFilteredBySupplier(supplier, errors);

    checkConditions();
  }
}
