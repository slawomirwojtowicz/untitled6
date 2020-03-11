package pl.ninebits.qa.eurocash.site.ehurt.tests.ordershistory.simplefilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.site.ehurt.helpers.EhurtAppLoginHelper;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtHomePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtStartPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.myaccount.OrdersHistoryPage;
import pl.ninebits.qa.eurocash.site.ehurt.tests.EhurtTestTemplate;

import java.text.MessageFormat;

public class C63DaysBackClearFilters extends EhurtTestTemplate {
  private static final Logger logger = LoggerFactory.getLogger(C63DaysBackClearFilters.class);

  @Test(groups = {"production", "preprod", "uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C63")
  public void daysBackClearFiltersTest() throws Exception {
    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    OrdersHistoryPage ordersHistoryPage = homePage.openOrdersHistoryPage();

    ordersHistoryPage.clearSearchFilters();
    String startDate = ordersHistoryPage.getStartDate();
    String endDate = ordersHistoryPage.getEndDate();
    String initialDaysBack = ordersHistoryPage.getDaysBack();
    String daysBack = ordersHistoryPage.chooseRandomDaysBackValue();

    logger.info(MessageFormat.format("Wylosowano wartość {0} dla pola Dni wstecz", daysBack));
    ordersHistoryPage.enterDaysBack(daysBack);
    ordersHistoryPage.checkDates(endDate, daysBack, errors);

    String newStartDate = ordersHistoryPage.getStartDate();
    String newEndDate = ordersHistoryPage.getEndDate();

    String sortType = ordersHistoryPage.checkInitialDateSort();
    if (!sortType.isEmpty()) {
      logger.info(MessageFormat.format("Dokumenty są wstępnie posortowane {0}", sortType));
    } else {
      logger.info("Dokumenty nie są wstępnie posortowane");
    }

    if (sortType.equals("asc")) {
      ordersHistoryPage.checkOrdersSortedByDateOfIssueAsc(newStartDate, newEndDate, errors);
      ordersHistoryPage.clickSortByIssueDateBtn();
      ordersHistoryPage.checkOrdersSortedByDateOfIssueDesc(newEndDate, newStartDate, errors);
    } else if (sortType.equals("desc")) {
      ordersHistoryPage.checkOrdersSortedByDateOfIssueDesc(newEndDate, newStartDate, errors);
      ordersHistoryPage.clickSortByIssueDateBtn();
      ordersHistoryPage.checkOrdersSortedByDateOfIssueAsc(newStartDate, newEndDate, errors);
    }

    ordersHistoryPage.clearSearchFilters();
    ordersHistoryPage.checkDefaultDaysBackValue(initialDaysBack, errors);
    ordersHistoryPage.checkDefaultStartDate(startDate, errors);
    ordersHistoryPage.checkDefaultEndDate(endDate, errors);

    checkConditions();
  }
}
