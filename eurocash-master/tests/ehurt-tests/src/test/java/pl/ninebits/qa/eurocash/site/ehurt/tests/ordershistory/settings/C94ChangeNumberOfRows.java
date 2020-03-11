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

public class C94ChangeNumberOfRows extends EhurtTestTemplate {

  @Test(groups = {"production", "preprod", "uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C94")
  public void changeNumberOfRowsTest() throws Exception {
    final String numberOfRows = "9";

    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    OrdersHistoryPage ordersHistoryPage = homePage.openOrdersHistoryPage();

    OrdersSettingsForm ordersSettingsForm = ordersHistoryPage.clickSettingsBtn();
    ordersSettingsForm.enterNumberOfRows(numberOfRows);
    ordersSettingsForm.clickChooseBtnInSettingPopup();
    ordersHistoryPage.checkOrdersListView(numberOfRows, errors);

    ordersSettingsForm = ordersHistoryPage.clickSettingsBtn();
    ordersSettingsForm.enterNumberOfRows("13");
    ordersSettingsForm.clickChooseBtnInSettingPopup();

    checkConditions();
  }
}
