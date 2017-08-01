package pl.piomin.services.order.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "service.order")
public class OrderServiceProperties {

    private String chanelOutput = "";

    public String getChanelOutput() {
        return chanelOutput;
    }

    public OrderServiceProperties setChanelOutput(String chanelOutput) {
        this.chanelOutput = chanelOutput;
        return this;
    }
}
