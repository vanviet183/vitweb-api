package com.vitweb.vitwebapi.application.inputs.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChangeAvatarInput {

  private Long id;

  private MultipartFile file;
}
