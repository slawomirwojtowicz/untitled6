package pl.ninebits.qa.eurocash.site.ehurt.tests.finances;

import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.site.ehurt.helpers.EhurtAppLoginHelper;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtHomePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtStartPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.myaccount.PaymentsPage;
import pl.ninebits.qa.eurocash.site.ehurt.tests.EhurtTestTemplate;

public class C2148PrintTransferForm extends EhurtTestTemplate {

  @Test(groups = {"production", "preprod", "uat"}, enabled = false)
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C2148")
  public void printTransferFormTest() throws Exception {
    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    PaymentsPage paymentsPage = homePage.openPaymentsPage();
    if (paymentsPage.markDocuments(3, errors)) {
      String sum = paymentsPage.getSumOfMarked();
      String accountNumber = paymentsPage.getAccountNumber();
      paymentsPage.checkTransferForm(sum, accountNumber, errors);
    }
    checkConditions();
  }
}
