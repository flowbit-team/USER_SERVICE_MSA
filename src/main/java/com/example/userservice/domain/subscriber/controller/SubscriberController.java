package com.example.userservice.domain.subscriber.controller;


import com.example.userservice.domain.member.dto.response.CreateMemberResponseDto;
import com.example.userservice.domain.subscriber.dto.request.SubscribeRequestDto;
import com.example.userservice.domain.subscriber.service.SubscriberService;
import com.example.userservice.global.common.CommonResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/subscribers")
public class SubscriberController {

    private final SubscriberService subscriberService;

    @PostMapping("/subscribe")
    public ResponseEntity<?> subscribe(@RequestBody SubscribeRequestDto subscribeRequestDto) {
        subscriberService.subscribe(subscribeRequestDto.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body("구독 완료: " + subscribeRequestDto.getEmail());
    }

    @DeleteMapping("/unsubscribe")
    public ResponseEntity<?> unsubscribe(@RequestParam String email) {
        subscriberService.unsubscribe(email);
        return ResponseEntity.status(HttpStatus.OK).body("구독 취소 완료: " + email);
    }
}
