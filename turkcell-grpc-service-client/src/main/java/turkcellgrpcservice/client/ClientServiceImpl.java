package turkcellgrpcservice.client;

import com.turkcell.pasaj.client.ClientRequest;
import com.turkcell.pasaj.client.ClientResponse;
import com.turkcell.pasaj.client.ClientServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import com.google.protobuf.Timestamp;

@GrpcService
public class ClientServiceImpl extends ClientServiceGrpc.ClientServiceImplBase {

    @Override
    public void executeClient(ClientRequest request, StreamObserver<ClientResponse> responseObserver) {
        Timestamp timestamp = Timestamp.getDefaultInstance();
        ClientResponse clientResponse = ClientResponse.newBuilder().setMyField(timestamp).build();
        responseObserver.onNext(clientResponse);
        responseObserver.onCompleted();
        System.out.println("Müşteri durumu " + request.getStatus());
    }
}
