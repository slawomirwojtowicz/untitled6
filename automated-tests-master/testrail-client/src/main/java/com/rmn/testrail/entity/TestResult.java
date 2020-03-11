package com.rmn.testrail.entity;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * If you have custom fields on TestResults in TestRails, it will be necessary to extend this class and add those fields
 * in order to capture them. Otherwise they will be ignored.
 *
 * @author mmerrell
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestResult extends BaseEntity implements Serializable {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("case_id")
    private Integer caseId;

    @JsonProperty("test_id")
    private Integer testId;

    @JsonProperty("status_id")
    private Integer statusId;

    @JsonProperty("created_by")
    private Integer createdBy;

    @JsonProperty("created_on")
    private Integer createdOn;

    @JsonProperty("assignedto_id")
    private Integer assignedtoId;

    @JsonProperty("comment")
    private String comment = "";

    @JsonProperty("version")
    private String version;

    @JsonProperty("elapsed")
    private String elapsed;

    @JsonProperty("defects")
    private String defects;

    @JsonProperty("custom_step_results")
    private List<StepResult> stepResults;

    /**
     * Set the Verdict for this TestResult (does not actually send the update to the TestRails Service)
     *
     * @param verdict the String verdict
     */
    public void setVerdict(String verdict) {
        this.statusId = TestStatus.getStatus(verdict);
    }

    /**
     * Set the verdict by using the actual StatusId (useful for customizations). Alternatively, you can extend the TestStatus
     * class and add custom status key-value pairs to it. This method can't be overloaded to match setVerdict(int), because
     * it confuses Jackson's Serializer
     *
     * @param verdict the integer verdict
     */
    public void setVerdictId(int verdict) {
        this.statusId = verdict;
    }
}
