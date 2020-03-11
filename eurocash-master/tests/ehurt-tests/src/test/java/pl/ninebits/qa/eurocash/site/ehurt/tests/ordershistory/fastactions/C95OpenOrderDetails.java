package pl.ninebits.qa.eurocash.site.ehurt.tests.ordershistory.fastactions;

import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.site.ehurt.helpers.EhurtAppLoginHelper;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtHomePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtStartPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.myaccount.OrderDetailsPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.myaccount.OrdersHistoryPage;
import pl.ninebits.qa.eurocash.site.ehurt.tests.EhurtTestTemplate;

public class C95OpenOrderDetails extends EhurtTestTemplate {

  @Test(groups = {"production", "preprod", "uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C95")
  public void openOrderDetailsTest() throws Exception {
    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    OrdersHistoryPage ordersHistoryPage = homePage.openOrdersHistoryPage();
    String orderNumber = ordersHistoryPage.getRandomOrderNumber();
    String netValue = ordersHistoryPage.getNetValue(orderNumber);
    String grossValue = ordersHistoryPage.getGrossValue(orderNumber);

    OrderDetailsPage orderDetailsPage = ordersHistoryPage.actionsOpenOrderDetails(orderNumber);
    orderDetailsPage.checkNetValue(netValue, errors);
    orderDetailsPage.checkGrossValue(grossValue, errors);

    checkConditions();
  }
}
