package com.rmn.testrail.entity;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * This object represents the entity you get in a TestRun under the "entries" element.
 * It's not there when you query lists of TestRuns, only when you query individual TestRuns
 *
 * @author mmerrell
 */
@Data
public class TestRunGroup extends BaseEntity {
    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("suite_id")
    private Integer suiteId;

    @JsonProperty("runs")
    private List<TestRun> runs;

    public List<TestRun> getRuns() {
        runs.forEach(r -> r.setTestRailService(getTestRailService()));
        return runs;
    }
}
