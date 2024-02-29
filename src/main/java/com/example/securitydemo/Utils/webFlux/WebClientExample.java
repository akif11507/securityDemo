package com.example.securitydemo.Utils.webFlux;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Objects;

public class WebClientExample {

    public static void main(String[] args) {
        String cookieName = "JSESSIONID";
        // Create a WebClient
        WebClient webClient = WebClient.create();

        // Make the first request to JSONPlaceholder to get a user's information and extract the email as a token
//        String cookie = webClient.post()
//                .uri("66.42.44.197:1080/rest/v2/users/authenticate")
//                .body(
//                        Mono.just(UserRequest.builder()
//                                .email("ole.islam@bjitgroup.com")
//                                .password("d4277f5bdd38ca210dbf498f29dfee3c")
//                                .build()),
//                        UserRequest.class
//                )
//                .exchangeToMono(response ->
//                Mono.just(response.cookies().getFirst(cookieName))).block().getValue();
//
//        System.out.println("-------->" + cookie);


        // Request body
        String requestBody = "{ \"email\": \"ole.islam@bjitgroup.com\", \"password\": \"d4277f5bdd38ca210dbf498f29dfee3c\" }";

        // Make the request to authenticate a user
        String cookie = Objects.requireNonNull(webClient.post()
                .uri("http://66.42.44.197:1080/rest/v2/users/authenticate")
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .header(HttpHeaders.ACCEPT, "application/json, text/plain, */*")
                .body(BodyInserters.fromValue(requestBody))
                .exchangeToMono(response ->
                        Mono.just(Objects.requireNonNull(response.cookies().getFirst(cookieName)))).block()).getValue();
        System.out.println("-----------> " + cookie);




//        AppSettings appSettings = webClientBuilder.build()
//                .get()
//                .uri(appSettingUrl)
//                .header("JSESSIONID", cookie)
//                .header("Connection", "keep-alive")
//                .retrieve()
//                .bodyToMono(AppSettings.class)
//                .block();
//        String appSettingUrl = amsEdgeServerName + "/rest/v2/applications/settings/WebRTCAppEE";






        // Print the response
//        responseMono.subscribe(response -> {
//            System.out.println("Response from the server: " + response);
//        });


//        System.out.println("-----------> " + tokenMono.block());

//        // Make the second request to JSONPlaceholder's posts endpoint using the extracted email as a token
//        tokenMono.flatMap(email ->
//                webClient.get()
//                        .uri("https://jsonplaceholder.typicode.com/posts?userId=" + email.hashCode())
//                        .header("Authorization", "Bearer " + email)
//                        .retrieve()
//                        .bodyToMono(PostResponse[].class)
//        ).subscribe(postResponses -> {
//            // Process the result of the second request
//            for (PostResponse postResponse : postResponses) {
//                System.out.println("Post ID: " + postResponse.getId() + ", Title: " + postResponse.getTitle());
//            }
//        });
    }

    // Example class representing the response from the first endpoint (JSONPlaceholder's users endpoint)
    @Getter
    private static class UserResponse {
        private String email;

    }

    @Getter
    @Builder
    private static class UserRequest {
        private String email;
        private String password;
    }

    // Example class representing the response from the second endpoint (JSONPlaceholder's posts endpoint)
    @Getter
    private static class PostResponse {
        private int id;
        private String title;

    }
}