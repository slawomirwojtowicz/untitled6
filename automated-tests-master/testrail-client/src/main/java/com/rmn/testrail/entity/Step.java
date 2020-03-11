package com.rmn.testrail.entity;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;

@Data
public class Step extends BaseEntity {

    @JsonProperty("content")
    private String content;

    @JsonProperty("expected")
    private String expected;
}
