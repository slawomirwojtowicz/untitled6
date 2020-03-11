package pl.ninebits.qa.eurocash.site.ehurt.tests.prereleasecheck;

import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.site.ehurt.constants.Contractor;
import pl.ninebits.qa.eurocash.site.ehurt.helpers.EhurtAppLoginHelper;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtHomePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtStartPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.OfferPage;
import pl.ninebits.qa.eurocash.site.ehurt.tests.EhurtTestTemplate;

public class C308CheckBannerSpeciallyForYou extends EhurtTestTemplate {
  @Test(groups = {"production", "preprod", "uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C308")
  public void checkBannerSpeciallyForYouTest() throws Exception {
    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();
    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    homePage.ensureProperContractorChosen(Contractor.ABC.getContractorId());

    homePage.checkSpeciallyForYouBoxPresent(errors);
    OfferPage offerPage = homePage.clickSpeciallyForYouBox();
    offerPage.openFirstProductPopupForm();
    offerPage.clickCloseBtn();
    offerPage.ensureProperContractorChosen(Contractor.DOMWESELNY.getContractorId());

    checkConditions();
  }
}
