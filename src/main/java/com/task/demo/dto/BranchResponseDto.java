package com.task.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
public class BranchResponseDto {

  String name;
  String lastCommit;

  public String getName() {
    return name;
  }

  public String getLastCommit() {
    return lastCommit;
  }
}
