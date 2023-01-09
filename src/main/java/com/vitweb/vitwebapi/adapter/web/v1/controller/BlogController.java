package com.vitweb.vitwebapi.adapter.web.v1.controller;

import com.vitweb.vitwebapi.adapter.web.base.RestApiV1;
import com.vitweb.vitwebapi.adapter.web.base.VsResponseUtil;
import com.vitweb.vitwebapi.application.constants.UrlConstant;
import com.vitweb.vitwebapi.application.inputs.blog.CreateBlogInput;
import com.vitweb.vitwebapi.application.inputs.blog.UpdateBlogInput;
import com.vitweb.vitwebapi.application.services.IBlogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestApiV1
public class BlogController {

  private final IBlogService blogService;

  public BlogController(IBlogService blogService) {
    this.blogService = blogService;
  }

  @GetMapping(UrlConstant.Blog.LIST)
  public ResponseEntity<?> getAll() {
    return VsResponseUtil.ok(blogService.getAll());
  }

  @GetMapping(UrlConstant.Blog.GET)
  public ResponseEntity<?> getBlogById(@PathVariable("id") Long id) {
    return VsResponseUtil.ok(blogService.getBlogById(id));
  }

  @PostMapping(UrlConstant.Blog.CREATE)
  public ResponseEntity<?> createBlog(@RequestBody CreateBlogInput createBlogInput) {
    return VsResponseUtil.ok(blogService.createBlog(createBlogInput));
  }

  @PatchMapping(UrlConstant.Blog.UPDATE)
  public ResponseEntity<?> updateBlog(@RequestBody UpdateBlogInput updateBlogInput) {
    return VsResponseUtil.ok(blogService.updateBlog(updateBlogInput));
  }

  @DeleteMapping(UrlConstant.Blog.DELETE)
  public ResponseEntity<?> deleteBlog(@PathVariable("id") Long id) {
    return VsResponseUtil.ok(blogService.deleteById(id));
  }
}
