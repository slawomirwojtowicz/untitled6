package pl.ninebits.qa.eurocash.site.vendorportal.tests.dashboard;

import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.VendorPortalUserRole;
import pl.ninebits.qa.eurocash.site.vendorportal.constants.DashboardBoxPeriod;
import pl.ninebits.qa.eurocash.site.vendorportal.helpers.LoginHelper;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.VendorDashboardPage;
import pl.ninebits.qa.eurocash.site.vendorportal.tests.VendorPortalTestTemplate;

import java.math.BigDecimal;

public class C4558AverageCartValueBoxFilter extends VendorPortalTestTemplate {

  @Test(groups = {"production", "preprod", "uat"}, description = "dashboard - check average cart value box values can be filtered")
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C4558")
  public void averageCartValueBoxFilterTest() throws Exception {
    VendorDashboardPage vendorDashboardPage = LoginHelper.doLogin(loadVendorLoginPage(), getVendorPortalUser(VendorPortalUserRole.ADMIN));
    vendorDashboardPage.checkAverageCartValueBox(errors);

    BigDecimal averageCartVal = vendorDashboardPage.getAverageCartVal();
    vendorDashboardPage.changeAverageCartValuePeriod(DashboardBoxPeriod.LAST_30_DAYS);
    vendorDashboardPage.checkAverageCartValChanged(averageCartVal, errors);

    checkConditions();
  }
}
