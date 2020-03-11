package pl.ninebits.qa.eurocash.site.ehurt.tests.ordershistory.simplefilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.automated.tests.core.utils.DateUtils;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.site.ehurt.helpers.EhurtAppLoginHelper;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtHomePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtStartPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.myaccount.OrdersHistoryPage;
import pl.ninebits.qa.eurocash.site.ehurt.tests.EhurtTestTemplate;

import java.text.MessageFormat;

public class C61DateFilterEnterDate extends EhurtTestTemplate {
  private static final Logger logger = LoggerFactory.getLogger(C61DateFilterEnterDate.class);

  @Test(groups = {"production", "preprod", "uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C61")
  public void dateFilterEnterDateTest() throws Exception {
    String startDate = DateUtils.getPastDateDashFormat(4);
    String endDate = DateUtils.getPastDateDashFormat(2);

    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    OrdersHistoryPage ordersHistoryPage = homePage.openOrdersHistoryPage();

    ordersHistoryPage.clearSearchFilters();
    ordersHistoryPage.enterStartDate(startDate);
    ordersHistoryPage.enterEndDate(endDate);

    String endDateChosen = ordersHistoryPage.getEndDate();
    boolean dateCorrect = ordersHistoryPage.checkDateCorrect(endDate, endDateChosen, errors);
    if (!dateCorrect) {
      endDate = endDateChosen;
    }

    String sortType = ordersHistoryPage.checkInitialDateSort();
    if (!sortType.isEmpty()) {
      logger.info(MessageFormat.format("Dokumenty są wstępnie posortowane {0}", sortType));
    } else {
      logger.info("Dokumenty nie są wstępnie posortowane");
    }

    if (sortType.equals("asc")) {
      ordersHistoryPage.checkOrdersSortedByDateOfIssueAsc(startDate, endDate, errors);
      ordersHistoryPage.clickSortByIssueDateBtn();
      ordersHistoryPage.checkOrdersSortedByDateOfIssueDesc(endDate, startDate, errors);
    } else if (sortType.equals("desc")) {
      ordersHistoryPage.checkOrdersSortedByDateOfIssueDesc(endDate, startDate, errors);
      ordersHistoryPage.clickSortByIssueDateBtn();
      ordersHistoryPage.checkOrdersSortedByDateOfIssueAsc(startDate, endDate, errors);
    }

    checkConditions();
  }
}
