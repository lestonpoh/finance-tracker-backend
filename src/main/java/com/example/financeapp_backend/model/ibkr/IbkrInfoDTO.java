package com.example.financeapp_backend.model.ibkr;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class IbkrInfoDTO {
    @NotNull(message = "Query is required")
    private String queryId;
    @NotNull(message = "token is required")
    private String token;
}
