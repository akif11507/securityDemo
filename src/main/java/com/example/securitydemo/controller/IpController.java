package com.example.securitydemo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@RestController("/api-ip/")
@Slf4j
public class IpController {

    List<String> addressList = new ArrayList<>();

    @GetMapping("/first")
    public void getIp(HttpServletRequest request) throws UnknownHostException {
        String clientIp = request.getHeader("X-Forwarded-For");
        log.info("remote " + request.getRemoteAddr());
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            log.info("data " + key + " " + value);
        }
//        if (clientIp == null || clientIp.isEmpty()) {
//            clientIp = request.getRemoteAddr();
//            clientIp = InetAddress.getLocalHost().getHostAddress();
//            addressList.add(clientIp);
//            log.info("remote address " + clientIp);
//        } else {
//            // check list for allowed headers
//            // check(clientIp)
//            log.info("predefined header " + clientIp);
//        }

    }
}
