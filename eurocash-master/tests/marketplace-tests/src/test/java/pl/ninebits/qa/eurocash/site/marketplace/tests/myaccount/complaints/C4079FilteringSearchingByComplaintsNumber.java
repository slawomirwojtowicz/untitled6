package pl.ninebits.qa.eurocash.site.marketplace.tests.myaccount.complaints;

import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUserRole;
import pl.ninebits.qa.eurocash.site.marketplace.forms.FilterForm;
import pl.ninebits.qa.eurocash.site.marketplace.helpers.LoginMarketHelper;
import pl.ninebits.qa.eurocash.site.marketplace.pages.ComplaintPage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.MarketplaceDashboardPage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.MarketplaceLoginPage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.MyAccountPage;
import pl.ninebits.qa.eurocash.site.marketplace.tests.MarketplaceTestTemplate;

public class C4079FilteringSearchingByComplaintsNumber extends MarketplaceTestTemplate {

  @Test(groups = {"uat", "dev"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C4079")
  public void filteringSearchingByComplaintsNumberTest() throws Exception {
    EhurtAppUser marketUser = getEhurtAppUser(EhurtAppUserRole.MARKETPLACE);

    MarketplaceLoginPage mLoginPage = loadMarketplaceLoginPage();
    MarketplaceDashboardPage mDashboardPage = LoginMarketHelper.doLogin(mLoginPage, marketUser);

    MyAccountPage myAccountPage = mDashboardPage.header.clickMyAccountBtn();
    ComplaintPage complaintPage = myAccountPage.clickComplaintBtn();
    String complaintNumber = complaintPage.getComplaintNumber();
    FilterForm filterForm = complaintPage.clickFilterBtn();
    filterForm.clickComplaintBtn();
    filterForm.typeComplaintValues(complaintNumber);
    filterForm.clickLoupeBtn();
    filterForm.clickHideResults();
    complaintPage.checComplaintNumber(complaintNumber, errors);

    //TODO Dodac checka na sprawdzanie czy pojawia się jeden element

    checkConditions();
  }
}
