package com.example.myapi.controllers;

import com.example.myapi.contract.definition.InternalAccessApi;
import com.example.myapi.model.Person;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InternalAccessController implements InternalAccessApi {

    @Override
    @SecurityRequirement(name = "M2MClientCredentials", scopes = {"customer:manage"})
    public ResponseEntity<Person> getPersonByNameInternal(String name) throws Exception {
        return null;
    }
}
