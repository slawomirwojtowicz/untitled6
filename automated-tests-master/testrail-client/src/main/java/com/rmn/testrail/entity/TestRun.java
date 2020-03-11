package com.rmn.testrail.entity;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * This class has all the fields in a TestRun API request.
 *
 * @author mmerrell
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestRun extends BaseEntity {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("suite_id")
    private Integer suiteId;

    @JsonProperty("milestone_id")
    private Integer milestoneId;

    @JsonProperty("config")
    private String config;

    @JsonProperty("is_completed")
    private Boolean isCompleted;

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

    @JsonProperty("created_by")
    private Integer createdBy;

    @JsonProperty("created_on")
    private String createdOn;

    @JsonProperty("project_id")
    private Integer projectId;

    @JsonProperty("plan_id")
    private Integer planId;

    @JsonProperty("assignedto_id")
    private Integer assignedtoId;

    @JsonProperty("include_all")
    private Boolean includeAll;

    @JsonProperty("completed_on")
    private Integer completedOn;

    @JsonProperty("url")
    private String url;

    @JsonProperty("config_ids")
    private List<Integer> configIds;

    /**
     * @return the list of TestInstance entities associated with this TestRun
     */
    public List<TestInstance> getTests() {
        return getTestRailService().getTests(this.getId());
    }
}
