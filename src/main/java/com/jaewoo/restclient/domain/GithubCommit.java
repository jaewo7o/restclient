package com.jaewoo.restclient.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GithubCommit {
    private String url;
    private String sha;
}
