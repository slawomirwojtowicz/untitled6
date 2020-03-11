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

import java.util.List;

public class C26UncheckColumns extends EhurtTestTemplate {

  @Test(groups = {"production", "preprod", "uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C26")
  public void uncheckColumnsTest() throws Exception {
    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    DocumentsHistoryPage documentsHistoryPage = homePage.openDocsHistoryPage();
    documentsHistoryPage.ensureAllColumnsDisplayed();

    SettingsForm settingsForm = documentsHistoryPage.clickSettingsBtn();
    List<String> columnsToUncheck = settingsForm.getAllColumns();
    settingsForm.clickChooseBtnInSettingPopup();

    for (String columnToUncheck : columnsToUncheck) {
      settingsForm = documentsHistoryPage.clickSettingsBtn();
      settingsForm.clickColumnCheckbox(columnToUncheck);
      settingsForm.clickChooseBtnInSettingPopup();
      documentsHistoryPage.checkColumnNotVisible(columnToUncheck, errors);

      settingsForm = documentsHistoryPage.clickSettingsBtn();
      settingsForm.clickColumnCheckbox(columnToUncheck);
      settingsForm.clickChooseBtnInSettingPopup();
      documentsHistoryPage.checkColumnIsVisible(columnToUncheck, errors);
    }

    checkConditions();
  }
}
