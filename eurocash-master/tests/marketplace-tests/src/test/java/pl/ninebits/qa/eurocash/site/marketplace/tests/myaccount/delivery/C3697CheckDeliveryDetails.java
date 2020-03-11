package pl.ninebits.qa.eurocash.site.marketplace.tests.myaccount.delivery;

import lombok.val;
import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUserRole;
import pl.ninebits.qa.eurocash.site.marketplace.helpers.CartMarketHelper;
import pl.ninebits.qa.eurocash.site.marketplace.helpers.LoginMarketHelper;
import pl.ninebits.qa.eurocash.site.marketplace.pages.DeliveryDetailsPage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.DeliveryPage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.MarketplaceDashboardPage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.MarketplaceLoginPage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.MyAccountPage;
import pl.ninebits.qa.eurocash.site.marketplace.tests.MarketplaceTestTemplate;

public class C3697CheckDeliveryDetails extends MarketplaceTestTemplate {

  @Test(groups = {"uat", "dev"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C3697")
  public void checkDeliveryDetailsTest() throws Exception {
    EhurtAppUser marketUser = getEhurtAppUser(EhurtAppUserRole.MARKETPLACE);

    val daysBack = "30";

    MarketplaceLoginPage mLoginPage = loadMarketplaceLoginPage();
    MarketplaceDashboardPage mDashboardPage = LoginMarketHelper.doLogin(mLoginPage, marketUser);
    CartMarketHelper.clearCart(mDashboardPage);

    MyAccountPage myAccountPage = mDashboardPage.header.clickMyAccountBtn();
    DeliveryPage deliveryPage = myAccountPage.clickDeliverPage();
    deliveryPage.sendDaysBack(daysBack);
    deliveryPage.clickSortUp();
    String deliveryNumber = deliveryPage.getDeliveryNumber();
    DeliveryDetailsPage deliveryDetailsPage = deliveryPage.clickDeliveryBtn(deliveryNumber);
    deliveryDetailsPage.checkTitleBox(errors);
    deliveryDetailsPage.checkDetailsBox(errors);
    deliveryDetailsPage.checkOrderedProductsBox(errors);
    deliveryDetailsPage.checkLink(errors);

    checkConditions();
  }
}
