package com.task.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RepoResponseDto {

  String name;
  String login;
  List<BranchResponseDto> branches;

  public RepoResponseDto(String name, String login) {
    this.name = name;
    this.login = login;
  }

  public RepoResponseDto(String name, String login, List<BranchResponseDto> branches) {
    this.name = name;
    this.login = login;
    this.branches = branches;
  }

  public String getName() {
    return name;
  }

  public String getLogin() {
    return login;
  }

  public List<BranchResponseDto> getBranches() {
    return branches;
  }

  public void setBranches(List<BranchResponseDto> branches) {
    this.branches = branches;
  }
}
