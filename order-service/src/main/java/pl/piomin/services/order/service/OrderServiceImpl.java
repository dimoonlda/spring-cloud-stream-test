package pl.piomin.services.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;
import pl.piomin.service.common.dto.OrderDto;
import pl.piomin.service.common.message.*;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.logging.Logger;

@Service
@EnableBinding(Source.class)
public class OrderServiceImpl extends AbstractRequestHandler<OrderDto, Order> implements RequestHandler<OrderDto, Order> {

    protected Logger logger = Logger.getLogger(OrderServiceImpl.class.getName());

    @Autowired
    private MessageChannel output;

    @Autowired
    @Qualifier("responseChanel")
    private SubscribableChannel responseChanel;

    @PostConstruct
    private void init() {
        responseChanel.subscribe(message -> {
            final Response<Order> response = (Response<Order>)message.getPayload();
            logger.info("Got response: " + response);
            sendResponse(response);
        });
    }

    @Override
    public DeferredResult<Response<Order>> doHandle(Request<OrderDto> request) {
        logger.info("Processing request: " + request);
        OrderDto orderDto = request.getData();

        Order order = new Order(orderDto.getId(), OrderType.PURCHASE, LocalDateTime.now(), OrderStatus.NEW, new Product("Example#2"), new Shipment(ShipmentType.SHIP));

        Request<Order> newRequest = new Request<Order>()
                .setUuid(request.getUuid())
                .setServerId(request.getServerId())
                .setData(order);

        output.send(MessageBuilder.withPayload(newRequest).build());
        logger.info("Output request: " + newRequest);
        return new DeferredResult<>(20000L);
    }
}
