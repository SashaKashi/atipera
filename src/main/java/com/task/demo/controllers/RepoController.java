package com.task.demo.controllers;

import com.task.demo.dto.RepoResponseDto;
import com.task.demo.services.RepoService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/repos")
public class RepoController {

  private final RepoService repoService;

  @GetMapping
  public List<RepoResponseDto> getRepos(@RequestParam(name = "username") String username) {
    return repoService.getRepos(username);
  }
}
