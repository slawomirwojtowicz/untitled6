package com.rmn.testrail.entity;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mmerrell
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestPlan extends BaseEntity {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("milestone_id")
    private Integer milestoneId;

    @JsonProperty("is_completed")
    private Boolean isCompleted;

    @JsonProperty("created_by")
    private int createdBy;

    @JsonProperty("created_on")
    private String createdOn;

    @JsonProperty("completed_on")
    private String completedOn;

    @JsonProperty("passed_count")
    private Integer passedCount;

    @JsonProperty("blocked_count")
    private Integer blockedCount;

    @JsonProperty("untested_count")
    private Integer untestedCount;

    @JsonProperty("retest_count")
    private Integer retestCount;

    @JsonProperty("failed_count")
    private Integer failedCount;

    @JsonProperty("project_id")
    private Integer projectId;

    @JsonProperty("assignedto_id")
    private Integer assignedtoId;

    @JsonProperty("url")
    private String url;

    @JsonProperty("entries")
    private List<TestRunGroup> entries;

    /**
     * Returns the list of entries within this TestPlan (assuming some TestRuns have been created and initialized)
     *
     * @return The List of TestRunGroup entries within this TestPlan
     */
    public List<TestRunGroup> getEntries() {
        //Re-query the API for the TestRunGroups (see note below). It's best to do this each time the entries are requested for better safety
        refreshEntriesFromService();
        return entries;
    }

    // Chances are good that you just queried for a bunch of test plans from a project or suite, and that list of plans didn't include the TestRun information for each TestPlan.
    // This block of code re-queries the API for this specific test plan to make certain it will include information about the TestRunGroup entities it contains
    private void refreshEntriesFromService() {
        TestPlan plan = getTestRailService().getTestPlan(this.getId());
        plan.entries.forEach(e -> e.setTestRailService(getTestRailService()));
        setEntries(plan.entries);
    }

    /**
     * Returns the list of TestRuns associated with this TestPlan (only returns the most recent TestRuns at the moment)
     *
     * @return The most recent list of TestRuns associated with this TestPlan
     */
    public List<TestRun> getTestRuns() {
        //Re-query the API for the TestRunGroups (see note below)
        refreshEntriesFromService();

        //Get the first TestRun in each "entry:run" element and pile it up with the first entries in all the other TestRunGroups to form one list of TestRuns. Not sure this is the ideal way to do
        // things, but by leaving getEntries() public you can get to the other test runs on your own
        List<TestRun> testRuns = new ArrayList<TestRun>(entries.size());
        for (TestRunGroup group : entries) {
            TestRun testRun = group.getRuns().get(0);
            if (null != testRun) {
                testRun.setTestRailService(getTestRailService());
                testRuns.add(testRun);
            }
        }
        return testRuns;
    }
}
