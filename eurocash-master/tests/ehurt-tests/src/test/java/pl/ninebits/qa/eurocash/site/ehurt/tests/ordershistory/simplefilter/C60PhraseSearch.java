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

public class C60PhraseSearch extends EhurtTestTemplate {

  @Test(groups = {"production", "preprod", "uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C60")
  public void phraseSearchTest() throws Exception {
    final String searchProductPhrase = "Żywiec";

    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    OrdersHistoryPage ordersHistoryPage = homePage.openOrdersHistoryPage();

    ordersHistoryPage.clearSearchFilters();
    String orderNumber = ordersHistoryPage.getRandomOrderNumber();
    OrderDetailsPage orderDetailsPage = ordersHistoryPage.openOrderWithNumber(orderNumber);
    String index = orderDetailsPage.getIndexNumber();
    ordersHistoryPage = orderDetailsPage.clickOrdersLink();
    ordersHistoryPage.typeSearchPhrase(index);

    orderDetailsPage = ordersHistoryPage.openRandomVisibleOrder();
    orderDetailsPage.checkIndexPresent(index, errors);

    ordersHistoryPage = orderDetailsPage.clickOrdersLink();
    ordersHistoryPage.typeSearchPhrase(searchProductPhrase);
    orderNumber = ordersHistoryPage.getRandomOrderNumber();

    ordersHistoryPage.openOrderWithNumber(orderNumber);
    orderDetailsPage.checkSearchProductPresent(searchProductPhrase, errors);

    //TODO działa ze sleepami, dodać waity

    checkConditions();
  }
}
