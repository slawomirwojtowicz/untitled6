package pl.ninebits.qa.eurocash.site.ehurt.tests.promotions;

import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.site.ehurt.forms.PromotionForm;
import pl.ninebits.qa.eurocash.site.ehurt.helpers.EhurtAppLoginHelper;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtHomePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtStartPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.PromotionsPage;
import pl.ninebits.qa.eurocash.site.ehurt.tests.EhurtTestTemplate;

public class C201SearchPromotions extends EhurtTestTemplate {

  @Test(groups = {"production", "preprod", "uat"}, enabled = false)
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C201")
  public void searchPromotionsTest() throws Exception {
    final String index = "348720";
    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    PromotionsPage promotionsPage = homePage.openPromotionsPage();
    if (promotionsPage.checkPromotionsPresent(errors)) {
      String promoName = promotionsPage.getRandomPromoName();
      promotionsPage.enterSearchPhrase(promoName);
      promotionsPage.checkPromoNames(promoName, errors);
      promotionsPage.clearFilters();

      String promoDescr = promotionsPage.getRandomPromoDescription();
      promotionsPage.enterSearchPhrase(promoDescr);
      promotionsPage.checkPromoDescriptions(promoDescr, errors);
      promotionsPage.clearFilters();

      promotionsPage.enterSearchPhrase(index);
      PromotionForm promotionForm = promotionsPage.clickFirstPromotion();
      promotionForm.checkIndex(index, errors);
      promotionForm.clickCloseBtn();
      promotionsPage.clearFilters();
    }

    checkConditions();
  }
}
