package com.lestonpoh.financeapp_backend.model.ibkr;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IbkrReportDTO {

    private float totalAssetValueSGD;
    private float totalPostionValueSGD;
    private float totalCashSgd;
    private List<Cash> cashList;
    private List<Position> positionList;

    @Data
    public static class Cash {
        private String currency;
        private float value;
    }

    @Data
    public static class Position {
        private String currency;
        private String symbol;
        private String description;
        private float position;
        private float positionValue;
        private float positionValueSGD;
        private float costPrice;
        private float currentPrice;
        private float unrealizedGains;
        private float unrealizedGainsPercent;
    }
}