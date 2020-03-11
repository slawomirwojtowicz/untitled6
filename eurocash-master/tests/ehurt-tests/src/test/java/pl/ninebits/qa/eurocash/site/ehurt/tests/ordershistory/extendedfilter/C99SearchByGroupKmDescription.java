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

public class C99SearchByGroupKmDescription extends EhurtTestTemplate {

  private static final Logger logger = LoggerFactory.getLogger(C99SearchByGroupKmDescription.class);

  @Test(groups = {"production", "preprod", "uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C99")
  public void searchByGroupKmDescriptionTest() throws Exception {
    final String inputLabel = "Opis grupy KM";

    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    OrdersHistoryPage ordersHistoryPage = homePage.openOrdersHistoryPage();
    ordersHistoryPage.ensureGivenColumnDisplayed(inputLabel);
    ordersHistoryPage.moveToElementOnGrid(inputLabel);
    String kmGroupDescription = ordersHistoryPage.getRandomStringValueFromGrid(inputLabel);

    logger.info(MessageFormat.format("Użyty opis grupy KM: {0}", kmGroupDescription));

    FilterOrdersForm filterOrdersForm = ordersHistoryPage.clickShowFiltersBtn();
    filterOrdersForm.typeInputValue2(inputLabel, kmGroupDescription);
    filterOrdersForm.hideFiltersTab();

    ordersHistoryPage.checkOrdersFoundByGivenCriteria(inputLabel, kmGroupDescription, errors);
    ordersHistoryPage.clearSearchFilters();

    checkConditions();
  }
}
