package pl.ninebits.qa.eurocash.site.ehurt.tests.documentshistory.simplefilter;

import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.site.ehurt.helpers.EhurtAppLoginHelper;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtHomePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtStartPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.myaccount.DocumentsHistoryPage;
import pl.ninebits.qa.eurocash.site.ehurt.tests.EhurtTestTemplate;

public class C8SimpleViewDateFilterCalendar extends EhurtTestTemplate {

  @Test(groups = {"production", "preprod", "uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C8")
  public void simpleViewDateFilterCalendarTest() throws Exception {
    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    DocumentsHistoryPage documentsHistoryPage = homePage.openDocsHistoryPage();
    documentsHistoryPage.clearSearchFilters();
    documentsHistoryPage.clickNormalView();
    documentsHistoryPage.clickSimpleView();
    documentsHistoryPage.checkListSimpleView(errors);

    String startDate = documentsHistoryPage.chooseStartDateInCalendar();
    String endDate = documentsHistoryPage.chooseEndDateInCalendar();

    String startDateChosen = documentsHistoryPage.getStartDate();
    String endDateChosen = documentsHistoryPage.getEndDate();
    documentsHistoryPage.checkCorrectDateChosenInCalendar(startDate, startDateChosen, errors);
    documentsHistoryPage.checkCorrectDateChosenInCalendar(endDate, endDateChosen, errors);

    documentsHistoryPage.clickSortByIssueDateBtn();
    documentsHistoryPage.checkDocumentsSortedByDateOfIssueAsc(startDate, endDate, errors);
    documentsHistoryPage.clearSearchFilters();
    documentsHistoryPage.clickNormalView();

    checkConditions();
  }
}
