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

public class C79SearchByInvoiceNumber extends EhurtTestTemplate {

  private static final Logger logger = LoggerFactory.getLogger(C79SearchByInvoiceNumber.class);

  @Test(groups = {"production", "preprod", "uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C79")
  public void searchByInvoiceNumberTest() throws Exception {
    final String inputLabel = "Nr faktury";

    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    OrdersHistoryPage ordersHistoryPage = homePage.openOrdersHistoryPage();
    ordersHistoryPage.ensureGivenColumnDisplayed(inputLabel);
    ordersHistoryPage.sortByInvoiceNumberDesc();
    String invoiceNumber = ordersHistoryPage.getInvoiceNumber();
    logger.info(MessageFormat.format("Użyty nr faktury: {0}", invoiceNumber));

    FilterOrdersForm filterOrdersForm = ordersHistoryPage.clickShowFiltersBtn();
    filterOrdersForm.typeInputValue0(inputLabel, invoiceNumber);
    filterOrdersForm.hideFiltersTab();
    ordersHistoryPage.checkOrdersFoundByGivenNumber(inputLabel, invoiceNumber, errors);

    checkConditions();
  }
}
