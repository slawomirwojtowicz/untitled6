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

import java.util.List;

public class C13FiltersOnDocumentTypes extends EhurtTestTemplate {

  @Test(groups = {"production", "preprod", "uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C13")
  public void filtersOnDocumentTypesTest() throws Exception {
    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    DocumentsHistoryPage documentsHistoryPage = homePage.openDocsHistoryPage();
    FiltersForm filtersForm = documentsHistoryPage.clickShowFiltersBtn();
    List<String> docTypesValues = filtersForm.getRandomTypesValues(2);
    filtersForm.hideFiltersTab();

    for (int i = 0; i < docTypesValues.size(); i += 2) {
      documentsHistoryPage.clearSearchFilters();
      filtersForm = documentsHistoryPage.clickShowFiltersBtn();
      filtersForm.chooseStatus(docTypesValues.get(i), Integer.valueOf(docTypesValues.get(i + 1)));
      filtersForm.hideFiltersTab();
      if (documentsHistoryPage.checkResultsPresent(docTypesValues.get(i), errors)) {
        documentsHistoryPage.checkDocumentOfType(docTypesValues.get(i), errors);
      }
    }

    checkConditions();
  }
}