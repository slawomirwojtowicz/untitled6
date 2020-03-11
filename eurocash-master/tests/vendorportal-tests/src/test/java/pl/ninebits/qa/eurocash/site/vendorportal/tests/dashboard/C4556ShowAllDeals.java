package pl.ninebits.qa.eurocash.site.vendorportal.tests.dashboard;

import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.VendorPortalUserRole;
import pl.ninebits.qa.eurocash.site.vendorportal.helpers.LoginHelper;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.GroupOffersPage;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.VendorDashboardPage;
import pl.ninebits.qa.eurocash.site.vendorportal.tests.VendorPortalTestTemplate;

public class C4556ShowAllDeals extends VendorPortalTestTemplate {

  @Test(groups = {"production", "preprod", "uat"}, description = "dashboard - check can show all deals for user in active deals box")
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C4556")
  public void showAllDealsTest() throws Exception {
    VendorDashboardPage vendorDashboardPage = LoginHelper.doLogin(loadVendorLoginPage(), getVendorPortalUser(VendorPortalUserRole.ADMIN));
    GroupOffersPage groupOffersPage = vendorDashboardPage.clickShowAllDeals();
    groupOffersPage.checkGroupOffersPagePresent(errors);

    checkConditions();
  }
}
