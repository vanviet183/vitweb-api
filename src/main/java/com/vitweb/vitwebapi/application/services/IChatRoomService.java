package com.vitweb.vitwebapi.application.services;

import com.vitweb.vitwebapi.adapter.web.v1.transfer.response.RequestResponse;
import com.vitweb.vitwebapi.application.inputs.chat_room.CreateChatRoomInput;
import com.vitweb.vitwebapi.domain.entities.ChatRoom;

import java.util.List;

public interface IChatRoomService {

  public List<ChatRoom> getAll();
  public ChatRoom getChatRoomById(Long id);
  public ChatRoom createChatRoom(CreateChatRoomInput createChatRoomInput);
  public RequestResponse deleteChatRoom(Long id);
}
