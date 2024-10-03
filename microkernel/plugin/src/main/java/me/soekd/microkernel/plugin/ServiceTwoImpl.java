package me.soekd.microkernel.plugin;

import lombok.extern.slf4j.Slf4j;
import me.soekd.microkernel.api.DefaultService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class ServiceTwoImpl implements DefaultService {

    @Override
    public HttpStatus doWork(Map<String, String> params) {
        log.info("Service 2 called");
        return HttpStatus.OK;
    }

}
