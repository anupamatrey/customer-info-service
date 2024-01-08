package com.anupam.controller;

import com.anupam.model.response.Response;
import com.anupam.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {
    private final static Logger LOG = LoggerFactory.getLogger(CustomerController.class);
    @Autowired
    CustomerService customerService;

    @GetMapping("/customer/{id}")
    public ResponseEntity<Response> customerInfo(@PathVariable final String id){
        LOG.info("====================== {} ",id);


        Response response = null;
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }
}
