package com.vitweb.vitwebapi.application.services.impl;

import com.github.slugify.Slugify;
import com.nimbusds.jose.shaded.json.JSONObject;
import com.vitweb.vitwebapi.adapter.web.v1.transfer.response.RequestResponse;
import com.vitweb.vitwebapi.application.constants.CommonConstant;
import com.vitweb.vitwebapi.application.constants.DevMessageConstant;
import com.vitweb.vitwebapi.application.constants.UserMessageConstant;
import com.vitweb.vitwebapi.application.inputs.blog.CreateBlogInput;
import com.vitweb.vitwebapi.application.inputs.blog.UpdateBlogInput;
import com.vitweb.vitwebapi.application.repositories.IBlogRepository;
import com.vitweb.vitwebapi.application.repositories.ICategoryRepository;
import com.vitweb.vitwebapi.application.services.IBlogService;
import com.vitweb.vitwebapi.application.utils.CloudinaryUtil;
import com.vitweb.vitwebapi.configs.exceptions.VsException;
import com.vitweb.vitwebapi.domain.entities.Blog;
import com.vitweb.vitwebapi.domain.entities.Category;
import com.vitweb.vitwebapi.domain.entities.Course;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BlogServiceImpl implements IBlogService {
  private final IBlogRepository blogRepository;
  private final ICategoryRepository categoryRepository;
  private final ModelMapper modelMapper;

  public BlogServiceImpl(IBlogRepository blogRepository, ICategoryRepository categoryRepository, ModelMapper modelMapper) {
    this.blogRepository = blogRepository;
    this.categoryRepository = categoryRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  public List<Blog> getAll() {
    return blogRepository.findAll();
  }

  @Override
  public Blog getBlogById(Long id) {
    Optional<Blog> oldBlog = blogRepository.findById(id);
    checkBlogExists(oldBlog, id);

    return oldBlog.get();
  }

  @Override
  public Blog createBlog(CreateBlogInput createBlogInput) {
    Optional<Category> oldCategory = categoryRepository.findById(createBlogInput.getIdCategory());
    checkCategoryExists(oldCategory, createBlogInput.getIdCategory());

    Blog newBlog = modelMapper.map(createBlogInput, Blog.class);
    Slugify slugify = new Slugify();
    String result = slugify.slugify(createBlogInput.getSubject());
    newBlog.setSlug(result);

    JSONObject json = new JSONObject();
    List<MultipartFile> files = createBlogInput.getFiles();
    files.forEach(item -> {
      json.put("path", CloudinaryUtil.getUrlFromFile(item));
    });
    newBlog.setImages(json.toJSONString());

    return blogRepository.save(newBlog);
  }

  @Override
  public Blog updateBlog(UpdateBlogInput updateBlogInput) {
    Optional<Blog> oldBlog = blogRepository.findById(updateBlogInput.getId());
    checkBlogExists(oldBlog, updateBlogInput.getId());

    modelMapper.map(updateBlogInput, oldBlog.get());
    Slugify slugify = new Slugify();
    String result = slugify.slugify(updateBlogInput.getSubject());
    oldBlog.get().setSlug(result);

    return blogRepository.save(oldBlog.get());
  }

  @Override
  public RequestResponse deleteById(Long id) {
    Optional<Blog> oldBlog = blogRepository.findById(id);
    checkBlogExists(oldBlog, id);

    blogRepository.deleteById(id);
    return new RequestResponse(CommonConstant.TRUE, CommonConstant.EMPTY_STRING);
  }

  private void checkCategoryExists(Optional<Category> Category, Long id) {
    if(Category.isEmpty()) {
      throw new VsException(UserMessageConstant.Category.ERR_NOT_FOUND_BY_ID,
          String.format(DevMessageConstant.Category.ERR_NOT_FOUND_BY_ID, id),
          new String[]{id.toString()});
    }
  }

  private void checkBlogExists(Optional<Blog> Blog, Long id) {
    if(Blog.isEmpty()) {
      throw new VsException(UserMessageConstant.ERR_EXCEPTION_GENERAL,
          String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "category", id));
    }
  }
}
