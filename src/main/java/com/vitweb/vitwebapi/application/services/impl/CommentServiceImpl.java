package com.vitweb.vitwebapi.application.services.impl;

import com.vitweb.vitwebapi.adapter.web.v1.transfer.response.RequestResponse;
import com.vitweb.vitwebapi.application.constants.CommonConstant;
import com.vitweb.vitwebapi.application.constants.DevMessageConstant;
import com.vitweb.vitwebapi.application.constants.UserMessageConstant;
import com.vitweb.vitwebapi.application.inputs.comment.*;
import com.vitweb.vitwebapi.application.repositories.*;
import com.vitweb.vitwebapi.application.services.ICommentService;
import com.vitweb.vitwebapi.application.utils.CloudinaryUtil;
import com.vitweb.vitwebapi.application.utils.SecurityUtil;
import com.vitweb.vitwebapi.configs.exceptions.NotFoundException;
import com.vitweb.vitwebapi.configs.exceptions.VsException;
import com.vitweb.vitwebapi.domain.entities.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements ICommentService {
  private final ICommentRepository commentRepository;
  private final IBlogRepository blogRepository;
  private final IPostRepository postRepository;
  private final ILessonRepository lessonRepository;
  private final ModelMapper modelMapper;
  private final IUserRepository userRepository;

  public CommentServiceImpl(ICommentRepository commentRepository, IBlogRepository blogRepository, IPostRepository postRepository, ILessonRepository lessonRepository, ModelMapper modelMapper,
                            IUserRepository userRepository) {
    this.commentRepository = commentRepository;
    this.blogRepository = blogRepository;
    this.postRepository = postRepository;
    this.lessonRepository = lessonRepository;
    this.modelMapper = modelMapper;
    this.userRepository = userRepository;
  }

  @Override
  public List<Comment> getAll() {
    return commentRepository.findAll();
  }

  @Override
  public Comment getCommentById(Long id) {
    Optional<Comment> oldComment = commentRepository.findById(id);
    checkCommentExists(oldComment, id);

    return oldComment.get();
  }

  // Create comment child
  @Override
  public Comment createComment(CreateCommentInput createCommentInput) {
    Optional<Comment> oldComment = commentRepository.findById(createCommentInput.getIdParent());
    checkCommentExists(oldComment, createCommentInput.getIdParent());
    Optional<User> oldUser = userRepository.findById(SecurityUtil.getCurrentUserLogin());
    checkUserExists(oldUser, SecurityUtil.getCurrentUserLogin());

    Comment newComment = modelMapper.map(createCommentInput, Comment.class);
    newComment.setParentComment(oldComment.get());
    newComment.setUser(oldUser.get());
    setImageComment(newComment, createCommentInput.getFile());

    if(oldComment.get().getBlog() != null) {
      newComment.setBlog(oldComment.get().getBlog());
    } else if(oldComment.get().getPost() != null) {
      newComment.setPost(oldComment.get().getPost());
    } else if(oldComment.get().getLesson() != null) {
      newComment.setLesson(oldComment.get().getLesson());
    }

    return commentRepository.save(newComment);
  }

  @Override
  public Comment createCommentBlog(CreateCommentBlogInput createCommentBlogInput) {
    Optional<Blog> oldBlog = blogRepository.findById(createCommentBlogInput.getIdBlog());
    checkBlogExists(oldBlog, createCommentBlogInput.getIdBlog());
    Optional<User> oldUser = userRepository.findById(SecurityUtil.getCurrentUserLogin());
    checkUserExists(oldUser, SecurityUtil.getCurrentUserLogin());

    Comment newComment = modelMapper.map(createCommentBlogInput, Comment.class);
    newComment.setUser(oldUser.get());
    newComment.setBlog(oldBlog.get());
    setImageComment(newComment, createCommentBlogInput.getFile());
    return commentRepository.save(newComment);
  }

  @Override
  public Comment createCommentPost(CreateCommentPostInput createCommentPostInput) {
    Optional<Post> oldPost = postRepository.findById(createCommentPostInput.getIdPost());
    checkPostExists(oldPost, createCommentPostInput.getIdPost());
    Optional<User> oldUser = userRepository.findById(SecurityUtil.getCurrentUserLogin());
    checkUserExists(oldUser, SecurityUtil.getCurrentUserLogin());

    Comment newComment = modelMapper.map(createCommentPostInput, Comment.class);
    newComment.setUser(oldUser.get());
    newComment.setPost(oldPost.get());
    setImageComment(newComment, createCommentPostInput.getFile());
    return commentRepository.save(newComment);
  }

  @Override
  public Comment createCommentLesson(CreateCommentLessonInput createCommentLessonInput) {
    Optional<Lesson> oldLesson = lessonRepository.findById(createCommentLessonInput.getIdLesson());
    checkLessonExists(oldLesson, createCommentLessonInput.getIdLesson());
    Optional<User> oldUser = userRepository.findById(SecurityUtil.getCurrentUserLogin());
    checkUserExists(oldUser, SecurityUtil.getCurrentUserLogin());

    Comment newComment = modelMapper.map(createCommentLessonInput, Comment.class);
    newComment.setUser(oldUser.get());
    newComment.setLesson(oldLesson.get());
    setImageComment(newComment, createCommentLessonInput.getFile());
    return commentRepository.save(newComment);
  }

  @Override
  public Comment updateComment(UpdateCommentInput updateCommentInput) {
    Optional<Comment> oldComment = commentRepository.findById(updateCommentInput.getId());
    checkCommentExists(oldComment, updateCommentInput.getId());

    modelMapper.map(updateCommentInput, oldComment.get());
    return commentRepository.save(oldComment.get());
  }

  @Override
  public RequestResponse deleteById(Long id) {
    Optional<Comment> oldComment = commentRepository.findById(id);
    checkCommentExists(oldComment, id);

    commentRepository.deleteById(id);
    return new RequestResponse(CommonConstant.TRUE, CommonConstant.EMPTY_STRING);
  }

  private void checkCommentExists(Optional<Comment> comment, Long id) {
    if(comment.isEmpty()) {
      throw new VsException(UserMessageConstant.ERR_EXCEPTION_GENERAL,
          String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "comment", id));
    }
  }

  private void checkUserExists(Optional<User> user, String id) {
    if(user.isEmpty()) {
      throw new NotFoundException(UserMessageConstant.ERR_EXCEPTION_GENERAL,
          String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "user", id));
    }
  }

  private void checkBlogExists(Optional<Blog> Blog, Long id) {
    if(Blog.isEmpty()) {
      throw new VsException(UserMessageConstant.ERR_EXCEPTION_GENERAL,
          String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "blog", id));
    }
  }

  private void checkPostExists(Optional<Post> Post, Long id) {
    if(Post.isEmpty()) {
      throw new VsException(UserMessageConstant.ERR_EXCEPTION_GENERAL,
          String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "category", id));
    }
  }

  private void checkLessonExists(Optional<Lesson> Lesson, Long id) {
    if(Lesson.isEmpty()) {
      throw new VsException(UserMessageConstant.ERR_EXCEPTION_GENERAL,
          String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "category", id));
    }
  }

  public void setImageComment(Comment comment, MultipartFile multipartFile) {
    if(comment.getImageUrl() != null) {
      CloudinaryUtil.removeFileFromUrl(comment.getImageUrl());
    }
    comment.setImageUrl(CloudinaryUtil.getUrlFromFile(multipartFile));
  }
}
