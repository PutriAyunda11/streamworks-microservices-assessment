package com.streamworks.api_gateway.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class GatewayController {
    private final RestTemplate restTemplate = new RestTemplate();

    private final String SERVICE_1_URL = "http://api-service-1:8081";
    private final String SERVICE_2_URL = "http://api-service-2:8082";

    @GetMapping("/users/**")
    public ResponseEntity<String> proxyUsers(jakarta.servlet.http.HttpServletRequest request) {
        String url = SERVICE_1_URL + request.getRequestURI();
        return restTemplate.getForEntity(url, String.class);
    }

    @GetMapping("/todos/**")
    public ResponseEntity<String> proxyTodos(jakarta.servlet.http.HttpServletRequest request) {
        String url = SERVICE_1_URL + request.getRequestURI();
        return restTemplate.getForEntity(url, String.class);
    }

    @PostMapping("/todos/**")
    public ResponseEntity<String> proxyTodosPost(jakarta.servlet.http.HttpServletRequest request, @RequestBody(required = false) String body) {
        String url = SERVICE_1_URL + request.getRequestURI();
        return restTemplate.postForEntity(url, body, String.class);
    }

    @GetMapping("/posts/**")
    public ResponseEntity<String> proxyPosts(jakarta.servlet.http.HttpServletRequest request) {
        String url = SERVICE_2_URL + request.getRequestURI();
        return restTemplate.getForEntity(url, String.class);
    }

    @PostMapping("/posts/**")
    public ResponseEntity<String> proxyPostsPost(jakarta.servlet.http.HttpServletRequest request, @RequestBody(required = false) String body) {
        String url = SERVICE_2_URL + request.getRequestURI();
        return restTemplate.postForEntity(url, body, String.class);
    }
}
