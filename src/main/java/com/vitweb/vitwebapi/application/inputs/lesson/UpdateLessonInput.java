package com.vitweb.vitwebapi.application.inputs.lesson;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateLessonInput {

  private Long id;

  private String name;

  private String path;

  private String content;
}
