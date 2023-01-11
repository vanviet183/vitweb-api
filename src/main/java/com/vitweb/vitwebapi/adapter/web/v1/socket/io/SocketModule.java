package com.vitweb.vitwebapi.adapter.web.v1.socket.io;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.vitweb.vitwebapi.application.services.IUserService;
import com.vitweb.vitwebapi.domain.entities.User;
import org.springframework.stereotype.Component;

@Component
public class SocketModule {

  private final IUserService userService;

  public SocketModule(SocketIOServer server, IUserService userService) {
    this.userService = userService;
    server.addConnectListener(onConnected());
    server.addDisconnectListener(onDisconnected());
  }

  private ConnectListener onConnected() {
    return socketIOClient -> {
      var params = socketIOClient.getHandshakeData().getUrlParams();
      String user = String.join("", params.get("user"));
      User account = userService.getUserById(Long.valueOf(user));
      System.out.println(account.getName() + " connected");
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
