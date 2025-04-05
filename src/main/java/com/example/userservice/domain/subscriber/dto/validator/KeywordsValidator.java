package com.example.userservice.domain.subscriber.dto.validator;

import org.apache.commons.lang.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.stream.Collectors;

public class KeywordsValidator implements ConstraintValidator<ValidKeywords, List<String>> {

    private int minSize;
    private int maxSize;
    private int keywordMinLength;
    private int keywordMaxLength;

    @Override
    public void initialize(ValidKeywords constraintAnnotation) {
        this.minSize = constraintAnnotation.minSize();
        this.maxSize = constraintAnnotation.maxSize();
        this.keywordMinLength = constraintAnnotation.keywordMinLength();
        this.keywordMaxLength = constraintAnnotation.keywordMaxLength();
    }

    @Override
    public boolean isValid(List<String> value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) return false;

        List<String > processed = value.stream()
                .map(String::trim)
                .filter(StringUtils::isNotBlank)
                .distinct()
                .collect(Collectors.toList());

        if (processed.size() < minSize || processed.size() > maxSize) {
            return false;
        }

        return processed.stream()
                .allMatch(s -> s.length() >= keywordMinLength && s.length() <= keywordMaxLength);
    }
}
