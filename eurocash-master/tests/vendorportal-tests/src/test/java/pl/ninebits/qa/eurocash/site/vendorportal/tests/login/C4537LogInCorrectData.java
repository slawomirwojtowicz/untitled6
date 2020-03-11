package pl.ninebits.qa.eurocash.site.vendorportal.tests.login;

import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.VendorPortalUser;
import pl.ninebits.qa.eurocash.site.vendorportal.helpers.LoginHelper;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.VendorDashboardPage;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.VendorLoginPage;
import pl.ninebits.qa.eurocash.site.vendorportal.tests.VendorPortalTestTemplate;

public class C4537LogInCorrectData extends VendorPortalTestTemplate {

  @Test(groups = {"production", "preprod", "uat"}, description = "log in user to vendor portal")
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C4537")
  public void logInCorrectDataTest() throws Exception {
    VendorPortalUser user = getVendorPortalUser();

    VendorLoginPage vendorLoginPage = loadVendorLoginPage();
    VendorDashboardPage vendorDashboardPage = LoginHelper.doLogin(vendorLoginPage, user);
    vendorDashboardPage.checkUserLoggedIn(errors);

    checkConditions();
  }
}