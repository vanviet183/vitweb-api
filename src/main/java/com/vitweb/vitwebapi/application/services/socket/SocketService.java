package com.vitweb.vitwebapi.application.services.socket;

import com.corundumstudio.socketio.SocketIOClient;
import com.vitweb.vitwebapi.adapter.web.base.EMessage;
import com.vitweb.vitwebapi.domain.entities.Message;
import org.springframework.stereotype.Service;

@Service
public class SocketService {

  public void sendMessage(String room, String eventName, SocketIOClient senderClient, String message) {
    for (
        SocketIOClient client : senderClient.getNamespace().getRoomOperations(room).getClients()) {
      if (!client.getSessionId().equals(senderClient.getSessionId())) {
        client.sendEvent(eventName,
            new Message(message, false, EMessage.SERVER, null, null));
      }
    }
  }
}
