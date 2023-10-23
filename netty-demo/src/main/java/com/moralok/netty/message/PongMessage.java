package com.moralok.netty.message;

public class PongMessage extends Message {
    @Override
    public int getMessageType() {
        return 0;
    }
}
