package com.task.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.task.demo.clients.RepoClient;
import com.task.demo.dto.BranchResponseDto;
import com.task.demo.dto.RepoResponseDto;
import com.task.demo.services.RepoServiceImpl;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RepoServiceImplTest {

  @Mock
  private RepoClient repoClient;

  @InjectMocks
  private RepoServiceImpl repoService;

  @Test
  void testGetRepos() {
    String username = "test";
    List<RepoResponseDto> repoList = List.of(
        new RepoResponseDto("repo1", "test"),
        new RepoResponseDto("repo2", "test")
    );

    List<RepoResponseDto> repoListWithBranches = List.of(
        new RepoResponseDto("repo1", "test",
            List.of(BranchResponseDto.builder().name("branch1").build())),
        new RepoResponseDto("repo2", "test",
            List.of(BranchResponseDto.builder().name("branch2").build()))
    );

    when(repoClient.getRepos(username)).thenReturn(repoList);
    when(repoClient.getBranches(repoList)).thenReturn(repoListWithBranches);

    List<RepoResponseDto> result = repoService.getRepos(username);

    assertEquals(2, result.size());
    assertEquals("repo1", result.get(0).getName());
    assertEquals("repo2", result.get(1).getName());
    assertEquals("branch1", result.get(0).getBranches().get(0).getName());
    assertEquals("branch2", result.get(1).getBranches().get(0).getName());
  }
}
