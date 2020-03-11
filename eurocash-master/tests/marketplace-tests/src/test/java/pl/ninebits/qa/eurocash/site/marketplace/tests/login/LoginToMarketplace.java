package pl.ninebits.qa.eurocash.site.marketplace.tests.login;

import org.testng.annotations.Test;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUserRole;
import pl.ninebits.qa.eurocash.site.marketplace.pages.MarketplaceDashboardPage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.MarketplaceLoginPage;
import pl.ninebits.qa.eurocash.site.marketplace.tests.MarketplaceTestTemplate;

public class LoginToMarketplace extends MarketplaceTestTemplate {

  @Test
  public void loginToMarketplaceTest() throws Exception {
    EhurtAppUser ehurtAppUser = getEhurtAppUser(EhurtAppUserRole.MARKETPLACE);

    MarketplaceLoginPage marketplaceLoginPage = loadMarketplaceLoginPage();
    marketplaceLoginPage.typeLogin(ehurtAppUser.getLogin());
    marketplaceLoginPage.typePassword(ehurtAppUser.getPassword());

    MarketplaceDashboardPage marketplaceDashboardPage = marketplaceLoginPage.clickLoginBtn();
    marketplaceDashboardPage.checkUserLoggedIn(ehurtAppUser.getLogin(), errors);

    checkConditions();
  }
}
