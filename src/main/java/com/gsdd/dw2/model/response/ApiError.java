package com.gsdd.dw2.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Builder
@Getter
@Jacksonized
public class ApiError {

  private String message;
  
}
