package com.lestonpoh.financeapp_backend.service;

import org.springframework.stereotype.Service;

import com.lestonpoh.financeapp_backend.client.IbkrClient;
import com.lestonpoh.financeapp_backend.mapper.ibkr.IbkrMapper;
import com.lestonpoh.financeapp_backend.model.ibkr.IbkrReportDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class InvestmentService {
    private final IbkrClient ibkrClient;
    private final IbkrMapper ibkrMapper;

    public IbkrReportDTO getIbkrReport() {
        String referenceCode = ibkrClient.generateReport();
        return ibkrMapper.toIbkrReportDTO(ibkrClient.getReport(referenceCode));
    }
}
