package pl.ninebits.qa.eurocash.site.ehurt.tests.prereleasecheck;

import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.site.ehurt.constants.Contractor;
import pl.ninebits.qa.eurocash.site.ehurt.helpers.EhurtAppLoginHelper;
import pl.ninebits.qa.eurocash.site.ehurt.pages.CompetitionPapersPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtHomePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtStartPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EroundCompetitionPage;
import pl.ninebits.qa.eurocash.site.ehurt.tests.EhurtTestTemplate;

public class C305CompetitionPapersLoadCheck extends EhurtTestTemplate {

  @Test(groups = {"production", "preprod", "uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C305")
  public void competitionPapersLoadCheckTest() throws Exception {
    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();
    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    homePage.ensureProperContractorChosen(Contractor.ABC.getContractorId());

    EroundCompetitionPage eroundCompetitionPage = homePage.clickShowCompetitionsPapers(errors);
    if (eroundCompetitionPage != null) {
      eroundCompetitionPage.checkCompetitionTabActive(errors);
      eroundCompetitionPage.checkShopListPresent(errors);
      CompetitionPapersPage competitionPapersPage = eroundCompetitionPage.clickShowPaper(errors);
      competitionPapersPage.checkPaperLoaded(errors);
    }
    eroundCompetitionPage.ensureProperContractorChosen(Contractor.DOMWESELNY.getContractorId());
    checkConditions();
  }
}
