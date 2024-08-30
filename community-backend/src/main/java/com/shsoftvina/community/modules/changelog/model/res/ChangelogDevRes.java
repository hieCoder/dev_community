package com.shsoftvina.community.modules.changelog.model.res;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ChangelogDevRes {

    private Long id;
    private String code;
    private Long componentId;
    private List<ExampleChangelogDevRes> examples;

    @Setter
    @Getter
    public static class ExampleChangelogDevRes {
        private String commit;
    }
}
