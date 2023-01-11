package com.vitweb.vitwebapi.adapter.web.v1.controller;

import com.vitweb.vitwebapi.adapter.web.base.RestApiV1;
import com.vitweb.vitwebapi.adapter.web.base.VsResponseUtil;
import com.vitweb.vitwebapi.application.constants.UrlConstant;
import com.vitweb.vitwebapi.application.inputs.chat_room.CreateChatRoomInput;
import com.vitweb.vitwebapi.application.services.IChatRoomService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestApiV1
public class ChatRoomController {

  private final IChatRoomService chatRoomService;

  public ChatRoomController(IChatRoomService chatRoomService) {
    this.chatRoomService = chatRoomService;
  }

  @ApiOperation("API get all chat room")
  @GetMapping(UrlConstant.ChatRoom.LIST)
  public ResponseEntity<?> getRoomById() {
    return VsResponseUtil.ok(chatRoomService.getAll());
  }

  @ApiOperation("API get chat room by id")
  @GetMapping(UrlConstant.ChatRoom.GET)
  public ResponseEntity<?> getRoomById(@PathVariable("id") Long id) {
    return VsResponseUtil.ok(chatRoomService.getChatRoomById(id));
  }

  @ApiOperation("API create chat room")
  @PostMapping(UrlConstant.ChatRoom.CREATE)
  public ResponseEntity<?> createChatRoom(@Valid @RequestBody CreateChatRoomInput createChatRoomInput) {
    return VsResponseUtil.ok(chatRoomService.createChatRoom(createChatRoomInput));
  }

  @ApiOperation("API delete chat room")
  @PostMapping(UrlConstant.ChatRoom.DELETE)
  public ResponseEntity<?> deleteChatRoom(@PathVariable("id") Long id) {
    return VsResponseUtil.ok(chatRoomService.deleteChatRoom(id));
  }
}
