package com.vitweb.vitwebapi.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vitweb.vitwebapi.adapter.web.base.EStatus;
import com.vitweb.vitwebapi.application.constants.TableNameConstant;
import com.vitweb.vitwebapi.domain.entities.base.AbstractAuditingEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = TableNameConstant.TBL_POST)
public class Post extends AbstractAuditingEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  private String content;

  @Enumerated(EnumType.STRING)
  private EStatus status;

  private String images;

  private String videos;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JsonIgnore
  private List<Comment> comments;

  @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
  private User user;
}
