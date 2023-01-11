package com.vitweb.vitwebapi.application.services.impl;

import com.vitweb.vitwebapi.adapter.web.v1.transfer.response.RequestResponse;
import com.vitweb.vitwebapi.application.constants.CommonConstant;
import com.vitweb.vitwebapi.application.constants.DevMessageConstant;
import com.vitweb.vitwebapi.application.constants.UserMessageConstant;
import com.vitweb.vitwebapi.application.inputs.chat_room.CreateChatRoomInput;
import com.vitweb.vitwebapi.application.repositories.IChatRoomRepository;
import com.vitweb.vitwebapi.application.repositories.IUserRepository;
import com.vitweb.vitwebapi.application.services.IChatRoomService;
import com.vitweb.vitwebapi.configs.exceptions.VsException;
import com.vitweb.vitwebapi.domain.entities.ChatRoom;
import com.vitweb.vitwebapi.domain.entities.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ChatRoomServiceImpl implements IChatRoomService {

  private final IChatRoomRepository chatRoomRepository;
  private final IUserRepository userRepository;

  public ChatRoomServiceImpl(IChatRoomRepository chatRoomRepository, IUserRepository userRepository) {
    this.chatRoomRepository = chatRoomRepository;
    this.userRepository = userRepository;
  }

  @Override
  public List<ChatRoom> getAll() {
    return chatRoomRepository.findAll();
  }

  @Override
  public ChatRoom getChatRoomById(Long id) {
    Optional<ChatRoom> oldChatRoom = chatRoomRepository.findById(id);
    checkChatRoomExists(oldChatRoom, id);

    return oldChatRoom.get();
  }

  @Override
  public ChatRoom createChatRoom(CreateChatRoomInput createChatRoomInput) {
    Optional<User> user1 = userRepository.findById(createChatRoomInput.getIdUserFirst());
    checkUserExists(user1);
    Optional<User> user2 = userRepository.findById(createChatRoomInput.getIdUserSecond());
    checkUserExists(user2);

    List<User> users = new ArrayList<>();
    users.add(user1.get());
    users.add(user2.get());
    ChatRoom chatRoom = new ChatRoom();
    chatRoom.setUsers(users);

    return chatRoomRepository.save(chatRoom);
  }

  @Override
  public RequestResponse deleteChatRoom(Long id) {
    Optional<ChatRoom> oldChatRoom = chatRoomRepository.findById(id);
    checkChatRoomExists(oldChatRoom, id);

    oldChatRoom.get().setDeleteFlag(true);
    chatRoomRepository.save(oldChatRoom.get());
    return new RequestResponse(CommonConstant.TRUE, CommonConstant.EMPTY_STRING);
  }

  private void checkUserExists(Optional<User> user) {
    if(user.isEmpty()) {
      throw new VsException(UserMessageConstant.User.ERR_NOT_FOUND_BY_ID);
    }
  }

  private void checkChatRoomExists(Optional<ChatRoom> chatRoom, Long id) {
    if(chatRoom.isEmpty()) {
      throw new VsException(UserMessageConstant.Category.ERR_NOT_FOUND_BY_ID,
          String.format(DevMessageConstant.Category.ERR_NOT_FOUND_BY_ID, id),
          new String[]{id.toString()});
    }
  }
}
