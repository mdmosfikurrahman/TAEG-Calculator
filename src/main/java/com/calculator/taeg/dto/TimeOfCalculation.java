package com.calculator.taeg.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TimeOfCalculation {

    private String date;
    private String time;

}
