package com.task.demo;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.task.demo.controllers.RepoController;
import com.task.demo.dto.RepoResponseDto;
import com.task.demo.services.RepoService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class RepoControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private RepoService repoService;
  @InjectMocks
  private RepoController repoController;

  @Test
  void testGetRepos() throws Exception {
    String username = "test";
    List<RepoResponseDto> repoList = List.of(
        new RepoResponseDto("repo1", "test"),
        new RepoResponseDto("repo2", "test")
    );
    when(repoService.getRepos(username)).thenReturn(repoList);
    mockMvc.perform(get("/repos")
            .param("username", username)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)));
  }
}