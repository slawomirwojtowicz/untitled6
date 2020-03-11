package pl.ninebits.qa.eurocash.site.ehurt.tests.documentshistory.simplefilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.automated.tests.core.utils.DateUtils;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.site.ehurt.helpers.EhurtAppLoginHelper;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtHomePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtStartPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.myaccount.DocumentsHistoryPage;
import pl.ninebits.qa.eurocash.site.ehurt.tests.EhurtTestTemplate;

import java.text.MessageFormat;

public class C7SimpleViewDateFilterEnterDate extends EhurtTestTemplate {

  private static final Logger logger = LoggerFactory.getLogger(C7SimpleViewDateFilterEnterDate.class);

  @Test(groups = {"production", "preprod", "uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C7")
  public void simpleViewDateFilterEnterDateTest() throws Exception {
    String startDate = DateUtils.getPastDateDashFormat(4);
    String endDate = DateUtils.getPastDateDashFormat(2);

    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    DocumentsHistoryPage documentsHistoryPage = homePage.openDocsHistoryPage();

    documentsHistoryPage.clearSearchFilters();
    documentsHistoryPage.clickSimpleView();
    documentsHistoryPage.checkListSimpleView(errors);
    documentsHistoryPage.enterStartDate(startDate);
    documentsHistoryPage.enterEndDate(endDate);

    String extractedStartDate = documentsHistoryPage.getStartDate();
    startDate = documentsHistoryPage.checkCorrectStartDateEntered(startDate, extractedStartDate, errors);
    String extractedEndDate = documentsHistoryPage.getEndDate();
    endDate = documentsHistoryPage.checkCorrectEndDateEntered(endDate, extractedEndDate, errors);

    if (documentsHistoryPage.checkSearchResultsPresent("StartDate: " + startDate + " EndDate: " + endDate, errors)) {

      String sortType = documentsHistoryPage.checkInitialDateSort();
      if (!sortType.isEmpty()) {
        logger.info(MessageFormat.format("Dokumenty są wstępnie posortowane {0}", sortType));
      } else {
        logger.info("Dokumenty nie są wstępnie posortowane");
      }

      if (sortType.equals("asc")) {
        documentsHistoryPage.checkDocumentsSortedByDateOfIssueAsc(startDate, endDate, errors);
        documentsHistoryPage.clickSortByIssueDateBtn();
        documentsHistoryPage.checkDocumentsSortedByDateDesc(endDate, startDate, errors);
      } else if (sortType.equals("desc")) {
        documentsHistoryPage.checkDocumentsSortedByDateDesc(endDate, startDate, errors);
        documentsHistoryPage.clickSortByIssueDateBtn();
        documentsHistoryPage.checkDocumentsSortedByDateOfIssueAsc(startDate, endDate, errors);
      }
    }
    documentsHistoryPage.clickNormalView();

    checkConditions();
  }
}
