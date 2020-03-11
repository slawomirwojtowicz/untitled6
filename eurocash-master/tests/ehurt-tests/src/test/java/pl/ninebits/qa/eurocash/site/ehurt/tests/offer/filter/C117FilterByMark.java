package pl.ninebits.qa.eurocash.site.ehurt.tests.offer.filter;

import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.site.ehurt.forms.OfferFiltersForm;
import pl.ninebits.qa.eurocash.site.ehurt.helpers.EhurtAppLoginHelper;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtHomePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtStartPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.OfferPage;
import pl.ninebits.qa.eurocash.site.ehurt.tests.EhurtTestTemplate;

public class C117FilterByMark extends EhurtTestTemplate {

  @Test(groups = {"production", "preprod","uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C117")
  public void filterByMarkTest() throws Exception {
    final String columnLabel = "Marka Producenta";

    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    OfferPage offerPage = homePage.openOfferPage();

    //TODO: na spotkanie, dziwne wyniki wyszukiwania
    OfferFiltersForm offerFiltersForm = offerPage.clickShowFilter();
    offerFiltersForm.spanMarksDropdown();
    String mark = offerFiltersForm.chooseRandomMarkValue();

    offerFiltersForm.clickHideFilter();
    offerPage.checkSearchResults(mark, columnLabel, errors);
    offerPage.clearSearchFilters();

    checkConditions();
  }
}
