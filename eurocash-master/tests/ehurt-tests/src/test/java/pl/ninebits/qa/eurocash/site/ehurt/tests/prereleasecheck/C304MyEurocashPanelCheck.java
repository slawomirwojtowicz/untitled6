package pl.ninebits.qa.eurocash.site.ehurt.tests.prereleasecheck;

import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.site.ehurt.helpers.EhurtAppLoginHelper;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtHomePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtStartPage;
import pl.ninebits.qa.eurocash.site.ehurt.tests.EhurtTestTemplate;

public class C304MyEurocashPanelCheck extends EhurtTestTemplate {

  @Test(groups = {"production", "preprod", "uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C304")
  public void myEurocashPanelCheckTest() throws Exception {
    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();
    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);

    homePage.goToHomePage(); //zapewnienie dashboarda
    homePage.checkMyEurocashBoxPresent(errors);
    homePage.checkMyEurocashBoxNotEmpty(errors);

    checkConditions();
  }
}