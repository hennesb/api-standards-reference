package com.example.myapi.controllers;

import com.example.myapi.model.Person;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class IndexController implements com.example.myapi.contract.definition.PersonApi {
    @Override
    public ResponseEntity<List<Person>> getAllCustomerProfiles() throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<Person> getPersonByName(String name) {
        return null;
    }
}
