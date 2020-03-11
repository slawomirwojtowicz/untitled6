package pl.ninebits.qa.automated.tests.core.selenium;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import pl.ninebits.qa.automated.tests.core.BrowserType;
import pl.ninebits.qa.automated.tests.core.TestPlatform;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class LocalWebDriversConfig {

  private final Map<BrowserType, LocalWebDriverConfig> config;

  public LocalWebDriversConfig() {
    this.config = new HashMap<>(4);
    this.config.put(BrowserType.FIREFOX, LocalWebDriverConfig.builder()
      .browserType(BrowserType.FIREFOX)
      .driverClass(FirefoxDriver.class)
      .driverPathPropertyName("webdriver.gecko.driver")
      .driverPath(TestPlatform.WINDOWS_10, getDriverFile("/local-drivers/geckodriver-v0.24.0-win64.exe"))
      .driverPath(TestPlatform.WINDOWS_7, getDriverFile("/local-drivers/geckodriver-v0.24.0-win64.exe"))
      .driverPath(TestPlatform.MAC_OS, getDriverFile("/local-drivers/geckodriver-v0.24.0-macos"))
      .driverPath(TestPlatform.LINUX, getDriverFile("/local-drivers/geckodriver-v0.24.0-linux64"))
      .build());

    this.config.put(BrowserType.CHROME, LocalWebDriverConfig.builder()
      .browserType(BrowserType.CHROME)
      .driverClass(ChromeDriver.class)
      .driverPathPropertyName("webdriver.chrome.driver")
      .driverPath(TestPlatform.WINDOWS_10, getDriverFile("/local-drivers/chromedriver-v79.0-win32.exe"))
      .driverPath(TestPlatform.WINDOWS_7, getDriverFile("/local-drivers/chromedriver-v79.0-win32.exe"))
      .driverPath(TestPlatform.MAC_OS, getDriverFile("/local-drivers/chromedriver-v79.0-macos"))
      .driverPath(TestPlatform.LINUX, getDriverFile("/local-drivers/chromedriver-v79.0-linux64"))
      .build());

    this.config.put(BrowserType.INTERNET_EXPLORER, LocalWebDriverConfig.builder()
      .browserType(BrowserType.INTERNET_EXPLORER)
      .driverClass(InternetExplorerDriver.class)
      .driverPathPropertyName("webdriver.ie.driver")
      .driverPath(TestPlatform.WINDOWS_10, getDriverFile("/local-drivers/IEDriverServer_Win32-v3.11.1.exe"))
      .driverPath(TestPlatform.WINDOWS_7, getDriverFile("/local-drivers/IEDriverServer_Win32-v3.11.1.exe"))
      .build());

    this.config.put(BrowserType.MICROSOFT_EDGE, LocalWebDriverConfig.builder()
      .browserType(BrowserType.MICROSOFT_EDGE)
      .driverClass(EdgeDriver.class)
      .driverPathPropertyName("webdriver.edge.driver")
      .driverPath(TestPlatform.WINDOWS_10, getDriverFile("/local-drivers/MicrosoftWebDriver-v5.16299.exe"))
      .driverPath(TestPlatform.WINDOWS_7, getDriverFile("/local-drivers/MicrosoftWebDriver-v5.16299.exe"))
      .build());

    this.config.put(BrowserType.CHROMIUM, LocalWebDriverConfig.builder()
      .browserType(BrowserType.CHROME)
      .driverClass(ChromeDriver.class)
      .driverPathPropertyName("webdriver.chrome.driver")
      .driverPath(TestPlatform.WINDOWS_10, getDriverFile("/local-drivers/chromedriver-v79.0-win32.exe"))
      .driverPath(TestPlatform.WINDOWS_7, getDriverFile("/local-drivers/chromedriver-v79.0-win32.exe"))
      .driverPath(TestPlatform.MAC_OS, getDriverFile("/local-drivers/chromedriver-v79.0-macos"))
      .driverPath(TestPlatform.LINUX, getDriverFile("/local-drivers/chromedriver-v79.0-linux64"))
      .build());
  }

  public LocalWebDriverConfig getDriverConfig(BrowserType browserType) {
    return config.get(browserType);
  }

  private static File getDriverFile(String driverResource) {
    return new File(LocalWebDriversConfig.class.getResource(driverResource).getFile());
  }
}
