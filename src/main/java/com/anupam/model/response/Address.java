package com.anupam.model.response;

import lombok.Data;
import lombok.Getter;

@Data
public class Address {
    private String street;
    private String city;
    private String state;
    private String zip_code;
}
