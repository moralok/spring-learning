package com.moralok.netty.message;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class GroupQuitRequestMessage extends Message {

    private String username;

    private String groupName;

    public GroupQuitRequestMessage() {
    }

    public GroupQuitRequestMessage(String username, String groupName) {
        this.username = username;
        this.groupName = groupName;
    }

    @Override
    public int getMessageType() {
        return GroupQuitRequestMessage ;
    }
}
