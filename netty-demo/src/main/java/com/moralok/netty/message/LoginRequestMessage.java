package com.moralok.netty.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class LoginRequestMessage extends Message {

    private String username;
    private String password;
    private String nickname;

    @Override
    public int getMessageType() {
        return LoginRequestMessage;
    }
}
