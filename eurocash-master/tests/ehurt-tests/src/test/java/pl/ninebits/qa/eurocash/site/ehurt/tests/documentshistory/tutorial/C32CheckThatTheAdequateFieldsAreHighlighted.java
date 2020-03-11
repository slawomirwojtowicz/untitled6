package pl.ninebits.qa.eurocash.site.ehurt.tests.documentshistory.tutorial;

import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.site.ehurt.forms.TutorialForm;
import pl.ninebits.qa.eurocash.site.ehurt.helpers.EhurtAppLoginHelper;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtHomePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtStartPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.myaccount.DocumentsHistoryPage;
import pl.ninebits.qa.eurocash.site.ehurt.tests.EhurtTestTemplate;

public class C32CheckThatTheAdequateFieldsAreHighlighted extends EhurtTestTemplate {

  @Test(groups = {"production", "preprod", "uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C32")
  public void checkThatTheAdequateFieldsAreHighlightedTest() throws Exception {

    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    DocumentsHistoryPage documentsHistoryPage = homePage.openDocsHistoryPage();
    TutorialForm tutorialForm = documentsHistoryPage.clickTutorialLink();
    tutorialForm.checkFormVisible(true, errors);
    String tutorialNumberOfSteps = tutorialForm.getTutorialNumberOfSteps();
    tutorialForm.clickWatchLaterBtn();
    tutorialForm.checkFormVisible(false, errors);
    tutorialForm = documentsHistoryPage.clickTutorialLink();
    for (int i = 1; i <= Integer.valueOf(tutorialNumberOfSteps); i++) {
      tutorialForm.checkCurrentStepPresent(i, errors);
      if (i != 1) {
        tutorialForm.checkAreaHighlighted(i, errors);
      }
      if (i == Integer.valueOf(tutorialNumberOfSteps)) {
        tutorialForm.clickPreviousStepBtn();
        tutorialForm.checkCurrentStepPresent(i - 1, errors);
        tutorialForm.clickNextBtn(i);
        tutorialForm.checkCurrentStepPresent(i, errors);
        tutorialForm.clickCloseBtn();
        tutorialForm.checkFormVisible(false, errors);
      } else {
        tutorialForm.clickNextBtn(i + 1);
      }
    }
    checkConditions();
  }
}
