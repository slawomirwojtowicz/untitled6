package pl.ninebits.qa.automated.tests.core.testrail;

import com.rmn.testrail.entity.Milestone;
import com.rmn.testrail.entity.Project;
import com.rmn.testrail.entity.TestInstance;
import com.rmn.testrail.entity.TestPlan;
import com.rmn.testrail.entity.TestRun;
import com.rmn.testrail.entity.TestRunGroup;
import com.rmn.testrail.service.TestRailService;
import org.apache.commons.lang3.StringUtils;
import pl.ninebits.qa.automated.tests.core.BrowserType;
import pl.ninebits.qa.automated.tests.core.TestPlatform;

import java.net.URL;
import java.text.MessageFormat;
import java.util.List;

public class TestRailClient {
    private TestRailService testRailService;
    private URL apiEndpoint;
    private String username;
    private String password;

    public TestRailClient(URL apiEndpoint, String username, String password) {
        this.apiEndpoint = apiEndpoint;
        this.username = username;
        this.password = password;
    }

    public void addTestResult(TestResult testResult) {
        ensureTestRailService();

        Project project = getProject(testResult.getProjectId());
        if (project == null) {
            throw new IllegalArgumentException(MessageFormat.format("There is no project with id ''{0}''",
                testResult.getProjectId()));
        }

        Milestone milestone = getMilestone(project);
        if (milestone == null) {
            throw new IllegalArgumentException(MessageFormat.format("There is no active milestone in project ''{1}''",
                project.getName()));
        }

        TestPlan testPlan = getTestPlan(project, milestone, testResult.getEnvironment());
        if (testPlan == null) {
            throw new IllegalArgumentException(MessageFormat.format("There is no test plan for environment ''{0}'' " +
                    "in project ''{1}'' and milestone ''{2}'",
                testResult.getEnvironment(), project.getName(), milestone.getName()));
        }

        Integer testCaseId = getNumericTestCaseId(testResult.getTestCaseId());
        TestInstance test = getTestInstance(testPlan, testCaseId, testResult.getBrowserType(), testResult.getPlatform());
        if (test == null) {
            throw new IllegalArgumentException(MessageFormat.format("There is no test instance for case ''{0}'' in test plan" +
                    " (environment ''{1}'', project ''{2}'', milestone ''{3}'')",
                testResult.getTestCaseId(), testResult.getEnvironment(), project.getName(), milestone.getName()));
        }

        com.rmn.testrail.entity.TestResult trResult = new com.rmn.testrail.entity.TestResult();
        trResult.setVerdict(testResult.getStatus().getName());
        trResult.setComment(testResult.getComment());
        trResult.setElapsed(MessageFormat.format("{0}s", testResult.getDurationInSec()));

        try {
            testRailService.addTestResult(test.getId(), trResult);
        } catch (Exception e) {
            throw new RuntimeException(MessageFormat.format("Failed to add test testResult for case ''{0}'' in test plan" +
                    " (environment ''{1}'', project ''{2}'', milestone ''{3}'')",
                testResult.getTestCaseId(), testResult.getEnvironment(), project.getName(), milestone.getName()), e);
        }
    }

    private TestRailService ensureTestRailService() {
        if (testRailService == null) {
            testRailService = new TestRailService(apiEndpoint, username, password);
            try {
                if (!testRailService.verifyCredentials()) {
                    throw new IllegalStateException(
                        MessageFormat.format("Invalid TestRail credentials, username = ''{0}'', api {1}", username, apiEndpoint));
                }
            } catch (Throwable e) {
                throw new RuntimeException("Failed to verify TestRail credentials, " + apiEndpoint, e);
            }
        }
        return testRailService;
    }

    private Project getProject(int projectId) {
        return testRailService.getProject(projectId);
    }

    private Milestone getMilestone(Project project) {
        List<Milestone> milestones = testRailService.getMilestones(project.getId());
        return milestones.stream().filter(m -> !m.getIsCompleted()).sorted().findFirst().orElseGet(null);
    }

    private TestPlan getTestPlan(Project project, Milestone milestone, String environment) {
        TestPlan testPlan = null;
        for (TestPlan plan : testRailService.getTestPlans(project.getId())) {
            if (Boolean.TRUE.equals(plan.getIsCompleted()) || !plan.getMilestoneId().equals(milestone.getId())) {
                continue;
            }
            if (plan.getName().toLowerCase().contains(environment.toLowerCase())) {
                if (testPlan == null) {
                    testPlan = plan;
                } else {
                    throw new IllegalStateException(MessageFormat.format("There is more then 1 active test plan " +
                            "for environment ''{0}'' in project ''{1}'', milestone ''{2}''",
                        environment, project.getName(), milestone.getName()));
                }
            }
        }
        return testPlan;
    }

    private TestInstance getTestInstance(TestPlan testPlan, int testCaseId, BrowserType browserType, TestPlatform platform) {
        for (TestRunGroup testRunGroup : testPlan.getEntries()) {
            for (TestRun testRun : testRunGroup.getRuns()) {
                if (!isTestRunMatchConfiguration(testRun.getConfig(), browserType, platform)) {
                    continue;
                }
                for (TestInstance testInstance : testRun.getTests()) {
                    if (testInstance.getCaseId().equals(testCaseId)) {
                        return testInstance;
                    }
                }
            }
        }
        return null;
    }

    private boolean isTestRunMatchConfiguration(String config, BrowserType browserType, TestPlatform platform) {
        if (StringUtils.isBlank(config)) {
            return true;
        }

        boolean match = true;
        if (browserType != null) {
            match = match && config.toLowerCase().contains(browserType.getName().toLowerCase());
        }
        if (platform != null) {
            match = match && config.toLowerCase().contains(platform.getName().toLowerCase());
        }
        return match;
    }

    private Integer getNumericTestCaseId(String testCaseId) {
        return Integer.valueOf(testCaseId.substring(1));
    }
}
