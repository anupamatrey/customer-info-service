package com.anupam.model.response;

import lombok.Data;
import lombok.Getter;

@Data
public class Response {
  private String customer_id;
  private String first_name;
  private String last_name;
  private String email;
  private String phone_number;
  Address address;
  private String membership_status;
  private String registration_date;
  private String last_purchase_date;
  private float total_purchases;
  Preferences preferences;
}
