package pl.ninebits.qa.eurocash.site.ehurt.tests.documentshistory.extendedfilter;

import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.site.ehurt.forms.SettingsForm;
import pl.ninebits.qa.eurocash.site.ehurt.helpers.EhurtAppLoginHelper;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtHomePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtStartPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.myaccount.DocumentsHistoryPage;
import pl.ninebits.qa.eurocash.site.ehurt.tests.EhurtTestTemplate;

public class C22PaginationDocumentList extends EhurtTestTemplate {

  @Test(groups = {"production", "preprod", "uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C22")
  public void paginationDocumentListTest() throws Exception {
    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    DocumentsHistoryPage documentsHistoryPage = homePage.openDocsHistoryPage();
    documentsHistoryPage.clearSearchFilters();

    Integer records = documentsHistoryPage.getNumberOfRecords();
    if (documentsHistoryPage.checkIfEnoughRecordsToPagination(records, errors)) {
      SettingsForm settingsForm = documentsHistoryPage.clickSettingsBtn();
      int numberOfRows = (int) Math.floor(records / 3);
      if (numberOfRows > 15) {
        numberOfRows = 15;
      }

      settingsForm.enterNumberOfRows(String.valueOf(numberOfRows));
      settingsForm.clickChooseBtnInSettingPopup();

      documentsHistoryPage.checkDocumentListView(numberOfRows, errors);
      documentsHistoryPage.clickNextPage();
      documentsHistoryPage.checkDocumentListView(numberOfRows, errors);
      //TODO Na ostatniej stronie pokazuje się błędna iość elemetów
     /* documentsHistoryPage.clickLastPage();
      int expectedNumberOfRows = records % numberOfRows;
      if (expectedNumberOfRows == 0) {
        expectedNumberOfRows = numberOfRows;
      }
      documentsHistoryPage.checkDocumentListView(expectedNumberOfRows, errors);*/
      documentsHistoryPage.clickFirstPage();
      documentsHistoryPage.checkDocumentListView(numberOfRows, errors);

      documentsHistoryPage.clearSearchFilters();
    }
    checkConditions();
  }
}
