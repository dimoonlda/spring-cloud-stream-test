package pl.piomin.services.order.controller;

import org.springframework.web.context.request.async.DeferredResult;
import pl.piomin.service.common.dto.OrderDto;
import pl.piomin.service.common.message.Order;
import pl.piomin.service.common.message.Request;
import pl.piomin.service.common.message.Response;

public interface OrderController {
    DeferredResult<Response<Order>> startProcessOrder(Request<OrderDto> orderRequest);
}
