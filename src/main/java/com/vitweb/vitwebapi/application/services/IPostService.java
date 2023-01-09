package com.vitweb.vitwebapi.application.services;

import com.vitweb.vitwebapi.adapter.web.v1.transfer.response.RequestResponse;
import com.vitweb.vitwebapi.application.inputs.post.CreatePostInput;
import com.vitweb.vitwebapi.application.inputs.post.UpdatePostInput;
import com.vitweb.vitwebapi.domain.entities.Post;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IPostService {
  List<Post> getAll();

  Post getPostById(Long id);

  Post createPost(CreatePostInput createPostInput);

  Post updatePost(UpdatePostInput updatePostInput);

  RequestResponse deleteById(Long id);
}
