package pl.ninebits.qa.eurocash.site.marketplace.tests.myaccount.complaints;

import lombok.val;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUserRole;
import pl.ninebits.qa.eurocash.site.marketplace.constants.ComplaintType;
import pl.ninebits.qa.eurocash.site.marketplace.constants.Delivery;
import pl.ninebits.qa.eurocash.site.marketplace.forms.ComplaintForm;
import pl.ninebits.qa.eurocash.site.marketplace.helpers.LoginMarketHelper;
import pl.ninebits.qa.eurocash.site.marketplace.pages.ComplaintPage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.MarketplaceDashboardPage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.MarketplaceLoginPage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.MyAccountPage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.NewComplaintPage;
import pl.ninebits.qa.eurocash.site.marketplace.tests.MarketplaceTestTemplate;

public class C4078ReportAnotherProblem extends MarketplaceTestTemplate {

  @Test(groups = {"uat", "dev"}, enabled = false)
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C4078")
  public void reportAnotherProblemTest() throws Exception {

    val email = RandomStringUtils.randomAlphabetic(8) + "@9bits.pl";
    val phoneNumber = RandomStringUtils.randomNumeric(9);

    EhurtAppUser marketUser = getEhurtAppUser(EhurtAppUserRole.MARKETPLACE);
    MarketplaceLoginPage mLoginPage = loadMarketplaceLoginPage();
    MarketplaceDashboardPage mDashboardPage = LoginMarketHelper.doLogin(mLoginPage, marketUser);

    MyAccountPage myAccountPage = mDashboardPage.header.clickMyAccountBtn();
    ComplaintPage complaintPage = myAccountPage.clickComplaintBtn();
    NewComplaintPage newComplaintPage = complaintPage.clickReturnArticleBtn();

    newComplaintPage.clickIWantToReportAnotherProblemBtn();
    ComplaintForm complaintForm = newComplaintPage.selectComplaint();
    complaintForm.complaintTypeBtn(ComplaintType.ANOTHER_PROBLEM);
    complaintForm.selectDelivery(Delivery.NESTLE);
    complaintForm.selectInvoiceNumber();
    complaintForm.typeEmailAddress(email);
    complaintForm.typePhone(phoneNumber);
    newComplaintPage = complaintForm.sendComplaint();
    newComplaintPage.checkThankYouForAddingComplaint(errors);

    checkConditions();
  }
}
