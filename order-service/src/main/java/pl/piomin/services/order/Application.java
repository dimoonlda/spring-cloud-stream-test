package pl.piomin.services.order;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.cloud.stream.binding.BindingService;
import org.springframework.cloud.stream.binding.BindingTargetFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.SubscribableChannel;
import pl.piomin.service.common.utils.ChanelUtils;
import pl.piomin.services.order.config.OrderServiceProperties;

import java.util.logging.Logger;

import static pl.piomin.services.order.service.AbstractRequestHandler.serverId;


@SpringBootApplication
@EnableConfigurationProperties({OrderServiceProperties.class})
public class Application {

	@Autowired
	private BindingTargetFactory bindingTargetFactory;
	@Autowired
	private BindingService bindingService;
	@Autowired
	private BeanFactory beanFactory;

	protected org.slf4j.Logger logger = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public AlwaysSampler defaultSampler() {
		logger.error("created test.");
	  return new AlwaysSampler();
	}

	private String getDestination() {
		return serverId + ChanelUtils.REST_FRONT_INPUT_TOPIC_SUFFIX;
	}

	@Bean("responseChanel")
	public SubscribableChannel createInputBindingTarget() {
		SubscribableChannel channel = (SubscribableChannel)bindingTargetFactory
				.createInput(getDestination());
		bindingService.bindConsumer(channel,
				getDestination());

		return channel;
	}
}
