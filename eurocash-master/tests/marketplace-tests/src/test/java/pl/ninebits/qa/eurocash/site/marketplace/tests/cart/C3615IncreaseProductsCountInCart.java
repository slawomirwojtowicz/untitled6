package pl.ninebits.qa.eurocash.site.marketplace.tests.cart;

import lombok.val;
import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUserRole;
import pl.ninebits.qa.eurocash.site.marketplace.helpers.CartMarketHelper;
import pl.ninebits.qa.eurocash.site.marketplace.helpers.LoginMarketHelper;
import pl.ninebits.qa.eurocash.site.marketplace.pages.CartStep1Page;
import pl.ninebits.qa.eurocash.site.marketplace.pages.MarketplaceDashboardPage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.MarketplaceLoginPage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.ProductPage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.ShoppingPage;
import pl.ninebits.qa.eurocash.site.marketplace.tests.MarketplaceTestTemplate;
import pl.ninebits.qa.eurocash.site.vendorportal.constants.Product;

public class C3615IncreaseProductsCountInCart extends MarketplaceTestTemplate {

  @Test(groups = {"uat", "dev"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C3615")
  public void increaseProductsCountInCartTest() throws Exception {
    EhurtAppUser marketUser = getEhurtAppUser(EhurtAppUserRole.MARKETPLACE);
    Product product = Product.POLISH_SAUSAGES;
    val ean = product.getEAN();
    val mName = product.getMarketName();
    //tych wartości nie można pobrać z gridu
    val initialUnits = 40;
    val unitsIncrement = 20;
    val unitsAndPackagesIncrement = 40;

    MarketplaceLoginPage mLoginPage = loadMarketplaceLoginPage();
    MarketplaceDashboardPage mDashboardPage = LoginMarketHelper.doLogin(mLoginPage, marketUser);
    CartMarketHelper.clearCart(mDashboardPage);

    ShoppingPage shoppingPage = mDashboardPage.header.clickShoppingBtn();
    shoppingPage.header.searchForProduct(ean);

    ProductPage productPage = shoppingPage.clickFirstProduct();
    productPage.addToCartBtn();

    CartStep1Page cartStep1Page = productPage.footer.clickGoToCartBtn();
    cartStep1Page.checkProductInCartPresent(mName);
    cartStep1Page.increaseUnits();
    cartStep1Page.checkOrderValue(product.getPrice() * (initialUnits + unitsIncrement), errors);
    cartStep1Page.decreaseUnits();
    cartStep1Page.checkOrderValue(product.getPrice() * initialUnits, errors);
    cartStep1Page.increaseUnitsAndPackages();
    cartStep1Page.checkOrderValue(product.getPrice() * (initialUnits + unitsAndPackagesIncrement), errors);

    checkConditions();
  }
}
