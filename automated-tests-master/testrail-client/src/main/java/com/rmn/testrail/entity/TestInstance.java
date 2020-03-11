package com.rmn.testrail.entity;

import com.rmn.testrail.parameters.ApiFilterValue;
import com.rmn.testrail.parameters.GetResultsFilter;
import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * The TestRail top-level entity is actually called a "Test", but that collides with the @Test annotation we're
 * using in the unit tests. Calling it a TestInstance will avoid ambiguity
 *
 * @author mmerrell
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestInstance extends BaseEntity {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("status_id")
    private Integer statusId;

    @JsonProperty("type_id")
    private Integer typeId;

    @JsonProperty("priority_id")
    private Integer priorityId;

    @JsonProperty("estimate")
    private String estimate;

    @JsonProperty("estimate_forecast")
    private String estimateForecast;

    @JsonProperty("assignedto_id")
    private Integer assignedtoId;

    @JsonProperty("run_id")
    private Integer runId;

    @JsonProperty("case_id")
    private Integer caseId;

    @JsonProperty("milestone_id")
    private Integer milestoneId;

    @JsonProperty("refs")
    private String refs;

    /**
     * Returns the list of test results (most recent first) associated with this TestInstance
     *
     * @param limit A limit to the number of results you want to see (1 will give you the single most recent)
     * @return The number of TestResults specified by the limit, in descending chron order (i.e. newest to oldest)
     */
    public List<TestResult> getResults(int limit) {
        return getTestRailService().getTestResults(this.getId(), new ApiFilterValue(GetResultsFilter.LIMIT, Integer.toString(limit)));
    }

    /**
     * Returns the single most recent TestResult for this TestInstance
     *
     * @return The single most recent TestResult for this TestInstance
     */
    public List<TestResult> getMostRecentResult() {
        return getResults(1);
    }
}
