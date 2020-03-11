package pl.ninebits.qa.eurocash.site.ehurt.tests.forecastedpricelists.sorting;

import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.site.ehurt.helpers.EhurtAppLoginHelper;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtHomePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtStartPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.ForecastedPriceListPage;
import pl.ninebits.qa.eurocash.site.ehurt.tests.EhurtTestTemplate;

public class C3461SortByGroup extends EhurtTestTemplate {

  @Test(groups = {"production", "preprod", "uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C3461")
  public void sortByGroupTest() throws Exception {
    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    ForecastedPriceListPage forecastedPriceListPage = homePage.openForecastedPricelistsPage();
    forecastedPriceListPage.sortColumnAsc("Grupa");
    forecastedPriceListPage.checkGroupSortedAsc(errors);

    forecastedPriceListPage.sortColumnDesc("Grupa");
    forecastedPriceListPage.checkGroupSortedDesc(errors);
    forecastedPriceListPage.clearFilters();

    checkConditions();
  }
}
