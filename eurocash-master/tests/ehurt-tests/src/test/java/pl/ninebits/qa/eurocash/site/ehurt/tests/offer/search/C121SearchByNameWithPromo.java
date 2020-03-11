package pl.ninebits.qa.eurocash.site.ehurt.tests.offer.search;

import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.site.ehurt.forms.PromotionBundleForm;
import pl.ninebits.qa.eurocash.site.ehurt.forms.PromotionForm;
import pl.ninebits.qa.eurocash.site.ehurt.helpers.EhurtAppLoginHelper;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtHomePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtStartPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.OfferPage;
import pl.ninebits.qa.eurocash.site.ehurt.tests.EhurtTestTemplate;

public class C121SearchByNameWithPromo extends EhurtTestTemplate {

  //TODO przepisać test
  @Test(groups = {"production", "preprod", "uat"}, enabled = false)
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C121")
  public void searchByNameWithPromoTest() throws Exception {
    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    OfferPage offerPage = homePage.openOfferPage();

    String productName = offerPage.getRandomProductWithPricePromo(errors);
    offerPage.enterSearchPhrase(productName);
    offerPage.confirmSearch();

    PromotionForm promotionForm = offerPage.clickPricePromoBtn(productName);
    promotionForm.enterSearchPhrase(productName.substring(0, 10));
    promotionForm.checkProductPresent(productName, errors);
    promotionForm.checkPromoType("Promocja Cenowa", errors);
    promotionForm.clickCloseBtn();

    offerPage.clearSearchFilters();
    productName = offerPage.getRandomProductWithPacketPromo(errors);
    PromotionBundleForm promotionBundleForm = offerPage.clickPromoBundleBtn(productName);
    promotionBundleForm.checkPromosPresent(errors);
    promotionBundleForm.checkPricesPresent(errors);
    promotionBundleForm.checkPromosActive(errors);
    promotionBundleForm.clickPromotionLink();
    promotionForm.enterSearchPhrase(productName.substring(0, 10));
    promotionForm.checkProductPresent(productName, errors);
    promotionForm.clickCloseBtn();

    productName = offerPage.getRandomProductWithMultiplePromos(errors);
    promotionBundleForm = offerPage.clickPromoBundleBtn(productName);
    promotionBundleForm.checkPromosPresent(errors);
    promotionBundleForm.checkPricesPresent(errors);
    promotionBundleForm.checkPromosActive(errors);
    promotionBundleForm.clickPromotionLink();
    promotionForm.enterSearchPhrase(productName.substring(0, 10));
    promotionForm.checkProductPresent(productName, errors);
    promotionForm.clickCloseBtn();

    checkConditions();
  }
}
