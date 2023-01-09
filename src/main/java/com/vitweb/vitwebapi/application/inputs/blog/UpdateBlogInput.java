package com.vitweb.vitwebapi.application.inputs.blog;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBlogInput {

  private Long id;

  private String subject;

  private String content;
}
