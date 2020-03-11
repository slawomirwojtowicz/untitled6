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
import pl.ninebits.qa.eurocash.site.marketplace.pages.payments.PaymentsPage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.payments.TransferSummaryPage;
import pl.ninebits.qa.eurocash.site.marketplace.tests.MarketplaceTestTemplate;
import pl.ninebits.qa.eurocash.site.vendorportal.constants.Product;

public class C3617PaymentViaBankTransfer extends MarketplaceTestTemplate {

  @Test(groups = {"uat", "dev"}, enabled = false)
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C3617")
  public void paymentViaBankTransferTest() throws Exception {
    EhurtAppUser marketUser = getEhurtAppUser(EhurtAppUserRole.MARKETPLACE);
    Product product = Product.POLISH_SAUSAGES;
    val ean = product.getEAN();
    val mName = product.getMarketName();

    MarketplaceLoginPage mLoginPage = loadMarketplaceLoginPage();
    MarketplaceDashboardPage mDashboardPage = LoginMarketHelper.doLogin(mLoginPage, marketUser);
    CartMarketHelper.clearCart(mDashboardPage);

    ShoppingPage shoppingPage = mDashboardPage.header.clickShoppingBtn();
    shoppingPage.header.searchForProduct(ean);

    ProductPage productPage = shoppingPage.clickFirstProduct();
    productPage.addToCartBtn();

    CartStep1Page cartStep1Page = productPage.footer.clickGoToCartBtn();
    cartStep1Page.checkProductInCartPresent(mName);
    cartStep1Page.checkMinLogisticCriteriaAreMet();

    PaymentsPage paymentsPage = cartStep1Page.clickGoToPayment();
    paymentsPage.closeVerifyYourAddressDetails();
    paymentsPage.clickPayViaBankTransferTab();
    paymentsPage.enterEmail("ckopko@9bits.pl");
    paymentsPage.enterPhoneNumber("123456789");

    TransferSummaryPage transferSummaryPage = paymentsPage.clickConfirmOrderAndPaymentTypeBtn();
    transferSummaryPage.checkSummaryPresent(errors);
    transferSummaryPage.header.openCartsToPayPage();
    //todo: jak sprawdzić pobieranie płatności? (element nie ma linka)

    checkConditions();
  }
}
