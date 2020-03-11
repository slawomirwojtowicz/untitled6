package pl.ninebits.qa.eurocash.site.ehurt.tests.offer.search;

import lombok.val;
import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.site.ehurt.forms.ProductForm;
import pl.ninebits.qa.eurocash.site.ehurt.helpers.EhurtAppLoginHelper;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtHomePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtStartPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.OfferPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.OfferTilesPage;
import pl.ninebits.qa.eurocash.site.ehurt.tests.EhurtTestTemplate;

public class C137TilesSearchByWords extends EhurtTestTemplate {

  @Test(groups = {"production", "preprod", "uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C137")
  public void tilesSearchByWords() throws Exception {
  //TODO Dane wrzucone na sztywno, podczas pobierania losowych danych wybiera krótkie indeksy po których wyszukuje również produkty których nie chcemy
    val productName = "ptasie mleczko";
    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();

    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    OfferPage offerPage = homePage.openOfferPage();

    OfferTilesPage offerTilesPage = offerPage.clickTilesView();
    offerTilesPage.enterSearchPhrase(productName);

    //  String productName = offerTilesPage.getRandomProductName();
    ProductForm productForm = offerTilesPage.openProductForm();

    String index = productForm.getProductAttribute("Indeks");
    String name = productForm.getTitle();
   // String producer = productForm.getProductAttribute("Producent");
    String eanCode = productForm.getEanCode();
    productForm.closeProductForm();

    offerTilesPage.enterSearchPhrase(index);
    productForm = offerTilesPage.openProductForm();
    productForm.checkAttributePresent(index, "Indeks", errors);
    productForm.closeProductForm();
    offerTilesPage.clearSearchFilters();

    offerTilesPage.enterSearchPhrase(name);
    productForm = offerTilesPage.openProductForm();
    productForm.checkTitlePresent(name, errors);
    productForm.closeProductForm();
    offerTilesPage.clearSearchFilters();

    //TODO Czasem wyszukuje błędne dane po pełnej nazwie producenta np HJ HEINZ POLSKA SP. Z O.O.
   /* offerTilesPage.enterSearchPhrase(producer);
    productForm = offerTilesPage.openFirstProductPopupForm();
    productForm.checkAttributePresent(producer, "Producent", errors);
    productForm.closeProductForm();
    offerTilesPage.clearSearchFilters();*/

    offerTilesPage.enterSearchPhrase(eanCode);
    productForm = offerTilesPage.openProductForm();
    productForm.checkEanPresent(eanCode, errors);
    productForm.closeProductForm();
    offerTilesPage.clearSearchFilters();

    checkConditions();
  }
}
