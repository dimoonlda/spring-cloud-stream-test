package pl.piomin.services.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import pl.piomin.service.common.dto.OrderDto;
import pl.piomin.service.common.message.Order;
import pl.piomin.service.common.message.Request;
import pl.piomin.service.common.message.Response;
import pl.piomin.services.order.service.OrderServiceImpl;

@RestController
@RequestMapping("/orders")
public class OrderControllerImpl implements OrderController {

    @Autowired
    private OrderServiceImpl orderService;

    @Override
    @RequestMapping(value = "/process", method = RequestMethod.POST)
    public DeferredResult<ResponseEntity<Response<Order>>> process(@RequestBody Request<OrderDto> orderRequest) {
        orderService.processOrder(orderRequest.getData());
        return null;
    }
}
