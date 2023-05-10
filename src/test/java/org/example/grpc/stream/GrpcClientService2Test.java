package org.example.grpc.stream;

import org.chb.examples.lib.FaultInjectionTcInfo;
import org.chb.examples.lib.HelloRequest;
import org.example.grpc.scenario2.stream.GrpcClientService2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GrpcClientService2Test {
    @Test
    public void streamingService(){
        //given
        // 사전에 정의된 TC 이름을 전송하기 위한 request message 객체를 생성
        FaultInjectionTcInfo tcInfo1 = FaultInjectionTcInfo.newBuilder().setTcName("TC-001").build();
        HelloRequest.Builder requestBuilder = HelloRequest.newBuilder();
        requestBuilder.addFitTcInfo(tcInfo1);
        HelloRequest request = requestBuilder.build();

        //when
        String tcName = request.getFitTcInfo(0).getTcName();

        //then
        Assertions.assertEquals(tcName, "TC-001");
    }
}
