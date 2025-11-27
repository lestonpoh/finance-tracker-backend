package com.example.financeapp_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.financeapp_backend.model.ibkr.IbkrInfoDTO;
import com.example.financeapp_backend.model.ibkr.IbkrReportDTO;
import com.example.financeapp_backend.service.InvestmentService;

import jakarta.validation.Valid;
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

    @PostMapping("/ibkr")
    public ResponseEntity<String> updateIbkrInfo(@Valid @RequestBody IbkrInfoDTO request) {
        investmentService.updateIbkrInfo(request);
        return ResponseEntity.ok("Info updated successfully");
    }
}
