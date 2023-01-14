package com.vitweb.vitwebapi.adapter.web.v1.socket.io;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.vitweb.vitwebapi.application.services.IUserService;
import com.vitweb.vitwebapi.application.services.socket.SocketService;
import com.vitweb.vitwebapi.domain.entities.Message;
import com.vitweb.vitwebapi.domain.entities.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SocketModule {

  private final IUserService userService;
  private final SocketService socketService;

  public SocketModule(SocketIOServer server, IUserService userService, SocketService socketService) {
    this.userService = userService;
    this.socketService = socketService;
    server.addConnectListener(onConnected());
    server.addDisconnectListener(onDisconnected());
    server.addEventListener("send_message", Message.class, onChatReceived());
  }

  private ConnectListener onConnected() {
    return socketIOClient -> {
      var params = socketIOClient.getHandshakeData().getUrlParams();
      String user = String.join("", params.get("user"));
      User account = userService.getUserById(Long.valueOf(user));
      System.out.println(account.getName() + " connected");
    };
  }

//  private ConnectListener onConnected() {
//    return (client) -> {
//      String room = client.getHandshakeData().getSingleUrlParam("room");
//      client.joinRoom(room);
//      log.info("Socket ID[{}]  Connected to socket", client.getSessionId().toString());
//    };
//
//  }

  private DataListener<Message> onChatReceived() {
    return (senderClient, data, ackSender) -> {
      log.info(data.toString());
//      socketService.sendMessage(data.getRoom(),"get_message", senderClient, data.getMessage());
      socketService.sendMessage(data.getChatRoom().getId().toString(),"get_message", senderClient, data.getContent());
    };
  }

  private DisconnectListener onDisconnected() {
    return socketIOClient -> {
      var params = socketIOClient.getHandshakeData().getUrlParams();
      String user = String.join("", params.get("user"));
      User account = userService.getUserById(Long.valueOf(user));
      System.out.println(account.getName() + " disconnected");
    };
  }
}
