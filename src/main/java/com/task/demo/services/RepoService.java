package com.task.demo.services;

import com.task.demo.dto.RepoResponseDto;
import java.util.List;

public interface RepoService {

  List<RepoResponseDto> getRepos(String username);
}
