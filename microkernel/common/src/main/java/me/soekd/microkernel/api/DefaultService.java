package me.soekd.microkernel.api;

import org.springframework.http.HttpStatus;

import java.util.Map;

public interface DefaultService {

    HttpStatus doWork(Map<String, String> params);

}
