package pl.ninebits.qa.eurocash.site.ehurt.tests.login;

import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtHomePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtStartPage;
import pl.ninebits.qa.eurocash.site.ehurt.tests.EhurtTestTemplate;

public class C1SuccessLogin extends EhurtTestTemplate {

  @Test(groups = {"production", "preprod", "uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C1test")
  public void portalSuccessLoginTest() throws Exception {
     // EhurtAppUser ehurtUSSER =getEhurtAppUser();
    EhurtAppUser ehurtUser = getEhurtAppUser();

    try {
      EhurtStartPage startPage = loadEhurtStartPage();
      startPage.typeLogin(ehurtUser.getLogin(), errors);
      startPage.typePassword(ehurtUser.getPassword(), errors);
      EhurtHomePage homePage = (EhurtHomePage) startPage.clickLoginBtn(errors);

      homePage.checkWelcomeText(ehurtUser.getLogin(), errors);
    } catch (Exception e) {
    }
    checkConditions();
  }
}