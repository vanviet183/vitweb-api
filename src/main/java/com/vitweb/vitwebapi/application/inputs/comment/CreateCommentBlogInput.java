package com.vitweb.vitwebapi.application.inputs.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCommentBlogInput {

  private Long idBlog;

  private String content;

  private MultipartFile file;
}
