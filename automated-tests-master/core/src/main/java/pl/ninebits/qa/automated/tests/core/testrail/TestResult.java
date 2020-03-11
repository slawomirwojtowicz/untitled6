package pl.ninebits.qa.automated.tests.core.testrail;

import lombok.Builder;
import lombok.Getter;
import pl.ninebits.qa.automated.tests.core.BrowserType;
import pl.ninebits.qa.automated.tests.core.TestPlatform;

@Builder
@Getter
public class TestResult {
    private int projectId;
    private String environment;
    private String testCaseId;
    private BrowserType browserType;
    private TestPlatform platform;
    private TestStatus status;
    private String comment;
    private int durationInSec;
}
