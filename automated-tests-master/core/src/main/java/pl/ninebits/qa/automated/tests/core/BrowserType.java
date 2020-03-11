package pl.ninebits.qa.automated.tests.core;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

public enum BrowserType {
  FIREFOX("Firefox", getFirefoxOptions()),
  CHROME("Chrome", getChromeOptions()),
  MICROSOFT_EDGE("Microsoft Edge", getEdgeOptions()),
  INTERNET_EXPLORER("Internet Explorer", getInternetExplorerOptions()),
  CHROMIUM("Chromium", getChromiumOptions());

  private final String name;
  private final Capabilities capabilities;

  BrowserType(String name, Capabilities capabilities) {
    this.name = name;
    this.capabilities = capabilities;
  }

  public String getName() {
    return name;
  }

  public Capabilities getCapabilities() {
    return capabilities;
  }

  private static FirefoxOptions getFirefoxOptions() {
    FirefoxOptions options = new FirefoxOptions();
    options.setAcceptInsecureCerts(true);
    return options;
  }

  private static ChromeOptions getChromeOptions() {
    ChromeOptions options = new ChromeOptions();
    options.setAcceptInsecureCerts(true);
    options.addArguments("--disable-notifications");
    return options;
  }

  private static EdgeOptions getEdgeOptions() {
    return new EdgeOptions();
  }

  private static DesiredCapabilities getInternetExplorerOptions() {
    return DesiredCapabilities.internetExplorer();
  }

  private static ChromeOptions getChromiumOptions() {
    ChromeOptions options = new ChromeOptions();
    options.setAcceptInsecureCerts(true);
    options.setBinary("C:/Program Files (x86)/Eurocash.Offline.Application/Eurocash.Offline.Application.exe");
    options.setCapability("debuggerAddress", "127.0.0.1:9222");
    options.addArguments("–no-sandbox", "–disable-dev-shm-usage", "--remote-debugging-port=9222");
    options.setExperimentalOption("useAutomationExtension", false);
    return options;
  }
}
