package pl.ninebits.qa.eurocash.site.ehurt.tests.shopinfo;

import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.site.ehurt.helpers.EhurtAppLoginHelper;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtHomePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtStartPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.myaccount.ShopAccountPage;
import pl.ninebits.qa.eurocash.site.ehurt.tests.EhurtTestTemplate;

public class C1046DownloadPdfFiles extends EhurtTestTemplate {

  @Test(groups = {"production", "preprod", "uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C1046")
  public void downloadPdfFilesTest() throws Exception {
    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    ShopAccountPage shopAccountPage = homePage.openShopAccountPage();
    if (shopAccountPage.checkPdfFilesToDownloadPresent(errors)) {
      shopAccountPage.checkCanDownloadPdfFiles(errors);
    }
    checkConditions();
  }
}
