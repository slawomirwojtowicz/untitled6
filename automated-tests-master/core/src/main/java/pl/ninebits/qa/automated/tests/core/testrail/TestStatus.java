package pl.ninebits.qa.automated.tests.core.testrail;

import org.testng.ITestResult;

public enum TestStatus {
    PASSED("Passed", ITestResult.SUCCESS),
    FAILED("Failed", ITestResult.FAILURE),
    UNTESTED("Untested", ITestResult.SKIP);

    private final String name;
    private final int result;

    TestStatus(String name, int result) {
        this.name = name;
        this.result = result;
    }

    public String getName() {
        return name;
    }

    public Integer getResult() {
        return result;
    }

    public static TestStatus valueOf(int result) {
        for (TestStatus status : values()) {
            if (status.getResult().equals(result)) {
                return status;
            }
        }
        return UNTESTED;
    }
}
