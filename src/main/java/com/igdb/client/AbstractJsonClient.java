package com.igdb.client;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.igdb.json.GeneralJsonModel;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Ldabul TODO: refactor to common somehere
 */
@Data
@Slf4j
public abstract class AbstractJsonClient {
    private RestTemplate restTemplate;
    private ObjectMapper objectMapper;

    protected HttpHeaders createHeaders(Map<String, String> params) {
        if (MapUtils.isEmpty(params)) {
            return null;
        }

        HttpHeaders headers = new HttpHeaders();
        for (String key : params.keySet()) {
            headers.set(key,
                        params.get(key));
        }
        return headers;
    }

    protected <T> HttpEntity<T> createRequest() {
        return createRequest(createHeaders());
    }

    protected <T> HttpEntity<T> createRequest(T req) {
        return createRequest(createHeaders(),
                             req);
    }

    protected <T> HttpEntity<T> createRequest(HttpHeaders headers) {
        return createRequest(headers,
                             null);
    }

    protected <T> HttpEntity<T> createRequest(HttpHeaders headers, T req) {
        return new HttpEntity<>(req,
                                headers);
    }

    @SuppressWarnings("unchecked")
    protected Map<String, Object> convertGetParams(Object body) {
        if (body == null) {
            return null;
        }
        return objectMapper.convertValue(body,
                                         HashMap.class);
    }

    @SuppressWarnings("rawtypes")
    protected <T> T exchange(String url, HttpMethod method, HttpEntity<?> entity, Class<T> rspType, Object... params) {
        UriComponentsBuilder ucb = UriComponentsBuilder.fromHttpUrl(url);
        if (HttpMethod.GET == method && entity != null) {
            Map<String, Object> gp = convertGetParams(entity.getBody());
            if (MapUtils.isNotEmpty(gp)) {
                for (Entry<String, Object> entry : gp.entrySet()) {
                    log.info("PARAM {}={}",
                             entry.getKey(),
                             entry.getValue());
                    if (entry.getValue() == null) {
                        continue;
                    }

                    if (Collection.class.isAssignableFrom(entry.getValue()
                                    .getClass())) {
                        if (!((Collection) entry.getValue()).isEmpty()) {
                            ucb.queryParam(entry.getKey(),
                                           ((Collection) entry.getValue()).toArray());
                        }
                    }
                    else {
                        ucb.queryParam(entry.getKey(),
                                       entry.getValue());
                    }
                }
            }
        }
        log.info("URL={}",
                 ucb.build()
                                 .encode()
                                 .toUriString());

        if (isOffline()) {
            try {
                return rspType.newInstance();
            }
            catch (Exception e) {
                log.error("AbstractJsonClient.exchange: error mocking request",
                          e);
                return null;
            }
        }

        return restTemplate.exchange(ucb.build()
                        .encode()
                        .toUriString(),
                                     method,
                                     entity,
                                     rspType,
                                     params)
                        .getBody();
    }

    protected <T extends GeneralJsonModel> T post(String url, HttpEntity<?> entity, Class<T> rspType, Object... params) {
        return exchange(url,
                        HttpMethod.POST,
                        entity,
                        rspType,
                        params);
    }

    protected <T> T get(String url, HttpEntity<?> entity, Class<T> rspType, Object... params) {
        return exchange(url,
                        HttpMethod.GET,
                        entity,
                        rspType,
                        params);
    }

    @SuppressWarnings("unchecked")
    protected <T> List<T> getList(String url, HttpEntity<?> entity, Class<T> rspType, Object... params) {
        T[] res = (T[]) get(url,
                            entity,
                            Array.newInstance(rspType,
                                              0)
                                            .getClass(),
                            params);
        if (ArrayUtils.isEmpty(res)) {
            return Collections.emptyList();
        }
        return Arrays.asList(res);
    }

    protected abstract boolean isOffline();

    protected abstract HttpHeaders createHeaders();
}
