package pl.ninebits.qa.automated.tests.core.tests;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import pl.ninebits.qa.automated.tests.core.BrowserType;
import pl.ninebits.qa.automated.tests.core.TestPlatform;

@Data
public class TestConfig {

    @Value("${selenium.webdriver.useLocalDriver}")
    private boolean useLocalDriver;

    @Value("${selenium.webdriver.browserType}")
    private BrowserType browserType;

    @Value("${selenium.webdriver.platform}")
    private TestPlatform platform;

    @Value("${testRail.enabled}")
    private boolean testRailEnabled;

    @Value("${selenium.webdriver.pageLoadTimeoutInSec}")
    private int pageLoadTimeoutInSec;

    @Value("${selenium.screenshots.server}")
    private String screenshotsServer;

    //@Value("${selenium.screenshots.deleteAgeInDays}")
    //private int screenshotsDeleteAgeInDays;
}
