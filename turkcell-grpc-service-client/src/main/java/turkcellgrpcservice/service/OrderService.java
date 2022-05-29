package turkcellgrpcservice.service;


import com.turkcell.pasaj.order.OrderRequest;
import com.turkcell.pasaj.order.OrderServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Service;

@Service
public class OrderService {


    public String getInfoMessage() {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();

        OrderServiceGrpc.OrderServiceBlockingStub orderServiceBlockingStub
                = OrderServiceGrpc.newBlockingStub(managedChannel);

        OrderRequest orderRequest = OrderRequest.newBuilder()
                .setEmail("hello@word.com")
                .setProduct("no-name")
                .setAmount(3)
                .build();

        return orderServiceBlockingStub.executeOrder(orderRequest).getInfo();
    }

}
