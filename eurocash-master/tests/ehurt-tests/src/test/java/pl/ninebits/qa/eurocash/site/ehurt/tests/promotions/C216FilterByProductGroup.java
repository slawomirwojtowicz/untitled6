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

import java.util.List;

public class C216FilterByProductGroup extends EhurtTestTemplate {

  //TODO Przepisać cały test
  @Test(groups = {"production", "preprod", "uat"}, enabled = false)
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C216")
  public void filterByProductGroupTest() throws Exception {
    final String productGroup = "Wino";

    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    PromotionsPage promotionsPage = homePage.openPromotionsPage();
    promotionsPage.chooseProductGroup(productGroup);
    List<String> promosToCheck = promotionsPage.getPromoNamesNotContainingPhrase(productGroup);
    if (!promosToCheck.isEmpty()) {
      for (String promoToCheck : promosToCheck) {
        PromotionForm promotionForm = promotionsPage.clickPromotion(promoToCheck);
        promotionForm.enterSearchPhrase(productGroup);
        promotionForm.checkProductContainsPhrase(productGroup, errors);
        promotionForm.clickCloseBtn();
      }
    }
    promotionsPage.clearFilters();

    checkConditions();
  }
}
