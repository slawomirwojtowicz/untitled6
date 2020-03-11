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

public class C72SearchByUser extends EhurtTestTemplate {

  private static final Logger logger = LoggerFactory.getLogger(C72SearchByUser.class);

  @Test(groups = {"production", "preprod","uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C72")
  public void searchByUserTest() throws Exception {
    final String inputLabel = "Użytkownik";

    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    OrdersHistoryPage ordersHistoryPage = homePage.openOrdersHistoryPage();
    ordersHistoryPage.ensureGivenColumnDisplayed(inputLabel);
    String userName = ordersHistoryPage.getRandomUser();

    logger.info(MessageFormat.format("Użyty użytkownik: {0}", userName));

    FilterOrdersForm filterOrdersForm = ordersHistoryPage.clickShowFiltersBtn();
    filterOrdersForm.typeInputValue0(inputLabel, userName);
    filterOrdersForm.hideFiltersTab();

    ordersHistoryPage.checkOrdersFoundByGivenNumber(inputLabel, userName, errors);
    ordersHistoryPage.clearSearchFilters();

    checkConditions();
  }
}
