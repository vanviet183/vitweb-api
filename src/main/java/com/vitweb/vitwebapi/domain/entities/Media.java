package com.vitweb.vitwebapi.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vitweb.vitwebapi.adapter.web.base.EMedia;
import com.vitweb.vitwebapi.application.constants.TableNameConstant;
import com.vitweb.vitwebapi.domain.entities.base.AbstractAuditingEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = TableNameConstant.TBL_MEDIA)
public class Media extends AbstractAuditingEntity {

  private String path;

  @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
  @JsonIgnore
  private Blog blog;

  @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
  @JsonIgnore
  private Post post;

  @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
  @JsonIgnore
  private Lesson lesson;

  @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
  @JsonIgnore
  private ChatRoom chatRoom;
}
