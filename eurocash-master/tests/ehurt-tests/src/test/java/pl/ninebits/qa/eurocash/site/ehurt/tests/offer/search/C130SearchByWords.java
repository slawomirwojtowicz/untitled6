package pl.ninebits.qa.eurocash.site.ehurt.tests.offer.search;

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

public class C130SearchByWords extends EhurtTestTemplate {


  //TODO Włączyć gdy zostanie poprawiony błąd pokazywaniem długich fraz na belce wyszukaj w ofercie
  @Test(groups = {"production", "preprod", "uat"}, enabled = false)
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C130")
  public void searchByWordsTest() throws Exception {
    final List<String> inputLabels = Arrays.asList("Kod Kreskowy", "Kod kresk. opak.");

    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();
    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);

    OfferPage offerPage = homePage.openOfferPage();

    for (String inputLabel : inputLabels) {
      offerPage.ensureGivenColumnDisplayed(inputLabel);
      offerPage.moveToElementOnGrid(inputLabel);
      String searchPhrase = offerPage.getRandomGridValue(inputLabel);

      offerPage.enterSearchPhrase(searchPhrase);
      offerPage.confirmSearch();
      offerPage.checkFilterBoxPresent(searchPhrase, errors);
      offerPage.checkSearchResults(searchPhrase, inputLabel, errors);
      offerPage.clearSearchFilters();
    }
    offerPage.clearSearchFilters();

    checkConditions();
  }
}
