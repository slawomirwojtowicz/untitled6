package pl.ninebits.qa.eurocash.site.vendorportal.tests.logistics;

import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.VendorPortalUserRole;
import pl.ninebits.qa.eurocash.site.vendorportal.helpers.LoginHelper;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.AdminLogisticsPage;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.VendorDashboardPage;
import pl.ninebits.qa.eurocash.site.vendorportal.tests.VendorPortalTestTemplate;

public class C4662MacroRegionSearch extends VendorPortalTestTemplate {


  @Test(groups = {"production", "preprod", "uat"}, description = "check macro region search")
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C4662")
  public void macroRegionSearchTest() throws Exception {
    String macroRegion = "Sosnowiec";

    VendorPortalUserRole userRole = VendorPortalUserRole.ADMIN;
    VendorDashboardPage vendorDashboardPage = LoginHelper.doLogin(loadVendorLoginPage(), getVendorPortalUser(userRole));
    AdminLogisticsPage adminLogisticsPage = (AdminLogisticsPage) vendorDashboardPage.sideMenu.clickLogisticLink(userRole);

    adminLogisticsPage.spanMacroRegionSearchMenu();
    adminLogisticsPage.clickMacroRegionInSearchMenu(macroRegion);
    adminLogisticsPage.checkMacroRegions(macroRegion, errors);

    checkConditions();
  }

}
