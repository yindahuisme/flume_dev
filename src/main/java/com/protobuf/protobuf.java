package com.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;

public class protobuf {
    public static void main(String[] args) {
        YindahuHello.hello.Builder builder = YindahuHello.hello.newBuilder();
        builder.setId(3);
        builder.setStr("yindahu");
        YindahuHello.hello obj =builder.build();
        byte[] bytes = obj.toByteArray();
        YindahuHello.hello hello_obj = null;
        try {
            hello_obj = YindahuHello.hello.parseFrom(bytes);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
        System.out.println(hello_obj.getId());
        System.out.println(hello_obj.getStr());
        System.out.println(hello_obj.getOpt());
    }
}
