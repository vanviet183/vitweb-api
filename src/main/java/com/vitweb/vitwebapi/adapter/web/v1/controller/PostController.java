package com.vitweb.vitwebapi.adapter.web.v1.controller;

import com.vitweb.vitwebapi.adapter.web.base.RestApiV1;
import com.vitweb.vitwebapi.adapter.web.base.VsResponseUtil;
import com.vitweb.vitwebapi.application.constants.UrlConstant;
import com.vitweb.vitwebapi.application.inputs.post.CreatePostInput;
import com.vitweb.vitwebapi.application.inputs.post.UpdatePostInput;
import com.vitweb.vitwebapi.application.services.IPostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestApiV1
public class PostController {

  private final IPostService postService;

  public PostController(IPostService postService) {
    this.postService = postService;
  }

  @GetMapping(UrlConstant.Post.LIST)
  public ResponseEntity<?> getAll() {
    return VsResponseUtil.ok(postService.getAll());
  }

  @GetMapping(UrlConstant.Post.GET)
  public ResponseEntity<?> getPostById(@PathVariable("id") Long id) {
    return VsResponseUtil.ok(postService.getPostById(id));
  }

  @PostMapping(UrlConstant.Post.CREATE)
  public ResponseEntity<?> createPost(@RequestBody CreatePostInput createPostInput) {
    return VsResponseUtil.ok(postService.createPost(createPostInput));
  }

  @PatchMapping(UrlConstant.Post.UPDATE)
  public ResponseEntity<?> updatePost(@RequestBody UpdatePostInput updatePostInput) {
    return VsResponseUtil.ok(postService.updatePost(updatePostInput));
  }

  @DeleteMapping(UrlConstant.Post.DELETE)
  public ResponseEntity<?> deletePost(@PathVariable("id") Long id) {
    return VsResponseUtil.ok(postService.deleteById(id));
  }
}
