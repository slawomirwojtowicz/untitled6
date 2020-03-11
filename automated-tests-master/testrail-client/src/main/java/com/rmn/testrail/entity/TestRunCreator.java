package com.rmn.testrail.entity;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * This class has all the fields that you can provide when creating a TestRun
 *
 * @author Colin McCormack
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestRunCreator extends BaseEntity {

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("suite_id")
    private Integer suiteId;

    @JsonProperty("milestone_id")
    private Integer milestoneId;

    @JsonProperty("include_all")
    private Boolean includeAll;

    @JsonProperty("assignedto_id")
    private Integer assignedToId;

    @JsonProperty("case_ids")
    private Integer[] caseIds;
}
