package com.example.userservice.domain.subscriber.controller;


import com.example.userservice.domain.member.dto.response.MemberRenewAccessTokenResponseDto;
import com.example.userservice.domain.subscriber.dto.request.SubscribeRequestDto;
import com.example.userservice.domain.subscriber.service.SubscriberService;
import com.example.userservice.global.common.CommonResDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/subscribers")
public class SubscriberController {

    private final SubscriberService subscriberService;

    @PostMapping("/subscribe")
    public ResponseEntity<?> subscribe(@Valid @RequestBody SubscribeRequestDto subscribeRequestDto) {
        subscriberService.subscribe(subscribeRequestDto.getEmail(), subscribeRequestDto.getKeywords());
        return new ResponseEntity<>(new CommonResDto<>(1, "구독이 완료되었습니다",""), HttpStatus.OK);
    }

    @DeleteMapping("/unsubscribe")
    public ResponseEntity<?> unsubscribe(@RequestParam String email) {
        subscriberService.unsubscribe(email);
        return new ResponseEntity<>(new CommonResDto<>(1, "구독이 취소되었습니다.",""), HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<?> getSubscriberCount() {
        long count = subscriberService.countSubscribers();
        return new ResponseEntity<>(new CommonResDto<>(1, "총 구독자 수 조회 성공", count), HttpStatus.OK);
    }


}
