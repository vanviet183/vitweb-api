package com.vitweb.vitwebapi.application.inputs.course;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCourseInput {

  private Long idCategory;

  private String name;

  private String description;

  private MultipartFile file;

  private Double price;
}
