package com.example.financeapp_backend.model.ibkr.getIbkrReportApiResponse;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@XmlRootElement(name = "FlexQueryResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class FlexQueryResponseDTO {

    @XmlElement(name = "FlexStatements")
    private FlexStatements flexStatements;

}