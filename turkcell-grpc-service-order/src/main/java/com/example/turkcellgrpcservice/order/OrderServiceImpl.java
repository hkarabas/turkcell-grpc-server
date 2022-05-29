package com.example.turkcellgrpcservice.order;


import com.google.protobuf.Timestamp;
import com.turkcell.pasaj.client.ClientRequest;
import com.turkcell.pasaj.client.ClientServiceGrpc;
import com.turkcell.pasaj.order.OrderRequest;
import com.turkcell.pasaj.order.OrderResponse;
import com.turkcell.pasaj.order.OrderServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;


@GrpcService
public class OrderServiceImpl extends OrderServiceGrpc.OrderServiceImplBase {

    @Override
    public void executeOrder(OrderRequest request, StreamObserver<OrderResponse> responseObserver) {
            OrderResponse orderResponse = OrderResponse.newBuilder().setInfo("Gelen order mail "+request.getEmail()).build();
            responseObserver.onNext(orderResponse);
            responseObserver.onCompleted();
            Timestamp timestamp =  this.sendStatusToClient("Sipariş Alındı");
            System.out.println("Müşteri Bilgilendirme Tarihi" + timestamp);

    }

    private Timestamp sendStatusToClient(String str) {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 9091)
                .usePlaintext()
                .build();

        ClientServiceGrpc.ClientServiceBlockingStub clientServiceBlockingStub
                = ClientServiceGrpc.newBlockingStub(managedChannel);

        ClientRequest  clientRequest = ClientRequest.newBuilder()
                .setStatus(str)
                .build();

        return clientServiceBlockingStub.executeClient(clientRequest).getMyField();

    }
}
