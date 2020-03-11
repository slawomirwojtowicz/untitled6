package pl.ninebits.qa.eurocash.site.ehurt.tests.promotions;

import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.site.ehurt.helpers.EhurtAppLoginHelper;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtHomePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtStartPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.PromotionsPage;
import pl.ninebits.qa.eurocash.site.ehurt.tests.EhurtTestTemplate;

import java.util.Arrays;
import java.util.List;

public class C213FilterByPromoType extends EhurtTestTemplate {

  @Test(groups = {"production", "preprod", "uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C213")
  public void filterByPromoTypeTest() throws Exception {
    final List<String> promoTypes = Arrays.asList("Cenowa", "Rabatowa z bonifikatą", "Pakietowa");

    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    PromotionsPage promotionsPage = homePage.openPromotionsPage();
    for (String promoType : promoTypes) {
      promotionsPage.choosePromoType(promoType);
      promotionsPage.checkPromotionsFilteredByType(promoType, errors);
      promotionsPage.clearFilters();
    }

    checkConditions();
  }
}
