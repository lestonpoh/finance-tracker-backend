package com.lestonpoh.financeapp_backend.utility;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Component;

@Component
public class NumberUtil {
    public float roundToDp(Float value, Integer decimalPoints) {
        return BigDecimal
                .valueOf(value)
                .setScale(decimalPoints, RoundingMode.HALF_UP)
                .floatValue();
    }
}
