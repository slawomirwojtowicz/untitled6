package pl.ninebits.qa.eurocash.site.vendorportal.helpers;

import pl.ninebits.qa.eurocash.api.users.VendorPortalUser;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.VendorDashboardPage;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.VendorLoginPage;

public class LoginHelper {

  private LoginHelper() {
    throw new IllegalStateException("Login helper class");
  }

  public static VendorDashboardPage doLogin(VendorLoginPage loginPage, VendorPortalUser user) throws Exception {
    loginPage.typeLogin(user.getLogin());
    loginPage.typePassword(user.getPassword());

    return loginPage.clickLoginBtnAndCheckIfUserLoggedInSuccessfully();
  }
}
