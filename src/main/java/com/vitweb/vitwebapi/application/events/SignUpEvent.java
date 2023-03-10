package com.vitweb.vitwebapi.application.events;

import com.vitweb.vitwebapi.domain.entities.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class SignUpEvent extends ApplicationEvent {

  private User user;
  private String applicationUrl;

  public SignUpEvent(User user, String applicationUrl) {
    super(user);
    this.user = user;
    this.applicationUrl = applicationUrl;
  }
}
