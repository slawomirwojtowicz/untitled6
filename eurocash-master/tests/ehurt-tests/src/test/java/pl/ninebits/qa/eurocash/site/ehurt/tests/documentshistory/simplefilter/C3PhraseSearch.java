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

public class C3PhraseSearch extends EhurtTestTemplate {

  @Test(groups = {"production", "preprod", "uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C3")
  public void phraseSearchTest() throws Exception {
    final String searchProductPhrase = "Żywiec";

    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    DocumentsHistoryPage documentsHistoryPage = homePage.openDocsHistoryPage();

    documentsHistoryPage.clearSearchFilters();
    String docNumber = documentsHistoryPage.getRandomDocNumber();
    DocumentDetailsPage documentDetailsPage = documentsHistoryPage.openDocumentWithNumber(docNumber);
    String index = documentDetailsPage.getIndexNumber();

    documentsHistoryPage = documentDetailsPage.clickDocsLink();
    documentsHistoryPage.typeSearchPhrase(index);

    documentDetailsPage = documentsHistoryPage.openRandomVisibleDocument();
    documentDetailsPage.checkIndexPresent(index, errors);

    documentsHistoryPage = documentDetailsPage.clickDocsLink();
    documentsHistoryPage.typeSearchPhrase(searchProductPhrase);
    docNumber = documentsHistoryPage.getRandomDocNumber();

    documentDetailsPage = documentsHistoryPage.openDocumentWithNumber(docNumber);
    documentDetailsPage.checkSearchProductPresent(searchProductPhrase, errors);

    checkConditions();
  }

}
