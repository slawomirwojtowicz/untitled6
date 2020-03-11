package pl.ninebits.qa.eurocash.site.ehurt.tests.offer.search;

import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.site.ehurt.forms.ProductForm;
import pl.ninebits.qa.eurocash.site.ehurt.helpers.EhurtAppLoginHelper;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtHomePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtStartPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.OfferPage;
import pl.ninebits.qa.eurocash.site.ehurt.tests.EhurtTestTemplate;

public class C114SearchByNameIndexProducer extends EhurtTestTemplate {

  @Test(groups = {"production", "preprod", "uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C114")
  public void searchByNameIndexProducerTest() throws Exception {
    final String productSearchPhrase = "Gin";
    final String producerSearchPhrase = "Wyborowa S.A.";

    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    OfferPage offerPage = homePage.openOfferPage();
    offerPage.sortColumnDescending("Indeks");

    ProductForm productForm = offerPage.openFirstProductPopupForm();
    String index = productForm.getProductAttribute("Indeks");
    productForm.closeProductForm();

    offerPage.enterSearchPhrase(productSearchPhrase);
    offerPage.confirmSearch();
    Thread.sleep(2000);
    offerPage.checkFilterBoxPresent(productSearchPhrase, errors);
    offerPage.checkSearchResults(productSearchPhrase, "Nazwa produktu", errors);
    offerPage.clearSearchFilters();

    offerPage.enterSearchPhrase(producerSearchPhrase);
    offerPage.confirmSearch();
    Thread.sleep(2000);
    offerPage.checkFilterBoxPresent(producerSearchPhrase, errors);
    offerPage.checkSearchResults(producerSearchPhrase, "Producent", errors);
    offerPage.clearSearchFilters();

    offerPage.enterSearchPhrase(index);
    offerPage.confirmSearch();
    Thread.sleep(2000);
    offerPage.checkFilterBoxPresent(index, errors);
    productForm = offerPage.openFirstProductPopupForm();
    productForm.checkAttributePresent(index, "Indeks", errors);
    productForm.closeProductForm();

    offerPage.clearSearchFilters();

    checkConditions();
  }
}
