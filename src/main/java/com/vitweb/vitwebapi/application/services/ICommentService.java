package com.vitweb.vitwebapi.application.services;

import com.vitweb.vitwebapi.adapter.web.v1.transfer.response.RequestResponse;
import com.vitweb.vitwebapi.application.inputs.comment.*;
import com.vitweb.vitwebapi.domain.entities.Comment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ICommentService {
  List<Comment> getAll();

  Comment getCommentById(Long id);

  Comment createComment(CreateCommentInput createCommentInput);

  Comment createCommentBlog(CreateCommentBlogInput createCommentBlogInput);

  Comment createCommentPost(CreateCommentPostInput createCommentPostInput);

  Comment createCommentLesson(CreateCommentLessonInput createCommentLessonInput);

  Comment updateComment(UpdateCommentInput updateCommentInput);

  RequestResponse deleteById(Long id);
}
