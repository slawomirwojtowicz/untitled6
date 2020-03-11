package pl.ninebits.qa.eurocash.site.ehurt.tests.finances;

import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.site.ehurt.constants.Contractor;
import pl.ninebits.qa.eurocash.site.ehurt.helpers.EhurtAppLoginHelper;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtHomePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtStartPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.myaccount.PaymentsPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.myaccount.ShopAccountPage;
import pl.ninebits.qa.eurocash.site.ehurt.tests.EhurtTestTemplate;

public class C2139CheckAccountNumber extends EhurtTestTemplate {

  @Test(groups = {"production", "preprod", "uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C2139")
  public void checkAccountNumberTest() throws Exception {
    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    homePage.ensureProperContractorChosen(Contractor.ABC.getContractorId());
    ShopAccountPage shopAccountPage = homePage.openShopAccountPage();
    String accountNumber = shopAccountPage.getAccountNumber();

    homePage = shopAccountPage.goToHomePage();
    homePage.ensureProperContractorChosen(Contractor.ABC.getContractorId());
    PaymentsPage paymentsPage = homePage.openPaymentsPage();
    paymentsPage.checkAccountNumber(accountNumber, errors);
    paymentsPage.ensureProperContractorChosen(Contractor.DOMWESELNY.getContractorId());

    checkConditions();
  }
}
