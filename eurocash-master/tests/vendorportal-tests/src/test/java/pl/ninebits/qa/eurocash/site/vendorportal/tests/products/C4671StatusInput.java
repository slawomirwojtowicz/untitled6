package pl.ninebits.qa.eurocash.site.vendorportal.tests.products;

import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.VendorPortalUserRole;
import pl.ninebits.qa.eurocash.site.vendorportal.constants.ProductStatus;
import pl.ninebits.qa.eurocash.site.vendorportal.helpers.LoginHelper;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.AdminProductsPage;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.VendorDashboardPage;
import pl.ninebits.qa.eurocash.site.vendorportal.tests.VendorPortalTestTemplate;

public class C4671StatusInput extends VendorPortalTestTemplate {

  @Test(groups = {"production", "preprod", "uat"}, description = "check products can be filtered by macro region")
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C4671")
  public void statusInputTest() throws Exception {
    VendorPortalUserRole role = VendorPortalUserRole.ADMIN;

    VendorDashboardPage vendorDashboardPage = LoginHelper.doLogin(loadVendorLoginPage(), getVendorPortalUser(role));
    AdminProductsPage productsPage = (AdminProductsPage) vendorDashboardPage.sideMenu.clickProductsSideLink(role);
    productsPage.openStatusMenu();
    productsPage.clickStatusInMenu(ProductStatus.INACTIVE);
    productsPage.checkProductsFilteredByStatus(ProductStatus.INACTIVE, errors);

    checkConditions();
  }
}
