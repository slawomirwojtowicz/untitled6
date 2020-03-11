package pl.ninebits.qa.eurocash.site.ehurt.tests.packages;

import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.site.ehurt.constants.Contractor;
import pl.ninebits.qa.eurocash.site.ehurt.helpers.EhurtAppLoginHelper;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtHomePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtStartPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.myaccount.PackagesPage;
import pl.ninebits.qa.eurocash.site.ehurt.tests.EhurtTestTemplate;

public class C1038CheckPackagesValues extends EhurtTestTemplate {

  @Test(groups = {"production", "preprod", "uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C1038")
  public void checkPackagesValuesTest() throws Exception {
    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    homePage.ensureProperContractorChosen(Contractor.ABC.getContractorId());
    PackagesPage packagesPage = homePage.openPackagesPage();
    String packagesSumValue = packagesPage.getPackagesSumValue();
    packagesPage.checkSumsFromSectionsEqualTotalSum(packagesSumValue, errors);
    packagesPage.openAllSectionsInGrid();
    packagesPage.checkProductsInSectionsPresent(errors);
    packagesPage.checkSumsFromSectionElements(packagesSumValue, errors);
    packagesPage.ensureProperContractorChosen(Contractor.DOMWESELNY.getContractorId());

    checkConditions();
  }
}
