package com.task.demo.services;

import com.task.demo.clients.RepoClient;
import com.task.demo.dto.RepoResponseDto;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RepoServiceImpl implements RepoService {

  public final RepoClient repoClient;

  @Override
  public List<RepoResponseDto> getRepos(String username) {
    List<RepoResponseDto> repos = repoClient.getRepos(username);
    return repoClient.getBranches(repos);
  }
}
