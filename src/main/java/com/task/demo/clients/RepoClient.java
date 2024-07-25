package com.task.demo.clients;

import com.task.demo.dto.RepoResponseDto;
import java.util.List;

public interface RepoClient {

  List<RepoResponseDto> getRepos(String username);

  List<RepoResponseDto> getBranches(List<RepoResponseDto> repos);
}
