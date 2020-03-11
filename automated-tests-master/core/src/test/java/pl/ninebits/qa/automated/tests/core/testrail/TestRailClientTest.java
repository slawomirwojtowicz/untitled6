package pl.ninebits.qa.automated.tests.core.testrail;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.ninebits.qa.automated.tests.core.BrowserType;
import pl.ninebits.qa.automated.tests.core.TestPlatform;

import java.net.URL;

public class TestRailClientTest {

    private TestRailClient testRailClient;

    @BeforeMethod
    public void setUp() throws Exception {
        testRailClient = new TestRailClient(new URL("http://testrail.nd0.pl:8099/"), "tester@9bits.pl", "Vc859ga5Pu");
    }

    @Test(enabled = false)
    public void testAddTestResult() throws Exception {
        TestResult testResult = TestResult.builder()
            .projectId(2)
            .environment("uat")
            .testCaseId("C42")
            .status(TestStatus.PASSED)
            .comment("Test Passed")
            .durationInSec(35)
            .browserType(BrowserType.FIREFOX)
            .platform(TestPlatform.WINDOWS_10)
            .build();

        testRailClient.addTestResult(testResult);
    }
}