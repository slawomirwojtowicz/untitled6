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

public class C18SearchByDepartment extends EhurtTestTemplate {

  private static final Logger logger = LoggerFactory.getLogger(C18SearchByDepartment.class);

  @Test(groups = {"production", "preprod", "uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C18")
  public void searchByDepartmentTest() throws Exception {
    final String inputLabel = "Oddział";

    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    DocumentsHistoryPage documentsHistoryPage = homePage.openDocsHistoryPage();
    documentsHistoryPage.ensureGivenColumnDisplayed(inputLabel);
    String departmentName = documentsHistoryPage.getRandomDepartmentNumber();
    logger.info(MessageFormat.format("Użyta nazwa oddziału: {0}", departmentName));

    FiltersForm filtersForm = documentsHistoryPage.clickShowFiltersBtn();
    filtersForm.typeInputValue(inputLabel, departmentName);
    filtersForm.confirmSearch();

    documentsHistoryPage.checkDocumentFoundByGivenCriteria(inputLabel, departmentName, errors);

    checkConditions();
  }
}
