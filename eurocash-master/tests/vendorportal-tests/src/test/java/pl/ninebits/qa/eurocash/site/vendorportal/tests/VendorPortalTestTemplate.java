package pl.ninebits.qa.eurocash.site.vendorportal.tests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import pl.ninebits.qa.automated.tests.core.tests.AbstractTestTemplate;
import pl.ninebits.qa.eurocash.api.users.VendorPortalUser;
import pl.ninebits.qa.eurocash.api.users.VendorPortalUserPool;
import pl.ninebits.qa.eurocash.api.users.VendorPortalUserRole;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.VendorLoginPage;

import java.text.MessageFormat;

@ContextConfiguration(locations = {"classpath:vendorportal-test-config.xml"})
public class VendorPortalTestTemplate extends AbstractTestTemplate {
  private static final Logger LOGGER = LoggerFactory.getLogger(VendorPortalTestTemplate.class);
  public static final int TEST_RAIL_PROJECT_ID = 1;

  @Value("${environmentName}")
  private String environmentName;

  @Value("${vendorportal.baseUrl}")
  private String vendorPortalBaseUrl;

  @Autowired
  private VendorPortalUserPool vendorPortalUserPool;

  private VendorPortalUser currentVendorPortalUser;

  @Override
  public String getEnvironmentName() {
    return environmentName;
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
  }

  protected String getVendorPortalBaseUrl() {
    return vendorPortalBaseUrl;
  }

  private void releasePortalUser() {
    if (currentVendorPortalUser != null) {
      vendorPortalUserPool.returnUser(currentVendorPortalUser);
      currentVendorPortalUser = null;
    }
  }
}
