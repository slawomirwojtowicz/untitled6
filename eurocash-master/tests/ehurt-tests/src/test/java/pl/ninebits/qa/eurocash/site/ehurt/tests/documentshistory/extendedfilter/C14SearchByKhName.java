package pl.ninebits.qa.eurocash.site.ehurt.tests.documentshistory.extendedfilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.site.ehurt.forms.FiltersForm;
import pl.ninebits.qa.eurocash.site.ehurt.helpers.EhurtAppLoginHelper;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtHomePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtStartPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.myaccount.DocumentsHistoryPage;
import pl.ninebits.qa.eurocash.site.ehurt.tests.EhurtTestTemplate;

import java.text.MessageFormat;

public class C14SearchByKhName extends EhurtTestTemplate {
  private static final Logger logger = LoggerFactory.getLogger(C14SearchByKhName.class);

  @Test(groups = {"production", "preprod","uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C14")
  public void searchByKhNameTest() throws Exception {
    final String inputLabel = "Nazwa KH";

    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    DocumentsHistoryPage documentsHistoryPage = homePage.openDocsHistoryPage();
    documentsHistoryPage.ensureGivenColumnDisplayed(inputLabel);
    String contractorName = documentsHistoryPage.getRandomContractorName();
    logger.info(MessageFormat.format("Użyta nazwa KH: {0}", contractorName));

    FiltersForm filtersForm = documentsHistoryPage.clickShowFiltersBtn();
    filtersForm.typeInputValue(inputLabel, contractorName);
    filtersForm.confirmSearch();

    documentsHistoryPage.checkDocumentFoundByGivenCriteria(inputLabel, contractorName, errors);
    checkConditions();
  }
}
