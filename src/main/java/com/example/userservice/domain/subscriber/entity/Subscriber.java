package com.example.userservice.domain.subscriber.entity;

import com.example.userservice.global.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "SUBSCRIBER")
@Builder
@AllArgsConstructor
public class Subscriber extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SUBSCRIBER_ID")
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private boolean subscribed = true; // 구독 상태 (true: 구독 중, false: 구독 취소)

    @OneToMany(mappedBy = "subscriber", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Interest> interests = new ArrayList<>();

    public Subscriber(String email) {
        this.email = email;
    }

    public Subscriber(String email, List<String> keywords) {
        this.email = email;

        keywords.stream()
                .map(Interest::new)
                .forEach(this::add);
    }

    public void unsubscribe() {
        this.subscribed = false;
    }

    // 연관관계 메서드
    public void add(Interest interest) {
        interests.add(interest);
        interest.setSubscriber(this);
    }

}
