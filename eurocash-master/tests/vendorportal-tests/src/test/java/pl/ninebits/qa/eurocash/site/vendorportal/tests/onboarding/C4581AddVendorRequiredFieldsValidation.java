package pl.ninebits.qa.eurocash.site.vendorportal.tests.onboarding;

import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.VendorPortalUserRole;
import pl.ninebits.qa.eurocash.site.vendorportal.helpers.LoginHelper;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.AddVendorPage;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.VendorDashboardPage;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.VendorsPage;
import pl.ninebits.qa.eurocash.site.vendorportal.tests.VendorPortalTestTemplate;

public class C4581AddVendorRequiredFieldsValidation extends VendorPortalTestTemplate {

  @Test(groups = {"production", "preprod", "uat"}, description = "vendor onboarding - check required data to add new vendor")
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C4581")
  public void addVendorRequiredFieldsValidationTest() throws Exception {

    VendorDashboardPage vendorDashboardPage = LoginHelper.doLogin(loadVendorLoginPage(), getVendorPortalUser(VendorPortalUserRole.ADMIN));
    VendorsPage vendorsPage = vendorDashboardPage.clickVendorsIcon();

    AddVendorPage addVendorPage = vendorsPage.clickAddVendorBtn();
    addVendorPage.clickSaveBtn();
    addVendorPage.checkDataRequiredValidationsPresent(errors);

    checkConditions();
  }
}
