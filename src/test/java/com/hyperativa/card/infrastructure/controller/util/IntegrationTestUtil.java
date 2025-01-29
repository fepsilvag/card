package com.hyperativa.card.infrastructure.controller.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;

public class IntegrationTestUtil {

    public IntegrationTestUtil() {
        throw new IllegalStateException("Utility class");
    }

    private static volatile ObjectMapper mapper = null;

    public static synchronized ObjectMapper getMapper() {
        if (mapper == null) {
            synchronized (IntegrationTestUtil.class) {
                if (mapper == null) {
                    mapper = new ObjectMapper()
                            .registerModule(new JavaTimeModule())
                            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
                }
            }
        }

        return mapper;
    }

    public static String convertObjectToJson(Object object) throws IOException {
        return getMapper().writeValueAsString(object);
    }

}