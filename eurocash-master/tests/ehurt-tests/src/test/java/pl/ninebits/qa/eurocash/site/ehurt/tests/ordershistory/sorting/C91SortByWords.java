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

public class C91SortByWords extends EhurtTestTemplate {

  @Test(groups = {"production", "preprod", "uat"}, enabled = false)
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C91")
  public void sortByWordsTest() throws Exception {
    List<String> sortingHeaders = Arrays.asList(
      "Oddział", "Makr.", /*"Kontrahent",*/ "Skrót KH", /*"Użytkownik",*/ "Status", "Szablon", "Deale", "Płatn.", "Transp.", "Nr obcy", /*"Koment.",*/ "FMD",
     "Opis grupy KM", "Targowe", "Asortyment", "Grupa KM", "Nr fakt.");
//TODO wyłączony kontrahent, sprawdzić dlaczego nie działa po nim sortowanie
    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    OrdersHistoryPage ordersHistoryPage = homePage.openOrdersHistoryPage();

    for (String sortingHeader : sortingHeaders) {

      ordersHistoryPage.ensureGivenColumnDisplayed(sortingHeader);
      ordersHistoryPage.clickSortHeader(sortingHeader);
      ordersHistoryPage.checkOrdersSortedByWord(sortingHeader, "asc", errors);
      ordersHistoryPage.clickSortHeader(sortingHeader);
      ordersHistoryPage.checkOrdersSortedByWord(sortingHeader, "desc", errors);
      ordersHistoryPage.scrollHorizontalBar(-ordersHistoryPage.getXoffset());
    }
    ordersHistoryPage.clearSearchFilters();
    checkConditions();
  }
}
