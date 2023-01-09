package com.vitweb.vitwebapi.application.services.impl;

import com.github.slugify.Slugify;
import com.vitweb.vitwebapi.adapter.web.v1.transfer.response.RequestResponse;
import com.vitweb.vitwebapi.application.constants.CommonConstant;
import com.vitweb.vitwebapi.application.constants.DevMessageConstant;
import com.vitweb.vitwebapi.application.constants.UserMessageConstant;
import com.vitweb.vitwebapi.application.inputs.lesson.CreateLessonInput;
import com.vitweb.vitwebapi.application.inputs.lesson.UpdateLessonInput;
import com.vitweb.vitwebapi.application.repositories.ICourseRepository;
import com.vitweb.vitwebapi.application.repositories.ILessonRepository;
import com.vitweb.vitwebapi.application.services.ILessonService;
import com.vitweb.vitwebapi.configs.exceptions.VsException;
import com.vitweb.vitwebapi.domain.entities.Course;
import com.vitweb.vitwebapi.domain.entities.Lesson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LessonServiceImpl implements ILessonService {
  private final ILessonRepository lessonRepository;
  private final ICourseRepository courseRepository;
  private final ModelMapper modelMapper;

  public LessonServiceImpl(ILessonRepository lessonRepository, ICourseRepository courseRepository, ModelMapper modelMapper) {
    this.lessonRepository = lessonRepository;
    this.courseRepository = courseRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  public List<Lesson> getAll() {
    return lessonRepository.findAll();
  }

  @Override
  public Lesson getLessonById(Long id) {
    Optional<Lesson> oldLesson = lessonRepository.findById(id);
    checkLessonExists(oldLesson, id);

    return oldLesson.get();
  }

  @Override
  public Lesson createLesson(CreateLessonInput createLessonInput) {
    Optional<Course> oldCourse = courseRepository.findById(createLessonInput.getIdCourse());
    checkCourseExists(oldCourse, createLessonInput.getIdCourse());

    Lesson newLesson = modelMapper.map(createLessonInput, Lesson.class);
    newLesson.setCourse(oldCourse.get());
    Slugify slugify = new Slugify();
    String result = slugify.slugify(createLessonInput.getName());
    newLesson.setSlug(result);
    return lessonRepository.save(newLesson);
  }

  @Override
  public Lesson updateLesson(UpdateLessonInput updateLessonInput) {
    Optional<Lesson> oldLesson = lessonRepository.findById(updateLessonInput.getId());
    checkLessonExists(oldLesson, updateLessonInput.getId());

    modelMapper.map(updateLessonInput, oldLesson.get());
    Slugify slugify = new Slugify();
    String result = slugify.slugify(updateLessonInput.getName());
    oldLesson.get().setSlug(result);
    return lessonRepository.save(oldLesson.get());
  }

  @Override
  public RequestResponse deleteById(Long id) {
    Optional<Lesson> oldLesson = lessonRepository.findById(id);
    checkLessonExists(oldLesson, id);

    oldLesson.get().setDeleteFlag(true);
    lessonRepository.save(oldLesson.get());
    return new RequestResponse(CommonConstant.TRUE, CommonConstant.EMPTY_STRING);
  }

  private void checkLessonExists(Optional<Lesson> Lesson, Long id) {
    if(Lesson.isEmpty()) {
      throw new VsException(UserMessageConstant.ERR_EXCEPTION_GENERAL,
          String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "category", id));
    }
  }

  private void checkCourseExists(Optional<Course> Course, Long id) {
    if(Course.isEmpty()) {
      throw new VsException(UserMessageConstant.ERR_EXCEPTION_GENERAL,
          String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "course", id));
    }
  }
}
