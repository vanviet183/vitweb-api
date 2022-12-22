package com.vitweb.vitwebapi.application.services.impl;

import com.github.slugify.Slugify;
import com.vitweb.vitwebapi.adapter.web.v1.transfer.response.RequestResponse;
import com.vitweb.vitwebapi.application.constants.CommonConstant;
import com.vitweb.vitwebapi.application.constants.DevMessageConstant;
import com.vitweb.vitwebapi.application.constants.UserMessageConstant;
import com.vitweb.vitwebapi.application.inputs.course.CreateCourseInput;
import com.vitweb.vitwebapi.application.inputs.course.UpdateCourseInput;
import com.vitweb.vitwebapi.application.repositories.ICourseRepository;
import com.vitweb.vitwebapi.application.services.ICourseService;
import com.vitweb.vitwebapi.application.utils.CloudinaryUtil;
import com.vitweb.vitwebapi.configs.exceptions.VsException;
import com.vitweb.vitwebapi.domain.entities.Course;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements ICourseService {
  private final ICourseRepository courseRepository;
  private final ModelMapper modelMapper;

  public CourseServiceImpl(ICourseRepository courseRepository, ModelMapper modelMapper) {
    this.courseRepository = courseRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  public List<Course> getAll() {
    return courseRepository.findAll();
  }

  @Override
  public Course getCourseById(Long id) {
    Optional<Course> oldCourse = courseRepository.findById(id);
    checkCourseExists(oldCourse, id);

    return oldCourse.get();
  }

  @Override
  public Course createCourse(CreateCourseInput createCourseInput) {
    Course newCourse = modelMapper.map(createCourseInput, Course.class);
    setImageCourse(newCourse, createCourseInput.getFile());

    Slugify slugify = new Slugify();
    String slug = slugify.slugify(createCourseInput.getName());
    newCourse.setSlug(slug);

    return courseRepository.save(newCourse);
  }

  @Override
  public Course updateCourse(UpdateCourseInput updateCourseInput) {
    Optional<Course> oldCourse = courseRepository.findById(updateCourseInput.getId());
    checkCourseExists(oldCourse, updateCourseInput.getId());

    modelMapper.map(updateCourseInput, oldCourse.get());
    setImageCourse(oldCourse.get(), updateCourseInput.getFile());

    Slugify slugify = new Slugify();
    String slug = slugify.slugify(updateCourseInput.getName());
    oldCourse.get().setSlug(slug);

    return courseRepository.save(oldCourse.get());
  }

  @Override
  public RequestResponse deleteById(Long id) {
    Optional<Course> oldCourse = courseRepository.findById(id);
    checkCourseExists(oldCourse, id);

    oldCourse.get().setDeleteFlag(true);
    courseRepository.save(oldCourse.get());
    return new RequestResponse(CommonConstant.TRUE, CommonConstant.EMPTY_STRING);
  }

  private void checkCourseExists(Optional<Course> Course, Long id) {
    if(Course.isEmpty()) {
      throw new VsException(UserMessageConstant.ERR_EXCEPTION_GENERAL,
          String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "course", id));
    }
  }

  public void setImageCourse(Course course, MultipartFile multipartFile) {
    if(course.getAvatarCourse() != null) {
      CloudinaryUtil.removeFileFromUrl(course.getAvatarCourse());
    }
    course.setAvatarCourse(CloudinaryUtil.getUrlFromFile(multipartFile));
  }
}
