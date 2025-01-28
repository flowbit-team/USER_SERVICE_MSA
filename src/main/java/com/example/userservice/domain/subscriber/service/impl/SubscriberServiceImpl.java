package com.example.userservice.domain.subscriber.service.impl;

import com.example.userservice.domain.member.dto.request.SignUpRequestDto;
import com.example.userservice.domain.subscriber.entity.Subscriber;
import com.example.userservice.domain.subscriber.repository.SubscriberRepository;
import com.example.userservice.domain.subscriber.service.SubscriberService;
import com.example.userservice.global.config.redis.util.EmailRedisUtil;
import com.example.userservice.global.exception.error.EmailNotValidException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
@Slf4j
public class SubscriberServiceImpl implements SubscriberService {

    private final SubscriberRepository subscriberRepository;
    private final EmailRedisUtil emailRedisUtil;

    @Override
    @Transactional
    public void subscribe(String email) {
        // 이메일 중복 체크
        subscriberRepository.findByEmail(email).ifPresent(subscriber -> {
            throw new IllegalArgumentException("이미 구독된 이메일입니다.");
        });
        emailVerifyCheck(email);

        // 새 구독자 저장
        Subscriber subscriber = new Subscriber(email);
        subscriberRepository.save(subscriber);
    }

    @Override
    @Transactional
    public void unsubscribe(String email) {
        // 구독된 이메일 확인
        Subscriber subscriber = subscriberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("구독된 이메일을 찾을 수 없습니다."));

        // 구독 취소
        subscriber.unsubscribe();
    }


    private void emailVerifyCheck(String email ) {
        log.info("구독 이메일 검증 중");

        List<String> data = emailRedisUtil.getData(email);
        if (data.isEmpty()){
            throw new EmailNotValidException();
        }
        String[] splitInfo = data.get(0).split("\\|");
        String redisVerifyPurpose = splitInfo[1];
        if(!Objects.equals(redisVerifyPurpose, "subscriberVerifySuccess")){
            throw new EmailNotValidException();
        }
    }
}
