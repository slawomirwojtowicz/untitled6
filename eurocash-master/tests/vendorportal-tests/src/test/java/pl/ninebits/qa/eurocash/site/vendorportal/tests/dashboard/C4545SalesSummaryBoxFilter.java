package pl.ninebits.qa.eurocash.site.vendorportal.tests.dashboard;

import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.VendorPortalUserRole;
import pl.ninebits.qa.eurocash.site.vendorportal.constants.DashboardBoxPeriod;
import pl.ninebits.qa.eurocash.site.vendorportal.helpers.LoginHelper;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.VendorDashboardPage;
import pl.ninebits.qa.eurocash.site.vendorportal.tests.VendorPortalTestTemplate;

import java.math.BigDecimal;

public class C4545SalesSummaryBoxFilter extends VendorPortalTestTemplate {

  @Test(groups = {"production", "preprod", "uat"}, description = "dashboard - check sales summary box filter")
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C4545")
  public void salesSummaryBoxFilterTest() throws Exception {
    VendorDashboardPage vendorDashboardPage = LoginHelper.doLogin(loadVendorLoginPage(), getVendorPortalUser(VendorPortalUserRole.ADMIN));
    vendorDashboardPage.checkSalesSummaryBox(errors);

    BigDecimal salesSummaryVal = vendorDashboardPage.getSalesSummaryVal();
    vendorDashboardPage.changeSalesSummaryPeriod(DashboardBoxPeriod.LAST_30_DAYS);
    vendorDashboardPage.checkSummarySalesValChanged(salesSummaryVal, errors);
    
    checkConditions();
  }
}
