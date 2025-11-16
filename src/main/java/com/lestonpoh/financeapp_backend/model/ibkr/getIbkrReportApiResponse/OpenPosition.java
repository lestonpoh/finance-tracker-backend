package com.lestonpoh.financeapp_backend.model.ibkr.getIbkrReportApiResponse;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class OpenPosition {

    @XmlAttribute(name = "currency")
    private String currency;

    @XmlAttribute(name = "symbol")
    private String symbol;

    @XmlAttribute(name = "description")
    private String description;

    @XmlAttribute(name = "position")
    private String position;

    @XmlAttribute(name = "positionValue")
    private String positionValue;

    @XmlAttribute(name = "costBasisPrice")
    private String costBasisPrice;

    @XmlAttribute(name = "markPrice")
    private String markPrice;

    @XmlAttribute(name = "fifoPnlUnrealized")
    private String fifoPnlUnrealized;

    @XmlAttribute(name = "fxRateToBase")
    private String fxRateToBase;

}