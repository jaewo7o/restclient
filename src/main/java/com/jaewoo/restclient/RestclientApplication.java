package com.jaewoo.restclient;

import com.jaewoo.restclient.domain.GithubCommit;
import com.jaewoo.restclient.domain.GithubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StopWatch;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@RequiredArgsConstructor
@SpringBootApplication
public class RestclientApplication {

    private final RestTemplateBuilder restTemplateBuilder;
    private final WebClient.Builder webClientBuilder;

    public static void main(String[] args) {
        SpringApplication.run(RestclientApplication.class, args);
    }

    @Bean
    public ApplicationRunner applicationRunner() {
        return args -> {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();

//			RestTemplate restTemplate = restTemplateBuilder.build();
//			GithubRepository[] result = restTemplate.getForObject("https://api.github.com/users/jaewo7o/repos", GithubRepository[].class);
//			Arrays.stream(result)
//					.forEach(x -> System.out.println(x.getUrl()));
//
//			GithubCommit[] commits = restTemplate.getForObject("https://api.github.com/repos/jaewo7o/srs/commits", GithubCommit[].class);
//			Arrays.stream(commits)
//					.forEach(x -> System.out.println(x.getSha()));


            WebClient webClient = webClientBuilder.baseUrl("https://api.github.com").build();

//            Mono<GithubRepository[]> monoRepositories = webClient.get()
//                    .uri("/users/jaewo7o/repos")
//                    .retrieve()
//                    .bodyToMono(GithubRepository[].class);
//
//            Mono<GithubCommit[]> monoCommits = webClient.get()
//                    .uri("/repos/jaewo7o/srs/commits")
//                    .retrieve()
//                    .bodyToMono(GithubCommit[].class);
//
//            monoRepositories.doOnSuccess(r -> Arrays.stream(r).forEach(x -> System.out.println(x.getUrl()))).subscribe();
//
//            monoCommits.doOnSuccess(r -> Arrays.stream(r).forEach(x -> System.out.println(x.getSha()))).subscribe();

            Flux<GithubRepository> fluxRepositories = webClient.get()
                    .uri("/users/jaewo7o/repos")
                    .retrieve()
                    .bodyToFlux(GithubRepository.class);

            Flux<GithubCommit> fluxCommits = webClient.get()
                    .uri("/repos/jaewo7o/srs/commits")
                    .retrieve()
                    .bodyToFlux(GithubCommit.class);

            fluxRepositories.doOnNext(x -> {
                System.out.println(x.getUrl());
            }).subscribe();

            fluxCommits.doOnNext(x -> {
                System.out.println(x.getSha());
            }).subscribe();

            stopWatch.stop();
            System.out.println(stopWatch.prettyPrint());
        };
    }
}
