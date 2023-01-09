package com.vitweb.vitwebapi.application.inputs.blog;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateBlogInput {

  private Long idCategory;

  private String subject;

  private String content;

  private List<MultipartFile> files;
}
