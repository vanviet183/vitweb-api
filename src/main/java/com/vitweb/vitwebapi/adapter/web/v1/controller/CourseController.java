package com.vitweb.vitwebapi.adapter.web.v1.controller;

import com.vitweb.vitwebapi.adapter.web.base.RestApiV1;
import com.vitweb.vitwebapi.adapter.web.base.VsResponseUtil;
import com.vitweb.vitwebapi.adapter.web.v1.transfer.response.ResponseHeader;
import com.vitweb.vitwebapi.application.constants.UrlConstant;
import com.vitweb.vitwebapi.application.inputs.course.CreateCourseInput;
import com.vitweb.vitwebapi.application.inputs.course.UpdateCourseInput;
import com.vitweb.vitwebapi.application.services.ICourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestApiV1
public class CourseController {

  private final ICourseService courseService;
  private final ResponseHeader responseHeader;

  public CourseController(ICourseService courseService, ResponseHeader responseHeader) {
    this.courseService = courseService;
    this.responseHeader = responseHeader;
  }

  @GetMapping(UrlConstant.Course.LIST)
  public ResponseEntity<?> getAll() {
    return VsResponseUtil.ok(this.responseHeader.getHeader(), courseService.getAll());
  }

  @GetMapping(UrlConstant.Course.GET)
  public ResponseEntity<?> getCourseById(@PathVariable("id") Long id) {
    return VsResponseUtil.ok(this.responseHeader.getHeader(), courseService.getCourseById(id));
  }

  @PostMapping(UrlConstant.Course.CREATE)
  public ResponseEntity<?> createCourse(@ModelAttribute CreateCourseInput createCourseInput) {
    return VsResponseUtil.ok(courseService.createCourse(createCourseInput));
  }

  @PatchMapping(UrlConstant.Course.UPDATE)
  public ResponseEntity<?> updateCourse(@Valid @ModelAttribute UpdateCourseInput updateCourseInput) {
    return VsResponseUtil.ok(courseService.updateCourse(updateCourseInput));
  }

  @DeleteMapping(UrlConstant.Course.DELETE)
  public ResponseEntity<?> deleteCourse(@PathVariable("id") Long id) {
    return VsResponseUtil.ok(courseService.deleteById(id));
  }
}
