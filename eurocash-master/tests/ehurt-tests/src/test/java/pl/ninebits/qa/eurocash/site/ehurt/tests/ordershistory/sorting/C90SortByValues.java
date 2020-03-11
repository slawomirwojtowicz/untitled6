package pl.ninebits.qa.eurocash.site.ehurt.tests.ordershistory.sorting;

import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.site.ehurt.helpers.EhurtAppLoginHelper;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtHomePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtStartPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.myaccount.OrdersHistoryPage;
import pl.ninebits.qa.eurocash.site.ehurt.tests.EhurtTestTemplate;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

public class C90SortByValues extends EhurtTestTemplate {

  @Test(groups = {"production", "preprod", "uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C90")
  public void sortByValuesTest() throws Exception {
    List<String> sortingHeaders = Arrays.asList( "Nr zam.", "Kod KH", "Wart. netto", "Wart. brutto", "Nr dysp.", "Płatnik");
    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    OrdersHistoryPage ordersHistoryPage = homePage.openOrdersHistoryPage();

    for (String sortingHeader : sortingHeaders) {
      logger.info(MessageFormat.format("Użyty nagłówek: {0}", sortingHeader));
      ordersHistoryPage.ensureGivenColumnDisplayed(sortingHeader);
      ordersHistoryPage.clickSortHeader(sortingHeader);
      ordersHistoryPage.checkOrdersSortedByValue(sortingHeader, "asc", errors);
      ordersHistoryPage.clickSortHeader(sortingHeader);
      ordersHistoryPage.checkOrdersSortedByValue(sortingHeader, "desc", errors);
    }
    ordersHistoryPage.clearSearchFilters();

    checkConditions();
  }
}
