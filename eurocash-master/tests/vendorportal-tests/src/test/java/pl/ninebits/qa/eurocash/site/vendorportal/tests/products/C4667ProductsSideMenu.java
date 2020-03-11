package pl.ninebits.qa.eurocash.site.vendorportal.tests.products;

import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.VendorPortalUserRole;
import pl.ninebits.qa.eurocash.site.vendorportal.constants.ProductStatus;
import pl.ninebits.qa.eurocash.site.vendorportal.helpers.LoginHelper;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.AdminProductsPage;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.VendorDashboardPage;
import pl.ninebits.qa.eurocash.site.vendorportal.tests.VendorPortalTestTemplate;

public class C4667ProductsSideMenu extends VendorPortalTestTemplate {

  @Test(groups = {"production", "preprod", "uat"}, description = "check products side menu sorting links")
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C4667")
  public void productsSideMenuTest() throws Exception {
    ProductStatus[] statuses = ProductStatus.values();
    VendorPortalUserRole role = VendorPortalUserRole.ADMIN;

    VendorDashboardPage vendorDashboardPage = LoginHelper.doLogin(loadVendorLoginPage(), getVendorPortalUser(role));
    AdminProductsPage productsPage = (AdminProductsPage) vendorDashboardPage.sideMenu.clickProductsSideLink(role);
    productsPage.sideMenu.openProductsStatuses();

    for (ProductStatus status : statuses) {
      productsPage.sideMenu.clickStatusLink(status);
      productsPage.checkProductsFilteredByStatus(status, errors);
    }

    checkConditions();
  }
}
