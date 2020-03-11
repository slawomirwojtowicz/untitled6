package com.rmn.testrail.entity;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author ragePowered
 */
@Data
public class SectionCreator extends BaseEntity {
    @JsonProperty("description")
    private String description;

    @JsonProperty("suite_id")
    private Integer suiteId;

    @JsonProperty("parent_id")
    private Integer parentId;

    @JsonProperty("name")
    private String name;
}
