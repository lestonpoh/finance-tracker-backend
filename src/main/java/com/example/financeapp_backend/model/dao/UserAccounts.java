package com.example.financeapp_backend.model.dao;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.example.financeapp_backend.model.ibkr.IbkrInfoDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
@Document(collection = "user_accounts")
public class UserAccounts {
    @Id
    private String id;
    @Indexed(unique = true)
    private String userId;
    private IbkrInfoDTO ibkr;
}
