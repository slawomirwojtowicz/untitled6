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

public class C20NarrowingTheResults extends EhurtTestTemplate {

  private static final Logger logger = LoggerFactory.getLogger(C20NarrowingTheResults.class);

  @Test(groups = {"production", "preprod", "uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C20")
  public void narrowingTheResultsTest() throws Exception {
    final String inputLabel = "Nazwa KH";
    final String inputLabel2 = "Płatnik";
    final String inputLabel3 = "Oddział";

    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    DocumentsHistoryPage documentsHistoryPage = homePage.openDocsHistoryPage();
    String departmentName = documentsHistoryPage.getRandomDepartmentNumber();

    logger.info(MessageFormat.format("Użyta nazwa oddziału: {0}", departmentName));

    FiltersForm filtersForm = documentsHistoryPage.clickShowFiltersBtn();
    filtersForm.typeInputValue(inputLabel3, departmentName);
    filtersForm.confirmSearch();

    String contractorName = documentsHistoryPage.getRandomContractorName();
    documentsHistoryPage.checkDocumentFoundByGivenCriteria(inputLabel3, departmentName, errors);
    documentsHistoryPage.clickShowFiltersBtn();
    filtersForm.typeInputValue(inputLabel, contractorName);
    filtersForm.confirmSearch();

    String payerNumber = documentsHistoryPage.getRandomPayerNumber();

    documentsHistoryPage.checkDocumentFoundByGivenCriteria(inputLabel3, departmentName, errors);
    documentsHistoryPage.clickFirstPage();
    documentsHistoryPage.checkDocumentFoundByGivenCriteria(inputLabel, contractorName, errors);
    documentsHistoryPage.clickShowFiltersBtn();

    filtersForm.typeInputValue(inputLabel2, payerNumber);
    filtersForm.confirmSearch();

    documentsHistoryPage.checkDocumentFoundByGivenCriteria(inputLabel3, departmentName, errors);
    documentsHistoryPage.clickFirstPage();
    documentsHistoryPage.checkDocumentFoundByGivenCriteria(inputLabel, contractorName, errors);
    documentsHistoryPage.clickFirstPage();
    documentsHistoryPage.checkDocumentFoundByGivenCriteria(inputLabel2, payerNumber, errors);

    checkConditions();
  }
}
