package me.soekd.event.topic;

import com.azure.messaging.servicebus.*;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@Slf4j
public class TopicConfiguration {

    public static final String TOPIC_SENDER_CLIENT = "topicsenderclient";
    public static final String TOPIC_PROCESSOR = "topicprocessor";

    private static final String SUBSCRIPTION_NAME = "subsaugusto";
    private static final String TOPIC_NAME = "das1";


    @Bean(name = TOPIC_SENDER_CLIENT)
    public ServiceBusSenderClient serviceBusQueueSenderClient(ServiceBusClientBuilder builder) {
        return builder
                .sender()
                .topicName(TOPIC_NAME)
                .buildClient();
    }

    @Bean(name = TOPIC_PROCESSOR)
    public ServiceBusProcessorClient serviceBusQueueProcessorClient(ServiceBusClientBuilder builder) {
        return builder.processor()
                .topicName(TOPIC_NAME)
                .subscriptionName(SUBSCRIPTION_NAME)
                .processMessage(TopicConfiguration::processMessage)
                .processError(TopicConfiguration::processError)
                .buildProcessorClient();
    }

    private static void processMessage(ServiceBusReceivedMessageContext context) {
        val message = context.getMessage();
        log.info("Processing message. Id: {}, Sequence #: {}. Contents: {}",
                message.getMessageId(),
                message.getSequenceNumber(),
                message.getBody()
        );
    }

    private static void processError(ServiceBusErrorContext context) {
        log.warn(
                "Error when receiving messages from namespace: '{}'. Entity: '{}'",
                context.getFullyQualifiedNamespace(),
                context.getEntityPath()
        );
    }

}
