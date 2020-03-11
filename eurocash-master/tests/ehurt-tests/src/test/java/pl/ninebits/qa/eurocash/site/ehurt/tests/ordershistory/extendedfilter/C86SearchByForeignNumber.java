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

public class C86SearchByForeignNumber extends EhurtTestTemplate {

  @Test(groups = {"production", "preprod", "uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C86")
  public void searchByForeignNumberTest() throws Exception {
    final String inputLabel = "Numer obcy";

    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    OrdersHistoryPage ordersHistoryPage = homePage.openOrdersHistoryPage();
    ordersHistoryPage.ensureGivenColumnDisplayed(inputLabel);
    ordersHistoryPage.sortByForeignNumberDesc();
    String foreignNumber = ordersHistoryPage.getRandomStringValueFromGrid(inputLabel);

    FilterOrdersForm filterOrdersForm = ordersHistoryPage.clickShowFiltersBtn();
    filterOrdersForm.typeInputValue(inputLabel, foreignNumber);
    filterOrdersForm.hideFiltersTab();
    ordersHistoryPage.checkOrdersFoundByGivenCriteria(inputLabel, foreignNumber, errors);
    ordersHistoryPage.clearSearchFilters();

    checkConditions();
  }
}
