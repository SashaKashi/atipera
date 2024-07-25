package com.task.demo.clients;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.demo.configs.GithubProperties;
import com.task.demo.dto.BranchResponseDto;
import com.task.demo.dto.RepoResponseDto;
import com.task.demo.exceptions.JsonException;
import com.task.demo.exceptions.UsernameNotFoundException;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@AllArgsConstructor
public class GithubClientImpl implements RepoClient {

  private final ObjectMapper objectMapper = new ObjectMapper();
  private final RestTemplate restTemplate;
  private final GithubProperties githubProperties;

  @Override
  public List<RepoResponseDto> getRepos(String username) {
    ResponseEntity<String> response;
    try {
      response = restTemplate.exchange(
          githubProperties.getGetReposUrl().replace("username", username), HttpMethod.GET,
          new HttpEntity<>(new HttpHeaders()), String.class);
    } catch (HttpClientErrorException e) {
      throw new UsernameNotFoundException(e.getStatusCode(), e.getMessage());
    }

    List<RepoResponseDto> repos = new ArrayList<>();
    JsonNode root;
    try {
      root = objectMapper.readTree(response.getBody());
    } catch (JsonProcessingException e) {
      log.error("Error in getRepos method with message " + e.getMessage());
      throw new JsonException(HttpStatusCode.valueOf(422), e.getMessage());
    }
    for (JsonNode node : root) {
      String fork = node.get("fork").asText();
      if (fork.equals("false")) {
        String name = node.get("name").asText();
        String login = node.get("owner").get("login").asText();
        repos.add(new RepoResponseDto(name, login));
      }
    }
    return repos;
  }

  @Override
  public List<RepoResponseDto> getBranches(List<RepoResponseDto> repos) {
    List<RepoResponseDto> result = new ArrayList<>();
    repos.forEach(repo -> {
      ResponseEntity<String> response = restTemplate.exchange(
          githubProperties.getGetBranchesUrl().replace("username", repo.getLogin())
              .replace("reponame", repo.getName()), HttpMethod.GET,
          new HttpEntity<>(new HttpHeaders()), String.class);
      List<BranchResponseDto> branches = new ArrayList<>();

      JsonNode root;
      try {
        root = objectMapper.readTree(response.getBody());
      } catch (JsonProcessingException e) {
        log.error("Error in getBranches method with message " + e.getMessage());
        throw new JsonException(HttpStatusCode.valueOf(422), e.getMessage());
      }
      for (JsonNode node : root) {
        String name = node.get("name").asText();
        String sha = node.get("commit").get("sha").asText();
        branches.add(new BranchResponseDto(name, sha));
      }
      RepoResponseDto newRepo = new RepoResponseDto(repo.getName(), repo.getLogin(), branches);
      result.add(newRepo);
    });
    return result;
  }
}
