package com.lestonpoh.financeapp_backend.model.ibkr.generateIbkrApiResponse;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@XmlRootElement(name = "FlexStatementResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class FlexStatementResponseDTO {

    @XmlElement(name = "Status")
    private String status;

    @XmlElement(name = "ReferenceCode")
    private String referenceCode;

    @XmlElement(name = "Url")
    private String url;

}