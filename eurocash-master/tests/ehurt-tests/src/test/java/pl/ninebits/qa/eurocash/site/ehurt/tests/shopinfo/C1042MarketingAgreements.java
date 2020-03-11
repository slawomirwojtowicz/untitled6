package pl.ninebits.qa.eurocash.site.ehurt.tests.shopinfo;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.site.ehurt.helpers.EhurtAppLoginHelper;
import pl.ninebits.qa.eurocash.site.ehurt.pages.DynamicReportsPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtHomePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtStartPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.myaccount.ShopAccountPage;
import pl.ninebits.qa.eurocash.site.ehurt.tests.EhurtTestTemplate;

public class C1042MarketingAgreements extends EhurtTestTemplate {

  @Test(groups = {"production", "preprod", "uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C1042")
  public void marketingAgreementsTest() throws Exception {
    final String phoneNumber = RandomStringUtils.random(9, false, true);
    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    final String email = ehurtAppUser.getLogin() + "@9bits.pl";
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    ShopAccountPage shopAccountPage = homePage.openShopAccountPage();
    if (shopAccountPage.checkAgreementsBoxesPresent(errors)) {
      shopAccountPage.clearAllAgreements();
      shopAccountPage.agreeForEmail(email, errors);
      shopAccountPage.agreeForSMS(phoneNumber, errors);
      shopAccountPage.agreeForCalls(phoneNumber, errors);
      shopAccountPage.checkAgreementsPresent(true, errors);
      homePage = shopAccountPage.goToHomePage();
      DynamicReportsPage dynamicReportsPage = homePage.openDynamicReportsPage();
      if (dynamicReportsPage.chooseMarketingAgreementsReport(errors)) {
        dynamicReportsPage.checkAllAndGenerateReport();
        int attempt = 0;
        while (!dynamicReportsPage.onDynamicReportsPage() && attempt < 10) {
          dynamicReportsPage = homePage.openDynamicReportsPage();
          dynamicReportsPage.chooseMarketingAgreementsReport(errors);
          dynamicReportsPage.checkAllAndGenerateReport();
          attempt++;
        }
        if (dynamicReportsPage.checkDataPresent(errors)) {
          dynamicReportsPage.checkUserEmailConsentPresent(ehurtAppUser.getLogin(), true, email, errors);
          dynamicReportsPage.checkUserCallConsentPresent(getEhurtAppUser().getLogin(), true, phoneNumber, errors);
          dynamicReportsPage.checkUserSmsConsentPresent(getEhurtAppUser().getLogin(), true, phoneNumber, errors);
        }
      }
    }

    checkConditions();
  }
}
