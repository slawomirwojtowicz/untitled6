package pl.ninebits.qa.eurocash.site.marketplace.helpers;

import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.site.marketplace.pages.MarketplaceDashboardPage;
import pl.ninebits.qa.eurocash.site.marketplace.pages.MarketplaceLoginPage;

public class LoginMarketHelper {

  public static MarketplaceDashboardPage doLogin(MarketplaceLoginPage mLoginPage, EhurtAppUser user) {
    mLoginPage.typeLogin(user.getLogin());
    mLoginPage.typePassword(user.getPassword());

    return mLoginPage.clickLoginBtn();
  }
}
