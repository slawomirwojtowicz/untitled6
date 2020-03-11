package com.rmn.testrail.entity;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author jsteigel
 */
@Data
public class EmptyMilestone extends BaseEntity {
    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("due_on")
    private String dueOn;
}