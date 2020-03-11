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

public class C102SearchByAssortment extends EhurtTestTemplate {

  private static final Logger logger = LoggerFactory.getLogger(C102SearchByAssortment.class);

  @Test(groups = {"production", "preprod", "uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C102")
  public void searchByAssormentTest() throws Exception {
    final String inputLabel = "Asortyment";

    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    OrdersHistoryPage ordersHistoryPage = homePage.openOrdersHistoryPage();
    ordersHistoryPage.ensureGivenColumnDisplayed(inputLabel);
    ordersHistoryPage.moveToElementOnGrid(inputLabel);
    String assortment = ordersHistoryPage.getRandomStringValueFromGrid(inputLabel);

    logger.info(MessageFormat.format("Użyty asortyment: {0}", assortment));

    FilterOrdersForm filterOrdersForm = ordersHistoryPage.clickShowFiltersBtn();
    filterOrdersForm.typeInputValue2(inputLabel, assortment);
    filterOrdersForm.hideFiltersTab();

    ordersHistoryPage.checkOrdersFoundByGivenCriteria(inputLabel, assortment, errors);
    ordersHistoryPage.clearSearchFilters();

    checkConditions();
  }

}
