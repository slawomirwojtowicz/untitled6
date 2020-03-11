package pl.ninebits.qa.eurocash.site.vendorportal.tests.promotions;

import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.VendorPortalUserRole;
import pl.ninebits.qa.eurocash.site.vendorportal.constants.PromotionType;
import pl.ninebits.qa.eurocash.site.vendorportal.helpers.LoginHelper;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.AdminPromotionsPage;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.VendorDashboardPage;
import pl.ninebits.qa.eurocash.site.vendorportal.tests.VendorPortalTestTemplate;

public class C4692SearchPromotionType extends VendorPortalTestTemplate {

  @Test(groups = {"production", "preprod", "uat"}, description = "check promotions can be filtered by type")
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C4692")
  public void searchPromotionTypeTest() throws Exception {
    PromotionType[] promotionTypes = PromotionType.values();
    VendorPortalUserRole role = VendorPortalUserRole.ADMIN;

    VendorDashboardPage vendorDashboardPage = LoginHelper.doLogin(loadVendorLoginPage(), getVendorPortalUser(role));
    AdminPromotionsPage promotionsPage = (AdminPromotionsPage) vendorDashboardPage.sideMenu.clickPromotionsSideLink(role);

    for (PromotionType promotionType : promotionTypes) {
      promotionsPage.openPromotionTypeMenu();
      promotionsPage.clickPromotionType(promotionType);
      promotionsPage.checkResultsFilteredByPromotionType(promotionType, errors);
    }

    checkConditions();
  }
}
