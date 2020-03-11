package com.rmn.testrail.entity;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author mmerrell
 */
@Data
public class TestSuiteCreator extends BaseEntity {
    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;
}