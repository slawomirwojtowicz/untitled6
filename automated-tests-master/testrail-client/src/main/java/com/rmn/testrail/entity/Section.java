package com.rmn.testrail.entity;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * @author mmerrell
 */
@Data
public class Section extends BaseEntity {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("parent_id")
    private Integer parentId;

    @JsonProperty("depth")
    private Integer depth;

    @JsonProperty("display_order")
    private Integer displayOrder;

    @JsonProperty("suite_id")
    private Integer suiteId;

    public Integer getProjectId() {
        return getTestRailService().getTestSuite(getSuiteId()).getProjectId();
    }

    /**
     * Returns the complete list of TestCases within this Section
     *
     * @return the complete list of TestCases within this Section
     */
    public List<TestCase> getTestCases() {
        return getTestRailService().getTestCases(getProjectId(), getSuiteId(), this.getId());
    }
}
