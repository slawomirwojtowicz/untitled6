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

import java.util.List;

public class C136TilesSearchByPromo extends EhurtTestTemplate {

  @Test(groups = {"production", "preprod", "uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C136")
  public void tilesSearchByPromoTest() throws Exception {
    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    OfferPage offerPage = homePage.openOfferPage();
    List<String> promotionsNamesFromBar = offerPage.getPromotionsNamesFromBar();

    OfferTilesPage offerTilesPage = offerPage.clickTilesView();
    for (String promotionName : promotionsNamesFromBar) {
      offerTilesPage.clickPromoFilter(promotionName);
      offerTilesPage.checkProductsFilteredByPromo(promotionName, errors);
      offerTilesPage.clearSearchFilters();
    }

    checkConditions();
  }
}
