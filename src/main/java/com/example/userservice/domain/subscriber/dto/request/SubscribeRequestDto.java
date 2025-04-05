package com.example.userservice.domain.subscriber.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@NoArgsConstructor
@Getter
public class SubscribeRequestDto {
    private String email;

    @Size(min=1, max = 3, message = "You must provide 1 to 3 keywords.")
    private List<
            @NotBlank(message = "Each keyword must not be blank.")
            @Size(min = 2, max = 20, message = "Keyword must be between 2 and 20 characters.")
                    String> keywords;
}
