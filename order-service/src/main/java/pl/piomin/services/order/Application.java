package pl.piomin.services.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.core.MessageSource;
import org.springframework.messaging.support.GenericMessage;
import pl.piomin.service.common.message.*;

import java.time.LocalDateTime;
import java.util.logging.Logger;


@SpringBootApplication
@EnableBinding(Sink.class)
public class Application {

	protected Logger logger = Logger.getLogger(Application.class.getName());
	
	private int index = 0;
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
//	@InboundChannelAdapter(value = Source.OUTPUT, poller = @Poller(fixedDelay = "30000", maxMessagesPerPoll = "1"))
	public MessageSource<Order> orderSource() {
		return () -> {
			Order o = new Order(index++, OrderType.PURCHASE, LocalDateTime.now(), OrderStatus.NEW, new Product("Example#2"), new Shipment(ShipmentType.SHIP));
			logger.info("Sending order: " + o);
			return new GenericMessage<>(o); 
		};
	}

	@StreamListener(Sink.INPUT)
	public void processOrder(String message) {
		logger.info("Processing message: " + message);
	}

	@Bean
	public AlwaysSampler defaultSampler() {
	  return new AlwaysSampler();
	}
	
}
