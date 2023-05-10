package org.example.grpc.stream;

import org.chb.examples.lib.SimpleGrpc;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GrpcClientGrpcClient2Test {
    @Test
    public void testCreateGrpcClient() {
        // given
        // 테스트에 사용할 호스트와 포트 정보
        String host = "localhost";
        int port = 9091;

        // when
        // GrpcCreateGrpcClient2 클래스의 createGrpcClient 메소드를 호출하여 SimpleStub 객체 생성
        SimpleGrpc.SimpleStub stub = GrpcClientGrpcClient2TestStub.createGrpcClient(host, port);

         // then
        Assertions.assertNotNull(stub);
    }
}
