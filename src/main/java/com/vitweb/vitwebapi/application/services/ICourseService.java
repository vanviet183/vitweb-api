package com.vitweb.vitwebapi.application.services;

import com.vitweb.vitwebapi.adapter.web.v1.transfer.response.RequestResponse;
import com.vitweb.vitwebapi.application.inputs.course.CreateCourseInput;
import com.vitweb.vitwebapi.application.inputs.course.UpdateCourseInput;
import com.vitweb.vitwebapi.domain.entities.Course;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ICourseService {
  List<Course> getAll();

  Course getCourseById(Long id);

  Course createCourse(CreateCourseInput createCourseInput);

  Course updateCourse(UpdateCourseInput updateCourseInput);

  RequestResponse deleteById(Long id);
}
