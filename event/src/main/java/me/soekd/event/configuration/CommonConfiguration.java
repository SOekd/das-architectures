package me.soekd.event.configuration;

import com.azure.core.amqp.AmqpTransportType;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class CommonConfiguration {

    private static final String SERVICE_BUS_FQDN = "das1univille.servicebus.windows.net";

    @Bean
    public ServiceBusClientBuilder serviceBusClientBuilder() {
        return new ServiceBusClientBuilder()
                .fullyQualifiedNamespace(SERVICE_BUS_FQDN)
                .transportType(AmqpTransportType.AMQP_WEB_SOCKETS)
                .credential(new DefaultAzureCredentialBuilder().build());
    }

}
