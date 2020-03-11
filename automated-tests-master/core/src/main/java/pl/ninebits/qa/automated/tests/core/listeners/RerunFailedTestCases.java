package pl.ninebits.qa.automated.tests.core.listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RerunFailedTestCases implements IRetryAnalyzer {

  private int retryCount = 1;
  private int maxRetryCount = 3;

  @Override
  public boolean retry(ITestResult iTestResult) {
    if (!iTestResult.isSuccess()) {
      if (retryCount < maxRetryCount) {
        retryCount++;
        System.out.println("Retry no. " + retryCount);
        return true;
      } else if (retryCount == maxRetryCount) {
        iTestResult.setStatus(ITestResult.FAILURE);
      }
    }
    return false;
  }
}
