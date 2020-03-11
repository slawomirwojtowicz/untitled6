package pl.ninebits.qa.eurocash.site.ehurt.helpers;

import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtHomePage;

public class CartHelper {

  public static void clearCart(EhurtHomePage homePage) {
    if (homePage.isCartNotEmpty()) {
      homePage.clearCart();
    }
  }
}
