package com.vitweb.vitwebapi.adapter.web.v1.controller;

import com.vitweb.vitwebapi.adapter.web.base.RestApiV1;
import com.vitweb.vitwebapi.adapter.web.base.VsResponseUtil;
import com.vitweb.vitwebapi.application.constants.UrlConstant;
import com.vitweb.vitwebapi.application.inputs.lesson.CreateLessonInput;
import com.vitweb.vitwebapi.application.inputs.lesson.UpdateLessonInput;
import com.vitweb.vitwebapi.application.services.ILessonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestApiV1
public class LessonController {

  private final ILessonService lessonService;

  public LessonController(ILessonService lessonService) {
    this.lessonService = lessonService;
  }

  @GetMapping(UrlConstant.Lesson.LIST)
  public ResponseEntity<?> getAll() {
    return VsResponseUtil.ok(lessonService.getAll());
  }

  @GetMapping(UrlConstant.Lesson.GET)
  public ResponseEntity<?> getLessonById(@PathVariable("id") Long id) {
    return VsResponseUtil.ok(lessonService.getLessonById(id));
  }

  @PostMapping(UrlConstant.Lesson.CREATE)
  public ResponseEntity<?> createLesson(@ModelAttribute CreateLessonInput createLessonInput) {
    return VsResponseUtil.ok(lessonService.createLesson(createLessonInput));
  }

  @PatchMapping(UrlConstant.Lesson.UPDATE)
  public ResponseEntity<?> updateLesson(@ModelAttribute UpdateLessonInput updateLessonInput) {
    return VsResponseUtil.ok(lessonService.updateLesson(updateLessonInput));
  }

  @DeleteMapping(UrlConstant.Lesson.DELETE)
  public ResponseEntity<?> deleteLesson(@PathVariable("id") Long id) {
    return VsResponseUtil.ok(lessonService.deleteById(id));
  }
}
