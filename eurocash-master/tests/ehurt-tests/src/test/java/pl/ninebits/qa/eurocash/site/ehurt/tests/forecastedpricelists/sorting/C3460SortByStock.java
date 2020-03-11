package pl.ninebits.qa.eurocash.site.ehurt.tests.forecastedpricelists.sorting;

import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.site.ehurt.helpers.EhurtAppLoginHelper;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtHomePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtStartPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.ForecastedPriceListPage;
import pl.ninebits.qa.eurocash.site.ehurt.tests.EhurtTestTemplate;

public class C3460SortByStock extends EhurtTestTemplate {


  @Test(groups = {"production", "preprod", "uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C3460")
  public void sortByStockTest() throws Exception {
    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    ForecastedPriceListPage forecastedPriceListPage = homePage.openForecastedPricelistsPage();
    forecastedPriceListPage.sortColumnAsc("Asortyment");
    forecastedPriceListPage.checkStockSortedAsc(errors);

    forecastedPriceListPage.sortColumnDesc("Asortyment");
    forecastedPriceListPage.checkStockSortedDesc(errors);
    forecastedPriceListPage.clearFilters();

    checkConditions();
  }
}
