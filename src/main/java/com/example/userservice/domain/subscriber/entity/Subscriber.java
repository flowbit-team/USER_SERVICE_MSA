package com.example.userservice.domain.subscriber.entity;

import com.example.userservice.global.entity.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Objects;

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

    public Subscriber(String email) {
        this.email = email;
    }

    public void unsubscribe() {
        this.subscribed = false;
    }

}
