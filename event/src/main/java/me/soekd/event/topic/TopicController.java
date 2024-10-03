package me.soekd.event.topic;

import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusProcessorClient;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/topic")
@Slf4j
public class TopicController {

    private final ServiceBusSenderClient topicSender;

    private final ServiceBusProcessorClient topicProcessor;

    @Autowired
    public TopicController(
            @Qualifier(TopicConfiguration.TOPIC_SENDER_CLIENT) ServiceBusSenderClient topicSender,
            @Qualifier(TopicConfiguration.TOPIC_PROCESSOR) ServiceBusProcessorClient topicProcessor
    ) {
        this.topicSender = topicSender;
        this.topicProcessor = topicProcessor;
    }

    @PostMapping("/send")
    public ResponseEntity topicSend(@RequestBody String message) {
        topicSender.sendMessage(new ServiceBusMessage(message));
        log.info("Message sent: {}", message);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/receive")
    public ResponseEntity topicReceive() {
        topicProcessor.start();
        return ResponseEntity.ok().build();
    }

}
