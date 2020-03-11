package pl.ninebits.qa.eurocash.site.vendorportal.tests.promotions;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.VendorPortalUserRole;
import pl.ninebits.qa.eurocash.site.vendorportal.constants.Product;
import pl.ninebits.qa.eurocash.site.vendorportal.helpers.LoginHelper;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.AdminPromotionsPage;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.VendorDashboardPage;
import pl.ninebits.qa.eurocash.site.vendorportal.tests.VendorPortalTestTemplate;

public class C4694PromoSearchMechanism extends VendorPortalTestTemplate {

  @Test(groups = {"production", "preprod", "uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C4694")
  public void promoSearchMechanismTest() throws Exception {
    String promoName = "Promocja_test_auto";
    String promoProduct = Product.JACK_DANIELS.getName();
    String randomQuery = RandomStringUtils.randomAlphabetic(10);

    VendorPortalUserRole role = VendorPortalUserRole.ADMIN;

    VendorDashboardPage vendorDashboardPage = LoginHelper.doLogin(loadVendorLoginPage(), getVendorPortalUser(role));
    AdminPromotionsPage promotionsPage = (AdminPromotionsPage) vendorDashboardPage.sideMenu.clickPromotionsSideLink(role);

    promotionsPage.typeSearchQuery(promoName);
    promotionsPage.checkPromotionsFilteredByName(promoName, errors);

    promotionsPage.typeSearchQuery(promoProduct);
    promotionsPage.checkPromotionsFilteredByProductName(promoProduct, errors);

    promotionsPage.typeSearchQuery(randomQuery);
    promotionsPage.checkNoProductsFound(errors);

    checkConditions();
  }
}