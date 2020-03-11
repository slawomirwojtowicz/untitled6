package com.rmn.testrail.entity;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * This class has the available fields when updating a TestRun.
 *
 * @author Sergey Franchuk
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class TestRunUpdater extends BaseEntity {

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("milestone_id")
    private Integer milestoneId;

    @JsonProperty("include_all")
    private Boolean includeAll;

    @JsonProperty("case_ids")
    private Integer[] caseIds;
}
