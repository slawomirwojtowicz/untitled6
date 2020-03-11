package pl.ninebits.qa.eurocash.site.ehurt.tests.documentshistory.simplefilter;

import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.site.ehurt.helpers.EhurtAppLoginHelper;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtHomePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtStartPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.myaccount.DocumentDetailsPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.myaccount.DocumentsHistoryPage;
import pl.ninebits.qa.eurocash.site.ehurt.tests.EhurtTestTemplate;

public class C10ClickingOnTheNumberAndInTheDisplay extends EhurtTestTemplate {

  @Test(groups = {"production", "preprod", "uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C10")
  public void clickingOnTheNumberAndInTheDisplayTest() throws Exception {

    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    DocumentsHistoryPage documentsHistoryPage = homePage.openDocsHistoryPage();

    String daysBack = documentsHistoryPage.chooseRandomDaysBackValue();

    documentsHistoryPage.clearSearchFilters();
    String docNumber = documentsHistoryPage.getRandomDocNumber();
    DocumentDetailsPage documentDetailsPage = documentsHistoryPage.openDocumentWithNumber(docNumber);
    documentDetailsPage.clickDocsLink();
    documentsHistoryPage.openDocumentWithDisplay(docNumber);
    documentDetailsPage.clickDocsLink();
    documentsHistoryPage.enterDaysBack(daysBack);
    documentsHistoryPage.clearSearchFilters();

    checkConditions();
  }
}
