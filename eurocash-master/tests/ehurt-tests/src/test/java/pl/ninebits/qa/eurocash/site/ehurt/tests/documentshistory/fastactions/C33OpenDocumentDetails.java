package pl.ninebits.qa.eurocash.site.ehurt.tests.documentshistory.fastactions;

import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.site.ehurt.helpers.EhurtAppLoginHelper;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtHomePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtStartPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.myaccount.DocumentDetailsPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.myaccount.DocumentsHistoryPage;
import pl.ninebits.qa.eurocash.site.ehurt.tests.EhurtTestTemplate;

public class C33OpenDocumentDetails extends EhurtTestTemplate {

  @Test(groups = {"production", "preprod", "uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C33")
  public void openDocumentDetailsTest() throws Exception {
    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    DocumentsHistoryPage documentsHistoryPage = homePage.openDocsHistoryPage();

    String docNumber = documentsHistoryPage.getRandomDocNumber();
    String netValue = documentsHistoryPage.getNetValue(docNumber);
    String grossValue = documentsHistoryPage.getGrossValue(docNumber);

    DocumentDetailsPage documentDetailsPage = documentsHistoryPage.actionsOpenDocDetails(docNumber);
    documentDetailsPage.checkDocNumberPresent(docNumber, errors);
    documentDetailsPage.checkNetValue(netValue, errors);
    documentDetailsPage.checkGrossValue(grossValue, errors);

    checkConditions();
  }
}
