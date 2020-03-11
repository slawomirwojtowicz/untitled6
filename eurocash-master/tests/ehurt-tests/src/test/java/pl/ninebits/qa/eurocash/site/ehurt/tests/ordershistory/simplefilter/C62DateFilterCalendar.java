package pl.ninebits.qa.eurocash.site.ehurt.tests.ordershistory.simplefilter;

import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.site.ehurt.helpers.EhurtAppLoginHelper;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtHomePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtStartPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.myaccount.OrdersHistoryPage;
import pl.ninebits.qa.eurocash.site.ehurt.tests.EhurtTestTemplate;

public class C62DateFilterCalendar extends EhurtTestTemplate {

  @Test(groups = {"production", "preprod", "uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C62")
  public void dateFilterCalendarTest() throws Exception {
    final String inputLabel = "Data wyst.";

    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    OrdersHistoryPage ordersHistoryPage = homePage.openOrdersHistoryPage();

    String startDate = ordersHistoryPage.chooseStartDateInCalendar();
    String endDate = ordersHistoryPage.chooseEndDateInCalendar();

    String startDateChosen = ordersHistoryPage.getStartDate();
    String endDateChosen = ordersHistoryPage.getEndDate();
    ordersHistoryPage.checkDateCorrect(startDate, startDateChosen, errors);
    ordersHistoryPage.checkDateCorrect(endDate, endDateChosen, errors);
    ordersHistoryPage.moveToElementOnGrid(inputLabel);

    ordersHistoryPage.clickSortByIssueDateBtn();
    ordersHistoryPage.checkOrdersSortedByDateOfIssueAsc(startDate, errors);
    ordersHistoryPage.clearSearchFilters();

    checkConditions();
  }
}
