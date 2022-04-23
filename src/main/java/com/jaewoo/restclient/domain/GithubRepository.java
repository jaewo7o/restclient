package com.jaewoo.restclient.domain;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GithubRepository {
    private String id;
    private String name;
    private String url;
}
