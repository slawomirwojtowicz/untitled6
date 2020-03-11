package pl.ninebits.qa.eurocash.site.vendorportal.tests.login;

import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.VendorLoginPage;
import pl.ninebits.qa.eurocash.site.vendorportal.tests.VendorPortalTestTemplate;

public class C4538LogInIncorrectData extends VendorPortalTestTemplate {

  @Test(groups = {"production", "preprod", "uat"}, description = "log in user to vendor portal with incorrect credentials")
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C4537")
  public void logInIncorrectDataTest() throws Exception {
    VendorLoginPage vendorLoginPage = loadVendorLoginPage();
    vendorLoginPage.typeLogin("madatst@test.pl");
    vendorLoginPage.typePassword("DisPassIsBad1234!@#@");
    vendorLoginPage.clickLoginBtn();
    vendorLoginPage.checkIncorrectCredentialsMsgPresent(errors);

    checkConditions();
  }
}
