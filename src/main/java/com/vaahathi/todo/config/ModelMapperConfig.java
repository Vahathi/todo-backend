package com.vaahathi.todo.config;

import com.vaahathi.todo.entity.Contact;
import com.vaahathi.todo.models.contact.ContactRequest;
import com.vaahathi.todo.models.contact.ContactResponse;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper;
    }
    @Autowired
    private ModelMapper modelMapper;

    @PostConstruct
    public void init() {
        modelMapper.createTypeMap(ContactRequest.class, Contact.class)
                .addMapping(ContactRequest::getPersonName, Contact::setPersonName)
                .addMapping(ContactRequest::getNickName, Contact::setNickName)
                .addMapping(ContactRequest::getPhoneNumber, Contact::setPhoneNumber);

        modelMapper.createTypeMap(Contact.class, ContactResponse.class)
                .addMapping(Contact::getPersonName, ContactResponse::setName)
                .addMapping(Contact::getNickName, ContactResponse::setNickName)
                .addMapping(Contact::getPhoneNumber, ContactResponse::setPhone);
    }

}
