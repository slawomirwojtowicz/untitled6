package pl.ninebits.qa.eurocash.site.ehurt.tests.ordershistory.extendedfilter;

import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.site.ehurt.forms.FilterOrdersForm;
import pl.ninebits.qa.eurocash.site.ehurt.helpers.EhurtAppLoginHelper;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtHomePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtStartPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.myaccount.OrdersHistoryPage;
import pl.ninebits.qa.eurocash.site.ehurt.tests.EhurtTestTemplate;

import java.util.Arrays;
import java.util.List;

public class C71FilterByStatus extends EhurtTestTemplate {

  @Test(groups = {"production", "preprod", "uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C71")
  public void filterByStatusTest() throws Exception {
    final String inputLabel = "Status";
    List<String> statusValues = Arrays.asList("Blokada windykacyjna - zamówienie niezrealizowane", "1", "Czekające na zatwierdzenie", "4");

    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    OrdersHistoryPage ordersHistoryPage = homePage.openOrdersHistoryPage();
    ordersHistoryPage.ensureGivenColumnDisplayed(inputLabel);

    for (int i = 0; i < statusValues.size(); i += 2) {
      ordersHistoryPage.clearSearchFilters();
      FilterOrdersForm filterOrdersForm = ordersHistoryPage.clickShowFiltersBtn();
      filterOrdersForm.chooseStatusValue(statusValues.get(i), Integer.valueOf(statusValues.get(i + 1)));
      filterOrdersForm.hideFiltersTab();
      if (ordersHistoryPage.checkResultsPresent(statusValues.get(i), errors)) {
        ordersHistoryPage.checkOrdersFoundByGivenCriteria(inputLabel, statusValues.get(i), errors);
      }
    }

    checkConditions();
  }
}
