package com.rmn.testrail.entity;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * @author mmerrell
 */
@Data
public class TestSuite extends BaseEntity {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("project_id")
    private Integer projectId;

    @JsonProperty("url")
    private String url;

    @JsonProperty("completed_on")
    private String completedOn;

    @JsonProperty("is_completed")
    private Boolean isCompleted;

    @JsonProperty("is_baseline")
    private Boolean isBaseline;

    @JsonProperty("is_master")
    private Boolean isMaster;

    public List<Section> getSections() {
        return getTestRailService().getSections(this.projectId, this.getId());
    }

    public List<TestCase> getTestCases() {
        return getTestRailService().getTestCases(projectId, this.getId());
    }
}