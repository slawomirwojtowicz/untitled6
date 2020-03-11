package pl.ninebits.qa.eurocash.site.ehurt.tests.documentshistory.sorting;

import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.site.ehurt.helpers.EhurtAppLoginHelper;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtHomePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtStartPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.myaccount.DocumentsHistoryPage;
import pl.ninebits.qa.eurocash.site.ehurt.tests.EhurtTestTemplate;

import java.util.Arrays;
import java.util.List;

public class C25SortByWords extends EhurtTestTemplate {

  @Test(groups = {"production", "preprod", "uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C25")
  public void sortByWordsTest() throws Exception {
    List<String> columnHeadersToSort = Arrays.asList(
      "Typ dok.", "Skrót KH", "Oddział"
    );
   //TODO wyłaczone sortowanie po polu Kontrahent ze względu na złe sortowanie
    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    DocumentsHistoryPage documentsHistoryPage = homePage.openDocsHistoryPage();
    documentsHistoryPage.clearSearchFilters();
    documentsHistoryPage.enterDaysBack("2");

    for (String columnHeaderToSort : columnHeadersToSort) {
      documentsHistoryPage.clickSortByWords(columnHeaderToSort);
      documentsHistoryPage.checkDocumentsSortedByWordsAsc(columnHeaderToSort, errors);
      documentsHistoryPage.clickSortByWords(columnHeaderToSort);
      documentsHistoryPage.checkDocumentsSortedByWordsDesc(columnHeaderToSort, errors);
    }
    documentsHistoryPage.clearSearchFilters();
    checkConditions();
  }
}
