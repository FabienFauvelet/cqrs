package org.acme;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.io.InputStream;

public class TestUtils {
    public static <T> T getValue(String path, Class<T> targetClass) throws IOException, ClassNotFoundException {
        InputStream jsonStream = Class.forName(Thread.currentThread().getStackTrace()[2].getClassName()).getResourceAsStream(path);
        return new ObjectMapper().registerModule(new JavaTimeModule()).readValue(jsonStream, targetClass);
    }
}
