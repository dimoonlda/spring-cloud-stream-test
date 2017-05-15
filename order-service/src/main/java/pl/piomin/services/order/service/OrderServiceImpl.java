package pl.piomin.services.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;
import pl.piomin.service.common.dto.OrderDto;
import pl.piomin.service.common.message.*;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.logging.Logger;

@Service
@EnableBinding(Source.class)
public class OrderServiceImpl {

    protected Logger logger = Logger.getLogger(OrderServiceImpl.class.getName());
    @Autowired
    private MessageChannel output;

    public Order processOrder(OrderDto orderDto) {
        logger.info("Processing order: " + orderDto);
        Order order = new Order(orderDto.getId(), OrderType.PURCHASE, LocalDateTime.now(), OrderStatus.NEW, new Product("Example#2"), new Shipment(ShipmentType.SHIP));
/*
        Request<Order> orderRequest = new Request<Order>()
                .setUuid(UUID.randomUUID().toString())
                .setData(order);
*/
        output.send(MessageBuilder.withPayload(order).build());
        logger.info("Output order: " + order);
        return order;
    }
}
