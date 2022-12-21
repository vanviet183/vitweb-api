package com.vitweb.vitwebapi.application.inputs.user;

import com.vitweb.vitwebapi.application.inputs.Input;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserInput implements Input {

  private String name;

  private String email;

  private String password;

}
