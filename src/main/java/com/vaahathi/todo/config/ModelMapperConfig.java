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

    private static final Logger logger = LoggerFactory.getLogger(ModelMapperConfig.class);

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        try {
            modelMapper.createTypeMap(ContactRequest.class, Contact.class)
                    .addMapping(ContactRequest::getPersonName, Contact::setPersonName)
                    .addMapping(ContactRequest::getNickName, Contact::setNickName)
                    .addMapping(ContactRequest::getPhoneNumber, Contact::setPhoneNumber);

            modelMapper.createTypeMap(Contact.class, ContactResponse.class)
                    .addMapping(Contact::getPersonName, ContactResponse::setName)
                    .addMapping(Contact::getNickName, ContactResponse::setNickName)
                    .addMapping(Contact::getPhoneNumber, ContactResponse::setPhone);
        } catch (Exception e) {
            logger.error("Error configuring ModelMapper", e);
            throw e;
        }

        return modelMapper;
    }
}
