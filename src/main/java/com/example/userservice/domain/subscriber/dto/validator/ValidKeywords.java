package com.example.userservice.domain.subscriber.dto.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = KeywordsValidator.class)
public @interface ValidKeywords {

    String message() default "Invalid keywords.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    int minSize() default 1;
    int maxSize() default 3;
    int keywordMinLength() default 1;
    int keywordMaxLength() default 200;
}
