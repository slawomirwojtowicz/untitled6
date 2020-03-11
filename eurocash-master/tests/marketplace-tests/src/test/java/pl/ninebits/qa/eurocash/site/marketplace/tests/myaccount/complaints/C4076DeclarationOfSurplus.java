package pl.ninebits.qa.eurocash.site.marketplace.tests.myaccount.complaints;

import lombok.val;
import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUserRole;
import pl.ninebits.qa.eurocash.api.users.VendorPortalUser;
import pl.ninebits.qa.eurocash.api.users.VendorPortalUserRole;
import pl.ninebits.qa.eurocash.site.marketplace.constants.ComplaintType;
import pl.ninebits.qa.eurocash.site.marketplace.constants.ProductName;
import pl.ninebits.qa.eurocash.site.marketplace.forms.ComplaintForm;
import pl.ninebits.qa.eurocash.site.marketplace.helpers.LoginMarketHelper;
import pl.ninebits.qa.eurocash.site.marketplace.pages.ComplaintDetailsPage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.ComplaintPage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.MarketplaceDashboardPage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.MarketplaceLoginPage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.MyAccountPage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.NewComplaintPage;
import pl.ninebits.qa.eurocash.site.marketplace.tests.MarketplaceTestTemplate;
import pl.ninebits.qa.eurocash.site.vendorportal.helpers.LoginHelper;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.ComplaintsPage;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.VendorDashboardPage;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.VendorLoginPage;

public class C4076DeclarationOfSurplus extends MarketplaceTestTemplate {

  @Test(groups = {"uat", "dev"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C4076")
  public void declarationOfSurplusTest() throws Exception {
    EhurtAppUser marketUser = getEhurtAppUser(EhurtAppUserRole.MARKETPLACE);
    val quantity = "1";

    MarketplaceLoginPage mLoginPage = loadMarketplaceLoginPage();
    MarketplaceDashboardPage mDashboardPage = LoginMarketHelper.doLogin(mLoginPage, marketUser);

    MyAccountPage myAccountPage = mDashboardPage.header.clickMyAccountBtn();
    ComplaintPage complaintPage = myAccountPage.clickComplaintBtn();
    NewComplaintPage newComplaintPage = complaintPage.clickReturnArticleBtn();

    newComplaintPage.clickIWantToReportSurplusBtn();

    ComplaintForm complaintForm = newComplaintPage.selectComplaint();
    complaintForm.complaintTypeBtn(ComplaintType.SURPLUS_CORRECTION);
    complaintForm.selectCommodity(ProductName.LOG_KRUGER);
    complaintForm.selectInvoiceNumber();
    String invoiceNumber = complaintForm.getInvoiceNumber();
    complaintForm.typeQuantityOrder(quantity);
    complaintForm.typeQuantityInvoice(quantity);
    complaintForm.typeQuantityDelivery(quantity);
    newComplaintPage = complaintForm.addProductToComplaint();
    newComplaintPage.clickSendComplaint();
    newComplaintPage.checkThankYouPresent(errors);
    newComplaintPage.checkInvoiceNumber(invoiceNumber, errors);
    newComplaintPage.checkProductName(ProductName.LOG_KRUGER, errors);
    ComplaintDetailsPage complaintDetailsPage = newComplaintPage.clickComplaintDetails();
    complaintDetailsPage.checkInvoiceNumber(invoiceNumber, errors);
    complaintDetailsPage.checkProductName(ProductName.LOG_KRUGER, errors);
    String complaintNumber = complaintDetailsPage.geComplaintNumber();
    complaintDetailsPage.logOut();

    VendorPortalUser vendorUser = getVendorPortalUser(VendorPortalUserRole.ADMIN);
    VendorLoginPage vLoginPage = loadVendorLoginPage();
    VendorDashboardPage vDashboardPage = LoginHelper.doLogin(vLoginPage, vendorUser);
    ComplaintsPage complaintsPage = vDashboardPage.sideMenu.clickComplaintsSideLink();
    complaintsPage.searchProduct(complaintNumber);
    complaintsPage.checkComplaint(complaintNumber, errors);

    checkConditions();
  }
}