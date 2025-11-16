package com.lestonpoh.financeapp_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lestonpoh.financeapp_backend.model.ibkr.IbkrReportDTO;
import com.lestonpoh.financeapp_backend.service.InvestmentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/investments")
@RequiredArgsConstructor
public class InvestmentController {
    private final InvestmentService investmentService;

    @GetMapping(value = "/ibkr")
    public ResponseEntity<IbkrReportDTO> getIbkrReport() {
        return ResponseEntity.ok(investmentService.getIbkrReport());
    }
}
