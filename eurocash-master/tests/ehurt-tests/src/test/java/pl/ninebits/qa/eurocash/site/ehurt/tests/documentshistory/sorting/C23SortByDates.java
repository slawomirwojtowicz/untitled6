package pl.ninebits.qa.eurocash.site.ehurt.tests.documentshistory.sorting;

import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.site.ehurt.helpers.EhurtAppLoginHelper;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtHomePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtStartPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.myaccount.DocumentsHistoryPage;
import pl.ninebits.qa.eurocash.site.ehurt.tests.EhurtTestTemplate;

public class C23SortByDates extends EhurtTestTemplate {

  @Test(groups = {"production", "preprod", "uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C23")
  public void sortByDatesTest() throws Exception {
    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    DocumentsHistoryPage documentsHistoryPage = homePage.openDocsHistoryPage();
    documentsHistoryPage.clearSearchFilters();
    documentsHistoryPage.enterDaysBack("2");

    documentsHistoryPage.clickSortByPaymentDate();
    documentsHistoryPage.checkDocumentsSortedByPaymentDateAsc(errors);
    documentsHistoryPage.clickSortByPaymentDate();
    documentsHistoryPage.checkDocumentsSortedByPaymentDateDesc(errors);

    documentsHistoryPage.clearSearchFilters();
    checkConditions();
  }
}
