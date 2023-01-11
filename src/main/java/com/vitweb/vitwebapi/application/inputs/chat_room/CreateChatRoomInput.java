package com.vitweb.vitwebapi.application.inputs.chat_room;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateChatRoomInput {

  private Long idUserFirst;

  private Long idUserSecond;
}
