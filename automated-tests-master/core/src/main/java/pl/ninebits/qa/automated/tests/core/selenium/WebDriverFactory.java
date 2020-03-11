package pl.ninebits.qa.automated.tests.core.selenium;

import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import pl.ninebits.qa.automated.tests.core.BrowserType;
import pl.ninebits.qa.automated.tests.core.TestPlatform;

import java.net.URL;
import java.util.concurrent.TimeUnit;

public class WebDriverFactory {

    private final LocalWebDriversConfig localDriversConfig;

    private URL seleniumGridUrl;

    private int scriptTimeoutInSec;

    private int pageLoadTimeoutInSec;

    public WebDriverFactory(URL seleniumGridUrl, int scriptTimeoutInSec, int pageLoadTimeoutInSec) {
        this.localDriversConfig = new LocalWebDriversConfig();
        this.seleniumGridUrl = seleniumGridUrl;
        this.scriptTimeoutInSec = scriptTimeoutInSec;
        this.pageLoadTimeoutInSec = pageLoadTimeoutInSec;
    }

    public WebDriver createLocalDriver(BrowserType browserType, TestPlatform testPlatform) {
        LocalWebDriverConfig driverConfig = localDriversConfig.getDriverConfig(browserType);
        Class<? extends WebDriver> driverClass = driverConfig.getDriverClass();

        if (driverConfig.getDriverPathPropertyName() != null) {
            System.setProperty(driverConfig.getDriverPathPropertyName(),
                driverConfig.getDriverPaths().get(testPlatform).getAbsolutePath());
        }

        WebDriver driver;
        try {
            Capabilities caps = browserType.getCapabilities();
            driver = ConstructorUtils.invokeConstructor(driverClass, caps);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create local driver for " + browserType, e);
        }

        configureDriver(driver);
        return driver;
    }

    public WebDriver createRemoteDriver(BrowserType browserType, TestPlatform platform) {
        Capabilities capabilities = browserType.getCapabilities();
        if (platform != null) {
            capabilities = capabilities.merge(platform.getCapabilities());
        }
        WebDriver driver;
        try {
            driver = new RemoteWebDriver(seleniumGridUrl, capabilities);
        } catch (Exception e){
            throw new RuntimeException(String.format("Brak połączenia z gridem\n%s", e));
        }
        configureDriver(driver);
        return driver;
    }

    private void configureDriver(WebDriver driver) {
        driver.manage().timeouts().setScriptTimeout(scriptTimeoutInSec, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(pageLoadTimeoutInSec, TimeUnit.SECONDS);
        try {
            //niedostępne dla aplikcaji chromium
            driver.manage().window().maximize();
        }
        catch (Exception e) {}
    }
}
