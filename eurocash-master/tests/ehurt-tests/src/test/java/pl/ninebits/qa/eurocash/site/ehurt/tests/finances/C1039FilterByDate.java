package pl.ninebits.qa.eurocash.site.ehurt.tests.finances;

import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.automated.tests.core.utils.DateUtils;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.site.ehurt.helpers.EhurtAppLoginHelper;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtHomePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtStartPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.myaccount.PaymentsPage;
import pl.ninebits.qa.eurocash.site.ehurt.tests.EhurtTestTemplate;

public class C1039FilterByDate extends EhurtTestTemplate {


  @Test(groups = {"production", "preprod", "uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C1039")
  public void filterByDateTest() throws Exception {
    String startDate = DateUtils.getPastDateDashFormat(4);
    String endDate = DateUtils.getLaterDateDashFormat(15);

    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    PaymentsPage paymentsPage = homePage.openPaymentsPage();
    paymentsPage.enterStartDate(startDate);
    paymentsPage.enterEndDate(endDate);
    paymentsPage.checkPaymentFilteredByStartDate(startDate, errors);

    checkConditions();
  }
}
