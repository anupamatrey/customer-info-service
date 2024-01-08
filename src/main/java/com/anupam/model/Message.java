package com.anupam.model;

import com.anupam.model.response.Address;
import com.anupam.model.response.Preferences;
import lombok.Data;
import lombok.Getter;

@Data
public class Message {
    private String customer_id;
    private String phone_number;
    private String jsonData;
}
