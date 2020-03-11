package pl.ninebits.qa.eurocash.site.marketplace.tests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import pl.ninebits.qa.automated.tests.core.tests.AbstractTestTemplate;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUserRole;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUsersPool;
import pl.ninebits.qa.eurocash.api.users.VendorPortalUser;
import pl.ninebits.qa.eurocash.api.users.VendorPortalUserPool;
import pl.ninebits.qa.eurocash.api.users.VendorPortalUserRole;
import pl.ninebits.qa.eurocash.site.marketplace.pages.MarketplaceLoginPage;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.VendorLoginPage;

import java.text.MessageFormat;

@ContextConfiguration(locations = {"classpath:marketplace-test-config.xml"})
public class MarketplaceTestTemplate extends AbstractTestTemplate {
  private static final Logger LOGGER = LoggerFactory.getLogger(MarketplaceTestTemplate.class);
  public static final int TEST_RAIL_PROJECT_ID = 7;

  @Value("${environmentName}")
  private String environmentName;

  @Value("${marketplace.baseUrl}")
  private String marketplaceBaseUrl;

  @Value("${vendorportal.baseUrl}")
  private String vendorPortalBaseUrl;

  @Autowired
  private EhurtAppUsersPool ehurtAppUsersPool;

  private EhurtAppUser currentEhurtUser;

  @Autowired
  private VendorPortalUserPool vendorPortalUserPool;

  private VendorPortalUser currentVendorPortalUser;

  @Override
  public String getEnvironmentName() {
    return environmentName;
  }

  protected MarketplaceLoginPage loadMarketplaceLoginPage() {
    return loadPage(marketplaceBaseUrl, MarketplaceLoginPage.class);
  }

  protected EhurtAppUser getEhurtAppUser() throws Exception {
    if (currentEhurtUser == null) {
      currentEhurtUser = ehurtAppUsersPool.borrowUser();
      LOGGER.info(MessageFormat.format("Test {0} is working on user {1}", getTestName(), currentEhurtUser.getLogin()));
    }
    return currentEhurtUser;
  }

  protected EhurtAppUser getEhurtAppUser(EhurtAppUserRole role) throws Exception {
    if (currentEhurtUser == null) {
      currentEhurtUser = ehurtAppUsersPool.borrowUser(role);
      LOGGER.info(MessageFormat.format("Test {0} is working on panel user {1} with role {2}", getTestName(),
        currentEhurtUser.getLogin(), currentEhurtUser.getRole().getRoleName()));
    }
    return currentEhurtUser;
  }

  protected VendorLoginPage loadVendorLoginPage() {
    return loadPage(vendorPortalBaseUrl, VendorLoginPage.class);
  }

  protected VendorPortalUser getVendorPortalUser() throws Exception {
    if (currentVendorPortalUser == null) {
      currentVendorPortalUser = vendorPortalUserPool.borrowUser();
      LOGGER.info(MessageFormat.format("Test {0} is working on user {1}", getTestName(), currentVendorPortalUser.getLogin()));
    }
    return currentVendorPortalUser;
  }

  protected VendorPortalUser getVendorPortalUser(VendorPortalUserRole role) throws Exception {
    if (currentVendorPortalUser == null) {
      currentVendorPortalUser = vendorPortalUserPool.borrowUser(role);
      LOGGER.info(MessageFormat.format("Test {0} is working on panel user {1} with role {2}", getTestName(),
        currentVendorPortalUser.getLogin(), currentVendorPortalUser.getRole().getRoleName()));
    }
    return currentVendorPortalUser;
  }

  @Override
  public void doAfterTest() {
    super.doAfterTest();
    releasePortalUser();
    releaseVendorPortalUserPool();
  }

  protected String getMarketplaceBaseUrl() {
    return marketplaceBaseUrl;
  }

  protected String getVendorPortalBaseUrl() {
    return vendorPortalBaseUrl;
  }

  private void releaseVendorPortalUserPool() {
    if (currentVendorPortalUser != null) {
      vendorPortalUserPool.returnUser(currentVendorPortalUser);
      currentVendorPortalUser = null;
    }
  }

  private void releasePortalUser() {
    if (currentEhurtUser != null) {
      ehurtAppUsersPool.returnUser(currentEhurtUser);
      currentEhurtUser = null;
    }
  }
}
