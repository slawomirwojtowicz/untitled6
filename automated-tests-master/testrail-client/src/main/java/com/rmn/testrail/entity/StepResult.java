package com.rmn.testrail.entity;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;

@Data
public class StepResult extends Step {

    @JsonProperty("actual")
    private String actual;

    @JsonProperty("status_id")
    private Integer statusId;
}
