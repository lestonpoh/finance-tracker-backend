package com.lestonpoh.financeapp_backend.service;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lestonpoh.financeapp_backend.utility.CryptoUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CacheService {

    private final RedisTemplate<String, String> redisTemplate;
    private final CryptoUtil cryptoUtil;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final long CACHE_TTL = 60 * 12; // half a day

    public void cacheInfo(String userId, String key, Object data) {
        try {
            String json = objectMapper.writeValueAsString(data);
            String encrypted = cryptoUtil.encrypt(json);

            redisTemplate.opsForValue()
                    .set(cacheKey(userId, key), encrypted, CACHE_TTL, TimeUnit.MINUTES);
            log.info("saved info to redis");
        } catch (Exception e) {
            log.warn("unable to set info to: " + cacheKey(userId, key), e);
        }

    }

    public <T> T getCachedInfo(String userId, String key, TypeReference<T> typeRef) {
        try {
            String encrypted = redisTemplate.opsForValue().get(cacheKey(userId, key));
            if (encrypted == null) {
                return null;
            }
            String decryptedJson = cryptoUtil.decrypt(encrypted);
            return objectMapper.readValue(decryptedJson, typeRef);
        } catch (Exception e) {
            log.warn("unable to get cached info from: " + cacheKey(userId, key), e);
            return null;
        }

    }

    private String cacheKey(String userId, String key) {
        return "user:" + userId + ":" + key;
    }

}