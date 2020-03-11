package com.rmn.testrail.entity;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * @author jsteigel
 */
@Data
public class PlanEntryRun extends BaseEntity {
    @JsonProperty("assignedto_id")
    private Integer assignedToId;

    @JsonProperty("include_all")
    private Boolean includeAll;

    @JsonProperty("case_ids")
    private List<Integer> caseIds;

    @JsonProperty("config_ids")
    private List<Integer> configIds;
}
