package pl.ninebits.qa.eurocash.site.vendorportal.tests.promotions;

import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.VendorPortalUserRole;
import pl.ninebits.qa.eurocash.site.vendorportal.constants.PromotionStatus;
import pl.ninebits.qa.eurocash.site.vendorportal.helpers.LoginHelper;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.AdminPromotionsPage;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.VendorDashboardPage;
import pl.ninebits.qa.eurocash.site.vendorportal.tests.VendorPortalTestTemplate;

public class C4693SearchPromotionStatus extends VendorPortalTestTemplate {

  @Test(groups = {"production", "preprod", "uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C4693")
  public void searchPromotionStatusTest() throws Exception {
    PromotionStatus[] statuses = PromotionStatus.values();
    VendorPortalUserRole role = VendorPortalUserRole.ADMIN;

    VendorDashboardPage vendorDashboardPage = LoginHelper.doLogin(loadVendorLoginPage(), getVendorPortalUser(role));
    AdminPromotionsPage promotionsPage = (AdminPromotionsPage) vendorDashboardPage.sideMenu.clickPromotionsSideLink(role);

    for (PromotionStatus status : statuses) {
      promotionsPage.openPromotionStatusMenu();
      promotionsPage.clickStatusType(status);
      promotionsPage.checkResultsFilteredByPromotionStatus(status, errors);
    }

    checkConditions();
  }
}