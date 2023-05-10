package org.example.grpc.scenario2.stream;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.chb.examples.lib.SimpleGrpc;

// gRPC 클라이언트를 초기화하고 SimpleStub 객체를 생성만을 목적으로 둠.
final class GrpcCreateGrpcClient2 {
    // gRPC 클라이언트를 초기화하고 SimpleStub 객체를 생성하는 메소드
    static SimpleGrpc.SimpleStub createGrpcClient(String host, int port) {
        // gRPC 채널 생성
        ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();

        // SimpleStub 객체 생성
        SimpleGrpc.SimpleStub stub = SimpleGrpc.newStub(channel);
        return stub;
    }
}
