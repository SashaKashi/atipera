package com.task.demo.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "github")
public class GithubProperties {

  String getReposUrl;
  String getBranchesUrl;
}