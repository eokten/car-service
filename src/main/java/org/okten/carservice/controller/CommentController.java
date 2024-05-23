package org.okten.carservice.controller;

import lombok.RequiredArgsConstructor;
import org.okten.carservice.api.controller.CommentsApi;
import org.okten.carservice.api.model.CommentDto;
import org.okten.carservice.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController implements CommentsApi {

    private final CommentService commentService;

    @Override
    public ResponseEntity<List<CommentDto>> getComments() {
        return ResponseEntity.ok(commentService.getComments());
    }
}
