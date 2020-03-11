package pl.ninebits.qa.eurocash.site.ehurt.tests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import pl.ninebits.qa.automated.tests.core.tests.AbstractTestTemplate;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUser;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUserRole;
import pl.ninebits.qa.eurocash.api.users.EhurtAppUsersPool;
import pl.ninebits.qa.eurocash.site.ehurt.pages.EhurtStartPage;

import java.text.MessageFormat;

@ContextConfiguration(locations = {"classpath:ehurt-test-config.xml"})
public class EhurtTestTemplate extends AbstractTestTemplate {
    private static final Logger LOGGER = LoggerFactory.getLogger(EhurtTestTemplate.class);
    public static final int TEST_RAIL_PROJECT_ID = 1;

    @Value("${environmentName}")
    private String environmentName;

    @Value("${ehurt.baseUrl}")
    private String ehurtBaseUrl;

    @Autowired
    private EhurtAppUsersPool ehurtAppUsersPool;

    private EhurtAppUser currentEhurtUser;

    @Override
    public String getEnvironmentName() {
        return environmentName;
    }

    protected EhurtStartPage loadEhurtStartPage() {
        return loadPage(ehurtBaseUrl, EhurtStartPage.class);
    }

    protected EhurtAppUser getEhurtAppUser() throws Exception {
        if (currentEhurtUser == null) {
            currentEhurtUser = ehurtAppUsersPool.borrowUser();
            LOGGER.info(MessageFormat.format("Test {0} is working on user {1}", getTestName(), currentEhurtUser.getLogin()));
        }
        return currentEhurtUser;
    }

    protected EhurtAppUser getEhurtAppUser(EhurtAppUserRole role) throws Exception {
        return null;// TODO
    }

    @Override
    public void doAfterTest() {
        super.doAfterTest();
        releasePortalUser();
    }

    protected String getEhurtBaseUrl() {
        return ehurtBaseUrl;
    }

    private void releasePortalUser() {
        if (currentEhurtUser != null) {
            ehurtAppUsersPool.returnUser(currentEhurtUser);
            currentEhurtUser = null;
        }
    }
}
