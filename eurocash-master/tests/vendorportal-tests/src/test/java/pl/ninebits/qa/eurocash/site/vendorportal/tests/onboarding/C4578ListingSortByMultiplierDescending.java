package pl.ninebits.qa.eurocash.site.vendorportal.tests.onboarding;

import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.VendorPortalUserRole;
import pl.ninebits.qa.eurocash.site.vendorportal.helpers.LoginHelper;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.VendorDashboardPage;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.VendorsPage;
import pl.ninebits.qa.eurocash.site.vendorportal.tests.VendorPortalTestTemplate;

public class C4578ListingSortByMultiplierDescending extends VendorPortalTestTemplate {

  @Test(groups = {"production", "preprod", "uat"}, description = "vendor onboarding - sort by multiplier descending")
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C4578")
  public void listingSortByMultiplierDescendingTest() throws Exception {

    VendorDashboardPage vendorDashboardPage = LoginHelper.doLogin(loadVendorLoginPage(), getVendorPortalUser(VendorPortalUserRole.ADMIN));
    VendorsPage vendorsPage = vendorDashboardPage.clickVendorsIcon();
    vendorsPage.sortByMultiplier();
    vendorsPage.sortByMultiplier();
    vendorsPage.checkDataSortedByMultiplierDescending(errors);

    checkConditions();
  }
}
