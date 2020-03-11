package pl.ninebits.qa.eurocash.site.ehurt.tests.offer.sorting;

import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.site.ehurt.helpers.EhurtAppLoginHelper;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtHomePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtStartPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.OfferPage;
import pl.ninebits.qa.eurocash.site.ehurt.tests.EhurtTestTemplate;

import java.util.Arrays;
import java.util.List;

public class C123SortByValues extends EhurtTestTemplate {

  @Test(groups = {"production", "preprod", "uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C123")
  public void sortByValuesTest() throws Exception {
    List<String> columns = Arrays.asList("Indeks");

    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    OfferPage offerPage = homePage.openOfferPage();

    for (String column : columns) {
      offerPage.ensureGivenColumnDisplayed(column);
      offerPage.sortColumnAscending(column, errors);
      offerPage.checkDataSortedByValues(column, "asc", errors);
      offerPage.sortColumnDescending(column);
      offerPage.checkDataSortedByValues(column, "desc", errors);
    }

    offerPage.clearSearchFilters();
    checkConditions();
  }
}
