package com.moralok.netty.server.session;

import lombok.Data;
import lombok.ToString;

import java.util.Collections;
import java.util.Set;

@Data
@ToString(callSuper = true)
public class Group {

    public static final Group EMPTY_GROUP = new Group(Collections.emptySet());

    public Group(Set<String> members) {
        this.members = members;
    }

    private Set<String> members;
}
