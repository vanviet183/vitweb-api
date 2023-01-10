package com.vitweb.vitwebapi.domain.entities;

import com.vitweb.vitwebapi.adapter.web.base.EMessage;
import com.vitweb.vitwebapi.application.constants.TableNameConstant;
import com.vitweb.vitwebapi.domain.entities.base.AbstractAuditingEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = TableNameConstant.TBL_MESSAGE)
public class Message extends AbstractAuditingEntity {

  @Nationalized
  private String content;

  private Boolean isRead = false;

  private EMessage type;

  @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
  private ChatRoom chatRoom;

  @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
  private User sender;
}
