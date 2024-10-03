package me.soekd.event.queue;

import com.azure.messaging.servicebus.*;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@Slf4j
public class QueueConfiguration {

    public static final String QUEUE_SENDER_CLIENT = "queuesenderclient";
    public static final String QUEUE_PROCESSOR = "queueprocessor";

    private static final String QUEUE_NAME = "queue-augusto";


    @Bean(name = QUEUE_SENDER_CLIENT)
    public ServiceBusSenderClient serviceBusQueueSenderClient(ServiceBusClientBuilder builder) {
        return builder
                .sender()
                .queueName(QUEUE_NAME)
                .buildClient();
    }

    @Bean(name = QUEUE_PROCESSOR)
    public ServiceBusProcessorClient serviceBusQueueProcessorClient(ServiceBusClientBuilder builder) {
        return builder.processor()
                .queueName(QUEUE_NAME)
                .processMessage(QueueConfiguration::processMessage)
                .processError(QueueConfiguration::processError)
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
