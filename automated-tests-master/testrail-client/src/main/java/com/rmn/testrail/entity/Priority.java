package com.rmn.testrail.entity;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author vliao
 */
@Data
public class Priority extends BaseEntity {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("is_default")
    private boolean isDefault;

    @JsonProperty("name")
    private String name;

    @JsonProperty("priority")
    private Integer priority;

    @JsonProperty("short_name")
    private String shortName;
}
