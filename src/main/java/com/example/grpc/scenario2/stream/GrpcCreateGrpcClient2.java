package com.example.grpc.scenario2.stream;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.chb.examples.lib.SimpleGrpc;

final class GrpcCreateGrpcClient2 {
    static SimpleGrpc.SimpleStub createGrpcClient(String host, int port) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();
        SimpleGrpc.SimpleStub stub = SimpleGrpc.newStub(channel);
        return stub;
    }
}
