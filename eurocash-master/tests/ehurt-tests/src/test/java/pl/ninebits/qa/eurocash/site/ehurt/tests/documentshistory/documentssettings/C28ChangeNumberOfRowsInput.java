package pl.ninebits.qa.eurocash.site.ehurt.tests.documentshistory.documentssettings;


import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.site.ehurt.forms.SettingsForm;
import pl.ninebits.qa.eurocash.site.ehurt.helpers.EhurtAppLoginHelper;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtHomePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtStartPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.myaccount.DocumentsHistoryPage;
import pl.ninebits.qa.eurocash.site.ehurt.tests.EhurtTestTemplate;

public class C28ChangeNumberOfRowsInput extends EhurtTestTemplate {

  @Test(groups = {"production", "preprod","uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C28")
  public void changeNumberOfRowsInputTest() throws Exception {
    final String lowerNumberOfRows = "10";

    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    DocumentsHistoryPage documentsHistoryPage = homePage.openDocsHistoryPage();
    SettingsForm settingsForm = documentsHistoryPage.clickSettingsBtn();
    settingsForm.enterNumberOfRows(lowerNumberOfRows);
    settingsForm.clickChooseBtnInSettingPopup();
    documentsHistoryPage.checkDocumentListView(Integer.valueOf(lowerNumberOfRows), errors);

    settingsForm = documentsHistoryPage.clickSettingsBtn();
    settingsForm.enterNumberOfRows("20");
    settingsForm.clickChooseBtnInSettingPopup();

    checkConditions();
  }

}
