package pl.piomin.services.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.binding.BinderAwareChannelResolver;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.support.MessageBuilder;
import pl.piomin.service.common.message.Order;

import java.util.Map;
import java.util.logging.Logger;

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
	public void processOrder(Order order, @Headers Map<String, Object> headers) {
		logger.info("Processing order: " + order);
		Order o = paymentService.processOrder(order);
		if (o != null) {
			logger.info("Final response: " + (o.getProduct().getPrice() + o.getShipment().getPrice()));
			sendMessage("test message", "ex.stream.in", "text/plain", headers);
		}
	}

	private void sendMessage(String body, String target, Object contentType, Map<String, Object> headers) {
		resolver.resolveDestination(target).send(MessageBuilder.createMessage(body,
				new MessageHeaders(headers)));
	}

	@Bean
	public AlwaysSampler defaultSampler() {
		return new AlwaysSampler();
	}

}
