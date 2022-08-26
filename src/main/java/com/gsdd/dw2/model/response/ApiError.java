package com.gsdd.dw2.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@AllArgsConstructor
@Getter
@Jacksonized
public class ApiError {

  private String message;
  
}
