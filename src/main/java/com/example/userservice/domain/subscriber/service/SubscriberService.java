package com.example.userservice.domain.subscriber.service;

import java.util.List;

public interface SubscriberService {
    void subscribe(String email, List<String> keywords);
    void unsubscribe(String email);

    long countSubscribers();
}
