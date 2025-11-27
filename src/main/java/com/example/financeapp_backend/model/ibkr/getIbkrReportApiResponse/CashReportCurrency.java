package com.example.financeapp_backend.model.ibkr.getIbkrReportApiResponse;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class CashReportCurrency {

    @XmlAttribute(name = "slbNetCash")
    private String slbNetCash;

    @XmlAttribute(name = "currency")
    private String currency;

}