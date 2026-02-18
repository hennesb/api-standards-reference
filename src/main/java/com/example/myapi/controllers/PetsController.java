package com.example.myapi.controllers;

import com.example.myapi.contract.definition.PetsApi;
import com.example.myapi.model.Cat;
import com.example.myapi.model.PetRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PetsController implements PetsApi {


    @Override
    public ResponseEntity<Void> addPets(PetRequest petRequest) throws Exception {
        return null;
    }
}
