package org.okten.carservice.service;

import lombok.RequiredArgsConstructor;
import org.okten.carservice.api.model.CommentDto;
import org.okten.carservice.dto.comment.MockApiCommentDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    @Qualifier("commentMockApiRestTemplate")
    private final RestTemplate restTemplate;

    @Qualifier("commentMockApiWebClient")
    private final WebClient webClient;

    public List<CommentDto> getComments() {
        return fetchCommentsWithWebClient();
    }

    public List<CommentDto> fetchCommentsWithWebClient() {
        List<MockApiCommentDto> comments = webClient
                .get()
                .uri("/comments")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<MockApiCommentDto>>() {
                })
                .block();

        return comments
                .stream()
                .map(mockApiCommentDto -> new CommentDto()
                        .id(mockApiCommentDto.getId())
                        .author(mockApiCommentDto.getAuthor())
                        .text(mockApiCommentDto.getText()))
                .toList();
    }

    private List<CommentDto> fetchCommentsWithRestTemplate() {
        ResponseEntity<List<MockApiCommentDto>> response = restTemplate.exchange(
                "/comments",
                HttpMethod.GET,
                new HttpEntity<>(Collections.emptyMap()),
                new ParameterizedTypeReference<List<MockApiCommentDto>>() {
                });

        if (response.getStatusCode().is2xxSuccessful()) {
            return response
                    .getBody()
                    .stream()
                    .map(MockApiCommentDto.class::cast)
                    .map(mockApiCommentDto -> new CommentDto()
                            .id(mockApiCommentDto.getId())
                            .author(mockApiCommentDto.getAuthor())
                            .text(mockApiCommentDto.getText()))
                    .toList();
        } else {
            return Collections.emptyList();
        }
    }
}
