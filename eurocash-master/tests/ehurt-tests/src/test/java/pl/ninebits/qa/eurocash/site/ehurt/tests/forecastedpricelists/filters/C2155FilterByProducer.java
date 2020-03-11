package pl.ninebits.qa.eurocash.site.ehurt.tests.forecastedpricelists.filters;

import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.site.ehurt.forms.ForecastedPriceListsFiltersForm;
import pl.ninebits.qa.eurocash.site.ehurt.helpers.EhurtAppLoginHelper;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtHomePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtStartPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.ForecastedPriceListPage;
import pl.ninebits.qa.eurocash.site.ehurt.tests.EhurtTestTemplate;

public class C2155FilterByProducer extends EhurtTestTemplate {

  @Test(groups = {"production", "preprod", "uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C2155")
  public void filterByProducerTest() throws Exception {
    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    ForecastedPriceListPage forecastedPriceListPage = homePage.openForecastedPricelistsPage();
    ForecastedPriceListsFiltersForm forecastedPriceListsFiltersForm = forecastedPriceListPage.clickShowFilters();
    String producer = forecastedPriceListsFiltersForm.chooseProducer();
    forecastedPriceListPage.checkPricelistsFiltered("Producent", producer, errors);
    forecastedPriceListPage.clearFilters();

    checkConditions();
  }
}
