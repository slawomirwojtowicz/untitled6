package pl.ninebits.qa.eurocash.site.vendorportal.tests.productgroups;

import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.VendorPortalUserRole;
import pl.ninebits.qa.eurocash.site.vendorportal.helpers.LoginHelper;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.ProductGroupsPage;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.VendorDashboardPage;
import pl.ninebits.qa.eurocash.site.vendorportal.tests.VendorPortalTestTemplate;

public class C4680ProductGroupsSearch extends VendorPortalTestTemplate {

  @Test(groups = {"production", "preprod", "uat"}, description = "check product groups search")
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C4680")
  public void productGroupsSearchTest() throws Exception {
    VendorDashboardPage vendorDashboardPage = LoginHelper.doLogin(loadVendorLoginPage(), getVendorPortalUser(VendorPortalUserRole.ADMIN));
    ProductGroupsPage productGroupsPage = vendorDashboardPage.sideMenu.clickProductGroupsSideLink();

    int groupsCount = productGroupsPage.getProductGroupsCount();
    String groupName = productGroupsPage.getGroupName();
    productGroupsPage.searchForGroup(groupName);
    productGroupsPage.checkGroupFound(groupName, errors);

    productGroupsPage.clearSearch(groupName);
    productGroupsPage.checkAllGroupsPresent(groupsCount, errors);

    checkConditions();
  }
}
