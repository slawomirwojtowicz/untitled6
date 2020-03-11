package pl.ninebits.qa.eurocash.site.ehurt.tests.offer;

import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.site.ehurt.forms.ProductForm;
import pl.ninebits.qa.eurocash.site.ehurt.helpers.EhurtAppLoginHelper;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtHomePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtStartPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.OfferPage;
import pl.ninebits.qa.eurocash.site.ehurt.tests.EhurtTestTemplate;

public class C127VerifyProductPageData extends EhurtTestTemplate {

  @Test(groups = {"production", "preprod", "uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C127")
  public void verifyProductPageDataTest() throws Exception {
    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    OfferPage offerPage = homePage.openOfferPage();
    offerPage.ensureGivenColumnDisplayed("Indeks");

    String index = offerPage.getRandomIndex();
    String productName = offerPage.getProductNameForGivenIndex("Nazwa produktu", index);

    ProductForm productForm = offerPage.clickRandomProduct(index);
    productForm.checkParametersPresent(errors);
    productForm.checkImagePresent(errors);
    productForm.checkTitlePresent(productName, errors);

    checkConditions();
  }
}
