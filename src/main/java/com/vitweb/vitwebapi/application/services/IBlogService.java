package com.vitweb.vitwebapi.application.services;

import com.vitweb.vitwebapi.adapter.web.v1.transfer.response.RequestResponse;
import com.vitweb.vitwebapi.application.inputs.blog.CreateBlogInput;
import com.vitweb.vitwebapi.application.inputs.blog.UpdateBlogInput;
import com.vitweb.vitwebapi.domain.entities.Blog;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IBlogService {
  List<Blog> getAll();

  Blog getBlogById(Long id);

  Blog createBlog(CreateBlogInput createBlogInput);

  Blog updateBlog(UpdateBlogInput updateBlogInput);

  RequestResponse deleteById(Long id);
}
