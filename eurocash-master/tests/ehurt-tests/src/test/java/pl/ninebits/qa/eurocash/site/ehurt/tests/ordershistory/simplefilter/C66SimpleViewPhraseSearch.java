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

public class C66SimpleViewPhraseSearch extends EhurtTestTemplate {

  @Test(groups = {"production", "preprod", "uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C66")
  public void simpleViewPhraseSearchTest() throws Exception {
    final String searchProductPhrase = "Żywiec";

    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    OrdersHistoryPage ordersHistoryPage = homePage.openOrdersHistoryPage();

    ordersHistoryPage.clearSearchFilters();
    ordersHistoryPage.clickSimpleView();
    ordersHistoryPage.checkListSimpleView(errors);

    String orderNumber = ordersHistoryPage.getRandomOrderNumber();
    OrderDetailsPage orderDetailsPage = ordersHistoryPage.openOrderWithNumber(orderNumber);
    String index = orderDetailsPage.getIndexNumber();

    ordersHistoryPage = orderDetailsPage.clickOrdersLink();
    ordersHistoryPage.clickSimpleView();
    ordersHistoryPage.typeSearchPhrase(index);

    if (ordersHistoryPage.checkSearchResultsPresent(index, errors)) {
      orderDetailsPage = ordersHistoryPage.openRandomVisibleOrder();
      orderDetailsPage.checkIndexPresent(index, errors);

      ordersHistoryPage = orderDetailsPage.clickOrdersLink();
      ordersHistoryPage.clickSimpleView();
    }

    ordersHistoryPage.typeSearchPhrase(searchProductPhrase);
    if (ordersHistoryPage.checkSearchResultsPresent(index, errors)) {
      orderNumber = ordersHistoryPage.getRandomOrderNumber();
      orderDetailsPage = ordersHistoryPage.openOrderWithNumber(orderNumber);
      orderDetailsPage.checkSearchProductPresent(searchProductPhrase, errors);

      ordersHistoryPage = orderDetailsPage.clickOrdersLink();
    }

    ordersHistoryPage.clickNormalView();

    checkConditions();
  }
}
