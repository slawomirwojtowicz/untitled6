package pl.ninebits.qa.automated.tests.core.selenium;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import org.openqa.selenium.WebDriver;
import pl.ninebits.qa.automated.tests.core.BrowserType;
import pl.ninebits.qa.automated.tests.core.TestPlatform;

import java.io.File;
import java.util.Map;

@Builder
@Getter
public class LocalWebDriverConfig {
    private BrowserType browserType;
    private Class<? extends WebDriver> driverClass;
    private String driverPathPropertyName;
    private @Singular Map<TestPlatform, File> driverPaths;
}
