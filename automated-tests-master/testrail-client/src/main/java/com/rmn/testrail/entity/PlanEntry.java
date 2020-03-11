package com.rmn.testrail.entity;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * @author jsteigel
 */
@Data
public class PlanEntry extends BaseEntity {
    @JsonProperty("suite_id")
    private Integer suiteId;

    @JsonProperty("assignedto_id")
    private Integer assignedToId;

    @JsonProperty("include_all")
    private Boolean includeAll;

    @JsonProperty("config_ids")
    private List<Integer> configIds;

    @JsonProperty("runs")
    private List<PlanEntryRun> runs;

    @JsonProperty("case_ids")
    private List<Integer> caseIds;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description") //requires TestRail 5.2 or later
    private String description;
}
