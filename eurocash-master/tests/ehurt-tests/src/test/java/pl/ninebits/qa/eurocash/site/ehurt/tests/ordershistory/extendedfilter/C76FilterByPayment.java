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

public class C76FilterByPayment extends EhurtTestTemplate {

  @Test(groups = {"production", "preprod", "uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C76")
  public void filterByPaymentTest() throws Exception {
    final String inputLabel = "Płatność";
    List<String> paymentValues = Arrays.asList("gotówka", "1", "przelew", "2", "przelew: Płatność do trzech dni", "3");

    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    OrdersHistoryPage ordersHistoryPage = homePage.openOrdersHistoryPage();
    ordersHistoryPage.ensureGivenColumnDisplayed(inputLabel);

    for (int i = 0; i < paymentValues.size(); i += 2) {
      ordersHistoryPage.clearSearchFilters();
      FilterOrdersForm filterOrdersForm = ordersHistoryPage.clickShowFiltersBtn();
      filterOrdersForm.choosePaymentValue(paymentValues.get(i), Integer.valueOf(paymentValues.get(i + 1)));
      filterOrdersForm.hideFiltersTab();
      if (ordersHistoryPage.checkResultsPresent(paymentValues.get(i), errors)) {
        ordersHistoryPage.checkOrdersFoundByGivenCriteria(inputLabel, paymentValues.get(i), errors);
      }
    }

    checkConditions();
  }
}
