package pl.ninebits.qa.eurocash.site.vendorportal.tests.dashboard;

import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.VendorPortalUserRole;
import pl.ninebits.qa.eurocash.site.vendorportal.helpers.LoginHelper;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.VendorDashboardPage;
import pl.ninebits.qa.eurocash.site.vendorportal.tests.VendorPortalTestTemplate;

public class C4549ActiveDealsBox extends VendorPortalTestTemplate {

  @Test(groups = {"production", "preprod", "uat"}, description = "dashboard - check active deals box exists")
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C4549")
  public void activeDealsBoxTest() throws Exception {
    VendorDashboardPage vendorDashboardPage = LoginHelper.doLogin(loadVendorLoginPage(), getVendorPortalUser(VendorPortalUserRole.VENDOR));
    vendorDashboardPage.checkActiveDealsBox(errors);

    checkConditions();
  }
}
