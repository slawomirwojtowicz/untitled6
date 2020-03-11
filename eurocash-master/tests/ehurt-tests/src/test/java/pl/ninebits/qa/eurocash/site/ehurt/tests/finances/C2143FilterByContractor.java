package pl.ninebits.qa.eurocash.site.ehurt.tests.finances;

import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.site.ehurt.constants.Contractor;
import pl.ninebits.qa.eurocash.site.ehurt.forms.FiltersForm;
import pl.ninebits.qa.eurocash.site.ehurt.helpers.EhurtAppLoginHelper;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtHomePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtStartPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.myaccount.PaymentsPage;
import pl.ninebits.qa.eurocash.site.ehurt.tests.EhurtTestTemplate;

public class C2143FilterByContractor extends EhurtTestTemplate {

  @Test(groups = {"production", "preprod", "uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C2143")
  public void filterByContractorTest() throws Exception {
    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    homePage.ensureProperContractorChosen(Contractor.ABC.getContractorId());

    PaymentsPage paymentsPage = homePage.openPaymentsPage();
    String contractorId = paymentsPage.getContractorId();
    String contractorName = paymentsPage.getContractorName();

    FiltersForm filtersForm = paymentsPage.clickShowFilters();
    filtersForm.typeInputValue("Kod KH", contractorId);
    filtersForm.hideFiltersTab();
    paymentsPage.checkPaymentsFilterByContractorId(contractorId, errors);
    paymentsPage.clickClearFilters();

    filtersForm = paymentsPage.clickShowFilters();
    filtersForm.typeInputValue("Nazwa KH", contractorName);
    filtersForm.hideFiltersTab();
    paymentsPage.checkPaymentsFilterByContractorName(contractorName, errors);
    paymentsPage.clickClearFilters();
    paymentsPage.ensureProperContractorChosen(Contractor.DOMWESELNY.getContractorId());


    //TODO Brakuje danych w polu Skrót KH na preprod
   /* filtersForm = paymentsPage.clickShowFilters();
    filtersForm.typeInputValue("Skrót KH", contractorAbrr);
    filtersForm.hideFiltersTab();
    paymentsPage.checkPaymentsFilterByContractorAbrr(contractorAbrr, errors);
    paymentsPage.clickClearFilters();
*/
    checkConditions();
  }
}
