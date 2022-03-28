package com.policequestions;

import java.util.List;

public class Root {
    private List<Group> groups;

    public Root() {
    }

    public Root(List<Group> groups) {
        this.groups = groups;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
}
