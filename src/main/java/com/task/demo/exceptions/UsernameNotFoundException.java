package com.task.demo.exceptions;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatusCode;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UsernameNotFoundException extends RuntimeException {

  HttpStatusCode status;

  public UsernameNotFoundException(HttpStatusCode status, String message) {
    super(message);
    this.status = status;
  }
}
