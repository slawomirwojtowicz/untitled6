package com.rmn.testrail.entity;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * A TestRails TestCase entity. In TestRails, a TestCase is more or less a "template" for executing a sequence of steps--you do not report
 * TestResults against a TestCase, but against a TestInstance (represented as a "Test" in TestRail, but named "TestInstance" here as to
 * avoid conflicts with the @Test annotation found in popular xUnit frameworks
 * <p>
 * If you have custom fields on TestCases in TestRails, it will be necessary to extend this class and add those fields in order to capture them.
 * Otherwise they will be ignored.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestCase extends BaseEntity {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("section_id")
    private Integer sectionId;

    @JsonProperty("type_id")
    private Integer typeId;

    @JsonProperty("priority_id")
    private Integer priorityId;

    @JsonProperty("estimate")
    private String estimate;

    @JsonProperty("estimate_forecast")
    private String estimateForecast;

    @JsonProperty("refs")
    private String refs;

    @JsonProperty("milestone_id")
    private Integer milestoneId;

    @JsonProperty("created_by")
    private Integer createdBy;

    @JsonProperty("created_on")
    private Integer createdOn;

    @JsonProperty("updated_by")
    private Integer updatedBy;

    @JsonProperty("updated_on")
    private Integer updatedOn;

    @JsonProperty("suite_id")
    private Integer suiteId;

    @JsonProperty("template_id") //requires TestRail 5.2 or later
    private Integer templateId;

    @JsonProperty("custom_state")
    private Integer customState;

    @JsonProperty("custom_steps_separated")
    private List<Step> customStepsSeparated;

    @JsonProperty("custom_preconds")
    private String customPreconditions;

    /**
     * Update (as in upload changes to TestRail) this TestCase--NOTE: This method actually makes the request to TestRails. Use it carefully!
     */
    public void updateTestCase() {
        getTestRailService().updateTestCase(this, this.getId());
    }
}