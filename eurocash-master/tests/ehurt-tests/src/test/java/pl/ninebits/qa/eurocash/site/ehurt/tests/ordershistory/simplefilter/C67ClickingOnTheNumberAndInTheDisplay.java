package pl.ninebits.qa.eurocash.site.ehurt.tests.ordershistory.simplefilter;

import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.site.ehurt.helpers.EhurtAppLoginHelper;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtHomePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtStartPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.myaccount.OrderDetailsPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.myaccount.OrdersHistoryPage;
import pl.ninebits.qa.eurocash.site.ehurt.tests.EhurtTestTemplate;

public class C67ClickingOnTheNumberAndInTheDisplay extends EhurtTestTemplate {

  @Test(groups = {"production", "preprod", "uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C67")
  public void clickingOnTheNumberAndInTheDisplayTest() throws Exception {

    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    OrdersHistoryPage ordersHistoryPage = homePage.openOrdersHistoryPage();

    String daysBack = ordersHistoryPage.chooseRandomDaysBackValue();

    ordersHistoryPage.clearSearchFilters();
    String orderNumber = ordersHistoryPage.getRandomOrderNumber();
    OrderDetailsPage orderDetailsPage = ordersHistoryPage.openOrderWithNumber(orderNumber);
    orderDetailsPage.clickOrdersLink();

    ordersHistoryPage.enterDaysBack(daysBack);
    ordersHistoryPage.clearSearchFilters();

    checkConditions();
  }

}
