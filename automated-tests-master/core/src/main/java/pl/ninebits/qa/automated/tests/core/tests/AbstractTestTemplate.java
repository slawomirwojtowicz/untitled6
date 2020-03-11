package pl.ninebits.qa.automated.tests.core.tests;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.AgeFileFilter;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pl.ninebits.qa.automated.tests.core.TestPlatform;
import pl.ninebits.qa.automated.tests.core.selenium.WebDriverFactory;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailCase;
import pl.ninebits.qa.automated.tests.core.testrail.TestRailClient;
import pl.ninebits.qa.automated.tests.core.testrail.TestResult;
import pl.ninebits.qa.automated.tests.core.testrail.TestStatus;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

public abstract class AbstractTestTemplate extends AbstractTestNGSpringContextTests {
  private static final Logger LOGGER = LoggerFactory.getLogger(AbstractTestTemplate.class);
  private static final String SCREENSHOTS_DIRECTORY_PATH = "target/screenshots/{0}/";
  private static final String SCREENSHOT_FILE_NAME = "{0}_{1}.png";
  private static final String DATA_DIRECTORY_PATH = "data/{0}/";
  private static final String DATA_FILE_NAME = "{0}.csv";
  private static final int MINIMUM_SCREEN_HIGHT = 600;

  @Autowired
  private TestConfig testConfig;

  @Autowired
  private WebDriverFactory driverFactory;

  @Autowired
  private TestRailClient testRailClient;

  protected List<String> errors = new ErrorList();
  protected WebDriver webDriver;
  private Method testMethod;
  private StopWatch stopWatch;

  private Hashtable<String, Long> timestapmsStart;
  private Hashtable<String, Long> timestapmsEnd;

  protected abstract String getEnvironmentName();

  @BeforeMethod(alwaysRun = true)
  protected void beforeTest(final Method method) throws Exception {
    testMethod = method;

    LOGGER.info(MessageFormat.format("beforeTest - {0}", getTestName()));
    deleteScreenshots();
    stopWatch = new StopWatch();
    stopWatch.start();

    timestapmsStart = new Hashtable<String, Long>();
    timestapmsEnd = new Hashtable<String, Long>();

    if (testConfig.isUseLocalDriver()) {
      webDriver = driverFactory.createLocalDriver(testConfig.getBrowserType(), TestPlatform.getCurrentPlatform());
    } else {
      webDriver = driverFactory.createRemoteDriver(testConfig.getBrowserType(), testConfig.getPlatform());
    }

    try {
      doBeforeTest();
    } catch (Throwable t) {
      errors.add(t.getMessage());
    }
  }

  protected void doBeforeTest() throws Exception {
    Dimension windowsSize = webDriver.manage().window().getSize();
    Assert.assertTrue(windowsSize.height >= MINIMUM_SCREEN_HIGHT, "Required minimum screen height " + MINIMUM_SCREEN_HIGHT + "px");
  }

  @AfterMethod(alwaysRun = true)
  protected void afterTest(ITestResult result) throws Exception {
    LOGGER.info(MessageFormat.format("afterTest - {0}", getTestName()));

    // test was skipped
    if (ITestResult.SKIP == result.getStatus() && webDriver == null) {
      return;
    }

    stopWatch.stop();
    int durationInSec = Double.valueOf(stopWatch.getTotalTimeSeconds()).intValue();
    LOGGER.info(MessageFormat.format("test duration: {0}s", durationInSec));

    // handle exception during test, make screenshot and add msg
    Throwable throwable = result.getThrowable();
    if (throwable != null && !(throwable instanceof AssertionError)) {
      LOGGER.error("Error occurred during test. " + throwable.getMessage());
      if(errors.size() > 0) {
        LOGGER.info(MessageFormat.format("{0} error(s) found:\n{1}\n\n", errors.size(), buildErrorMessages()));
      }
    }

    if (testConfig.isTestRailEnabled()) {
      reportTestResult(testMethod, result, durationInSec);
    }

    if (webDriver != null) {
      try {
        webDriver.quit();
      } catch (Exception e) {
        // ignore
      }
    }

    reportTimestamps(durationInSec);

    // cleanup
    errors.clear();
    webDriver = null;
    testMethod = null;
    stopWatch = null;

    try {
      doAfterTest();
    } catch (Throwable t) {
      logger.error(t.getMessage(), t);
    }
  }

  protected void doAfterTest() {

  }

  protected <T> T loadPage(String url, Class<T> pageObjectType) {
    if (url != null) {
      webDriver.get(url);
    }

    try {
      return ConstructorUtils.invokeConstructor(pageObjectType, webDriver, testConfig.getPageLoadTimeoutInSec());
    } catch (Exception e) {
      throw new RuntimeException(MessageFormat.format("Failed to create page object of type {0}", pageObjectType), e);
    }
  }

  protected String getTestName() {
    return this.getClass().getSimpleName() + "." + testMethod.getName();
  }

  protected void checkConditions() {
    if (errors.isEmpty()) {
      return;
    }
    Assert.fail(MessageFormat.format("{0} error(s) found:\n{1}\n\n", errors.size(), buildErrorMessages()));
  }

  private void reportTestResult(Method testMethod, ITestResult result, int durationInSec) {
    if (!testMethod.isAnnotationPresent(TestRailCase.class)) {
      return;
    }

    TestRailCase testCase = testMethod.getAnnotation(TestRailCase.class);
    TestStatus status = TestStatus.valueOf(result.getStatus());
    TestResult testResult = TestResult.builder()
      .projectId(testCase.projectId())
      .environment(getEnvironmentName())
      .testCaseId(testCase.caseId())
      .browserType(testConfig.getBrowserType())
      .platform(testConfig.getPlatform())
      .status(status)
      .comment(TestStatus.PASSED.equals(status) ? "Test Passed" : buildErrorMessages())
      .durationInSec(durationInSec)
      .build();

    try {
      testRailClient.addTestResult(testResult);
    } catch (Throwable t) {
      LOGGER.error("failed to report test result to testrail. " + t.getMessage(), t);
    }
  }

  protected void waitUntil(Condition condition, int trialsNo, int intervalInSec, String errorMessage) {
    for (int i = 0; i < trialsNo; i++) {
      if (condition.isFulfilled()) {
        return;
      }
      sleep(intervalInSec * 1000);
    }
    errors.add(errorMessage);
  }

  private void sleep(long milis) {
    try {
      Thread.sleep(milis);
    } catch (InterruptedException e) {
      throw new RuntimeException("Sleep error", e);
    }
  }

  private String buildErrorMessages() {
    ErrorList errorsList = (ErrorList) errors;
    List<String> errorMsgs = new ArrayList<String>();
    for (TestCheckError error : errorsList.getInternalErrors()) {
      String screenshotPath = error.getScreenshotFile().getAbsolutePath();
      String screenshotUrl = getScreenshotUrl(screenshotPath);
      String message = error.getMessage();
      message = message.substring(0, Math.min(message.length(), 255));
      if (StringUtils.isEmpty(screenshotUrl)) {
        errorMsgs.add(MessageFormat.format("\t- {0} Screeshot saved into:\n\t\t{1}",
          message, screenshotPath));
      } else {
        errorMsgs.add(MessageFormat.format("\t- {0} Screeshot saved into:\n\t\t[{1}]({2})",
          message, FilenameUtils.getName(screenshotPath), screenshotUrl));
      }
    }
    return String.join("\n", errorMsgs);
  }

  private String getScreenshotUrl(String screenshotPath) {
    if (testConfig.isUseLocalDriver() /*|| !testConfig.isTestRailEnabled()*/) {
      return null;
    }
    String url = screenshotPath.replace("/var/lib/jenkins/workspace", testConfig.getScreenshotsServer() + "/job");
    url = url.replace("/tests/", "/ws/tests/");
    url = url.replace("/smyk-tests/", "/ws/smyk-tests/");
    url = url.replace(" ", "%20");
    return url;
  }

  private synchronized File takeScreenshot() {
    File srcFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
    File dstFile = new File(getScreenshotsDirectoryPath() + "/" + getScreenshotFileName());
    try {
      FileUtils.moveFile(srcFile, dstFile);
    } catch (IOException e) {
      throw new RuntimeException("Failed to move screenshot file", e);
    }
    return dstFile;
  }

  private File getScreenshotsDirectoryPath() {
    String testClassName = this.getClass().getSimpleName();
    return new File(MessageFormat.format(SCREENSHOTS_DIRECTORY_PATH, testClassName));
  }

  private String getScreenshotFileName() {
    String directoryPath = getScreenshotsDirectoryPath().getAbsolutePath();

    int i = 1;
    File file;
    String fileName;

    do {
      fileName = MessageFormat.format(SCREENSHOT_FILE_NAME, getTestName(), i);
      file = new File(directoryPath + File.separator + fileName);
      i++;
    } while (file.exists());
    return fileName;
  }

  private void deleteScreenshots() {
    File dir = getScreenshotsDirectoryPath();
    if (!dir.exists()) {
      return;
    }
    try {
      int days = 7;//testConfig.getScreenshotsDeleteAgeInDays();
      if(days > 0) {
        Date threshold = DateUtils.addDays(new Date(), -days);
        AgeFileFilter filter = new AgeFileFilter(threshold);

        File[] oldFolders = FileFilterUtils.filter(filter, dir);

        for (File file : oldFolders) {
          file.delete();
        }
      }
      else {
        FileUtils.cleanDirectory(dir);
      }
    } catch (Exception e) {
      throw new RuntimeException("Failed to delete previous screenshots from " + dir.getAbsolutePath(), e);
    }
  }

  protected long setTimestamp(String name) {
    return setTimestamp(name, false);
  }

  protected long setTimestamp(String name, boolean isBegin) {
    long duration = 0;
    if (stopWatch != null) {
      stopWatch.stop();
      duration = stopWatch.getTotalTimeMillis();
      stopWatch.start();
    }

    if (isBegin == false) {
      if (timestapmsEnd == null) {
        timestapmsEnd = new Hashtable<String, Long>();
      }

      if (timestapmsEnd.containsKey(name)) {
        timestapmsEnd.replace(name, duration);
      } else {
        timestapmsEnd.put(name, duration);
      }

      long durationStart = 0;
      if (timestapmsStart != null && timestapmsStart.containsKey(name)) {
        durationStart = timestapmsStart.get(name);
      }
      int durationInSec = Long.valueOf((duration - durationStart) / 1000).intValue();
      LOGGER.info(MessageFormat.format("Timestamp duration [{0}]: {1}s", name, durationInSec));
    } else {
      if (timestapmsStart == null) {
        timestapmsStart = new Hashtable<String, Long>();
      }

      if (timestapmsStart.containsKey(name)) {
        timestapmsStart.replace(name, duration);
      } else {
        timestapmsStart.put(name, duration);
      }
    }

    return duration;
  }

  protected void reportTimestamps(int durationInSec) {
    try {
      if (testMethod.isAnnotationPresent(TestDurationLogger.class)) {
        TestDurationLogger attribute = testMethod.getAnnotation(TestDurationLogger.class);
        if (attribute.enabled() == true) {
          SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
          String dataRow = MessageFormat.format("{0},{1}", sdf.format(new Date()), durationInSec);
          for (int i = 0; i < attribute.breakpoints().length; i++) {
            long itemDuration = 0;
            String key = attribute.breakpoints()[i];
            if (timestapmsEnd != null && timestapmsEnd.containsKey(key)) {
              itemDuration = timestapmsEnd.get(key);
              if (timestapmsStart != null && timestapmsStart.containsKey(key)) {
                itemDuration = itemDuration - timestapmsStart.get(key);
              }
            } else if (timestapmsStart != null && timestapmsStart.containsKey(key)) {
              itemDuration = stopWatch.getTotalTimeMillis() - timestapmsStart.get(key);
            }

            dataRow += "," + Integer.toString(Long.valueOf(itemDuration / 1000).intValue());
          }

          File file = WriteDataFileRow(dataRow);

          LOGGER.info(MessageFormat.format("Log test duration data file: {0}", file.getAbsolutePath()));
          LOGGER.info("[" + dataRow + "]");
        }
      }
    } catch (Throwable t) {
      logger.error(t.getMessage(), t);
    }
  }

  private File getDataDirectoryPath() {
    String testClassName = this.getClass().getSimpleName();
    return new File(MessageFormat.format(DATA_DIRECTORY_PATH, testClassName));
  }

  private File getDataFile() throws IOException {
    //Wyznacz nagłówek danych
    ArrayList<String> breakpoints = new ArrayList<String>();
    //Pierwsza pozycja "test" jako domyślna - zawsze dostępna
    breakpoints.add(0, "timestamp");
    breakpoints.add(1, "test");
    if (testMethod.isAnnotationPresent(TestDurationLogger.class)) {
      TestDurationLogger attribute = testMethod.getAnnotation(TestDurationLogger.class);
      String[] arr = attribute.breakpoints();
      if (arr != null && arr.length > 0) {
        breakpoints.addAll(Arrays.asList(arr));
      }
    }
    String header = "\"" + String.join("\",\"", breakpoints) + "\"";

    String directoryPath = getDataDirectoryPath().getAbsolutePath();
    String fileName = MessageFormat.format(DATA_FILE_NAME, "data");
    File file = new File(directoryPath + "/" + fileName);
    if (!file.exists()) {
      File parent = file.getParentFile();
      if (!parent.exists() && !parent.mkdirs()) {
        throw new IllegalStateException("Couldn't create dir: " + parent);
      }

      file.createNewFile();
      //Dodaj nagłówek danych do pliku
      WriteDataFileRow(file, header, false);
    } else {
      //Jeżeli plik nie jest pusty a nagłówki są inne > wyczyść plik i dodaj nowy nagłówk
      BufferedReader reader = new BufferedReader(new FileReader(file));
      String line = reader.readLine();
      reader.close();
      if (line == null || !line.contentEquals(header)) {
        WriteDataFileRow(file, header, false);
      }
    }

    return file;
  }

  private File WriteDataFileRow(String data) throws IOException {
    File file = getDataFile();
    WriteDataFileRow(file, data, true);
    return file;
  }

  private void WriteDataFileRow(File file, String data, boolean append) throws IOException {
    BufferedWriter writer = new BufferedWriter(new FileWriter(file, append));
    writer.write(data);
    writer.newLine();
    writer.close();
  }

  private class ErrorList extends ArrayList<String> {
    private List<TestCheckError> internalErrors = new ArrayList<TestCheckError>();

    @Override
    public boolean add(String element) {
      handleTestError(element);
      return super.add(element);
    }

    @Override
    public void add(int index, String element) {
      handleTestError(element);
      super.add(index, element);
    }

    @Override
    public boolean addAll(Collection<? extends String> elements) {
      handleTestError(elements);
      return super.addAll(elements);
    }

    @Override
    public boolean addAll(int index, Collection<? extends String> elements) {
      handleTestError(elements);
      return super.addAll(index, elements);
    }

    private void handleTestError(String errorMsg) {
      internalErrors.add(new TestCheckError(errorMsg, takeScreenshot()));
    }

    private void handleTestError(Collection<? extends String> errorMsgs) {
      File screenshotFile = takeScreenshot();
      errorMsgs.forEach(e -> internalErrors.add(new TestCheckError(e, screenshotFile)));
    }

    List<TestCheckError> getInternalErrors() {
      return internalErrors;
    }
  }

  @Data
  @AllArgsConstructor
  private static class TestCheckError {
    private String message;
    private File screenshotFile;
  }
}
