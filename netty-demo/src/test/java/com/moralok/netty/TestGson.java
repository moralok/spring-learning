package com.moralok.netty;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.moralok.netty.protocol.Serializer;
import org.junit.Test;

public class TestGson {

    @Test
    public void test() {
        Gson gson = new GsonBuilder().registerTypeAdapter(Class.class, new Serializer.ClassCodec()).create();
        System.out.println(gson.toJson(String.class));
    }
}
