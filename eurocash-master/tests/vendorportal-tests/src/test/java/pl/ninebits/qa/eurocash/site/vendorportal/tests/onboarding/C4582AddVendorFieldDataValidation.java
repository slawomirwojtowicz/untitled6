package pl.ninebits.qa.eurocash.site.vendorportal.tests.onboarding;

import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.VendorPortalUserRole;
import pl.ninebits.qa.eurocash.site.vendorportal.helpers.LoginHelper;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.AddVendorPage;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.VendorDashboardPage;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.VendorsPage;
import pl.ninebits.qa.eurocash.site.vendorportal.tests.VendorPortalTestTemplate;

public class C4582AddVendorFieldDataValidation extends VendorPortalTestTemplate {

  @Test(groups = {"production", "preprod", "uat"}, description = "vendor onboarding - check data validations in new vendor form")
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C4582")
  public void addVendorFieldDataValidationTest() throws Exception {

    VendorDashboardPage vendorDashboardPage = LoginHelper.doLogin(loadVendorLoginPage(), getVendorPortalUser(VendorPortalUserRole.ADMIN));
    VendorsPage vendorsPage = vendorDashboardPage.clickVendorsIcon();

    AddVendorPage addVendorPage = vendorsPage.clickAddVendorBtn();
    addVendorPage.typeEmail("1");
    addVendorPage.checkWrongEmailMsgPresent(errors);

    addVendorPage.typePostalCode("1");
    addVendorPage.checkWrongPostalCodeMsgPresent(errors);

    addVendorPage.typeRegon("1");
    addVendorPage.checkWrongRegonMsgPresent(errors);

    addVendorPage.typeEmailForInvoiceCorrections("1");
    addVendorPage.checkWrongEmailForInvoiceCorrectionsMsgPresent(errors);

    addVendorPage.typeAccountNumber("1");
    addVendorPage.checkWrongAccountNumberMsgPresent(errors);

    addVendorPage.typeWebsite("1");
    addVendorPage.checkWrongWebsiteMsgPresent(errors);

    addVendorPage.clickSaveBtn();
    addVendorPage.checkErrorSnackBarPresent(errors);

    checkConditions();
  }
}
