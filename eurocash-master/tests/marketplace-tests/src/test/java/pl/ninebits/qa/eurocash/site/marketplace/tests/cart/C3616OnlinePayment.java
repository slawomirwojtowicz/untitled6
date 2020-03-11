package pl.ninebits.qa.eurocash.site.marketplace.tests.cart;

import lombok.val;
import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUserRole;
import pl.ninebits.qa.eurocash.api.users.VendorPortalUserRole;
import pl.ninebits.qa.eurocash.site.marketplace.constants.PaymentType;
import pl.ninebits.qa.eurocash.site.marketplace.forms.FilterForm;
import pl.ninebits.qa.eurocash.site.marketplace.helpers.CartMarketHelper;
import pl.ninebits.qa.eurocash.site.marketplace.helpers.LoginMarketHelper;
import pl.ninebits.qa.eurocash.site.marketplace.pages.CartStep1Page;
import pl.ninebits.qa.eurocash.site.marketplace.pages.DeliveryPage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.MarketplaceDashboardPage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.MarketplaceLoginPage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.ProductPage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.ShoppingPage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.payments.AfterPaymentPage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.payments.BlueMediaPaymentPage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.payments.PaymentsPage;
import pl.ninebits.qa.eurocash.site.marketplace.tests.MarketplaceTestTemplate;
import pl.ninebits.qa.eurocash.site.vendorportal.constants.Product;
import pl.ninebits.qa.eurocash.site.vendorportal.helpers.LoginHelper;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.OrdersPage;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.VendorDashboardPage;

public class C3616OnlinePayment extends MarketplaceTestTemplate {

  @Test(groups = {"uat", "dev"}, description = "Online payment shopping") //todo: dodać do regressu po zakończeniu części vendorowej
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C3616")
  public void onlinePaymentTest() throws Exception {
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
    paymentsPage.clickOnlinePaymentsTab();
    paymentsPage.enterEmail("ckopko@9bits.pl");
    paymentsPage.enterPhoneNumber("123456789");

    BlueMediaPaymentPage blueMediaPaymentPage = paymentsPage.clickPayment(PaymentType.TEST_PAYMENT);
    AfterPaymentPage afterPaymentPage = blueMediaPaymentPage.clickPayBtn();
    afterPaymentPage.checkPaymentSuccessfulMsgPresent(errors);
    String cartId = afterPaymentPage.getCartId();
    DeliveryPage deliveryPage = afterPaymentPage.clickGoToDeliveryPage();

    FilterForm filterForm = deliveryPage.clickFilterBtn();
    filterForm.filterByCartId(cartId);

    String deliveryId = deliveryPage.getDeliveryId();

    VendorDashboardPage vDashboardPage = LoginHelper.doLogin(loadVendorLoginPage(), getVendorPortalUser(VendorPortalUserRole.VENDOR));
    OrdersPage ordersPage = vDashboardPage.sideMenu.clickOrdersSideLink();
    ordersPage.searchOrder(deliveryId);
    ordersPage.checkOrderPresent(deliveryId, errors);

    checkConditions();
  }
}