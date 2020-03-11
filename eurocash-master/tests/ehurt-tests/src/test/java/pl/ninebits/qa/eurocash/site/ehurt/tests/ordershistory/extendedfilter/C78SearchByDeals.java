package pl.ninebits.qa.eurocash.site.ehurt.tests.ordershistory.extendedfilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.site.ehurt.forms.FilterOrdersForm;
import pl.ninebits.qa.eurocash.site.ehurt.helpers.EhurtAppLoginHelper;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtHomePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtStartPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.myaccount.OrdersHistoryPage;
import pl.ninebits.qa.eurocash.site.ehurt.tests.EhurtTestTemplate;

import java.text.MessageFormat;

public class C78SearchByDeals extends EhurtTestTemplate {

  private static final Logger logger = LoggerFactory.getLogger(C78SearchByDeals.class);

  @Test(groups = {"production", "preprod", "uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C78")
  public void searchByDealsTest() throws Exception {
    final String inputLabel = "Deale";

    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();
    // sample

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    OrdersHistoryPage ordersHistoryPage = homePage.openOrdersHistoryPage();
    ordersHistoryPage.clearSearchFilters();
    ordersHistoryPage.enterDaysBack("2");
    ordersHistoryPage.ensureGivenColumnDisplayed(inputLabel);
    ordersHistoryPage.sortByDealsDesc();
    String deals = ordersHistoryPage.getRandomStringValueFromGrid(inputLabel);

    //TODO Askrzynski, brak danych na UAT
    logger.info(MessageFormat.format("Użyty deals: {0}", deals));

    FilterOrdersForm filterOrdersForm = ordersHistoryPage.clickShowFiltersBtn();
    filterOrdersForm.typeInputValue0(inputLabel, deals);
    filterOrdersForm.hideFiltersTab();
    ordersHistoryPage.checkOrdersFoundByGivenCriteria(inputLabel, deals, errors);

    checkConditions();
  }
}
