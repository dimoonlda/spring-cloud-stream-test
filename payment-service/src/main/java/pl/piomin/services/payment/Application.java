package pl.piomin.services.payment;

import java.util.Map;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.binding.BinderAwareChannelResolver;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;

import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.handler.annotation.Headers;
import pl.piomin.service.common.message.Order;
import pl.piomin.service.common.message.Request;
import pl.piomin.service.common.message.Response;
import pl.piomin.service.common.utils.ChanelUtils;

@SpringBootApplication
@EnableBinding(Sink.class)
public class Application {

	@Autowired
	private PaymentService paymentService;
	@Autowired
	private BinderAwareChannelResolver resolver;

	protected Logger logger = Logger.getLogger(Application.class.getName());

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@StreamListener(Sink.INPUT)
	public void processOrder(Request<Order> order, @Headers Map<String, Object> headers) {
		logger.info("Processing order: " + order);
		Order o = paymentService.processOrder(order.getData());
		if (null != o) {
			final Response<Order> orderResponse = new Response<Order>()
					.setData(o).setServerId(order.getServerId())
					.setUuid(order.getUuid())
					.setResultCode("200");
			logger.info("Final response: " + orderResponse);
			resolver.resolveDestination(order.getServerId() + ChanelUtils.REST_FRONT_INPUT_TOPIC_SUFFIX)
					.send(MessageBuilder.withPayload(orderResponse)/*.copyHeaders(headers)*/.build());
		}
	}

	@Bean
	public AlwaysSampler defaultSampler() {
		return new AlwaysSampler();
	}

}
