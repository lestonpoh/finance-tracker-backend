package com.lestonpoh.financeapp_backend.client;

import java.io.StringReader;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.lestonpoh.financeapp_backend.model.ibkr.generateIbkrApiResponse.FlexStatementResponseDTO;
import com.lestonpoh.financeapp_backend.model.ibkr.getIbkrReportApiResponse.FlexQueryResponseDTO;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class IbkrClient {

    @Value("${ibkr.urls.generateReport}")
    private String generateReportBaseUrl;
    @Value("${ibkr.urls.getReport}")
    private String getReportBaseUrl;
    @Value("${ibkr.secrets.flexToken}")
    private String flexToken;
    @Value("${ibkr.secrets.flexQueryId}")
    private String flexQueryId;

    private final WebClient webClient;

    public String generateReport() {
        String url = String.format(
                "%s?v=3&t=%s&q=%s",
                generateReportBaseUrl, flexToken, flexQueryId);

        log.info("Requesting report from ibkr... ");
        String responseXml = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        try {
            JAXBContext context = JAXBContext.newInstance(FlexStatementResponseDTO.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            FlexStatementResponseDTO response = (FlexStatementResponseDTO) unmarshaller
                    .unmarshal(new StringReader(responseXml));
            return response.getReferenceCode();
        } catch (Exception e) {
            throw new RuntimeException("Cannot parse xml response");
        }
    }

    public FlexQueryResponseDTO getReport(String referenceCode) {
        String url = String.format(
                "%s?v=3&t=%s&q=%s",
                getReportBaseUrl, flexToken, referenceCode);

        String responseXml = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        try {
            JAXBContext context = JAXBContext.newInstance(FlexQueryResponseDTO.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (FlexQueryResponseDTO) unmarshaller.unmarshal(new StringReader(responseXml));
        } catch (Exception e) {
            throw new RuntimeException("Cannot parse xml response");
        }
    }

}
