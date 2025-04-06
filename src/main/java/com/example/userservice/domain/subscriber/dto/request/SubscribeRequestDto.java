package com.example.userservice.domain.subscriber.dto.request;

import com.example.userservice.domain.subscriber.dto.validator.ValidKeywords;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
public class SubscribeRequestDto {
    @Getter
    private String email;

    @ValidKeywords(minSize = 1, maxSize = 3, keywordMinLength = 2, keywordMaxLength = 20,
            message = "Keywords must be 1~3, each 2~20 characters and unique.")
    private List<String> keywords;

    // 중복 제거
    public List<String> getKeywords() {
        return keywords.stream()
                .map(String::trim)
                .filter(StringUtils::isNotBlank)
                .distinct()
                .collect(Collectors.toList());
    }
}
