package com.igdb.config;

import java.util.Collections;

import javax.annotation.Resource;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.igdb.client.IgdbClient;
import com.igdb.client.IgdbClientImpl;

/**
 * @author Ldabul
 * @version 0.1 - 06.03.2015
 */
@Configuration
@ComponentScan("com.igdb")
@PropertySource(value = "classpath:igdb.properties", ignoreResourceNotFound = true)
public class IgdbConfig {
    private static final int DEFAULT_TIMEOUT = 30 * 1000;

    @Resource
    private Environment env;

    @Bean
    public IgdbClient igdbClient() {
        IgdbClientImpl ic = new IgdbClientImpl();
        ic.setRestTemplate(igdbRestTemplate());
        ic.setObjectMapper(igdbObjectMapper());
        return ic;
    }

    /**
     * Creates rest template for TGDB client
     * 
     * @return
     */
    @Bean
    public RestTemplate igdbRestTemplate() {
        RestTemplate rt = new RestTemplate();
        rt.setRequestFactory(getRequestFactory());
        rt.setMessageConverters(Collections.singletonList(igdbConverter()));
        return rt;
    }

    /**
     * Creates request factory for rest template
     * 
     * @return
     */
    private ClientHttpRequestFactory getRequestFactory() {
        RequestConfig.Builder b = RequestConfig.custom();
        b.setConnectTimeout(env.getProperty("igdb.connectTimeout",
                                            Integer.class,
                                            DEFAULT_TIMEOUT));
        b.setSocketTimeout(env.getProperty("igdb.connectTimeout",
                                           Integer.class,
                                           DEFAULT_TIMEOUT));
        b.setConnectionRequestTimeout(env.getProperty("igdb.readTimeout",
                                                      Integer.class,
                                                      DEFAULT_TIMEOUT));

        HttpClientBuilder hcb = HttpClientBuilder.create();
        hcb.setDefaultRequestConfig(b.build());

        return new HttpComponentsClientHttpRequestFactory(hcb.build());
    }

    @Bean
    public MappingJackson2HttpMessageConverter igdbConverter() {
        MappingJackson2HttpMessageConverter mc = new MappingJackson2HttpMessageConverter();
        mc.setObjectMapper(igdbObjectMapper());
        return mc;
    }

    @Bean
    public ObjectMapper igdbObjectMapper() {
        ObjectMapper om = new ObjectMapper();
        om.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                     false);
        om.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);

        om.setVisibility(PropertyAccessor.ALL,
                         Visibility.NONE);
        om.setVisibility(PropertyAccessor.GETTER,
                         Visibility.PUBLIC_ONLY);
        om.setVisibility(PropertyAccessor.IS_GETTER,
                         Visibility.PUBLIC_ONLY);
        om.setVisibility(PropertyAccessor.FIELD,
                         Visibility.ANY);
        //om.setPropertyNamingStrategy(new PascalCaseStrategy());

        //om.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        //om.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sssZ"));
        //om.registerModule(new ConstantModule());

        return om;
    }
}
