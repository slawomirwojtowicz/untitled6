package pl.ninebits.qa.eurocash.site.marketplace.tests.myaccount.invoice;

import lombok.val;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUserRole;
import pl.ninebits.qa.eurocash.api.users.VendorPortalUser;
import pl.ninebits.qa.eurocash.api.users.VendorPortalUserRole;
import pl.ninebits.qa.eurocash.site.marketplace.constants.PaymentType;
import pl.ninebits.qa.eurocash.site.marketplace.helpers.CartMarketHelper;
import pl.ninebits.qa.eurocash.site.marketplace.helpers.LoginMarketHelper;
import pl.ninebits.qa.eurocash.site.marketplace.pages.CartStep1Page;
import pl.ninebits.qa.eurocash.site.marketplace.pages.DeliveryPage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.InvoiceDetailsPage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.InvoicePage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.MarketplaceDashboardPage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.MarketplaceLoginPage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.MyAccountPage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.ProductPage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.ShoppingPage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.payments.AfterPaymentPage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.payments.BlueMediaPaymentPage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.payments.PaymentsPage;
import pl.ninebits.qa.eurocash.site.marketplace.tests.MarketplaceTestTemplate;
import pl.ninebits.qa.eurocash.site.vendorportal.helpers.LoginHelper;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.OrderDetailsPage;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.OrdersPage;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.VendorDashboardPage;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.VendorLoginPage;

public class C3665CorrectInvoicingProcess extends MarketplaceTestTemplate {

  @Test(groups = {"uat", "dev"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C3665")
  public void correctInvoicingProcessTest() throws Exception {
    EhurtAppUser marketUser = getEhurtAppUser(EhurtAppUserRole.MARKETPLACE);
    val ean = "7613036908290";
    val mName = "SOS WINIARY AZJATYCKI 250ML";
    val invoiceNumber = RandomStringUtils.randomNumeric(6);

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
    paymentsPage.enterEmail("askrzynski@9bits.pl");
    paymentsPage.enterPhoneNumber("123456789");

    BlueMediaPaymentPage blueMediaPaymentPage = paymentsPage.clickPayment(PaymentType.TEST_PAYMENT);
    AfterPaymentPage afterPaymentPage = blueMediaPaymentPage.clickPayBtn();
    afterPaymentPage.checkPaymentSuccessfulMsgPresent(errors);
    DeliveryPage deliveryPage = afterPaymentPage.clickGoToDeliveryPage();
    deliveryPage.clickSortUp();
    String deliveryNumber = deliveryPage.getDeliveryNumber();
    deliveryPage.logOut();

    VendorPortalUser vendorUser = getVendorPortalUser(VendorPortalUserRole.ADMIN);
    VendorLoginPage vLoginPage = loadVendorLoginPage();
    VendorDashboardPage vDashboardPage = LoginHelper.doLogin(vLoginPage, vendorUser);
    OrdersPage ordersPage = vDashboardPage.sideMenu.clickOrdersSideLink();
    ordersPage.searchOrder(deliveryNumber);
    OrderDetailsPage orderDetailsPage = ordersPage.clickEditOrder(deliveryNumber);
    orderDetailsPage.enterInvoiceNumber(invoiceNumber);
    orderDetailsPage.clickChangeStatusBtn();
    orderDetailsPage.clickConfirmBtn();
    orderDetailsPage.clickSaveBtn();
    vLoginPage = orderDetailsPage.logOut();

    mLoginPage = loadMarketplaceLoginPage();
    mDashboardPage = LoginMarketHelper.doLogin(mLoginPage, marketUser);
    MyAccountPage myAccountPage = mDashboardPage.header.clickMyAccountBtn();
    InvoicePage invoicePage = myAccountPage.clickInvoicePage();
    invoicePage.clickSortUp();
    InvoiceDetailsPage invoiceDetailsPage = invoicePage.clickInvoiceNumberBtn(invoiceNumber);
    invoiceDetailsPage.checkInvoiceNumber(invoiceNumber, errors);

    checkConditions();
  }
}
