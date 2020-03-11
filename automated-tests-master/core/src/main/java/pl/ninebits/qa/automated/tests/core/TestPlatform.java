package pl.ninebits.qa.automated.tests.core;

import org.apache.commons.exec.OS;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;

public enum TestPlatform {
  WINDOWS_7("Windows 7", Platform.VISTA),
  WINDOWS_10("Windows 10", Platform.WIN10),
  MAC_OS("macOS", Platform.MAC),
  LINUX("Linux", Platform.LINUX);

  private final String name;
  private final Platform platform;

  TestPlatform(String name, Platform platform) {
    this.name = name;
    this.platform = platform;
  }

  public String getName() {
    return name;
  }

  public Capabilities getCapabilities() {
    DesiredCapabilities capabilities = new DesiredCapabilities();
    capabilities.setPlatform(platform);
    return capabilities;
  }

  public static TestPlatform getCurrentPlatform() {
    if (OS.isFamilyMac()) return MAC_OS;
    if (OS.isFamilyWindows()) {
      if (OS.isVersion("10") || OS.isVersion("10.0")) return WINDOWS_10;
      if (OS.isVersion("6.3")) return WINDOWS_7; //win8 ale działa tylko jako win7
      if (OS.isVersion("6.1")) return WINDOWS_7;
    }
    if (OS.isFamilyUnix()) return LINUX;
    return null;
  }
}
