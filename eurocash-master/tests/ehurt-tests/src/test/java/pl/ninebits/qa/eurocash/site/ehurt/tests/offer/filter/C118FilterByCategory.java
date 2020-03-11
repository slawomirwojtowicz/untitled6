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

public class C118FilterByCategory extends EhurtTestTemplate {

  @Test(groups = {"production", "preprod", "uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C118")
  public void filterByCategoryTest() throws Exception {

    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    OfferPage offerPage = homePage.openOfferPage();

    OfferFiltersForm offerFiltersForm = offerPage.clickShowFilter();
    String categoryName = offerFiltersForm.getRandomCategoryName("1");
    offerFiltersForm.clickCategory("1", categoryName);
    offerFiltersForm.clickHideFilter();
    offerPage.confirmSearch();
    offerPage.checkFilterBoxPresent(categoryName, errors);
    offerPage.clickShowFilter();

    String subCategoryName = offerFiltersForm.getRandomCategoryName("2");
    offerFiltersForm.clickCategory("2", subCategoryName);
    offerFiltersForm.clickHideFilter();
    offerPage.confirmSearch();
    offerPage.checkFilterBoxPresent(subCategoryName, errors);
    offerPage.clickShowFilter();

    //TODO Do zastanowienia czy zostawić 3 poziom kategori
   /* String subSubCategoryName = offerFiltersForm.getRandomCategoryName("3");
    offerFiltersForm.clickCategory("3", subSubCategoryName);
    offerFiltersForm.clickHideFilter();
    offerPage.confirmSearch();
    offerPage.checkFilterBoxPresent(subSubCategoryName, errors);
    offerPage.clickShowFilter();*/

    offerPage.clearSearchFilters();

    checkConditions();
  }
}
