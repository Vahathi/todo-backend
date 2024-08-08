package com.vaahathi.todo.config;

import com.vaahathi.todo.entity.Contact;
import com.vaahathi.todo.models.contact.ContactRequest;
import com.vaahathi.todo.models.contact.ContactResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
        @Bean
        public ModelMapper modelMapper() {
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
            return modelMapper;
        }
    }

