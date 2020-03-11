package com.rmn.testrail.entity;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author jsteigel
 */
@Data
public class Milestone extends BaseEntity {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("is_completed")
    private Boolean isCompleted;

    @JsonProperty("url")
    private String url;

    @JsonProperty("completed_on")
    private String completedOn;

    @JsonProperty("description")
    private String description;

    @JsonProperty("due_on")
    private String dueOn;

    @JsonProperty("project_id")
    private Integer projectId;
}