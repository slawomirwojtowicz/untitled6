package pl.ninebits.qa.eurocash.site.ehurt.tests.documentshistory.extendedfilter;

import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.site.ehurt.forms.FiltersForm;
import pl.ninebits.qa.eurocash.site.ehurt.helpers.EhurtAppLoginHelper;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtHomePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtStartPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.myaccount.DocumentsHistoryPage;
import pl.ninebits.qa.eurocash.site.ehurt.tests.EhurtTestTemplate;

public class C19SearchByMacroregion extends EhurtTestTemplate {

  @Test(groups = {"production", "preprod", "uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C19")
  public void searchByMacroregionTest() throws Exception {
    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    String macroregion = "Będzin";
    String department = "Kraków";

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    DocumentsHistoryPage documentsHistoryPage = homePage.openDocsHistoryPage();

    FiltersForm filtersForm = documentsHistoryPage.clickShowFiltersBtn();
    filtersForm.typeMacroregionName(macroregion);
    filtersForm.confirmSearch();

    documentsHistoryPage.checkSearchDepartmentPresent(department, errors);

    checkConditions();
  }
}
