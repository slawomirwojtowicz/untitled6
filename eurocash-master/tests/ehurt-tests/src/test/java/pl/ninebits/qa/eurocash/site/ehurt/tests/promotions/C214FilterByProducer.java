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

public class C214FilterByProducer extends EhurtTestTemplate {

  @Test(groups = {"production", "preprod", "uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C214")
  public void filterByProducerTest() throws Exception{
    final String producerName = "HARIBO";

    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    PromotionsPage promotionsPage = homePage.openPromotionsPage();
    promotionsPage.chooseProducer(producerName);

    PromotionForm promotionForm = promotionsPage.clickFirstPromotion();
    promotionForm.enterSearchPhrase(producerName);
    promotionForm.checkProductsContainsProducerName(producerName, errors);
    promotionForm.clickCloseBtn();
    promotionsPage.clearFilters();

    checkConditions();
  }
}
