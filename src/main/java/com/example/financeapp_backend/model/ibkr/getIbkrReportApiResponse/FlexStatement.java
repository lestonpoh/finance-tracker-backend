package com.example.financeapp_backend.model.ibkr.getIbkrReportApiResponse;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class FlexStatement {

    @XmlElement(name = "CashReport")
    private CashReport cashReport;

    @XmlElement(name = "OpenPositions")
    private OpenPositions openPositions;
}