package pl.ninebits.qa.eurocash.site.marketplace.helpers;

import pl.ninebits.qa.eurocash.site.marketplace.pages.CartStep1Page;
import pl.ninebits.qa.eurocash.site.marketplace.pages.MarketplaceDashboardPage;

public class CartMarketHelper {

  private CartMarketHelper() {
    throw new UnsupportedOperationException("CartMarketHelper utility class");
  }

  public static void clearCart(MarketplaceDashboardPage dashboardPage) throws Exception {
    if (!dashboardPage.footer.checkCartEmpty()) {
      CartStep1Page cartStep1Page = dashboardPage.footer.clickGoToCartBtn();
      cartStep1Page.deleteProducts();
    }
  }
}
