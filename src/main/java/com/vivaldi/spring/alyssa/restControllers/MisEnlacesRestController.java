package com.vivaldi.spring.alyssa.restControllers;

import com.vivaldi.spring.alyssa.data.MisEnlaces;
import com.vivaldi.spring.alyssa.services.ServiceMisEnlaces;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class MisEnlacesRestController {

    private final ServiceMisEnlaces serviceMisEnlaces;
    Map<String, Object> response = new HashMap<>();

    public MisEnlacesRestController(ServiceMisEnlaces serviceMisEnlaces) {
        this.serviceMisEnlaces = serviceMisEnlaces;
    }

    @PostMapping("/newlink")
    private ResponseEntity<?> AddNewLink(MisEnlaces misEnlaces){

        response.clear();

        serviceMisEnlaces.SaveLink(misEnlaces);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
