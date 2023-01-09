package com.vitweb.vitwebapi.application.inputs.post;

import com.vitweb.vitwebapi.adapter.web.base.EStatus;
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
public class CreatePostInput {

  private String content;

  private EStatus status;

  private List<MultipartFile> files;
}
