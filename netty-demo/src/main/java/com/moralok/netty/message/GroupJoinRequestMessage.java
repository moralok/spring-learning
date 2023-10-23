package com.moralok.netty.message;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class GroupJoinRequestMessage extends Message {

    private String username;
    private String groupName;

    public GroupJoinRequestMessage() {
    }

    public GroupJoinRequestMessage(String username, String groupName) {
        this.username = username;
        this.groupName = groupName;
    }

    @Override
    public int getMessageType() {
        return GroupJoinRequestMessage ;
    }
}
