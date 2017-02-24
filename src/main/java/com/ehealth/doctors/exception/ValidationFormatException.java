package com.ehealth.doctors.exception;

import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by vilyam on 24.02.17.
 */
public class ValidationFormatException extends Exception {
    public final List<ValidationExplanation> data;

    public ValidationFormatException(BindingResult bindingResult) {
        data = bindingResult.getFieldErrors().stream()
                .map(fe -> new ValidationExplanation(fe.getField(), fe.getDefaultMessage()))
                .collect(Collectors.toList());
    }

    public ValidationFormatException(String field, String message) {
        data = Collections.singletonList(new ValidationExplanation(field, message));
    }

    public class ValidationExplanation {
        public String field;
        public String message;

        public ValidationExplanation(String field, String message) {
            this.field = field;
            this.message = message;
        }
    }
}
