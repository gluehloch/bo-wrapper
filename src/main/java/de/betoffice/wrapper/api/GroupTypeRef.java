package de.betoffice.wrapper.api;

import java.util.Objects;

public class GroupTypeRef {

    private final String groupTypeName;

    private GroupTypeRef(String groupTypeName) {
        this.groupTypeName = groupTypeName;
    }

    public static GroupTypeRef of(String groupTypeName) {
        Objects.requireNonNull(groupTypeName);
        return new GroupTypeRef(groupTypeName);
    }

    public String groupType() {
        return groupTypeName;
    }

}
