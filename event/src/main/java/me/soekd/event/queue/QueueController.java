package me.soekd.event.queue;

import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusProcessorClient;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/queue")
@Slf4j
public class QueueController {


    private final ServiceBusSenderClient queueSender;

    private final ServiceBusProcessorClient queueProcessor;

    @Autowired
    public QueueController(
            @Qualifier(QueueConfiguration.QUEUE_SENDER_CLIENT) ServiceBusSenderClient queueSender,
            @Qualifier(QueueConfiguration.QUEUE_PROCESSOR) ServiceBusProcessorClient queueProcessor
    ) {
        this.queueSender = queueSender;
        this.queueProcessor = queueProcessor;
    }

    @PostMapping("/send")
    public ResponseEntity queueSend(@RequestBody String message) {
        queueSender.sendMessage(new ServiceBusMessage(message));
        log.info("Message sent: {}", message);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/receive")
    public ResponseEntity queueReceive() {
        queueProcessor.start();
        return ResponseEntity.ok().build();
    }

}
