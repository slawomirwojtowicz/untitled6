package com.rmn.testrail.entity;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * @author vliao
 */
@Data
public class UpdatePlanEntry extends BaseEntity {
    @JsonProperty("assignedto_id")
    private Integer assignedToId;

    @JsonProperty("include_all")
    private boolean includeAll;

    @JsonProperty("case_ids")
    private List<Integer> caseIds;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description") //requires TestRail 5.2 or later
    private String description;
}
