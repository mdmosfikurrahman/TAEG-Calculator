package com.calculator.taeg.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CalculationTimeResponse {

    private String date;
    private String time;

}
