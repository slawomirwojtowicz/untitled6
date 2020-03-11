package pl.ninebits.qa.eurocash.site.ehurt.tests.documentshistory.simplefilter;

import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.site.ehurt.forms.SettingsForm;
import pl.ninebits.qa.eurocash.site.ehurt.helpers.EhurtAppLoginHelper;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtHomePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtStartPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.myaccount.DocumentsHistoryPage;
import pl.ninebits.qa.eurocash.site.ehurt.tests.EhurtTestTemplate;

public class C11PaginationDocumentList extends EhurtTestTemplate {

  @Test(groups = {"production", "preprod", "uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C11")
  public void paginationDocumentListTest() throws Exception {

    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    DocumentsHistoryPage documentsHistoryPage = homePage.openDocsHistoryPage();
    documentsHistoryPage.clearSearchFilters();
    documentsHistoryPage.clickSimpleView();
    SettingsForm settingsForm = documentsHistoryPage.clickSettingsBtn();
    settingsForm.clickChooseBtnInSettingPopup();
    Integer element = documentsHistoryPage.getNumberOfRecords();
    Integer rowsNumber = 5;

    documentsHistoryPage.checkDocumentListView(rowsNumber, errors);
    documentsHistoryPage.clickNextPage();
    documentsHistoryPage.checkDocumentListView(rowsNumber, errors);
    documentsHistoryPage.clickFirstPage();
    documentsHistoryPage.checkDocumentListView(rowsNumber, errors);

    documentsHistoryPage.clearSearchFilters();

    checkConditions();
  }
}
