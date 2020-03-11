package pl.ninebits.qa.eurocash.site.vendorportal.tests.logistics;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.VendorPortalUserRole;
import pl.ninebits.qa.eurocash.site.vendorportal.helpers.LoginHelper;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.AdminLogisticsPage;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.VendorDashboardPage;
import pl.ninebits.qa.eurocash.site.vendorportal.tests.VendorPortalTestTemplate;

@Slf4j
public class C4661VendorsSearch extends VendorPortalTestTemplate {

  @Test(groups = {"production", "preprod", "uat"}, description = "check vendors search")
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C4661")
  public void vendorsSearchTest() throws Exception {
    VendorPortalUserRole userRole = VendorPortalUserRole.ADMIN;
    VendorDashboardPage vendorDashboardPage = LoginHelper.doLogin(loadVendorLoginPage(), getVendorPortalUser(userRole));
    AdminLogisticsPage adminLogisticsPage = (AdminLogisticsPage) vendorDashboardPage.sideMenu.clickLogisticLink(userRole);

    String vendor = adminLogisticsPage.getVendorName();
    log.info(String.format("Working on vendor %s", vendor));
    int initialVendorCount = adminLogisticsPage.getVendorCount();
    adminLogisticsPage.spanNameSearchInput();
    adminLogisticsPage.typeVendorNameInSearchInput(vendor);
    adminLogisticsPage.checkVendorsAutoCompletedInSelectMenu(vendor, errors);
    adminLogisticsPage.clickVendorInSelect(vendor);
    adminLogisticsPage.checkVendorsFound(vendor, errors);

    adminLogisticsPage.spanNameSearchInput();
    adminLogisticsPage.typeVendorNameInSearchInput("NoSuchVendorPresent");
    adminLogisticsPage.checkVendorsNotAutoCompletedInSelectMenu(errors);
    adminLogisticsPage.clickVendorInSelect("Wszyscy");
    adminLogisticsPage.checkVendorCount(initialVendorCount, errors);

    checkConditions();
  }
}
