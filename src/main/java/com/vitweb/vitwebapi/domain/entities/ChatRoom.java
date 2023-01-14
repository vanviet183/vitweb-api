package com.vitweb.vitwebapi.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vitweb.vitwebapi.adapter.web.base.ERoom;
import com.vitweb.vitwebapi.application.constants.TableNameConstant;
import com.vitweb.vitwebapi.domain.entities.base.AbstractAuditingEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = TableNameConstant.TBL_ROOM)
public class ChatRoom extends AbstractAuditingEntity {

  @Nationalized
  private String nickname;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "chatRoom")
  @JsonIgnore
  private List<Message> messages;

  @JoinTable(name = "chat_room_users",
      joinColumns = @JoinColumn(name = "chat_room_id"),
      inverseJoinColumns = @JoinColumn(name = "user_id"),
      foreignKey = @ForeignKey(name = "FK_CHATROOM_USER"))
  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JsonIgnore
  private List<User> users;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "chatRoom")
  @JsonIgnore
  private List<Media> medias;
}
