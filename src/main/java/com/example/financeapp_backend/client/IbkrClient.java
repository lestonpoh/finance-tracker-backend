package com.example.financeapp_backend.client;

import java.io.StringReader;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.financeapp_backend.model.ibkr.generateIbkrApiResponse.FlexStatementResponseDTO;
import com.example.financeapp_backend.model.ibkr.getIbkrReportApiResponse.FlexQueryResponseDTO;

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

    private final WebClient webClient;

    public String generateReport(String queryId, String token) {
        String url = String.format(
                "%s?v=3&q=%s&t=%s",
                generateReportBaseUrl, queryId, token);

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
            log.info("report generated");
            return response.getReferenceCode();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("Cannot parse xml response after generating report");
        }
    }

    public FlexQueryResponseDTO getReport(String token, String referenceCode) {
        String url = String.format(
                "%s?v=3&t=%s&q=%s",
                getReportBaseUrl, token, referenceCode);

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
            log.error(e.getMessage());
            throw new RuntimeException("Cannot parse xml response from generated report");
        }
    }

}
