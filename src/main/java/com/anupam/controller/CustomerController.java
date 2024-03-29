package com.anupam.controller;

import com.anupam.model.Greeting;
import com.anupam.model.response.Response;
import com.anupam.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class CustomerController {
    private final static Logger LOG = LoggerFactory.getLogger(CustomerController.class);
    @Autowired
    CustomerService customerService;


    private static final String template = "Hello, %s Anupam! GM";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/customer/{id}")
    public ResponseEntity<Response> customerInfo(@PathVariable final String id){
        LOG.info("Calling CustomerController with customer Id {} ",id);
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }
    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));

        /*
              - name: Deploy Amazon ECS task definition
        uses: aws-actions/amazon-ecs-deploy-task-definition@v1
        env:
          SECRET_1: $SECRET_1
          SECRET_2: $SECRET_2
        with:
          task-definition: ${{ steps.task-def.outputs.task-definition }}
          service: customer-information-service1
          cluster: CustomerServiceCluster
          wait-for-service-stability: true
         */
    }
}
