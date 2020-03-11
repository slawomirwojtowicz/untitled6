package com.rmn.testrail.entity;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * A Project as defined within the API. This object offers some convenience methods, which will remove some of the busy work about re-querying that
 * you would have to handle otherwise.
 * <p>
 * I did not implement the "get_runs" end-point, because I doubt it is useful, it's slow,
 * it returns a gigantic payloads, and if you really want it, you can stitch it together yourself.
 * I'm afraid if I implement it, someone will use it and be completely baffled by the results...
 * It returns ALL test runs underneath ALL test plans within the history of the project. Useless. If
 * you can present me with a good use-case for implementing it, I'll be all ears (and very surprised)
 *
 * @author mmerrell
 */
@Data
public class ProjectCreator extends BaseEntity {
    @JsonProperty("name")
    private String name;

    @JsonProperty("announcement")
    private String announcement;

    @JsonProperty("show_announcement")
    private Boolean showAnnouncement;

    @JsonProperty("suite_mode")
    private Integer suiteMode;
}
