package com.vitweb.vitwebapi.adapter.web.v1.controller;

import com.vitweb.vitwebapi.adapter.web.base.RestApiV1;
import com.vitweb.vitwebapi.adapter.web.base.VsResponseUtil;
import com.vitweb.vitwebapi.application.constants.UrlConstant;
import com.vitweb.vitwebapi.application.inputs.comment.*;
import com.vitweb.vitwebapi.application.services.ICommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestApiV1
public class CommentController {

  private final ICommentService commentService;

  public CommentController(ICommentService commentService) {
    this.commentService = commentService;
  }

  @GetMapping(UrlConstant.Comment.LIST)
  public ResponseEntity<?> getAll() {
    return VsResponseUtil.ok(commentService.getAll());
  }

  @GetMapping(UrlConstant.Comment.GET)
  public ResponseEntity<?> getCommentById(@PathVariable("id") Long id) {
    return VsResponseUtil.ok(commentService.getCommentById(id));
  }

  // Create comment child
  @PostMapping(UrlConstant.Comment.CREATE)
  public ResponseEntity<?> createComment(@ModelAttribute CreateCommentInput createCommentInput) {
    return VsResponseUtil.ok(commentService.createComment(createCommentInput));
  }

  @PostMapping(UrlConstant.Comment.CREATE_COMMENT_BLOG)
  public ResponseEntity<?> createCommentBlog(@ModelAttribute CreateCommentBlogInput createCommentBlogInput) {
    return VsResponseUtil.ok(commentService.createCommentBlog(createCommentBlogInput));
  }

  @PostMapping(UrlConstant.Comment.CREATE_COMMENT_POST)
  public ResponseEntity<?> createCommentPost(@ModelAttribute CreateCommentPostInput createCommentPostInput) {
    return VsResponseUtil.ok(commentService.createCommentPost(createCommentPostInput));
  }

  @PostMapping(UrlConstant.Comment.CREATE_COMMENT_LESSON)
  public ResponseEntity<?> createCommentLesson(@ModelAttribute CreateCommentLessonInput createCommentLessonInput) {
    return VsResponseUtil.ok(commentService.createCommentLesson(createCommentLessonInput));
  }

  @PatchMapping(UrlConstant.Comment.UPDATE)
  public ResponseEntity<?> updateComment(@ModelAttribute UpdateCommentInput updateCommentInput) {
    return VsResponseUtil.ok(commentService.updateComment(updateCommentInput));
  }

  @DeleteMapping(UrlConstant.Comment.DELETE)
  public ResponseEntity<?> deleteComment(@PathVariable("id") Long id) {
    return VsResponseUtil.ok(commentService.deleteById(id));
  }
}
