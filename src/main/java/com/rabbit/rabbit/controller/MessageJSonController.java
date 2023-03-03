package com.rabbit.rabbit.controller;

import com.rabbit.rabbit.dto.User;
import com.rabbit.rabbit.publisher.RabbitMQJsonProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/v1")
public class MessageJSonController {
    private RabbitMQJsonProducer rabbitMQJsonProducer;

    public MessageJSonController(RabbitMQJsonProducer rabbitMQJsonProducer) {
        this.rabbitMQJsonProducer = rabbitMQJsonProducer;
    }

    @PostMapping("/publish")
    public ResponseEntity<String> sendJsonMessage(@RequestBody User user){
        rabbitMQJsonProducer.sendJsonMessage(user);
        return  ResponseEntity.ok("Json Message sent to RabbitMQ ...");

    }
}
