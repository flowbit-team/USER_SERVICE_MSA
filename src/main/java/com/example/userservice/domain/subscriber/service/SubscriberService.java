package com.example.userservice.domain.subscriber.service;

public interface SubscriberService {
    void subscribe(String email);
    void unsubscribe(String email);
}
