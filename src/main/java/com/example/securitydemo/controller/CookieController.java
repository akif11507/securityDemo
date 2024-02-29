package com.example.securitydemo.controller;

import com.example.securitydemo.configurations.DevDatasourceConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController("/cookie")
@Slf4j
public class CookieController {

    @Value("${spring.application.name}")
    public String connectionStringTest;

    @GetMapping("/read-spring-cookie-custom")
    public ResponseEntity<?> readCookieCustom(
            @CookieValue(name = "user-id", defaultValue = "default-user-id") String userId) {

        DevDatasourceConfig devDatasourceConfig = new DevDatasourceConfig();
        System.out.println("this is data " + devDatasourceConfig.getAppName());

        ResponseCookie springCookie = ResponseCookie.from("user-id", "c2FtLnNtaXRoQGV4YW1wbGUuY29t")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(60)
                .domain("http://localhost:3000/")
                .build();
        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, springCookie.toString())
                .build();
//        return userId;
    }

    @GetMapping("/create-spring-cookie")
    public ResponseEntity<?> setCookie() {
        log.info("Call started");
        ResponseCookie resCookie = ResponseCookie.from("user-id", "c2FtLnNtaXRoQGV4YW1wbGUuY29t")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(24 * 60 * 60)
//                .domain("localhost")
//                .sameSite("Strict")
                .build();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, resCookie.toString()).build();

    }

    @GetMapping("/delete-spring-cookie")
    public ResponseEntity<?> deleteCookie() {

        // create a cookie
        ResponseCookie resCookie = ResponseCookie.from("user-id", null)
                .build();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, resCookie.toString()).build();

    }

    @GetMapping("/read-spring-cookie")
    public String readCookie(@CookieValue(name = "user-id", defaultValue = "default-user-id") String cookieName) {
        return String.format("value of the cookie with name user-id is: %s", cookieName);
    }

    @GetMapping("/read-spring-cookie-2")
    public String readServletCookie(HttpServletRequest request, String name) {
        for (var data : request.getCookies()) {
            System.out.println("cookie data " + data.getName() + " " + data.getValue());
        }
        return "ok";
    }
}
