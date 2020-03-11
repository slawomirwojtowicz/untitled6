package pl.ninebits.qa.eurocash.site.ehurt.tests.login;

import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtStartPage;
import pl.ninebits.qa.eurocash.site.ehurt.tests.EhurtTestTemplate;

public class C2IncorrectLogin extends EhurtTestTemplate {

  @Test(groups = {"production", "preprod", "uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C2")
  public void portalIncorrectLoginTest() throws Exception {
    EhurtStartPage startPage = loadEhurtStartPage();
    startPage.typeLogin("nie_ma_takiego_konta@9bits.pl");
    startPage.typePassword("1234567");
    startPage.clickLoginBtn();

    startPage.checkLoginErrorMessage(true, errors);

    checkConditions();
  }
}
