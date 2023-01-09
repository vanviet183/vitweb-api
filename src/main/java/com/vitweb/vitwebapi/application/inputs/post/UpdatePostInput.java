package com.vitweb.vitwebapi.application.inputs.post;

import com.vitweb.vitwebapi.adapter.web.base.EStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePostInput {

  private Long idPost;

  private String content;

  private EStatus status;

}
