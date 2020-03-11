package pl.ninebits.qa.eurocash.site.vendorportal.tests.dashboard;

import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.VendorPortalUserRole;
import pl.ninebits.qa.eurocash.site.vendorportal.helpers.LoginHelper;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.VendorDashboardPage;
import pl.ninebits.qa.eurocash.site.vendorportal.tests.VendorPortalTestTemplate;

public class C4548NewOrdersBox extends VendorPortalTestTemplate {

  @Test(groups = {"production", "preprod", "uat"}, description = "dashboard - check new orders box exists")
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C4548")
  public void newOrdersBoxTest() throws Exception {
    VendorDashboardPage vendorDashboardPage = LoginHelper.doLogin(loadVendorLoginPage(), getVendorPortalUser(VendorPortalUserRole.ADMIN));
    vendorDashboardPage.checkNewOrdersBox(errors);

    checkConditions();
  }
}