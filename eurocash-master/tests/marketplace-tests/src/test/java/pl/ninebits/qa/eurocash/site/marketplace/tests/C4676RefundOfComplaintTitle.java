package pl.ninebits.qa.eurocash.site.marketplace.tests;

import lombok.val;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.automated.tests.core.utils.DateUtils;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUserRole;
import pl.ninebits.qa.eurocash.api.users.VendorPortalUser;
import pl.ninebits.qa.eurocash.api.users.VendorPortalUserRole;
import pl.ninebits.qa.eurocash.site.marketplace.constants.ComplaintType;
import pl.ninebits.qa.eurocash.site.marketplace.constants.PaymentType;
import pl.ninebits.qa.eurocash.site.marketplace.constants.ProductName;
import pl.ninebits.qa.eurocash.site.marketplace.forms.ComplaintForm;
import pl.ninebits.qa.eurocash.site.marketplace.forms.FilterForm;
import pl.ninebits.qa.eurocash.site.marketplace.helpers.CartMarketHelper;
import pl.ninebits.qa.eurocash.site.marketplace.helpers.LoginMarketHelper;
import pl.ninebits.qa.eurocash.site.marketplace.pages.CartStep1Page;
import pl.ninebits.qa.eurocash.site.marketplace.pages.ComplaintDetailsPage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.ComplaintPage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.DeliveryPage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.MarketplaceDashboardPage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.MarketplaceLoginPage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.MyAccountPage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.NewComplaintPage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.ProductPage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.ShoppingPage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.payments.AfterPaymentPage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.payments.BlueMediaPaymentPage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.payments.PaymentsPage;
import pl.ninebits.qa.eurocash.site.vendorportal.forms.AddingNewCorrectionForm;
import pl.ninebits.qa.eurocash.site.vendorportal.helpers.LoginHelper;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.ComplaintsPage;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.EditComplaintPage;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.LineDetailsPage;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.OrderDetailsPage;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.OrdersPage;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.RefundsPage;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.VendorDashboardPage;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.VendorLoginPage;

public class C4676RefundOfComplaintTitle extends MarketplaceTestTemplate {

  @Test(groups = {"uat", "dev"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C4676")
  public void refundOfComplaintTitleTest() throws Exception {
    EhurtAppUser marketUser = getEhurtAppUser(EhurtAppUserRole.MARKETPLACE);
    //Product product = Product.POLISH_SAUSAGES;
    val quantity = "1";
    val ean = "5900820000165";//product.getEAN();
    val mName = "MLEKO UHT LACIATE ZIELONE 0,5% 500ML MLEKPOL";//product.getMarketName();
    val amountOfProducts = "300";
    val invoiceNumber = RandomStringUtils.randomNumeric(7);
    val correctionNumber = RandomStringUtils.randomNumeric(6);
    val currentDate = DateUtils.getCurrentDate("ddMMyyyy");
    val producer = "NESTLE_2";

    MarketplaceLoginPage mLoginPage = loadMarketplaceLoginPage();
    MarketplaceDashboardPage mDashboardPage = LoginMarketHelper.doLogin(mLoginPage, marketUser);
    CartMarketHelper.clearCart(mDashboardPage);

    ShoppingPage shoppingPage = mDashboardPage.header.clickShoppingBtn();
    shoppingPage.clickProducerFilterCheckBox(producer);
    shoppingPage.header.searchForProduct(ean);

    ProductPage productPage = shoppingPage.clickFirstProduct();
    productPage.typeAmountOfProducts(amountOfProducts);
    productPage.clickAddToCartBtn();

    CartStep1Page cartStep1Page = productPage.footer.clickGoToCartBtn();
    cartStep1Page.checkProductInCartPresent(mName);

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
    ComplaintPage complaintPage = myAccountPage.clickComplaintBtn();
    NewComplaintPage newComplaintPage = complaintPage.clickReturnArticleBtn();
    newComplaintPage.clickReturnArticleBtn();

    ComplaintForm complaintForm = newComplaintPage.selectComplaint();
    complaintForm.complaintTypeBtn(ComplaintType.DEFECT);
    complaintForm.selectCommodity(ProductName.LOG_NESTLE2);
    complaintForm.selectInvoiceNumber();
    complaintForm.typeQuantity(quantity);
    newComplaintPage = complaintForm.addProductToComplaint();
    newComplaintPage.clickSendComplaint();
    newComplaintPage.checkThankYouPresent(errors);
    newComplaintPage.checkInvoiceNumber(invoiceNumber, errors);
    newComplaintPage.checkProductName(ProductName.LOG_NESTLE2, errors);
    ComplaintDetailsPage complaintDetailsPage = newComplaintPage.clickComplaintDetails();
    complaintDetailsPage.checkInvoiceNumber(invoiceNumber, errors);
    complaintDetailsPage.checkProductName(ProductName.LOG_NESTLE2, errors);
    String complaintNumber = complaintDetailsPage.geComplaintNumber(); //"6000001836";//
    complaintDetailsPage.logOut();

    vendorUser = getVendorPortalUser(VendorPortalUserRole.ADMIN);
    vLoginPage = loadVendorLoginPage();
    vDashboardPage = LoginHelper.doLogin(vLoginPage, vendorUser);
    ComplaintsPage complaintsPage = vDashboardPage.sideMenu.clickComplaintsSideLink();
    complaintsPage.searchProduct(complaintNumber);
    complaintsPage.checkComplaint(complaintNumber, errors);
    EditComplaintPage editComplaintPage = complaintsPage.clickEditComplaintBtn();
    LineDetailsPage lineDetailsPage = editComplaintPage.clickFilterBtn();
    lineDetailsPage.typeAmountOfProducts(quantity);
    editComplaintPage = lineDetailsPage.clickApproveBtn();
    AddingNewCorrectionForm addingNewCorrectionForm = editComplaintPage.addNewCorrection();
    addingNewCorrectionForm.typeCorrectionNumberValue(correctionNumber);
    addingNewCorrectionForm.typeCurrentDate(currentDate);
    addingNewCorrectionForm.clickCheckboxBtn();
    editComplaintPage = addingNewCorrectionForm.clickApproveBtn();
    editComplaintPage.checkRecognitionOfComplaints(errors);
    RefundsPage refundsPage = vDashboardPage.sideMenu.clickRefundSideLink();
    refundsPage.searchProduct(complaintNumber);
    refundsPage.clickEditBtn();
    refundsPage.logOut();

    mLoginPage = loadMarketplaceLoginPage();
    mDashboardPage = LoginMarketHelper.doLogin(mLoginPage, marketUser);

    mDashboardPage.header.clickMyAccountBtn();
    deliveryPage = myAccountPage.clickDeliverPage();
    FilterForm filterForm = deliveryPage.clickFilterBtn();
    filterForm.clickDeliveryBtn();
    filterForm.typeDeliveryValues(deliveryNumber);
    filterForm.clickLoupeBtn();
    filterForm.clickHideResults();
    deliveryPage.moveToRefundToAccount(deliveryNumber);
  //  complaintPage.checComplaintNumber(complaintNumber, errors);

    checkConditions();
  }
}
