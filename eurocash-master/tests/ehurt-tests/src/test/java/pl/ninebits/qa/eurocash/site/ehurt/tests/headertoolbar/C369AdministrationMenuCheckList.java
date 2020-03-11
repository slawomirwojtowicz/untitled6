package pl.ninebits.qa.eurocash.site.ehurt.tests.headertoolbar;

import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.site.ehurt.helpers.EhurtAppLoginHelper;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtHomePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtStartPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.administration.AbbreviationPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.administration.ContractorGroupPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.administration.ContractorPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.administration.CoverageOfAccountsPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.administration.DynamicFiltersPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.administration.MarketingConsentsPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.administration.MenuPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.administration.MessagesPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.administration.NewAccessManuPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.administration.NewMenuPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.administration.PermissionsCategoryPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.administration.PermissionsPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.administration.RankPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.administration.UsersGroupPage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.administration.UsersPage;
import pl.ninebits.qa.eurocash.site.ehurt.tests.EhurtTestTemplate;

public class C369AdministrationMenuCheckList extends EhurtTestTemplate {
  @Test(groups = {"production", "preprod", "production"}, enabled = false)
  @TestRailCase(projectId = TEST_RAIL_PROJECT_ID, caseId = "C369")
  public void shoppingMenuCheckLinksTest() throws Exception {
    EhurtAppUser ehurtAppUser = getEhurtAppUser();
    EhurtStartPage ehurtStartPage = loadEhurtStartPage();
    EhurtHomePage homePage = EhurtAppLoginHelper.doLogin(ehurtStartPage, ehurtAppUser);
    MenuPage menuePage = homePage.clickMenueLink_administrationMenu(errors);
    if (menuePage != null) {
      menuePage.checkMenuePagePresent(errors);
      homePage = menuePage.goToHomePage();
    }

    RankPage rankPage = homePage.clickMenueLink_RanknMenu(errors);
    if (rankPage != null) {
      rankPage.checkRankPagePresent(errors);
      homePage = menuePage.goToHomePage();
    }

    DynamicFiltersPage dynamicFiltersPage = homePage.clickMenueLink_DynamicFiltersMenu(errors);
    if (dynamicFiltersPage != null) {
      dynamicFiltersPage.checkDynamicPagePresent(errors);
      homePage = menuePage.goToHomePage();
    }

    CoverageOfAccountsPage coverageOfAccountsPage = homePage.clickMenueLink_AccountRangeFiltersMenu(errors);
    if (coverageOfAccountsPage != null) {
      coverageOfAccountsPage.checkCoverageOfAccountsPagePresent(errors);
      homePage = menuePage.goToHomePage();
    }

    NewMenuPage newMenuPage = homePage.clickMenueLink_NewMenuFiltersMenu(errors);
    if (newMenuPage != null) {
      newMenuPage.checkNewMenuPagePresent(errors);
      homePage = menuePage.goToHomePage();
    }

    NewAccessManuPage newAccessManuPage = homePage.clickMenueLink_NewAccessMenuFiltersMenu(errors);
    if (newAccessManuPage != null) {
      newAccessManuPage.checkNewAccessMenuPagePresent(errors);
      homePage = menuePage.goToHomePage();
    }

    UsersPage usersPage = homePage.clickMenueLink_UsersPageFiltersMenu(errors);
    if (usersPage != null) {
      usersPage.checkUsersPresent(errors);
      homePage = menuePage.goToHomePage();
    }

    ContractorPage contractorPage = homePage.clickMenueLink_ContractorFiltersMenu(errors);
    if (contractorPage != null) {
      contractorPage.checkContractorPresent(errors);
      homePage = menuePage.goToHomePage();
    }

    UsersGroupPage usersGroupPage = homePage.clickMenueLink_UsersGroupFiltersMenu(errors);
    if (usersGroupPage != null) {
      usersGroupPage.checkUsersGroupPresent(errors);
      homePage = menuePage.goToHomePage();
    }

    ContractorGroupPage contractorGroupPage = homePage.clickMenueLink_ContractorGroupFiltersMenu(errors);
    if (contractorGroupPage != null) {
      contractorGroupPage.checkContractorGroupPresent(errors);
      homePage = menuePage.goToHomePage();
    }

    MessagesPage messagesPage = homePage.clickMenueLink_MessagesFiltersMenu(errors);
    if (menuePage != null) {
      messagesPage.checkMessagesPresent(errors);
      homePage = menuePage.goToHomePage();
    }

    AbbreviationPage abbreviationPage = homePage.clickMenueLink_AbbreviationFiltersMenu(errors);
    if (abbreviationPage != null) {
      abbreviationPage.checkAbbreviationPresent(errors);
      homePage = menuePage.goToHomePage();
    }

    PermissionsPage permissionsPage = homePage.clickMenueLink_PermissionsFiltersMenu(errors);
    if (permissionsPage != null) {
      permissionsPage.checkPermissionsPresent(errors);
      homePage = menuePage.goToHomePage();
    }

    PermissionsCategoryPage permissionsCategoryPage = homePage.clickMenueLink_PermissionsCategoryFiltersMenu(errors);
    if (permissionsCategoryPage != null) {
      permissionsCategoryPage.checkPemissionsCategoryPresent(errors);
      homePage = menuePage.goToHomePage();
    }

    MarketingConsentsPage marketingConsentsyPage = homePage.clickMenueLink_MarketingConsentsFiltersMenu(errors);
    if (marketingConsentsyPage != null) {
      marketingConsentsyPage.checkMarketingConsentsPresent(errors);
      homePage = menuePage.goToHomePage();
    }


//TODO Dokończyć pozostałe odnośniki, zastanowić się nad podzieleniem testu
    checkConditions();
  }
}
