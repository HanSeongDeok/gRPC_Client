package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.chb.examples.lib.HelloReply;
import org.chb.examples.lib.HelloRequest;
import org.chb.examples.lib.SimpleGrpc;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class GrpcClientService {
    private static int typeCount=0;
    //@GrpcClient("fit")
    //private SimpleGrpc.SimpleBlockingStub simpleStub;

    @GrpcClient("good")
    private SimpleGrpc.SimpleStub simpleStub2;

   /* public String sendMessage(final String name) {
        try {
            HelloReply response = this.simpleStub.sayHello(HelloRequest.newBuilder().setName(name).build());
            return response.getMessage();
        } catch (StatusRuntimeException e) {
            return "FAILED with " + e.getStatus().getCode().name();
        }
    }*/

    // 바이너리 동적 양방향 통신 시작
    public void bidirectionalStreaming() {
        StreamObserver<HelloReply> streamObserver = createStreamObserver();
        StreamObserver<HelloRequest> requestObserver = this.simpleStub2.streamFromClientToServer(streamObserver);
        startCommunication(requestObserver);
    }

    // StreamObserver<HelloReply> 구현한 객체 생성
    private StreamObserver<HelloReply> createStreamObserver(){
        return new StreamObserver<HelloReply>() {
            @Override
            public void onNext(HelloReply reply) {
                // 서버에서 응답한 데이터 처리
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
        };
    }

    // 양방향 통신 시작
    private static void startCommunication(StreamObserver<HelloRequest> requestObserver) {
        ObjectMapper objectMapper = new ObjectMapper();
        // FitFaultInjectionInfo 객체 생성 (데이터 세팅)
        FitFaultInjectionInfo fitInfo = setFitFaultInjectionInfo();
        Scanner scanner = new Scanner(System.in);
        try{
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                try {
                    switch (line) {
                        case "fault":
                            String json = objectMapper.writeValueAsString(fitInfo);
                            requestObserver.onNext(HelloRequest.newBuilder().setObject(json).build());
                            break;
                        case "exit":
                            requestObserver.onCompleted();
                            break;
                        default:
                            requestObserver.onNext(HelloRequest.newBuilder().setName(line).build());
                            break;
                    }
                } catch(JsonProcessingException e){
                    throw new RuntimeException(e);
                }
            }
        }catch (IllegalStateException e){
            System.out.println("양방향 통신이 onCompleted 되었습니다!");
        }
    }

    // 싱글톤 객체 생성 후 값 새팅
    private static FitFaultInjectionInfo setFitFaultInjectionInfo() {
        FitFaultInjectionInfo info = FitFaultInjectionInfo.getFitFaultInjectionInfo();
        info.setName("TEST_FAULT_INJECTION_NAME");
        info.setTime(3000);
        info.setType(++typeCount);
        return info;
    }
}
