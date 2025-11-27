package com.example.financeapp_backend.model.ibkr.getIbkrReportApiResponse;

import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class CashReport {

    @XmlElement(name = "CashReportCurrency")
    private List<CashReportCurrency> cashReportCurrencyList;

}