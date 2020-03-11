package com.rmn.testrail.entity;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * Created by jsteigel on 7/15/14.
 */
@Data
public class TestPlanCreator extends BaseEntity {
    @JsonProperty("name")
    private String name;

    @JsonProperty("milestone_id")
    private Integer milestoneId;

    @JsonProperty("description")
    private String description;

    @JsonProperty("entries")
    private List<PlanEntry> entries;
}
