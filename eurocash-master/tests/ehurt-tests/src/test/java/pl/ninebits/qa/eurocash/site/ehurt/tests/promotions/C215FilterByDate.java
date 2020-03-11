package pl.ninebits.qa.eurocash.site.ehurt.tests.promotions;

import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.site.ehurt.helpers.EhurtAppLoginHelper;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtHomePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtStartPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.PromotionsPage;
import pl.ninebits.qa.eurocash.site.ehurt.tests.EhurtTestTemplate;

import java.util.List;

public class C215FilterByDate extends EhurtTestTemplate {

  @Test(groups = {"production", "preprod", "uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C215")
  public void filterByDateTest() throws Exception {
    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    PromotionsPage promotionsPage = homePage.openPromotionsPage();
    List<String> availableStartEndDates = promotionsPage.getAvailableStartEndDates();
    promotionsPage.enterStartDate(availableStartEndDates.get(0));
    promotionsPage.checkPromotionsFilteredByStartDate(availableStartEndDates.get(0), errors);
    promotionsPage.enterEndDate(availableStartEndDates.get(1));
    promotionsPage.checkPromotionsFilterByDates(availableStartEndDates.get(0), availableStartEndDates.get(1), errors);
    promotionsPage.clearFilters();

    checkConditions();
  }
}
