package com.example.financeapp_backend.service;

import org.springframework.stereotype.Service;

import com.example.financeapp_backend.client.IbkrClient;
import com.example.financeapp_backend.mapper.ibkr.IbkrMapper;
import com.example.financeapp_backend.model.dao.UserAccounts;
import com.example.financeapp_backend.model.ibkr.IbkrInfoDTO;
import com.example.financeapp_backend.model.ibkr.IbkrReportDTO;
import com.example.financeapp_backend.repository.UserAccountsRepository;
import com.example.financeapp_backend.utility.CryptoUtil;
import com.example.financeapp_backend.utility.SecurityUtil;
import com.fasterxml.jackson.core.type.TypeReference;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class InvestmentService {
    private final IbkrClient ibkrClient;
    private final IbkrMapper ibkrMapper;
    private final UserAccountsRepository userAccountsRepository;
    private final CryptoUtil cryptoUtil;
    private final CacheService cacheService;

    public IbkrReportDTO getIbkrReport() {
        final String userId = SecurityUtil.getCurrentUserId();
        final String cachekey = "ibkr";

        // check if it exist in redis
        IbkrReportDTO cachedInfo = cacheService.getCachedInfo(userId, cachekey, new TypeReference<IbkrReportDTO>() {
        });
        if (cachedInfo != null) {
            log.info("retrieved info from redis");
            return cachedInfo;
        }

        log.info("ibkr info does not exists in redis, fetching from api...");

        UserAccounts userAccounts = userAccountsRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("No user_accounts found"));

        String token;
        String queryId;

        if (userAccounts.getIbkr() != null) {
            token = userAccounts.getIbkr().getToken();
            queryId = userAccounts.getIbkr().getQueryId();
            if (token == null || queryId == null) {
                throw new RuntimeException("Ibkr info invalid");
            }
        } else {
            throw new RuntimeException("Ibkr info invalid");
        }

        try {
            token = cryptoUtil.decrypt(token);
            queryId = cryptoUtil.decrypt(queryId);
        } catch (Exception e) {
            throw new RuntimeException("unable to decrypt token");
        }

        String referenceCode = ibkrClient.generateReport(queryId, token);
        IbkrReportDTO response = ibkrMapper.toIbkrReportDTO(ibkrClient.getReport(token, referenceCode));

        // cache to redis
        cacheService.cacheInfo(userId, cachekey, response);

        return response;
    }

    public void updateIbkrInfo(IbkrInfoDTO request) {
        String currentuserId = SecurityUtil.getCurrentUserId();

        // hash the info
        try {
            request.setQueryId(cryptoUtil.encrypt(request.getQueryId()));
            request.setToken(cryptoUtil.encrypt(request.getToken()));
        } catch (Exception e) {
            throw new RuntimeException("unable to encrypt tokens");
        }

        UserAccounts userAccounts = userAccountsRepository.findByUserId(currentuserId)
                .orElse(UserAccounts.builder()
                        .userId(currentuserId).build());
        userAccounts.setIbkr(request);
        System.out.println(userAccounts);

        userAccountsRepository.save(userAccounts);
    }
}
