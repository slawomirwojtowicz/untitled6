package pl.ninebits.qa.eurocash.site.ehurt.tests.ordershistory.sorting;

import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.site.ehurt.helpers.EhurtAppLoginHelper;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtHomePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtStartPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.myaccount.OrdersHistoryPage;
import pl.ninebits.qa.eurocash.site.ehurt.tests.EhurtTestTemplate;

import java.util.Arrays;
import java.util.List;

public class C89SortByDates extends EhurtTestTemplate {

  @Test(groups = {"production", "preprod", "uat"}, enabled = false)
  //TODO Test niestabilyny, wyłączony
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C89")
  public void sortByDatesTest() throws Exception {
    List<String> sortingDates = Arrays.asList("Termin real.", "Data cennika");

    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    OrdersHistoryPage ordersHistoryPage = homePage.openOrdersHistoryPage();

    for (String sortingDate : sortingDates) {
      ordersHistoryPage.clickSortByDate(sortingDate);
      ordersHistoryPage.checkOrdersSortedByDate(sortingDate, "asc", errors);
      ordersHistoryPage.clickSortByDate(sortingDate);
      ordersHistoryPage.checkOrdersSortedByDate(sortingDate, "desc", errors);
      ordersHistoryPage.clearSearchFilters();
    }
    checkConditions();
  }
}
