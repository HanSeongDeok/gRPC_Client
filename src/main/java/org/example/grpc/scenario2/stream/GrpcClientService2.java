package org.example.grpc.scenario2.stream;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.chb.examples.lib.FaultInjectionTcInfo;
import org.chb.examples.lib.HelloReply;
import org.chb.examples.lib.HelloRequest;
import org.chb.examples.lib.SimpleGrpc;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class GrpcClientService2 {
    // 클라이언트에서 사용할 호스트와 포트 정보
    // 즉, 서버에서 알려준 host ip + 설정한 포트 9090
    String host = "localhost";
    int port = 9091;
    // SimpleStub 객체 생성
    final SimpleGrpc.SimpleStub stub = GrpcCreateGrpcClient2.createGrpcClient(host, port);

    public void streamingService(){
        /**
         * TC Name 으로 사전에 정의된 어떤 결함주입을 해야할 정보(예)어느 주소에 어떤 변수 값을 주입할 지) 요청함.
         */
        // 사전에 정의된 TC 이름을 전송하기 위한 request message 객체를 생성
        FaultInjectionTcInfo tcInfo1 = FaultInjectionTcInfo.newBuilder().setTcName("TC-001").build();
        HelloRequest.Builder requestBuilder = HelloRequest.newBuilder();
        requestBuilder.addFitTcInfo(tcInfo1);
        HelloRequest request = requestBuilder.build();

        // 생성된 SimpleStub 객체를 사용하여 gRPC 메소드 호출
        StreamObserver<HelloRequest> requestStreamObserver = this.stub.streamFromClientToServer(new StreamObserver<HelloReply>() {
            @Override
            public void onNext(HelloReply reply) {
                // 서버에서 응답한 데이터 처리
                reply.getFitRequestList().stream();
                System.out.println("Message from server: " + reply.getMessage());
            }
            @Override
            public void onError(Throwable t) {
                // 서버에서 오류 발생시 처리 로직 구현
                System.out.println("Error in streamFromServerToClient: " + t.getMessage());
            }
            @Override
            public void onCompleted() {
                // 서버에서 응답 종료 시 처리 로직 구현
                // 그리고 내부에서 또한 서버와 통신을 종료하는 로직의 코드가 구현되어 있음
                System.out.println("streamFromServerToClient completed.");
            }
        });


        // 생성된 메시지 객체를 stream 직렬화 하여 (연결된 채널의)서버딘으로 전송
        requestStreamObserver.onNext(request);
    }
}
