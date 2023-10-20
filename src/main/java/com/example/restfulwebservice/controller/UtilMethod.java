package com.example.restfulwebservice.controller;

import com.example.restfulwebservice.domain.User;
import com.example.restfulwebservice.domain.UserV2;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.converter.json.MappingJacksonValue;

import java.util.List;

public class UtilMethod {
    // 함수로 만들어 사용
    public static <T> MappingJacksonValue getMappingJacksonValue(List<T> users, String jsonFilter, String... fields) {
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept(fields);

        FilterProvider filters = new SimpleFilterProvider().addFilter(jsonFilter,filter);

        MappingJacksonValue mapping = new MappingJacksonValue(users);
        mapping.setFilters(filters);
        return mapping;
    }
    public static MappingJacksonValue getMappingJacksonValueUser(User user, String jsonFilter, String... fields) {
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept(fields);

        FilterProvider filters = new SimpleFilterProvider().addFilter(jsonFilter,filter);

        MappingJacksonValue mapping = new MappingJacksonValue(user);
        mapping.setFilters(filters);
        return mapping;
    }
}
