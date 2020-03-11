package pl.ninebits.qa.eurocash.site.vendorportal.tests.dashboard;

import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.VendorPortalUserRole;
import pl.ninebits.qa.eurocash.site.vendorportal.helpers.LoginHelper;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.EditComplaintPage;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.VendorDashboardPage;
import pl.ninebits.qa.eurocash.site.vendorportal.tests.VendorPortalTestTemplate;

public class C4560OpenComplaint extends VendorPortalTestTemplate {

  @Test(groups = {"production", "preprod", "uat"}, description = "dashboard - check can open complaint")
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C4560")
  public void openComplaintTest() throws Exception {
    VendorDashboardPage vendorDashboardPage = LoginHelper.doLogin(loadVendorLoginPage(), getVendorPortalUser(VendorPortalUserRole.ADMIN));

    String complaintNumber = vendorDashboardPage.getComplaintNumberFromComplaintsBox();
    EditComplaintPage editComplaintPage = vendorDashboardPage.clickComplaintInComplaintsBox();
    editComplaintPage.checkComplaintOpened(complaintNumber, errors);

    checkConditions();
  }
}
