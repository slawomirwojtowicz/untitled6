package pl.ninebits.qa.eurocash.site.ehurt.tests.ordershistory.settings;

import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.site.ehurt.forms.OrdersSettingsForm;
import pl.ninebits.qa.eurocash.site.ehurt.helpers.EhurtAppLoginHelper;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtHomePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtStartPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.myaccount.OrdersHistoryPage;
import pl.ninebits.qa.eurocash.site.ehurt.tests.EhurtTestTemplate;

import java.util.List;

public class C93UncheckColumns extends EhurtTestTemplate {

  //TODO test wyłączony, problem z zaznaczaniem elemntów w ustawianiach kolumn - "śrubka"
  @Test(groups = {"production", "preprod", "uat"}, enabled = false)
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C93")
  public void uncheckColumnsTest() throws Exception {
    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    OrdersHistoryPage ordersHistoryPage = homePage.openOrdersHistoryPage();
    ordersHistoryPage.ensureAllColumnsDisplayed();
    ordersHistoryPage.enterDaysBack("1");

    OrdersSettingsForm ordersSettingsForm = ordersHistoryPage.clickSettingsBtn();
    List<String> columnsToUncheck = ordersSettingsForm.getRandomColumns(2);
    ordersSettingsForm.clickChooseBtnInSettingPopup();

    for (String columnToUncheck : columnsToUncheck) {
      ordersSettingsForm = ordersHistoryPage.clickSettingsBtn();
      ordersSettingsForm.clickColumnCheckbox(columnToUncheck);
      ordersSettingsForm.clickChooseBtnInSettingPopup();
      ordersHistoryPage.checkColumnNotVisible(columnToUncheck, errors);

      ordersSettingsForm = ordersHistoryPage.clickSettingsBtn();
      ordersSettingsForm.clickColumnCheckbox(columnToUncheck);
      ordersSettingsForm.clickChooseBtnInSettingPopup();
      ordersHistoryPage.checkColumnIsVisible(columnToUncheck, errors);
    }
    ordersHistoryPage.clearSearchFilters();

    checkConditions();
  }
}
