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

public class C73SearchByKhCode extends EhurtTestTemplate {
  private static final Logger logger = LoggerFactory.getLogger(C73SearchByKhCode.class);

  @Test(groups = {"production", "preprod","uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C73")
  public void searchByKhCodeTest() throws Exception {
    final String inputLabel = "Kod KH";

    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    OrdersHistoryPage ordersHistoryPage = homePage.openOrdersHistoryPage();
    ordersHistoryPage.ensureGivenColumnDisplayed(inputLabel);
    String khCode = ordersHistoryPage.getRandomKhCodeNumber();
    logger.info(MessageFormat.format("Użyty kod KH: {0}", khCode));

    FilterOrdersForm filterOrdersForm = ordersHistoryPage.clickShowFiltersBtn();
    filterOrdersForm.typeInputValue0(inputLabel, khCode);
    filterOrdersForm.hideFiltersTab();

    ordersHistoryPage.checkOrdersFoundByGivenCriteria(inputLabel, khCode, errors);
    ordersHistoryPage.clearSearchFilters();
    checkConditions();
  }
}
