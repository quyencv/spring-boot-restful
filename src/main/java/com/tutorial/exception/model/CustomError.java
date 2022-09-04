package com.tutorial.exception.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class CustomError {

    private HttpStatus httpStatus;

    private int status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;

    private String messageCode;

    private String message;

    private List<CustomSubError> subErrors = new ArrayList<>();

    private CustomError() {
        timestamp = LocalDateTime.now();
    }

    public CustomError(HttpStatus httpStatus) {
        this();
        this.status = httpStatus.value();
        this.httpStatus = httpStatus;
    }

    public CustomError(HttpStatus httpStatus, Throwable ex) {
        this();
        this.status = httpStatus.value();
        this.httpStatus = httpStatus;
        this.messageCode = httpStatus.name();
        this.message = ex.getLocalizedMessage();
    }

    public CustomError(HttpStatus httpStatus, String messageCode, Throwable ex) {
        this();
        this.status = httpStatus.value();
        this.httpStatus = httpStatus;
        this.messageCode = messageCode;
        this.message = ex.getLocalizedMessage();
    }

    private void addSubError(CustomSubError subError) {
        if (CollectionUtils.isEmpty(subErrors)) {
            subErrors = new ArrayList<>();
        }
        subErrors.add(subError);
    }

    private void addValidationError(String object, String field, Object rejectedValue, String message) {
        addSubError(new CustomValidationError(object, field, rejectedValue, message));
    }

    private void addValidationError(String object, String message) {
        addSubError(new CustomValidationError(object, message));
    }

    private void addValidationError(FieldError fieldError) {
        this.addValidationError(fieldError.getObjectName(), fieldError.getField(), fieldError.getRejectedValue(),
                fieldError.getDefaultMessage());
    }

    public void addValidationErrors(List<FieldError> fieldErrors) {
        fieldErrors.forEach(this::addValidationError);
    }

    private void addValidationError(ObjectError objectError) {
        this.addValidationError(objectError.getObjectName(), objectError.getDefaultMessage());
    }

    public void addValidationError(List<ObjectError> globalErrors) {
        globalErrors.forEach(this::addValidationError);
    }

    /**
     * Utility method for adding error of ConstraintViolation. Usually when a @Validated validation fails.
     *
     * @param cv the ConstraintViolation
     */
    private void addValidationError(ConstraintViolation<?> cv) {
        this.addValidationError(cv.getRootBeanClass().getSimpleName(),
                ((PathImpl) cv.getPropertyPath()).getLeafNode().asString(), cv.getInvalidValue(), cv.getMessage());
    }

    public void addValidationErrors(Set<ConstraintViolation<?>> constraintViolations) {
        constraintViolations.forEach(this::addValidationError);
    }

}