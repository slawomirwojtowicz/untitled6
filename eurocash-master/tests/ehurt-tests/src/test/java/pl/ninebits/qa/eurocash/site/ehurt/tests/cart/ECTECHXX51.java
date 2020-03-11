package pl.ninebits.qa.eurocash.site.ehurt.tests.cart;

import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.automated.tests.core.tests.TestDurationLogger;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.site.ehurt.constants.ProductsName;
import pl.ninebits.qa.eurocash.site.ehurt.helpers.CartHelper;
import pl.ninebits.qa.eurocash.site.ehurt.helpers.EhurtAppLoginHelper;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtHomePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtStartPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.myaccount.OrdersHistoryPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.myaccount.PaymentsPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.shopping.*;
import pl.ninebits.qa.eurocash.site.ehurt.tests.EhurtTestTemplate;

import java.text.MessageFormat;
import java.util.List;

public class ECTECHXX51 extends EhurtTestTemplate {

    @Test(groups = {"production"})
    @TestDurationLogger(breakpoints = {"dashboard", "import1", "import2", "productTotal", "product1", "product2", "product3", "product4", "product5", "product6", "product7",
            "product8", "product9", "product10", "cartPage1", "offerPage", "cartPage2", "cartSearch"})
    public void Test() throws Exception {
        final String userLogin = "Monit24.off";
        final String userPass = "monit24";

        ProductsName[] products = ProductsName.values();
        int pCount = 10;
        if (pCount > products.length) {
            pCount = products.length;
        }

        EhurtStartPage startPage = loadEhurtStartPage();
        startPage.typeLogin(userLogin);
        startPage.typePassword(userPass);
        EhurtHomePage homePage = (EhurtHomePage) startPage.clickLoginBtn();

        //EhurtAppUser ehurtAppUser = getEhurtAppUser();
        //EhurtStartPage ehurtStartPage = loadEhurtStartPage();
        //EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);

        setTimestamp("dashboard");
        homePage.closePopups();

        //1 .Zaimportowanie z pliku około 500 indeksow – czas (plik do formatu 2x po 300 format  PC Market 7 (do koszyka)
        homePage.clearCartIfNotEmpty();
        CartStep1Page cartPage = homePage.clickGoToCartLink();

        cartPage.cartImportStep1("C:\\temp\\1.PCMarket_300_1_z_2.txt", "PC Market 7");
        setTimestamp("import1", true);
        cartPage.cartImportStep2();
        setTimestamp("import1");

        cartPage.cartImportStep1("C:\\temp\\1.PCMarket_300_2_z_2.txt", "PC Market 7");
        setTimestamp("import2", true);
        cartPage.cartImportStep2();
        setTimestamp("import2");

        //2. Przechodzenie po ofercie scrolem min 5scroli
        OfferPage offerPage = homePage.clickOffersLink_shoppingMenu(errors);
        if (offerPage != null) {
            offerPage.checkOfferPageLoaded(errors);
        }

        //3. Wyszukiwanie w ofercie: Czas od momentu wpisania frazy w wyszukiwarce do wyświetlenia wyników w ofercie (ale uwaga powinny to być czasy nie jednego wpisu tylko kolejnych np. powyżej 10 frazy
        setTimestamp("productTotal", true);
        for (int i = 0; i < pCount; i++) {
            setTimestamp(MessageFormat.format("product{0}", (i + 1)), true);
            offerPage.enterSearchPhrase(products[i]);
            offerPage.confirmSearch();

            //INFO: ujednolicenie do Offline - nie działa na offline wielokrotne wyszukiwanie bez czyszczenia filtra
            setTimestamp(MessageFormat.format("product{0}", (i + 1)));
            offerPage.clearSearchFilters();
        }
        setTimestamp("productTotal");

        //4. Przejście pomiędzy oferta a koszykiem (mierzony do momentu wyświetlenia zawartości koszyka)
        setTimestamp("cartPage1", true);
        homePage.clickGoToCartLink();
        setTimestamp("cartPage1");

        //5. powrót z koszyka do oferty (mierzony do momentu wyświetlenia produktów w ofercie)
        setTimestamp("offerPage", true);
        offerPage = homePage.clickOffersLink_shoppingMenu(errors);
        if (offerPage != null) {
            offerPage.checkOfferPageLoaded(errors);
        }
        setTimestamp("offerPage");

        //6. Przejście pomiędzy oferta a koszykiem (mierzony do momentu wyświetlenia zawartości koszyka)
        setTimestamp("cartPage2", true);
        cartPage = homePage.clickGoToCartLink();
        setTimestamp("cartPage2");

        //8. Wyszukanie towaru w koszyku
        List<String> cartProductList = cartPage.getProductNames();
        if(cartProductList == null || cartProductList.size() > 0) {
            final String cartSearchProduct = cartProductList.get(0);
            setTimestamp("cartSearch", true);
            cartPage.enterSearchPhrase(cartSearchProduct);
            setTimestamp("cartSearch");
            cartPage.checkCartSize(1, errors);
            cartPage.checkProductInCartPresent(cartSearchProduct, true, errors);
        }
        else {
            errors.add("Brak produktów w koszyku.");
        }

        //7. Obniżenie ceny budżetem w koszyku
        //cartPage.clickBudgetDown(cartSearchProduct, errors);

        cartPage.clearCart();
        checkConditions();
    }

}
