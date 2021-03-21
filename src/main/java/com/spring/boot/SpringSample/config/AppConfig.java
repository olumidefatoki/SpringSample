package com.spring.boot.SpringSample.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;


@Configuration
@Slf4j
@RequiredArgsConstructor
public class AppConfig {

    private final Environment env;

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true)
                .setAmbiguityIgnored(true).setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }

}
