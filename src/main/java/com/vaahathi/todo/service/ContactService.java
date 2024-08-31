package com.vaahathi.todo.service;

import com.vaahathi.todo.entity.Contact;
import com.vaahathi.todo.repository.ContactRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {
    @Autowired
    ContactRepository contactRepository;

    @Autowired
    private ModelMapper modelMapper;

        public List<Contact> searchContactsByName(String personName) {
            return contactRepository.findByNameFuzzy(personName);
        }
    }




