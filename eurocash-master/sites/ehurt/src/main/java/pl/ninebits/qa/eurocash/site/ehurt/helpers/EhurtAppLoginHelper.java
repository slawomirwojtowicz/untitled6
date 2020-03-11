package pl.ninebits.qa.eurocash.site.ehurt.helpers;

import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtHomePage;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtStartPage;

public class EhurtAppLoginHelper {

  public static EhurtHomePage doLogin(EhurtStartPage startPage, String username, String password) {
    startPage.typeLogin(username);
    startPage.typePassword(password);
    return (EhurtHomePage) startPage.clickLoginBtn();
  }

  public static EhurtHomePage doLogin(EhurtStartPage startPage, EhurtAppUser user) {
    startPage.typeLogin(user.getLogin());
    startPage.typePassword(user.getPassword());
    return (EhurtHomePage) startPage.clickLoginBtn();
  }
}
