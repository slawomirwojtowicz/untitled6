package pl.ninebits.qa.eurocash.site.vendorportal.tests.onboarding;

import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.VendorPortalUserRole;
import pl.ninebits.qa.eurocash.site.vendorportal.helpers.LoginHelper;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.VendorDashboardPage;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.VendorsPage;
import pl.ninebits.qa.eurocash.site.vendorportal.tests.VendorPortalTestTemplate;

public class C4571ListingSortByNameDescending extends VendorPortalTestTemplate {

  /**
   * Test wyłaczony ponieważ dane się źle sortuja
   */
  @Test(groups = {"production", "preprod", "uat"}, description = "vendor onboarding - sort by name descending", enabled = false)
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C4571")
  public void listingSortByNameDescendingTest() throws Exception {

    VendorDashboardPage vendorDashboardPage = LoginHelper.doLogin(loadVendorLoginPage(), getVendorPortalUser(VendorPortalUserRole.ADMIN));
    VendorsPage vendorsPage = vendorDashboardPage.clickVendorsIcon();
    vendorsPage.sortByName();
    vendorsPage.sortByName();
    vendorsPage.checkDataSortedByNameDescending(errors);

    checkConditions();
  }
}