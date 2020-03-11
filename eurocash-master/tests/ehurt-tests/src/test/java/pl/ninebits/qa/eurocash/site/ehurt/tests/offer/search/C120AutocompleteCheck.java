package pl.ninebits.qa.eurocash.site.ehurt.tests.offer.search;

import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.site.ehurt.helpers.EhurtAppLoginHelper;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtHomePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtStartPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.OfferPage;
import pl.ninebits.qa.eurocash.site.ehurt.tests.EhurtTestTemplate;

public class C120AutocompleteCheck extends EhurtTestTemplate {

  @Test(groups = {"production", "preprod", "uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C120")
  public void autocompleteCheckTest() throws Exception {
    String searchPhrase = "mleko";
    int autocompleteMinChars = 3;

    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    OfferPage offerPage = homePage.openOfferPage();
    offerPage.enterSearchPhrase(searchPhrase.substring(0, autocompleteMinChars));
    offerPage.checkAutocompleteSuggestions(searchPhrase.substring(0, autocompleteMinChars), errors);

    for (int i = autocompleteMinChars; i < searchPhrase.length(); i++) {
      char inputCharacter = searchPhrase.charAt(i);
      String searchCharacter = String.valueOf(inputCharacter);

      offerPage.addSearchPhrase(searchCharacter);
      offerPage.checkAutocompleteSuggestions(searchPhrase.substring(0, i + 1), errors);
    }

    checkConditions();
  }
}
