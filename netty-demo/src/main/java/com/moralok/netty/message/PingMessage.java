package com.moralok.netty.message;

public class PingMessage extends Message {
    @Override
    public int getMessageType() {
        return 0;
    }
}
