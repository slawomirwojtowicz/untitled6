package pl.ninebits.qa.eurocash.site.marketplace.tests.myaccount.delivery;

import lombok.val;
import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUserRole;
import pl.ninebits.qa.eurocash.site.marketplace.constants.PaymentType;
import pl.ninebits.qa.eurocash.site.marketplace.forms.FilterForm;
import pl.ninebits.qa.eurocash.site.marketplace.helpers.CartMarketHelper;
import pl.ninebits.qa.eurocash.site.marketplace.helpers.LoginMarketHelper;
import pl.ninebits.qa.eurocash.site.marketplace.pages.CartStep1Page;
import pl.ninebits.qa.eurocash.site.marketplace.pages.DeliveryPage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.MarketplaceDashboardPage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.MarketplaceLoginPage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.ProductPage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.ShoppingListPage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.ShoppingPage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.payments.AfterPaymentPage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.payments.BlueMediaPaymentPage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.payments.PaymentsPage;
import pl.ninebits.qa.eurocash.site.marketplace.tests.MarketplaceTestTemplate;

public class C3699FilteringSearchByOrderNumber extends MarketplaceTestTemplate {

  @Test(groups = {"uat", "dev"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C3699")
  public void filteringSearchByOrderNumbertTest() throws Exception {
    EhurtAppUser marketUser = getEhurtAppUser(EhurtAppUserRole.MARKETPLACE);
    val ean = "7613036908290";//product.getEAN();
    val mName = "SOS WINIARY AZJATYCKI 250ML";

    MarketplaceLoginPage mLoginPage = loadMarketplaceLoginPage();
    MarketplaceDashboardPage mDashboardPage = LoginMarketHelper.doLogin(mLoginPage, marketUser);
    CartMarketHelper.clearCart(mDashboardPage);
    ShoppingListPage shoppingListPage = mDashboardPage.clickShoppinListgBtn();
    shoppingListPage.removeLists();
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
    FilterForm filterForm = deliveryPage.clickFilterBtn();
    filterForm.clickDeliveryBtn();
    filterForm.typeDeliveryValues(deliveryNumber);
    filterForm.clickLoupeBtn();
    filterForm.clickHideResults();
    deliveryPage.checkDeliveryNumber(deliveryNumber, errors);

    checkConditions();
  }
}
