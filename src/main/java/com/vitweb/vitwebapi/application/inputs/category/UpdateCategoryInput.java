package com.vitweb.vitwebapi.application.inputs.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCategoryInput {

  private Long id;

  private String name;

  private String description;
}
