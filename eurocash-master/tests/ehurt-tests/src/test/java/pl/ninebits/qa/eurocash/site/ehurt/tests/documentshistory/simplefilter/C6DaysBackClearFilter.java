package pl.ninebits.qa.eurocash.site.ehurt.tests.documentshistory.simplefilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.site.ehurt.helpers.EhurtAppLoginHelper;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtHomePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtStartPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.myaccount.DocumentsHistoryPage;
import pl.ninebits.qa.eurocash.site.ehurt.tests.EhurtTestTemplate;

import java.text.MessageFormat;

public class C6DaysBackClearFilter extends EhurtTestTemplate {
  private static final Logger logger = LoggerFactory.getLogger(C6DaysBackClearFilter.class);
//TODO CZeka na poprwę błędu z datami
  @Test(groups = {"production", "preprod", "uat"}, enabled = false)
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C6")
  public void daysBackClearFilterTest() throws Exception {
    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    DocumentsHistoryPage documentsHistoryPage = homePage.openDocsHistoryPage();
    documentsHistoryPage.clearSearchFilters();

    String startDate = documentsHistoryPage.getStartDate();
    String endDate = documentsHistoryPage.getEndDate();
    String initialDaysBack = documentsHistoryPage.getDaysBack();
    String daysBack = documentsHistoryPage.chooseRandomDaysBackValue();

    logger.info(MessageFormat.format("Wylosowano wartość {0} dla pola Dni wstecz", daysBack));
    documentsHistoryPage.enterDaysBack(daysBack);
    documentsHistoryPage.checkDates(endDate, daysBack, errors);

    String newStartDate = documentsHistoryPage.getStartDate();
    String newEndDate = documentsHistoryPage.getEndDate();

    String sortType = documentsHistoryPage.checkInitialDateSort();
    if (!sortType.isEmpty()) {
      logger.info(MessageFormat.format("Dokumenty są wstępnie posortowane {0}", sortType));
    } else {
      logger.info("Dokumenty nie są wstępnie posortowane");
    }

    if (sortType.equals("asc")) {
      documentsHistoryPage.checkDocumentsSortedByDateOfIssueAsc(newStartDate, newEndDate, errors);
      documentsHistoryPage.clickSortByIssueDateBtn();
      documentsHistoryPage.checkDocumentsSortedByDateDesc(newEndDate, newStartDate, errors);
    } else if (sortType.equals("desc")) {
      documentsHistoryPage.checkDocumentsSortedByDateDesc(newEndDate, newStartDate, errors);
      documentsHistoryPage.clickSortByIssueDateBtn();
      documentsHistoryPage.checkDocumentsSortedByDateOfIssueAsc(newStartDate, newEndDate, errors);
    }
    documentsHistoryPage.clearSearchFilters();
    documentsHistoryPage.checkDefaultDaysBackValue(initialDaysBack, errors);
    documentsHistoryPage.checkDefaultStartDate(startDate, errors);
    documentsHistoryPage.checkDefaultEndDate(endDate, errors);

    checkConditions();
  }
}
