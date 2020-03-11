package pl.ninebits.qa.eurocash.site.ehurt.tests.offer.search;

import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.site.ehurt.helpers.EhurtAppLoginHelper;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtHomePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtStartPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.OfferPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.OfferTilesPage;
import pl.ninebits.qa.eurocash.site.ehurt.tests.EhurtTestTemplate;

public class C138TilesNarrowingByAssortment extends EhurtTestTemplate {

  @Test(groups = {"production", "preprod", "uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C138")
  public void tilesNarrowingByAssortmentTest() throws Exception {

    final String productSearchPhrase = "Wino";

    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();
    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    OfferPage offerPage = homePage.openOfferPage();

    OfferTilesPage offerTilesPage = offerPage.clickTilesView();
    offerPage.clickAssortmentMarkBtn();
    offerPage.clickWine();
    offerPage.clickFoldBtn();
    offerTilesPage.enterSearchPhrase(productSearchPhrase);
    offerTilesPage.checkSearchResults(productSearchPhrase, errors);

    checkConditions();
  }
}
