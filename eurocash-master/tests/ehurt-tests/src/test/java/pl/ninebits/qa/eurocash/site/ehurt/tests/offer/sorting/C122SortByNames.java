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

public class C122SortByNames extends EhurtTestTemplate {

  @Test(groups = {"production", "preprod", "uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C122")
  public void sortByNamesTest() throws Exception {
    //TODO: sortowanie po producencie wyłaczone do czasu poprawy w aplikacji
    List<String> columns = Arrays.asList("Nazwa produktu", /*"Producent",*/ "Marka Producenta", "Kategoria", "Podkategoria", "Asortyment");
    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    OfferPage offerPage = homePage.openOfferPage();

    for (String column : columns) {
      offerPage.ensureGivenColumnDisplayed(column);
      offerPage.sortColumnAscending(column, errors);
      offerPage.checkDataSortedByWords(column, "asc", errors);
      offerPage.sortColumnDescending(column);
      offerPage.checkDataSortedByWords(column, "desc", errors);
    }

    offerPage.clearSearchFilters();
    checkConditions();
  }
}
