package pl.ninebits.qa.eurocash.site.vendorportal.tests.groupoffers;

import lombok.val;
import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.automated.tests.core.utils.DateUtils;
import pl.ninebits.qa.eurocash.api.users.VendorPortalUserRole;
import pl.ninebits.qa.eurocash.site.vendorportal.helpers.LoginHelper;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.AddOfferPage;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.DealDataPage;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.GroupOffersPage;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.LogisticsPage;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.VendorDashboardPage;
import pl.ninebits.qa.eurocash.site.vendorportal.tests.VendorPortalTestTemplate;

public class C4585listingADealerWithAFixedLogisticsPriceEC extends VendorPortalTestTemplate {

  @Test(groups = {"production", "preprod", "uat"})
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C4585")
  public void listingADealerWithAFixedLogisticsPriceECTest() throws Exception {
    val vendor = "OSM KRASNYSTAW_2";
    val netPrice = "10,56";
    val regularPrice = "12";
    val pack = "10";
    val aggregateMinimum = "300";
    val aggregateMax = "500";
    val individualComment = "Test automatyczny";
    val logistic = "Lublin";

    String startDate = DateUtils.getPastDateDashFormatMP(0);
    String endDate = DateUtils.getLaterDateDashFormatMP(3);
    String startSupplyDate = DateUtils.getLaterDateDashFormatMP(6);


    VendorDashboardPage vendorDashboardPage = LoginHelper.doLogin(loadVendorLoginPage(), getVendorPortalUser(VendorPortalUserRole.ADMIN));
    GroupOffersPage groupOffersPage = vendorDashboardPage.clickShowAllDeals();
    AddOfferPage addOfferPage = groupOffersPage.clickCreateANewDealPage();
    addOfferPage.clickSortIcon();
    addOfferPage.typeSearchForVendorValues(vendor);
    addOfferPage.clickConfirmBtn();
    addOfferPage.clickSortIcon();
    addOfferPage.clickRandomProduct();
    DealDataPage dealDataPage = addOfferPage.clickAddOffer();
    dealDataPage.typeNetPriceValue(netPrice);
    dealDataPage.typeRegularPriceValue(regularPrice);
    dealDataPage.typePackValue(pack);
    dealDataPage.typeAggregateMinimumValue(aggregateMinimum);
    dealDataPage.typeAggregateMaxValue(aggregateMax);
    dealDataPage.typeYourIndividualComment(individualComment);
    dealDataPage.typeOfferDescription(individualComment);
    dealDataPage.enterStartDate(startDate);
    LogisticsPage logisticsPage = dealDataPage.enterEndDate(endDate);
    logisticsPage.changeOneLogistics(logistic);
    logisticsPage.enterStartDate(startSupplyDate);
    logisticsPage.clickSendForVerificationBtn();
    vendorDashboardPage.sideMenu.clickGroupOffersSideLink();
  //TODO Poprawić

    checkConditions();
  }
}
