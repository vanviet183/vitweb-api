package com.vitweb.vitwebapi.application.services;

import com.vitweb.vitwebapi.adapter.web.v1.transfer.response.RequestResponse;
import com.vitweb.vitwebapi.application.inputs.lesson.CreateLessonInput;
import com.vitweb.vitwebapi.application.inputs.lesson.UpdateLessonInput;
import com.vitweb.vitwebapi.domain.entities.Lesson;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ILessonService {
  List<Lesson> getAll();

  Lesson getLessonById(Long id);

  Lesson createLesson(CreateLessonInput createLessonInput);

  Lesson updateLesson(UpdateLessonInput updateLessonInput);

  RequestResponse deleteById(Long id);
}
