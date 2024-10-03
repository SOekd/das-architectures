package me.soekd.microkernel.kernel.controller;

import lombok.extern.slf4j.Slf4j;
import me.soekd.microkernel.api.DefaultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
@Slf4j
public class KernelController {

    private final Map<String, DefaultService> services;

    public KernelController(@Autowired(required = false) Map<String, DefaultService> services) {
        this.services = services;
    }

    @GetMapping
    public ResponseEntity<String> index() {
        String serviceList = services.entrySet().stream()
                .peek(entry -> entry.getValue().doWork(null))
                .map(Map.Entry::getKey)
                .collect(Collectors.joining(", "));

        log.info("Services: {}", serviceList);
        return new ResponseEntity<>(serviceList, HttpStatus.OK);
    }

}
