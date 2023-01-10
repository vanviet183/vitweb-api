package com.vitweb.vitwebapi.application.services.impl;

import com.nimbusds.jose.shaded.json.JSONObject;
import com.vitweb.vitwebapi.adapter.web.v1.transfer.response.RequestResponse;
import com.vitweb.vitwebapi.application.constants.CommonConstant;
import com.vitweb.vitwebapi.application.constants.DevMessageConstant;
import com.vitweb.vitwebapi.application.constants.UserMessageConstant;
import com.vitweb.vitwebapi.application.inputs.post.CreatePostInput;
import com.vitweb.vitwebapi.application.inputs.post.UpdatePostInput;
import com.vitweb.vitwebapi.application.repositories.IPostRepository;
import com.vitweb.vitwebapi.application.repositories.IUserRepository;
import com.vitweb.vitwebapi.application.services.IPostService;
import com.vitweb.vitwebapi.application.utils.CloudinaryUtil;
import com.vitweb.vitwebapi.application.utils.SecurityUtil;
import com.vitweb.vitwebapi.configs.exceptions.VsException;
import com.vitweb.vitwebapi.domain.entities.Media;
import com.vitweb.vitwebapi.domain.entities.Post;
import com.vitweb.vitwebapi.domain.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements IPostService {
  private final IPostRepository postRepository;
  private final IUserRepository userRepository;
  private final ModelMapper modelMapper;

  public PostServiceImpl(IPostRepository postRepository, IUserRepository userRepository, ModelMapper modelMapper) {
    this.postRepository = postRepository;
    this.userRepository = userRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  public List<Post> getAll() {
    return postRepository.findAll();
  }

  @Override
  public Post getPostById(Long id) {
    Optional<Post> oldPost = postRepository.findById(id);
    checkPostExists(oldPost, id);

    return oldPost.get();
  }

  @Override
  public Post createPost(CreatePostInput createPostInput) {
    Optional<User> oldUser = userRepository.findByEmail(SecurityUtil.getCurrentUserLogin());
    checkUserExists(oldUser);

    Post newPost = modelMapper.map(createPostInput, Post.class);
    newPost.setUser(oldUser.get());

    List<Media> medias = new ArrayList<>();
    List<MultipartFile> files = createPostInput.getFiles();
    files.forEach(item -> {
      Media media = new Media();
      media.setPath(CloudinaryUtil.getUrlFromFile(item));
      media.setPost(newPost);
      medias.add(media);
    });
    newPost.setMedias(medias);

    return postRepository.save(newPost);
  }

  @Override
  public Post updatePost(UpdatePostInput updatePostInput) {
    Optional<User> oldUser = userRepository.findByEmail(SecurityUtil.getCurrentUserLogin());
    checkUserExists(oldUser);

    Optional<Post> oldPost = postRepository.findById(updatePostInput.getIdPost());
    checkPostExists(oldPost, updatePostInput.getIdPost());

    if(oldUser.get() != oldPost.get().getUser()) {
      throw new VsException("Bạn không phải người đă bài viết");
    }
    modelMapper.map(updatePostInput, oldPost.get());

    return postRepository.save(oldPost.get());
  }

  @Override
  public RequestResponse deleteById(Long id) {
    Optional<Post> oldPost = postRepository.findById(id);
    checkPostExists(oldPost, id);

    postRepository.deleteById(id);
    return new RequestResponse(CommonConstant.TRUE, CommonConstant.EMPTY_STRING);
  }

  private void checkPostExists(Optional<Post> Post, Long id) {
    if(Post.isEmpty()) {
      throw new VsException(UserMessageConstant.ERR_EXCEPTION_GENERAL,
          String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "category", id));
    }
  }

  private void checkUserExists(Optional<User> user) {
    if(user.isEmpty()) {
      throw new VsException(UserMessageConstant.User.ERR_NOT_FOUND_BY_ID);
    }
  }
}
