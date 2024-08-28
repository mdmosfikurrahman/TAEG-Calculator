package com.calculator.taeg.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValidationError {

    private String className;
    private String fieldName;
    private String errorReason;
    private Object rejectedValue;

}
