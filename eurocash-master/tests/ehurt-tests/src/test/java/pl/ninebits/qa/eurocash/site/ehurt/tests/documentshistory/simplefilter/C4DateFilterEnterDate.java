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

public class C4DateFilterEnterDate extends EhurtTestTemplate {
  private static final Logger logger = LoggerFactory.getLogger(C4DateFilterEnterDate.class);

  @Test(groups = {"production", "preprod", "uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C4")
  public void dateFilterEnterDateTest() throws Exception {
    String startDate = DateUtils.getPastDateDashFormat(4);
    String endDate = DateUtils.getPastDateDashFormat(2);

    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    DocumentsHistoryPage documentsHistoryPage = homePage.openDocsHistoryPage();

    documentsHistoryPage.clearSearchFilters();
    documentsHistoryPage.enterStartDate(startDate);
    documentsHistoryPage.enterEndDate(endDate);

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

    checkConditions();
  }
}
