package pl.ninebits.qa.eurocash.site.ehurt.tests.ordershistory.extendedfilter;

import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.site.ehurt.forms.FilterOrdersForm;
import pl.ninebits.qa.eurocash.site.ehurt.helpers.EhurtAppLoginHelper;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtHomePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtStartPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.myaccount.OrderDetailsPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.myaccount.OrdersHistoryPage;
import pl.ninebits.qa.eurocash.site.ehurt.tests.EhurtTestTemplate;

import java.util.List;

public class C69FilterBySource extends EhurtTestTemplate {

  @Test(groups = {"production", "preprod", "uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C69")
  public void filterBySourceTest() throws Exception {
    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    OrdersHistoryPage ordersHistoryPage = homePage.openOrdersHistoryPage();
    FilterOrdersForm filterOrdersForm = ordersHistoryPage.clickShowFiltersBtn();
    List<String> originValues = filterOrdersForm.getRandomOriginValues(2);
    filterOrdersForm.hideFiltersTab();

    for (String originValue : originValues) {
      filterOrdersForm = ordersHistoryPage.clickShowFiltersBtn();
      filterOrdersForm.clearOriginFilter();
      filterOrdersForm.chooseOriginValue(originValue);
      filterOrdersForm.hideFiltersTab();
      if (ordersHistoryPage.checkResultsPresent(originValue, errors)) {
        OrderDetailsPage orderDetailsPage = ordersHistoryPage.openRandomVisibleOrder();
        orderDetailsPage.checkOriginValueInHeaderPresent(originValue, errors);
        orderDetailsPage.checkOriginValueInBoxPresent(originValue, errors);
        ordersHistoryPage = orderDetailsPage.clickOrdersLink();
      }
    }
    filterOrdersForm = ordersHistoryPage.clickShowFiltersBtn();
    filterOrdersForm.clearOriginFilter();
    checkConditions();
  }
}
