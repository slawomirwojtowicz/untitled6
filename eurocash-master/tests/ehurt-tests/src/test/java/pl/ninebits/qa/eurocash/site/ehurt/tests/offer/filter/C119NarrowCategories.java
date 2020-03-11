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

public class C119NarrowCategories extends EhurtTestTemplate {

  @Test(groups = {"production", "preprod", "uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C119")
  public void narrowCategoriesTest() throws Exception {
    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    OfferPage offerPage = homePage.openOfferPage();

    OfferFiltersForm offerFiltersForm = offerPage.clickShowFilter();
    offerFiltersForm.clickCategory("1", "Alkohole");
    offerFiltersForm.clickCategory("2", "Piwo");
    offerFiltersForm.clickCategory("3", "Mocne");
    offerFiltersForm.clickHideFilter();

    offerPage.checkSearchResults("Mocne", "Podgrupa", errors);

    checkConditions();
  }
}
